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

import com.tonik.model.EventAffectedBrand;
import com.tonik.service.EventAffectedBrandService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响品牌 servlet
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedBrandServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedBrandServlet"
 */
public class EventAffectedBrandServlet extends BaseServlet
{
    private EventAffectedBrandService eventAffectedBrandService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventAffectedBrandService = (EventAffectedBrandService) ctx.getBean("eventAffectedBrandService");
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
                if ("BrandQueryList".equals(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    Long eventId =Long.parseLong(request.getParameter("eventId"));
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                 

                    String strTotal = eventAffectedBrandService.getEventAffectedBrandTotal(eventId);
                    List<EventAffectedBrand> ls = eventAffectedBrandService.getEventAffectedBrandPaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery,eventId);
                    String strJson = "";

                    for (EventAffectedBrand item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId()
                                + "\",\"EventId\":\"" + item.getEventId()
                                + "\",\"BrandId\":\"" + item.getBrandId()
                                + "\",\"BrandName\":\"" + item.getBrandName()                             
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"eventAffectedBrandList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("editBrand".equals(methodName))//编辑事件影响品牌
                {
                    EventAffectedBrand e=eventAffectedBrandService.getEventAffectedBrandById(Long.parseLong(request.getParameter("id")));
                    Long brandId = Long.parseLong(request.getParameter("brandId"));
                    String brandName =URLDecoder.decode(request.getParameter("brandName"),"UTF-8");
                    
                    e.setBrandId(brandId);
                    e.setBrandName(brandName);
                    eventAffectedBrandService.saveEventAffectedBrand(e);//保存
                    response.getWriter().write("true");
                }
                else if ("addBrand".equals(methodName))//添加事件影响品牌
                {
                    EventAffectedBrand e = new EventAffectedBrand();
                    
                    Long eventId = Long.parseLong(request.getParameter("eventId"));
                    Long brandId = Long.parseLong(request.getParameter("brandId"));
                    String brandName = URLDecoder.decode(request.getParameter("brandName"),"UTF-8");
                    
                    e.setEventId(eventId);
                    e.setBrandId(brandId);
                    e.setBrandName(brandName);
                 
                    eventAffectedBrandService.saveEventAffectedBrand(e);
                    response.getWriter().write("true");
                }
                else if ("delBrand".equals(methodName))//删除事件影响品牌
                {
                    EventAffectedBrand e=eventAffectedBrandService.getEventAffectedBrandById(Long.parseLong(request.getParameter("id")));
                    eventAffectedBrandService.removeEventAffectedBrand(e);
                    response.getWriter().write("true"); 
                }
                else if("initBrand".equals(methodName))//初始化事件影响品牌
                {
                    EventAffectedBrand brand = eventAffectedBrandService.getEventAffectedBrandById(Long.parseLong(request.getParameter("id")));
                    
                    String str= "{\"Id\":\"" + brand.getId()
                    + "\",\"BrandId\":\"" + brand.getBrandId()
                    + "\",\"BrandName\":\"" + brand.getBrandName() + "\"}";
                    response.getWriter().write(str);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
