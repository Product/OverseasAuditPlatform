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

import com.tonik.model.EventAffectedArea;
import com.tonik.service.EventAffectedAreaService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响地区 servlet
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedAreaServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedAreaServlet"
 */
public class EventAffectedAreaServlet extends BaseServlet
{
    private EventAffectedAreaService eventAffectedAreaService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventAffectedAreaService = (EventAffectedAreaService) ctx.getBean("eventAffectedAreaService");
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
                if ("AreaQueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    Long eventId = Long.parseLong(request.getParameter("eventId"));

                    String strTotal = eventAffectedAreaService.getEventAffectedAreaTotal(eventId);
                    List<EventAffectedArea> ls = eventAffectedAreaService.getEventAffectedAreaPaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery,eventId);
                    String strJson = "";

                    for (EventAffectedArea item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId()
                                + "\",\"EventId\":\"" + item.getEventId()
                                + "\",\"RegionId\":\"" + item.getRegionId()
                                + "\",\"RegionName\":\"" + item.getRegionName()
                                + "\",\"CountryId\":\"" + item.getCountryId()
                                + "\",\"CountryName\":\"" + item.getCountryName()        
                                + "\",\"AreaId\":\"" + item.getAreaId()
                                + "\",\"AreaName\":\"" + item.getAreaName()        
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventAffectedAreaList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("editArea".equals(methodName))//编辑事件影响地区
                {
                    Long regionId =Long.parseLong(request.getParameter("regionId"));
                    Long countryId =Long.parseLong(request.getParameter("countryId"));
                    Long areaId = Long.parseLong(request.getParameter("areaId"));
                    String regionName = URLDecoder.decode(request.getParameter("regionName"),"UTF-8");
                    String countryName = URLDecoder.decode(request.getParameter("countryName"),"UTF-8");
                    String areaName = URLDecoder.decode(request.getParameter("areaName"),"UTF-8");
                    EventAffectedArea e=eventAffectedAreaService.getEventAffectedAreaById(Long.parseLong(request.getParameter("id")));
                    
                    e.setRegionId(regionId);
                    e.setCountryId(countryId);
                    e.setAreaId(areaId);
                    e.setRegionName(regionName);
                    e.setCountryName(countryName);
                    e.setAreaName(areaName);
                    
                    eventAffectedAreaService.saveEventAffectedArea(e);
                    response.getWriter().write("true");
                }
                else if ("addArea".equals(methodName))//添加事件影响地区
                {
                    EventAffectedArea e = new EventAffectedArea();
                    Long regionId =Long.parseLong(request.getParameter("regionId"));
                    Long countryId =Long.parseLong(request.getParameter("countryId"));
                    Long areaId = Long.parseLong(request.getParameter("areaId"));
                    String regionName = URLDecoder.decode(request.getParameter("regionName"),"UTF-8");
                    String countryName = URLDecoder.decode(request.getParameter("countryName"),"UTF-8");
                    String areaName = URLDecoder.decode(request.getParameter("areaName"),"UTF-8");
                    
                    e.setEventId(Long.parseLong(request.getParameter("eventId")));
                    e.setRegionId(regionId);
                    e.setCountryId(countryId);
                    e.setAreaId(areaId);
                    e.setRegionName(regionName);
                    e.setCountryName(countryName);
                    e.setAreaName(areaName);
                 
                    eventAffectedAreaService.saveEventAffectedArea(e);
                    response.getWriter().write("true");
                }
                else if ("delArea".equals(methodName))//删除事件影响地区
                {
                    EventAffectedArea e=eventAffectedAreaService.getEventAffectedAreaById(Long.parseLong(request.getParameter("id")));
                    eventAffectedAreaService.removeEventAffectedArea(e);
                    response.getWriter().write("true"); 
                }
                else if("initArea".equals(methodName))//初始化事件影响地区
                {
                    EventAffectedArea area = eventAffectedAreaService.getEventAffectedAreaById(Long.parseLong(request.getParameter("id")));
                    
                    String str= "{\"Id\":\"" + area.getId()
                    + "\",\"RegionId\":\"" + area.getRegionId()
                    + "\",\"RegionName\":\"" + area.getRegionName() 
                    + "\",\"CountryId\":\"" + area.getCountryId()
                    + "\",\"CountryName\":\"" + area.getCountryName() 
                    + "\",\"AreaId\":\"" + area.getAreaId()
                    + "\",\"AreaName\":\"" + area.getAreaName() 
                    + "\"}";
                    response.getWriter().write(str);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
