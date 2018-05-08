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

import com.tonik.model.EventAffectedMaterial;
import com.tonik.service.EventAffectedMaterialService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响配方原料 servlet
 * </p>
 * @since Nov 19, 2015
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedMaterialServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedMaterialServlet"
 */
public class EventAffectedMaterialServlet extends BaseServlet
{
        private EventAffectedMaterialService eventAffectedMaterialService;

        public void init(ServletConfig config) throws ServletException
        {
            super.init(config);
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
            eventAffectedMaterialService = (EventAffectedMaterialService) ctx.getBean("eventAffectedMaterialService");
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
                    if ("MaterialQueryList".equals(methodName))
                    {
                        String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                        String strPageIndex = request.getParameter("strPageIndex");
                        String strPageCount = request.getParameter("strPageCount");
                        Long eventId = Long.parseLong(request.getParameter("eventId"));

                        String strTotal = eventAffectedMaterialService.getEventAffectedMaterialTotal(eventId);
                        List<EventAffectedMaterial> ls = eventAffectedMaterialService.getEventAffectedMaterialPaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery,eventId);
                        String strJson = "";

                        for (EventAffectedMaterial item : ls)
                        {
                            strJson += "{\"Id\":\"" + item.getId()
                                    + "\",\"EventId\":\"" + item.getEventId()
                                    + "\",\"MaterialTypeId\":\"" + item.getMaterialTypeId()
                                    + "\",\"MaterialTypeName\":\"" + item.getMaterialTypeName()
                                    + "\",\"MaterialId\":\"" + item.getMaterialId()
                                    + "\",\"MaterialName\":\"" + item.getMaterialName()            
                                    + "\"},";
                        }
                        if (strJson.length() > 0)
                        {
                            strJson = strJson.substring(0, strJson.length() - 1);
                            response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventAffectedMaterialList\":[" + strJson + "]}");
                        }
                        else
                        {
                            response.getWriter().write("false");
                        }
                    }
                    else if ("editMaterial".equals(methodName))//编辑事件影响配方原料
                    {
                        EventAffectedMaterial e=eventAffectedMaterialService.getEventAffectedMaterialById(Long.parseLong(request.getParameter("id")));
                        e.setMaterialTypeId(Long.parseLong(request.getParameter("materialTypeId")));
                        e.setMaterialTypeName(URLDecoder.decode(request.getParameter("materialTypeName"),"UTF-8"));
                        e.setMaterialId(Long.parseLong(request.getParameter("materialId")));
                        e.setMaterialName(URLDecoder.decode(request.getParameter("materialName"),"UTF-8"));
                        
                        eventAffectedMaterialService.saveEventAffectedMaterial(e);
                        response.getWriter().write("true");
                    }
                    else if ("addMaterial".equals(methodName))//添加事件影响配方原料
                    {
                        EventAffectedMaterial e = new EventAffectedMaterial();
                        Long MaterialTypeId = Long.parseLong(request.getParameter("materialTypeId"));
                        String MaterialTypeName = URLDecoder.decode(request.getParameter("materialTypeName"),"UTF-8");
                        Long MaterialId = Long.parseLong(request.getParameter("materialId"));
                        String MaterialName = URLDecoder.decode(request.getParameter("materialName"),"UTF-8");
                        
                        e.setEventId(Long.parseLong(request.getParameter("eventId")));
                        e.setMaterialTypeId(MaterialTypeId);
                        e.setMaterialId(MaterialId);
                        e.setMaterialTypeName(MaterialTypeName);
                        e.setMaterialName(MaterialName);
                     
                        eventAffectedMaterialService.saveEventAffectedMaterial(e);
                        response.getWriter().write("true");
                    }
                    else if ("delMaterial".equals(methodName))//删除事件影响配方原料
                    {
                        EventAffectedMaterial e=eventAffectedMaterialService.getEventAffectedMaterialById(Long.parseLong(request.getParameter("id")));
                        eventAffectedMaterialService.removeEventAffectedMaterial(e);
                        response.getWriter().write("true"); 
                    }
                    else if("initMaterial".equals(methodName))//初始化事件影响配方原料
                    {
                        EventAffectedMaterial material = eventAffectedMaterialService.getEventAffectedMaterialById(Long.parseLong(request.getParameter("id")));
                        
                        String str= "{\"Id\":\"" + material.getId()
                        + "\",\"MaterialTypeId\":\"" + material.getMaterialTypeId()
                        + "\",\"MaterialTypeName\":\"" + material.getMaterialTypeName() 
                        + "\",\"MaterialId\":\"" + material.getMaterialId()
                        + "\",\"MaterialName\":\"" + material.getMaterialName() 
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
