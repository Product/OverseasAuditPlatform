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
import com.tonik.service.CountryService;
import com.tonik.service.RegionService;
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
 * @web.servlet name="regionServlet"
 * @web.servlet-mapping url-pattern="/servlet/RegionServlet"
 */
@SuppressWarnings("serial")
public class RegionServlet extends BaseServlet
{
    private RegionService RegionService;
    private CountryService CountryService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        RegionService = (RegionService)ctx.getBean("RegionService");
        CountryService =(CountryService)ctx.getBean("CountryService");
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
                    String strTotal = RegionService.RegionTotal(strQuery);
                    List<Region> regions = RegionService.RegionPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery);
                    String strJson = "";
                    for(Region item:regions)
                    {
                        String strCountries = "";
                        for(Country country : item.getCountries())
                        {
                            strCountries +="{\"Id\":\"" + country.getId() + "\",\"Name\":\"" + country.getName() + "\",\"CountryCode\":\""
                                + country.getCountryCode() + "\"},";
                        }
                        if(strCountries.length()>0)
                            strCountries = strCountries.substring(0,strCountries.length()-1);
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"RegionCode\":\""
                                + item.getRegionCode() + "\",\"countriesList\":[" + strCountries + "]},";
                    }
                    if(strJson.length()>0)
                    {
                        strJson = strJson.substring(0, strJson.length()-1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
                }
                else if("getRegionList".equals(methodName))
                {
                    List<Region> ls= RegionService.getRegionList();
                    String strJson ="";
                    for(Region item:ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"RegionCode\":\""
                                + item.getRegionCode() + "\"},";
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
                else if("add".equals(methodName))
                {
                    Region region = new Region();
                    region.setRegionCode(URLDecoder.decode(request.getParameter("RegionCode"),"UTF-8"));
                    region.setName(URLDecoder.decode(request.getParameter("RegionName"),"UTF-8"));
                    Set<Country> countries = new HashSet<Country>();
                    //String strCountries ="";
                    if(!"".equals(request.getParameter("strMenu")))
                    {
                        for(String item : request.getParameter("strMenu").split("_"))
                        {
                            Country c = CountryService.getCountryById(Long.parseLong(item));
                            countries.add(c);
                        }
                        region.setCountries(countries);
                    }
//                    for(String item : request.getParameter("strMenu").split("_"))
//                    {
//                        Country c = CountryService.getCountryById(Long.parseLong(item));
//                        countries.add(c);
//                    }
//                    if(strCountries.length()>0)
//                    {
//                        strCountries = strCountries.substring(0, strCountries.length()-1);
//                    }
//                    if(countries.size()>0)
//                        region.setCountries(countries);
                    RegionService.saveRegion(region);
                    response.getWriter().write("true");
                }
            }catch(Exception e){
                response.getWriter().write("false");
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
