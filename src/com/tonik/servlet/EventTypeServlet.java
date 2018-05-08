package com.tonik.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.EventType;
import com.tonik.service.EventTypeService;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件类别 servlet
 * </p>
 * @since Nov 03, 2015
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventTypeServlet"
 */
public class EventTypeServlet extends BaseServlet
{
    private EventTypeService eventTypeService;


    public EventTypeService getEventTypeService()
    {
        return eventTypeService;
    }

    public void setEventTypeService(EventTypeService eventTypeService)
    {
        this.eventTypeService = eventTypeService;
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventTypeService = (EventTypeService) ctx.getBean("eventTypeService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    List<EventType> eventTypes = eventTypeService.getEventTypes();

                    String strJson = "";

                    for (EventType item : eventTypes)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"eventTypeList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("EventMap".equals(methodName))//在地图上显示疫情事件外的事件类型
                {
                    List<EventType> eventTypes = eventTypeService.getEventTypesExceptEpidemic();

                    String strJson = "";

                    for (EventType item : eventTypes)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"eventTypeList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }

            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
