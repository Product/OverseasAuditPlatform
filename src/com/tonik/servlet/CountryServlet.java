package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Country;
import com.tonik.model.Region;
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
 * @author bchen
 * @web.servlet name="countryServlet"
 * @web.servlet-mapping url-pattern="/servlet/CountryServlet"
 */
@SuppressWarnings("serial")
public class CountryServlet extends BaseServlet
{
    private CountryService CountryService;
    private AreasService AreasService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        CountryService =(CountryService)ctx.getBean("CountryService");
        AreasService =(AreasService)ctx.getBean("AreasService");
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
                    String strRegionId=request.getParameter("regionId");
                    
                    String strTotal = CountryService.CountryTotal(strQuery,strRegionId);
                    List<Country> ls= CountryService.CountryPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery,strRegionId);
                    String strJson = "";   
                    for(Country item: ls)
                    {
                        String regions ="";
                        for(Region it : item.getRegions())
                        {
                            regions += "{\"Id\":\""+it.getId()+"\",\"Name\":\""+it.getName()+"\"},";
                        }
                        if(regions.length()>0)
                        {
                            regions = regions.substring(0, regions.length()-1);
                        }
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"CountryCode\":\""+item.getCountryCode() + "\","
                                + "\"Name\":\"" +item.getName() +"\",\"Regions\":["+ regions + "]},";
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
                    Country country =new Country();
                    country.setCountryCode(URLDecoder.decode(request.getParameter("CountryCode"),"UTF-8"));
                    country.setName(URLDecoder.decode(request.getParameter("CountryName"),"UTF-8"));
                    Set<Region> regions = new HashSet<Region>();
                    String strRegions= "";
                    for(String item : request.getParameter("strMenu").split("_"))
                    {
                        Region r = CountryService.getRegionById(Long.parseLong(item));
                        regions.add(r);
                        strRegions += "{\"Id\":\""+r.getId()+"\",\"Name\":\""+r.getName()+"\"},";
                    }
                    if(strRegions.length()>0)
                    {
                        strRegions = strRegions.substring(0, strRegions.length()-1);
                    }
                    country.setRegions(regions);
                    CountryService.SaveCountry(country);
                    response.getWriter().write("{\"Id\":\"" +country.getId() + "\",\"CountryCode\":\""+country.getCountryCode() + "\","
                                + "\"Name\":\"" +country.getName() +"\",\"Regions\":["+ strRegions + "]}");
                }
                else if("edit".equals(methodName))
                {
                    Country country = CountryService.getCountryById(Long.parseLong(request.getParameter("id")));
                    country.setCountryCode(URLDecoder.decode(request.getParameter("CountryCode"),"UTF-8"));
                    country.setName(URLDecoder.decode(request.getParameter("CountryName"),"UTF-8"));
                    Set<Region> regions = new HashSet<Region>();
                    String strRegions= "";
                    for(String item : request.getParameter("strMenu").split("_"))
                    {
                        Region r = CountryService.getRegionById(Long.parseLong(item));
                        regions.add(r);
                        strRegions += "{\"Id\":\""+r.getId()+"\",\"Name\":\""+r.getName()+"\"},";
                    }
                    if(strRegions.length()>0)
                    {
                        strRegions = strRegions.substring(0, strRegions.length()-1);
                    }
                    country.setRegions(regions);
                    CountryService.SaveCountry(country);
                    response.getWriter().write("{\"Id\":\"" +country.getId() + "\",\"CountryCode\":\""+country.getCountryCode() + "\","
                            + "\"Name\":\"" +country.getName() +"\",\"Regions\":["+ strRegions + "]}");
                }
                else if("del".equals(methodName))
                {
                    if (Integer.parseInt(AreasService.AreasTotal("", Long.parseLong(request.getParameter("id"))))>0){
                        response.getWriter().write("error");}
                    else
                    {
                        CountryService.DelCountry(Long.parseLong(request.getParameter("id")));
                        response.getWriter().write("true");
                    }
                }
                else if("getCountryList".equals(methodName))
                {
                    List<Country> ls = CountryService.getCountryList();
                    String strJson ="";
                    for(Country item :ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"CountryCode\":\""
                                + item.getCountryCode() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if("init".equals(methodName))
                {
                    String id = request.getParameter("id");
                    Country ct =CountryService.getCountryById(Long.parseLong(id));
                    String regions ="";
                    for(Region item: ct.getRegions())
                    {
                        regions += "{\"Id\":\""+item.getId()+"\"},";
                    }
                    if(regions.length()>0)
                    {
                        regions = regions.substring(0, regions.length()-1);
                    }
                    response.getWriter().write(
                            "{\"Id\":\"" + ct.getId() + "\",\"CountryCode\":\"" + ct.getCountryCode() + "\", \"Name\":\""
                                    + ct.getName() + "\",\"Regions\":["+ regions + "]}");
                }
            }catch(Exception e){
                response.getWriter().write("false");
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
