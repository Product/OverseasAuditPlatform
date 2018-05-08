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

import com.tonik.model.EventAffectedProductType;
import com.tonik.service.EventAffectedProductTypeService;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响商品类别 servlet
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @web.servlet name="eventAffectedProductTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/EventAffectedProductTypeServlet"
 */
public class EventAffectedProductTypeServlet extends BaseServlet
{
    private EventAffectedProductTypeService eventAffectedProductTypeService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        eventAffectedProductTypeService = (EventAffectedProductTypeService) ctx.getBean("eventAffectedProductTypeService");
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
                if ("QueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    Long eventId = Long.parseLong(request.getParameter("eventId"));

                    String strTotal = eventAffectedProductTypeService.getEventAffectedProductTypeTotal(eventId);
                    List<EventAffectedProductType> ls = eventAffectedProductTypeService.getEventAffectedProductTypePaging(Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery,eventId);
                    String strJson = "";

                    for (EventAffectedProductType item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId()
                                + "\",\"EventId\":\"" + item.getEventId()
                                + "\",\"ProductFirstTypeId\":\"" + item.getProductFirstTypeId()
                                + "\",\"ProductFirstTypeName\":\"" + item.getProductFirstTypeName()
                                + "\",\"ProductSecondTypeId\":\"" + item.getProductSecondTypeId()
                                + "\",\"ProductSecondTypeName\":\"" + item.getProductSecondTypeName()        
                                + "\",\"ProductThirdTypeId\":\"" + item.getProductThirdTypeId()
                                + "\",\"ProductThirdTypeName\":\"" + item.getProductThirdTypeName()        
                                + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"productTypeList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("edit".equals(methodName))//编辑事件影响商品类别
                {
                    Long productFirstTypeId =Long.parseLong(request.getParameter("productFirstTypeId"));
                    Long productSecondTypeId =Long.parseLong(request.getParameter("productSecondTypeId"));
                    Long productThirdTypeId = Long.parseLong(request.getParameter("productThirdTypeId"));
                    String productFirstTypeName = URLDecoder.decode(request.getParameter("productFirstTypeName"),"UTF-8");
                    String productSecondTypeName = URLDecoder.decode(request.getParameter("productSecondTypeName"),"UTF-8");
                    String productThirdTypeName = URLDecoder.decode(request.getParameter("productThirdTypeName"),"UTF-8");
                    EventAffectedProductType e=eventAffectedProductTypeService.getEventAffectedProductTypeById(Long.parseLong(request.getParameter("id")));
                    
                    e.setProductFirstTypeId(productFirstTypeId);
                    e.setProductSecondTypeId(productSecondTypeId);
                    e.setProductThirdTypeId(productThirdTypeId);
                    e.setProductFirstTypeName(productFirstTypeName);
                    e.setProductSecondTypeName(productSecondTypeName);
                    e.setProductThirdTypeName(productThirdTypeName);
                    eventAffectedProductTypeService.saveEventAffectedProductType(e);//保存
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName))//添加事件影响商品类别
                {
                    EventAffectedProductType e = new EventAffectedProductType();
                    Long productFirstTypeId =Long.parseLong(request.getParameter("productFirstTypeId"));
                    Long productSecondTypeId =Long.parseLong(request.getParameter("productSecondTypeId"));
                    Long productThirdTypeId = Long.parseLong(request.getParameter("productThirdTypeId"));
                    String productFirstTypeName = URLDecoder.decode(request.getParameter("productFirstTypeName"),"UTF-8");
                    String productSecondTypeName = URLDecoder.decode(request.getParameter("productSecondTypeName"),"UTF-8");
                    String productThirdTypeName = URLDecoder.decode(request.getParameter("productThirdTypeName"),"UTF-8");
                    
                    e.setEventId(Long.parseLong(request.getParameter("eventId")));
                    e.setProductFirstTypeId(productFirstTypeId);
                    e.setProductSecondTypeId(productSecondTypeId);
                    e.setProductThirdTypeId(productThirdTypeId);
                    e.setProductFirstTypeName(productFirstTypeName);
                    e.setProductSecondTypeName(productSecondTypeName);
                    e.setProductThirdTypeName(productThirdTypeName);
                 
                    eventAffectedProductTypeService.saveEventAffectedProductType(e);
                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName))//删除事件影响商品类别
                {
                    EventAffectedProductType e=eventAffectedProductTypeService.getEventAffectedProductTypeById(Long.parseLong(request.getParameter("id")));
                    eventAffectedProductTypeService.removeEventAffectedProductType(e);
                    response.getWriter().write("true"); 
                }
                else if("init".equals(methodName))//初始化事件影响商品类别
                {
                    EventAffectedProductType area = eventAffectedProductTypeService.getEventAffectedProductTypeById(Long.parseLong(request.getParameter("id")));
                    
                    String str= "{\"Id\":\"" + area.getId()
                    + "\",\"ProductFirstTypeId\":\"" + area.getProductFirstTypeId()
                    + "\",\"ProductFirstTypeName\":\"" + area.getProductFirstTypeName() 
                    + "\",\"ProductSecondTypeId\":\"" + area.getProductSecondTypeId()
                    + "\",\"ProductSecondTypeName\":\"" + area.getProductSecondTypeName() 
                    + "\",\"ProductThirdTypeId\":\"" + area.getProductThirdTypeId()
                    + "\",\"ProductThirdTypeName\":\"" + area.getProductThirdTypeName() 
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
