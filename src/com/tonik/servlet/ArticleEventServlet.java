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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.model.Event;
import com.tonik.service.ArticleEventService;
import com.tonik.service.EventService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Nov 17, 2015
 * @version 1.0
 * @author zby
 * @web.servlet name="articleEventServlet"
 * @web.servlet-mapping url-pattern="/servlet/ArticleEventServlet"
 */
public class ArticleEventServlet extends BaseServlet
{
    private ArticleEventService articleEventService;
    private EventService eventService;
    public ArticleEventService getArticleEventService()
    {
        return articleEventService;
    }
    public void setArticleEventService(ArticleEventService articleEventService)
    {
        this.articleEventService = articleEventService;
    }
    public EventService getEventService()
    {
        return eventService;
    }
    public void setEventService(EventService eventService)
    {
        this.eventService = eventService;
    }
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        articleEventService = (ArticleEventService) ctx.getBean("ArticleEventService");
        eventService = (EventService) ctx.getBean("eventService");
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
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    Long articleId = Long.parseLong(request.getParameter("articleId"));
                    
                    List<Event> events = articleEventService.getEventsByArticle(articleId);
                    String strTotal = Integer.toString(events.size());
                    String strJson = "";
                    for (Event item : events){
                        strJson += eventService.getEventInfo(item) + ",";
                    }
                    if (strJson.length() > 0){
                       strJson = strJson.substring(0, strJson.length() - 1);
                       out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else{
                       out.print("false");
                    }
                }
                else if ("del".equalsIgnoreCase(methodName)){
                    Long articleId = Long.parseLong(request.getParameter("articleId"));
                    Long eventId = Long.parseLong(request.getParameter("eventId"));
                    articleEventService.removeArticleEvent(articleId, eventId);
                   out.print("true");
                }
                else if("add".equalsIgnoreCase(methodName)){
                    Long eid = Long.parseLong(request.getParameter("eventId"));
                    Long aid = Long.parseLong(request.getParameter("articleId"));
                    Event event = articleEventService.saveArticleEvent(eid, aid);
                    out.print(eventService.getEventInfo(event));
                }
                else if("queryEvents".equalsIgnoreCase(methodName)){
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String strStartTime = request.getParameter("strStartTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    Date date = new Date();
                    SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);
                    if ("".equals(strEndTime))
                    {
                        strEndTime = dateFormater.format(date);
                    }
                    if ("".equals(strStartTime))
                    {
                        strStartTime = "1980-01-01 00:00:01";
                    }
                    Long aid = Long.parseLong(request.getParameter("articleId"));
                    List<Event> events = articleEventService.getEventsByArticle(aid);
                    List<Event> aim =  articleEventService.getUnrelatedEvents(events, Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStartTime, strEndTime);
                    String strTotal = articleEventService.getUnrelatedEventsTotal(events, strQuery, strStartTime, strEndTime);
                    String strJson = "";
                    for (Event item : aim)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                + "\",\"TypeName\":\"" + item.getTypeName() + "\",\"BeginDate\":\""
                                + dateFormater.format(item.getBeginDate()) + "\",\"EndDate\":\""
                                + dateFormater.format(item.getEndDate()) + "\",\"Remark\":\"" + item.getRemark()
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
            } catch (Exception e)
            {
                out.println("false");
                out.println(e.getMessage());
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
