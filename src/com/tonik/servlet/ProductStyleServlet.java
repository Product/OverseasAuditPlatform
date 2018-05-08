package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.ProductStyle;
import com.tonik.model.UserInfo;
import com.tonik.service.ProductStyleService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="productStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductStyleServlet"
 */
public class ProductStyleServlet extends BaseServlet
{
    private ProductStyleService ProductStyleService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ProductStyleService = (ProductStyleService) ctx.getBean("ProductStyleService");
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
                if ("QueryList".equals(methodName))//获取商品类型列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))//设置起止时间
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = ProductStyleService.ProductStyleTotal(strQuery, strStraTime, strEndTime);
                    List<ProductStyle> ls = ProductStyleService.ProductStylePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (ProductStyle item : ls)
                    {
                        strJson += ProductStyleService.getProductStyleInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"proList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                else if ("edit".equals(methodName))//编辑
                {
                    ProductStyle ps = new ProductStyle();
                    ps.setId(Long.parseLong(request.getParameter("id")));
                    ps.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ps.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ps.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ps.setCreatePerson(ui.getId().toString());
                    
                    ProductStyleService.SaveProductStyle(ps);
                    
                    String strps = ProductStyleService.getProductStyleInfo(ps);
                    response.getWriter().write(strps);
                }
                else if ("add".equals(methodName))//新增
                {
                    ProductStyle ps = new ProductStyle();
                    ps.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ps.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ps.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ps.setCreatePerson(ui.getId().toString());
                    
                    ProductStyleService.SaveProductStyle(ps);
                    String strps = ProductStyleService.getProductStyleInfo(ps);
                    response.getWriter().write(strps);
                }
                else if ("del".equals(methodName))//删除
                {
                    ProductStyleService.DelProductStyle(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if ("init".equals(methodName))//初始化
                {
                    ProductStyle ps = ProductStyleService
                            .GetProductStyleById(Long.parseLong(request.getParameter("id")));
                    String strps = ProductStyleService.getProductStyleInfo(ps);
                    response.getWriter().write(strps);
                }
                else if ("allProductStyles".equals(methodName))//初始化
                {
                    String result = ProductStyleService.getAllProductStyles();
                    response.getWriter().write(result);
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
