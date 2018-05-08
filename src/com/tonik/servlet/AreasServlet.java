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

import com.tonik.model.Area;
import com.tonik.service.AreasService;
import com.tonik.service.CountryService;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @web.servlet name="areasServlet"
 * @web.servlet-mapping url-pattern="/servlet/AreasServlet"
 */
@SuppressWarnings("serial")
public class AreasServlet extends BaseServlet
{
    private AreasService AreasService;
    private CountryService CountryService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        AreasService =(AreasService)ctx.getBean("AreasService");
        CountryService = (CountryService)ctx.getBean("CountryService");
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
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
                if("QueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String strCountryId = request.getParameter("countryId");
                    String strTotal = AreasService.AreasTotal(strQuery,Long.parseLong(strCountryId));
                    List<Area> ls = AreasService.AreasPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery,Long.parseLong(strCountryId));
                    
                    String strJson = "";   
                    for(Area item : ls)
                    {
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"AreaCode\":\""+item.getAreaCode() + "\","
                                + "\"Name\":\"" +item.getName() +"\",\"Country\":\""+item.getCountry().getName() +"\"},";
                    }
                    if(strJson.length()>0)
                    {
                        strJson = strJson.substring(0, strJson.length()-1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
                }
                else if("add".equals(methodName))
                {
                    Area area= new Area();
                    area.setAreaCode(URLDecoder.decode(request.getParameter("AreasCode"),"UTF-8"));
                    area.setName(URLDecoder.decode(request.getParameter("AreasName"),"UTF-8"));
                    area.setCountry(CountryService.getCountryById(Long.parseLong(request.getParameter("countryId"))));
                    AreasService.SaveArea(area);
                    response.getWriter().write("{\"Id\":\"" +area.getId() + "\",\"AreaCode\":\""+area.getAreaCode() + "\","
                            + "\"Name\":\"" +area.getName() +"\",\"Country\":\""+area.getCountry().getName() +"\"}");
                }
                else if("edit".equals(methodName))
                {
                    Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("id")));
                    area.setAreaCode(URLDecoder.decode(request.getParameter("AreasCode"),"UTF-8"));
                    area.setName(URLDecoder.decode(request.getParameter("AreasName"),"UTF-8"));
                    AreasService.SaveArea(area);
                    response.getWriter().write("{\"Id\":\"" +area.getId() + "\",\"AreaCode\":\""+area.getAreaCode() + "\","
                            + "\"Name\":\"" +area.getName() +"\",\"Country\":\""+area.getCountry().getName() +"\"}");
                }
                else if("del".equals(methodName))
                {
                    AreasService.removeArea(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if("init".equals(methodName))
                {
                    Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("{\"Id\":\"" +area.getId() + "\",\"AreaCode\":\""+area.getAreaCode() + "\","
                            + "\"Name\":\"" +area.getName() +"\",\"Country\":\""+area.getCountry().getName() +"\"}");
                }
            }catch(Exception e){
            response.getWriter().write("false");    
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
