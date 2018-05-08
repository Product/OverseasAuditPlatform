package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Epidemic;
import com.tonik.model.ProductType;
import com.tonik.model.UserInfo;
import com.tonik.service.EpidemicService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author Ly
 * @web.servlet name="epidemicServlet"
 * @web.servlet-mapping url-pattern="/servlet/EpidemicServlet"
 */
public class EpidemicServlet extends BaseServlet
{
    private EpidemicService epidemicService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        epidemicService = (EpidemicService) ctx.getBean("EpidemicService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {   
                //查询
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = epidemicService.EpidemicTotal(strQuery, strStraTime, strEndTime);
                    List<Epidemic> ls = epidemicService.EpidemicPaging(Integer.parseInt(strPageIndex),Integer.parseInt(strPageCount), 
                            strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for (Epidemic item : ls)
                    {
                        strJson += epidemicService.getEpidemicInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"EpidemicList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                //编辑
                else if ("edit".equals(methodName))
                {
                    Epidemic ws = epidemicService.GetEpidemicById(Long.parseLong(request.getParameter("id")));        
                    ws.setTitle(URLDecoder.decode(request.getParameter("title"),"UTF-8"));
                    ws.setContent(URLDecoder.decode(request.getParameter("content"),"UTF-8"));
                    
                    Set<ProductType> productType = new HashSet<ProductType>();
                    for(String item : request.getParameter("productType").split("_"))
                    {
                        productType.add(epidemicService.getProductTypeById(Long.parseLong(item)));
                    }
                    ws.setProductTypes(productType);
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    epidemicService.SaveEpidemic(ws);
                    String strEpidemic = epidemicService.getEpidemicInfo(ws);
                    out.print(strEpidemic);
                }
                //新增
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Epidemic ws = new Epidemic();
                    ws.setTitle(URLDecoder.decode(request.getParameter("title"),"UTF-8"));
                    ws.setContent(URLDecoder.decode(request.getParameter("content"),"UTF-8"));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setCreateTime(new Date());
                    
                    Set<ProductType> productType = new HashSet<ProductType>();
                    for(String item : request.getParameter("productStyle").split("_"))
                    {
                        productType.add(epidemicService.getProductTypeById(Long.parseLong(item)));
                    }
                    ws.setProductTypes(productType);
                    ws.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    epidemicService.SaveEpidemic(ws);
                    String strEpidemic = epidemicService.getEpidemicInfo(ws);
                    out.print(strEpidemic);
                }
                //删除
                else if ("del".equalsIgnoreCase(methodName))
                {
                    epidemicService.DelEpidemic(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                //编辑、查询页面初始化
                else if ("init".equalsIgnoreCase(methodName))
                {
                    Epidemic Epidemic = epidemicService.GetEpidemicById(Long.parseLong(request.getParameter("id")));
                    //商品类型
                    String strJson="";
                    for(ProductType item : Epidemic.getProductTypes())
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                    }
                    //其余字段
                    String strEpidemic = epidemicService.getEpidemicInfo(Epidemic);
                    if (strEpidemic.length() > 0)
                    {
                        strEpidemic = strEpidemic.substring(0, strEpidemic.length() - 1);
                    }
                    out.print(strEpidemic + ", \"epsList\":[" + strJson + "]}");
                }
                //获取商品类别
                else if ("getProductStyle".equals(methodName))
                {
                    List<ProductType> PType = epidemicService.getProductType();
                    String strProductType = "";
                    for (ProductType item : PType)
                    {
                        strProductType += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strProductType != "")
                    {
                        strProductType = strProductType.substring(0, strProductType.length() - 1);
                    }

                    response.getWriter().write("{\"ProductStyle\":[" + strProductType + "]}");
                }
                //获取所有疫情类型
                else if("getEpidemic".equals(methodName))
                {
                    List<Object[]> epidemicList = epidemicService.getEpidemic();
                    String strJson="";
                    
                    for (Object[] epidemic : epidemicList)
                    {
                        strJson += "{\"Id\":\"" +epidemic[0]+"\",\"Name\":\"" + epidemic[1] +"\"},";
                    }
                    if(strJson.length()>0){
                        strJson = strJson.substring(0,strJson.length() -1);
                    }
                    
                    response.getWriter().write("{\"EpidemicList\":[" + strJson + "]}");
                }
            } catch (Exception e)
            {
                out.print("false");
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
