package com.tonik.servlet;

import java.io.IOException;
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
import com.tonik.model.Area;
import com.tonik.model.Brand;
import com.tonik.model.Country;
import com.tonik.service.AreasService;
import com.tonik.service.BrandService;
import com.tonik.service.CountryService;
import com.tonik.service.ProductDefinitionService;
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
 * @web.servlet name="brandServlet"
 * @web.servlet-mapping url-pattern="/servlet/BrandServlet"
 */
@SuppressWarnings("serial")
public class BrandServlet extends BaseServlet
{
    private BrandService BrandService;
    private AreasService AreasService;
    private CountryService CountryService;
    private ProductDefinitionService ProductDefinitionService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        BrandService = (BrandService)ctx.getBean("BrandService");
        AreasService = (AreasService)ctx.getBean("AreasService");
        ProductDefinitionService =(ProductDefinitionService)ctx.getBean("ProductDefinitionService");
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
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    
                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
                    
                    String strTotal = BrandService.BrandTotal(strQuery,strStraTime, strEndTime);
                    List<Brand> ls = BrandService.BrandPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery,strStraTime, strEndTime);
                    String strJson = "";
                    for(Brand item: ls)
                    {
                       
                        String CountryName = item.getCountry().getName();
                        String AreaName="";
                        if(item.getArea()!=null)
                            AreaName = item.getArea().getName();
                        
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"Name_EN\":\"" +item.getName_en() +"\","
                                + "\"Name_CN\":\""+item.getName_cn() +"\","
                                + "\"Name_other\":\"" + item.getName_other() + "\","
                                + "\"Popularity\":\"" + item.getPopularity() +"\","
                                + "\"MarketShare\":\"" + item.getMarketShare() +"\","
                                + "\"AreaName\":\"" + AreaName +"\","
                                + "\"CountryName\":\""+ CountryName + "\","
                                + "\"CreateTime\":\"" + item.getFormatCreateTime() + "\"},";
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
                    Brand brand = new Brand();
                    brand.setName_cn(URLDecoder.decode(request.getParameter("name_cn"),"UTF-8"));
                    brand.setName_en(URLDecoder.decode(request.getParameter("name_en"),"UTF-8"));
                    brand.setName_other(URLDecoder.decode(request.getParameter("name_other"),"UTF-8"));
                    
                    if(request.getParameter("popularity") == null)
                        brand.setPopularity(0);
                    else
                        brand.setPopularity(Integer.parseInt(request.getParameter("popularity")));
                    if(request.getParameter("marketShare") == null)
                        brand.setMarketShare(0);
                    else
                        brand.setMarketShare(Float.parseFloat(request.getParameter("marketShare")));
                    
                    Country country = CountryService.getCountryById(Long.parseLong(request.getParameter("countryId")));
                    String AreaName="";
                    if(!"".equals(request.getParameter("areaId")))
                    {
                        Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("areaId")));
                        AreaName=area.getName();
                        brand.setArea(area);
                    }
                    brand.setCountry(country);
                    brand.setCreateTime(new Date());
                    BrandService.SaveBrand(brand);
                    String CountryName = country.getName();
                    response.getWriter().write("{\"Id\":\"" +brand.getId() + "\","
                            + "\"Name_EN\":\"" +brand.getName_en() +"\","
                            + "\"Name_CN\":\""+brand.getName_cn() +"\","
                            + "\"Name_other\":\"" + brand.getName_other() + "\","
                            + "\"AreaName\":\"" + AreaName +"\","
                            + "\"CountryName\":\""+ CountryName + "\","
                            + "\"CreateTime\":\"" + brand.getFormatCreateTime() + "\"}");
                }
                else if("edit".equals(methodName))
                {
                    Brand brand = BrandService.getBrandById(Long.parseLong(request.getParameter("id")));
                    brand.setName_cn(URLDecoder.decode(request.getParameter("name_cn"),"UTF-8"));
                    brand.setName_en(URLDecoder.decode(request.getParameter("name_en"),"UTF-8"));
                    brand.setName_other(URLDecoder.decode(request.getParameter("name_other"),"UTF-8"));
                    
                    if(request.getParameter("popularity") == null)
                        brand.setPopularity(0);
                    else
                        brand.setPopularity(Integer.parseInt(request.getParameter("popularity")));
                    if(request.getParameter("marketShare") == null)
                        brand.setMarketShare(0);
                    else
                        brand.setMarketShare(Float.parseFloat(request.getParameter("marketShare")));
                    
                    Country country = CountryService.getCountryById(Long.parseLong(request.getParameter("countryId")));
                    brand.setCountry(country);
                    String AreaName="";
                    if(!"".equals(request.getParameter("areaId")))
                    {
                        Area area = AreasService.getAreaById(Long.parseLong(request.getParameter("areaId")));
                        AreaName=area.getName();
                        brand.setArea(area);
                    }
                    else
                        brand.setArea(null);
                    BrandService.SaveBrand(brand);
                    String CountryName = country.getName();
                    response.getWriter().write("{\"Id\":\"" +brand.getId() + "\","
                            + "\"Name_EN\":\"" +brand.getName_en() +"\","
                            + "\"Name_CN\":\""+brand.getName_cn() +"\","
                            + "\"Name_other\":\"" + brand.getName_other() + "\","
                            + "\"Popularity\":\"" + brand.getPopularity() +"\","
                            + "\"MarketShare\":\"" + brand.getMarketShare() +"\","
                            + "\"AreaName\":\"" + AreaName +"\","
                            + "\"CountryName\":\""+ CountryName + "\","
                            + "\"CreateTime\":\"" + brand.getFormatCreateTime() + "\"}");
                }
                else if("del".equals(methodName))
                {
                    if((ProductDefinitionService.ProductDefinitionByBrandTotal(Long.parseLong(request.getParameter("id")))>0))
                    {
                        response.getWriter().write("error");
                    }
                    else
                    {
                        BrandService.RemoveBrand(Long.parseLong(request.getParameter("id")));
                        response.getWriter().write("true");
                    }
                }
                else if("init".equals(methodName))
                {
                    Brand brand = BrandService.getBrandById(Long.parseLong(request.getParameter("id")));
                    String CountryName = brand.getCountry().getName();
                    String AreaName="";
                    String AreaId="";
                    if(brand.getArea()!=null)
                    {
                        AreaId = Long.toString(brand.getArea().getId());
                        AreaName = brand.getArea().getName();
                    }
                    response.getWriter().write("{\"Id\":\"" +brand.getId() + "\","
                            + "\"Name_EN\":\"" +brand.getName_en() +"\","
                            + "\"Name_CN\":\"" +brand.getName_cn() +"\","
                            + "\"Name_other\":\"" + brand.getName_other() + "\","
                            + "\"Popularity\":\"" + brand.getPopularity() +"\","
                            + "\"MarketShare\":\"" + brand.getMarketShare() +"\","
                            + "\"AreaId\":\"" + AreaId +"\","
                            + "\"AreaName\":\"" + AreaName +"\","
                            + "\"CountryName\":\""+ CountryName + "\","
                            + "\"CreateTime\":\"" + brand.getFormatCreateTime() + "\"}");
                }
            }catch(Exception e){
            response.getWriter().write("false");    
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
