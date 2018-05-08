package com.tonik.servlet;

import java.io.IOException;
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
import com.tonik.model.WebsiteStyle;
import com.tonik.service.WebsiteStyleService;

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
 * @web.servlet name="websiteStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/WebsiteStyleServlet"
 */
public class WebsiteStyleServlet extends BaseServlet
{
    private WebsiteStyleService websiteStyleService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        websiteStyleService = (WebsiteStyleService) ctx.getBean("WebsiteStyleService");
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
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))/*获取网站类型列表*/
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
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

                    String strTotal = websiteStyleService.WebsiteStyleTotal(strQuery, strStraTime, strEndTime);
                    List<WebsiteStyle> ls = websiteStyleService.WebsiteStylePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    
                    String strJson = "";

                    for (WebsiteStyle item : ls)
                    {
                        strJson += websiteStyleService.getWebsiteStyleInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("edit".equals(methodName))//编辑网站类型
                {
                    WebsiteStyle ws = new WebsiteStyle();
                    ws.setId(Long.parseLong(request.getParameter("id")));
                    ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ws.setCreatePerson(ui.getId().toString());
                    websiteStyleService.SaveWebsiteStyle(ws);
                    String strws = websiteStyleService.getWebsiteStyleInfo(ws);
                    response.getWriter().write(strws);
                }
                else if ("add".equals(methodName))//新增网站类型
                {
                    WebsiteStyle ws = new WebsiteStyle();
                    
                  
                    
        
                    ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));

                   
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    ws.setCreatePerson(ui.getId().toString());

                    websiteStyleService.SaveWebsiteStyle(ws);
                    String strws = websiteStyleService.getWebsiteStyleInfo(ws);
                    response.getWriter().write(strws);
                    
                }
                else if ("del".equals(methodName))//删除网站类型
                {
                    websiteStyleService.DelWebsiteStyle(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if("init".equals(methodName))//初始化网站类型信息
                {
                    WebsiteStyle ws = websiteStyleService.GetWebsiteStyleById(Long.parseLong(request.getParameter("id")));
                    String strws = websiteStyleService.getWebsiteStyleInfo(ws);
                    response.getWriter().write(strws);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
