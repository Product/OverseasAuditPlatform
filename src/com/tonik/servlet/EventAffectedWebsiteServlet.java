package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.EventAffectedWebsite;
import com.tonik.service.EventAffectedWebsiteService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响网站 servlet
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedWebsiteServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedWebsiteServlet"
 */
public class EventAffectedWebsiteServlet extends BaseServlet
{
    private EventAffectedWebsiteService eventAffectedWebsiteService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventAffectedWebsiteService = (EventAffectedWebsiteService) ctx.getBean("eventAffectedWebsiteService");
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
                if ("WebsiteQueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    Long eventId = Long.parseLong(request.getParameter("eventId"));
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                 

                    String strTotal = eventAffectedWebsiteService.getEventAffectedWebsiteTotal(eventId);
                    List<EventAffectedWebsite> ls = eventAffectedWebsiteService.getEventAffectedWebsitePaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery,eventId);
                    String strJson = "";

                    for (EventAffectedWebsite item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId()
                                + "\",\"EventId\":\"" + item.getEventId()
                                + "\",\"WebsiteId\":\"" + item.getWebsiteId()
                                + "\",\"WebsiteName\":\"" + item.getWebsiteName()                             
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventAffectedWebsiteList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("editWebsite".equals(methodName))//编辑事件影响网站
                {
                    EventAffectedWebsite e=eventAffectedWebsiteService.getEventAffectedWebsiteById(Long.parseLong(request.getParameter("id")));
                    Long websiteId = Long.parseLong(request.getParameter("websiteId"));
                    String websiteName=URLDecoder.decode(request.getParameter("websiteName"),"UTF-8");
                    
                    e.setWebsiteId(websiteId);
                    e.setWebsiteName(websiteName);
                    eventAffectedWebsiteService.saveEventAffectedWebsite(e);//保存
                    response.getWriter().write("true");
                }
                else if ("addWebsite".equals(methodName))//添加事件影响网站
                {
                    EventAffectedWebsite e = new EventAffectedWebsite();
                    Long websiteId = Long.parseLong(request.getParameter("websiteId"));
                    String websiteName=URLDecoder.decode(request.getParameter("websiteName"),"UTF-8");
                    
                    e.setEventId(Long.parseLong(request.getParameter("eventId")));
                    e.setWebsiteId(websiteId);
                    e.setWebsiteName(websiteName);
                 
                    eventAffectedWebsiteService.saveEventAffectedWebsite(e);
                    response.getWriter().write("true");
                }
                else if ("delWebsite".equals(methodName))//删除事件影响网站
                {
                    EventAffectedWebsite e=eventAffectedWebsiteService.getEventAffectedWebsiteById(Long.parseLong(request.getParameter("id")));
                    eventAffectedWebsiteService.removeEventAffectedWebsite(e);
                    response.getWriter().write("true"); 
                }
                else if("initWebsite".equals(methodName))//初始化事件影响网站
                {
                    EventAffectedWebsite website = eventAffectedWebsiteService.getEventAffectedWebsiteById(Long.parseLong(request.getParameter("id")));
                    
                    String str= "{\"Id\":\"" + website.getId()
                    + "\",\"websiteId\":\"" + website.getWebsiteId()
                    + "\",\"websiteName\":\"" + website.getWebsiteName() + "\"}";
                    response.getWriter().write(str);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
