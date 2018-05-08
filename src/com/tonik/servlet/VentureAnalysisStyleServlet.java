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

import com.tonik.model.UserInfo;
import com.tonik.model.VentureAnalysisStyle;
import com.tonik.service.VentureAnalysisStyleService;

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
 * @web.servlet name="ventureAnalysisStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/VentureAnalysisStyleServlet"
 */
public class VentureAnalysisStyleServlet extends BaseServlet
{
    private VentureAnalysisStyleService ventureAnalysisStyleService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ventureAnalysisStyleService = (VentureAnalysisStyleService) ctx.getBean("VentureAnalysisStyleService");
    }
    public VentureAnalysisStyleService getVentureAnalysisStyleService()
    {
        return ventureAnalysisStyleService;
    }

    public void setVentureAnalysisStyleService(VentureAnalysisStyleService VentureAnalysisStyleService)
    {
        this.ventureAnalysisStyleService = VentureAnalysisStyleService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
               if("QueryList".equals(methodName))
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
                
                String strTotal = ventureAnalysisStyleService.VentureAnalysisStyleTotal(strQuery, strStraTime,
                        strEndTime);
                List<VentureAnalysisStyle> ls = ventureAnalysisStyleService.VentureAnalysisStylePaging(Integer.parseInt(strPageIndex), 
                        Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                String strJson = "";
                for (VentureAnalysisStyle item : ls)
                {

                    strJson += ventureAnalysisStyleService.getVentureAnalysisStyleInfo(item)+",";
                }
                if (strJson.length() > 0)
                {
                    strJson = strJson.substring(0, strJson.length() - 1);
                    out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                }
                else
                    out.print("false");
               }
               
               //编辑
               else if ("edit".equals(methodName))
               {
                   VentureAnalysisStyle ws = (VentureAnalysisStyle)ventureAnalysisStyleService.GetVentureAnalysisStyleById(Long.parseLong(request.getParameter("id")));
                   ws.setId(Long.parseLong(request.getParameter("id")));
                   ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                   ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                   ws.setCreateTime(new Date());
                   HttpSession session = request.getSession();
                   UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                   ws.setCreatePerson(ui.getId().toString());
                   ventureAnalysisStyleService.SaveVentureAnalysisStyle(ws);
                   String strVentureAnalysisStyle = ventureAnalysisStyleService.getVentureAnalysisStyleInfo(ws);
                   out.print(strVentureAnalysisStyle);
               }
               
               //新增
               else if ("add".equals(methodName))
               {
                   VentureAnalysisStyle ws = new VentureAnalysisStyle();
                   ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                   ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                   ws.setCreateTime(new Date());
                   HttpSession session = request.getSession();
                   UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                   ws.setCreatePerson(ui.getId().toString());
                   ventureAnalysisStyleService.SaveVentureAnalysisStyle(ws);
                   String strVentureAnalysisStyle = ventureAnalysisStyleService.getVentureAnalysisStyleInfo(ws);
                   out.print(strVentureAnalysisStyle);
               }
               
               //删除
               else if ("del".equals(methodName))
               {
                   ventureAnalysisStyleService.DelVentureAnalysisStyle(Long.parseLong(request.getParameter("id")));
                   out.print("true");
               }
               
               //编辑、查询页面初始化
               else if("init".equals(methodName))
               {
                   VentureAnalysisStyle ws = ventureAnalysisStyleService.GetVentureAnalysisStyleById(Long.parseLong(request.getParameter("id")));
                   String strVentureAnalysisStyle = ventureAnalysisStyleService.getVentureAnalysisStyleInfo(ws);
                   out.print(strVentureAnalysisStyle);
               }
               //code by yk!
               else if ("getVentureAnalysisStyle".equals(methodName))
               {
                   List<VentureAnalysisStyle> VentureAnalysisStyle = ventureAnalysisStyleService.GetVentureAnalysisStyles();
                   String strVentureAnalysisStyle = "";//, name;
                   for(VentureAnalysisStyle item: VentureAnalysisStyle){
                       strVentureAnalysisStyle+="{\"Id\":\""+item.getId() +"\", \"Name\":\""+item.getName() + "\"},";
                   }
                   if(strVentureAnalysisStyle.length() > 0){
                       strVentureAnalysisStyle = strVentureAnalysisStyle.substring(0, strVentureAnalysisStyle.length() - 1);
                       out.print("{\"total\":\"" + VentureAnalysisStyle.size() + "\",\"webList\":[" + strVentureAnalysisStyle + "]}");
                   }
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
