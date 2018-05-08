package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Country;
import com.tonik.service.BrandService;
import com.tonik.service.CountryService;
import com.tonik.service.ProductDefinitionService;
import com.tonik.service.ProductService;
import com.tonik.service.ProductTypeService;
import com.tonik.service.WebsiteService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author lxt
 * @web.servlet name="regulatoryServlet"
 * @web.servlet-mapping url-pattern="/servlet/RegulatoryServlet"
 */
public class RegulatoryServlet extends BaseServlet
{
    private WebsiteService websiteService;
    private CountryService countryService;
    private BrandService brandService;
    private ProductTypeService productTypeService;
    private ProductService productService;
    private ProductDefinitionService productDefinitionService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        websiteService = (WebsiteService) ctx.getBean("WebsiteService");
        countryService = (CountryService) ctx.getBean("CountryService");
        brandService = (BrandService) ctx.getBean("BrandService");
        productTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
        productService = (ProductService) ctx.getBean("ProductService");
        productDefinitionService = (ProductDefinitionService) ctx.getBean("ProductDefinitionService");
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        /*if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }*/
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(-1);
        String methodName = request.getParameter("methodName");
        if (methodName != "" && methodName != null)
        {
            
            try
            {
                if("getTotals".equals(methodName))
                {
                    String country = URLDecoder.decode(request.getParameter("country"),"UTF-8");
                    country = URLDecoder.decode(country,"UTF-8");
                    Country c = null;
                    if(country != null && country != ""){                        
                        c = countryService.getCountryByName(country);
                        session.setAttribute("country", c);
                    }
                    String choices = request.getParameter("strChoices");
                    Integer choiceNum = Integer.parseInt(request.getParameter("choicesLength"));
                    String ptl = getProductType(choices);
                    String websiteTotal = websiteService.getWebsiteTotalByCountryAndProduct(c, ptl);
                    String productTypeTotal = productTypeService.getProductTypeTotalByCountryAndProduct(c, ptl);
                    String brandTotal = brandService.getBrandTotalByCountryAndProduct(c, ptl);
                    String productTotal = productService.getProductTotalByCountryAndProduct(c, ptl);
                    String productDefineTotal = productDefinitionService.getProductDefineTotalByCountryAndProduct(c, ptl);
                    
                    out.print("{\"websiteTotal\":\"" + websiteTotal + "\",\"productTotal\":\"" + productTotal +"\",\"productTypeTotal\":\"" + productTypeTotal +"\",\"brandTotal\":\"" + brandTotal  +"\",\"productDefineTotal\":\"" + productDefineTotal + "\"}");
                }else if("WebsiteNumInit".equals(methodName)){
                    String ptl = "";
                    if(request.getParameter("Choices") != "" && request.getParameter("Choices") != null){
                        ptl = getProductType(request.getParameter("Choices"));   
                    }
                    session.setAttribute("ptl", ptl);
                    session.setAttribute("method", "WebsiteExcelInit");
                    Country c = (Country)request.getSession().getAttribute("country");
                    String total = websiteService.getWebsiteTotalByProduct(ptl, c);
                    out.print(total);
                }else if("ProductDefNumInit".equals(methodName)){
                    String ptl = "";
                    if(request.getParameter("Choices") != "" && request.getParameter("Choices") != null){
                        ptl = getProductType(request.getParameter("Choices"));   
                    }
                    session.setAttribute("ptl", ptl);
                    session.setAttribute("method", "ProductDefExcelInit");
                    Country c = (Country)request.getSession().getAttribute("country");
                    String total = productDefinitionService.getProductDefTotalByProduct(ptl, c);
                    out.print(total);
                }else if("ProductNumInit".equals(methodName)){
                    String ptl = "";
                    if(request.getParameter("Choices") != "" && request.getParameter("Choices") != null){
                        ptl = getProductType(request.getParameter("Choices"));   
                    }
                    session.setAttribute("ptl", ptl);
                    session.setAttribute("method", "ProductExcelInit");
                    Country c = (Country)request.getSession().getAttribute("country");
                    String total = productService.getProductTotalByProduct(ptl, c);
                    out.print(total);
                }else if("BrandNumInit".equals(methodName)){
                    String ptl = "";
                    if(request.getParameter("Choices") != "" && request.getParameter("Choices") != null){
                        ptl = getProductType(request.getParameter("Choices"));   
                    }
                    session.setAttribute("ptl", ptl);
                    session.setAttribute("method", "BrandExcelInit");
                    Country c = (Country)request.getSession().getAttribute("country");
                    String total = brandService.getBrandTotalByProduct(ptl, c);
                    out.print(total);
                }else if("ProductTypeNumInit".equals(methodName)){
                    String ptl = "";
                    if(request.getParameter("Choices") != "" && request.getParameter("Choices") != null){
                        ptl = getProductType(request.getParameter("Choices"));   
                    }
                    session.setAttribute("ptl", ptl);
                    session.setAttribute("method", "ProductTypeExcelInit");
                    Country c = (Country)request.getSession().getAttribute("country");
                    String total = productTypeService.getProductTypeTotal(ptl, c);
                    out.print(total);
                }
                else{
                    session.setAttribute("country", null);
                    
                    String ptl = "";
                    
                                        
                    if(request.getParameter("Choices")!= "")
                    {
                        ptl = getProductType(request.getParameter("Choices"));
                    }
                    if("getWebsiteInfoByCountry".equals(methodName))//展示模块，按国家显示网站信息
                    {
                       out.print(websiteService.getWorldMapWebsiteInfo(ptl));
                    }
                    else if("getBrandInfoByCountry".equals(methodName))//展示模块，按国家显示品牌信息
                    {
                        out.print(brandService.getWorldMapBrandInfo(ptl));
                    }
                    else if("getProductInfoByCountry".equals(methodName))//展示模块，按国家显示商品信息
                    {  
                      out.print(productService.getWorldMapProductInfo(ptl));
                    }
                    else if("getProductTypeInfoByCountry".equals(methodName))//展示模块，按国家显示商品类别信息
                    {
                        out.print(productTypeService.getWorldMapProductTypeInfo(ptl));
                    }
                    else if("getProductDefineInfoByCountry".equals(methodName)){
                        out.print(productDefinitionService.getWorldMapProductTypeInfo(ptl));
                    }
                }
                
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }else{
            String task = (String)request.getSession().getAttribute("method");
            Country c = (Country) request.getSession().getAttribute("country");
            String ptl = (String)request.getSession().getAttribute("ptl");
            String start = (String)request.getParameter("start");
            String len = (String)request.getParameter("length");
            String order = (String)request.getParameter("order[0][column]");
            String dir = (String)request.getParameter("order[0][dir]");
            if("WebsiteExcelInit".equals(task)){
                String str = "";
                List<Object[]> dss = websiteService.getWebsitePagingList(ptl, c, start, len, order, dir);
                for(Object[] ds:dss){
                    str += WebsiteService.getWebsiteJsonInfo(ds)+",";
                }
                if (str.length() > 0){
                    str = str.substring(0, str.length() - 1);
                }
                String total = websiteService.getWebsiteTotalByProduct(ptl, c);
                out.print("{\"recordsTotal\":\""+total+"\",\"recordsFiltered\":\""+total+"\",\"webList\":[" + str + "]}");
            }else if("ProductDefExcelInit".equals(task)){
                String str = "";
                List<Object[]> dss = productDefinitionService.getProductDefinitionPagingList(ptl, c, start, len, order, dir);
                for(Object[] ds:dss){
                    str += productDefinitionService.getProductDefinitionJsonInfo(ds)+",";
                }
                if (str.length() > 0){
                    str = str.substring(0, str.length() - 1);
                }
                String total = productDefinitionService.getProductDefTotalByProduct(ptl, c);
                out.print("{\"recordsTotal\":\""+total+"\",\"recordsFiltered\":\""+total+"\",\"webList\":[" + str + "]}");
            }else if("ProductExcelInit".equals(task)){
                String str = "";
                List<Object[]> dss = productService.getProductPagingList(ptl, c, start, len, order, dir);
                for(Object[] ds:dss){
                    str += productService.getProductJsonInfo(ds)+",";
                }
                if (str.length() > 0){
                    str = str.substring(0, str.length() - 1);
                }
                String total = productService.getProductTotalByProduct(ptl, c);
                out.print("{\"recordsTotal\":\""+total+"\",\"recordsFiltered\":\""+total+"\",\"webList\":[" + str + "]}");
            }
            else if("BrandExcelInit".equals(task)){
                String str = "";
                List<Object[]> dss = brandService.getBrandtPagingList(ptl, c, start, len, order, dir);
                for(Object[] ds:dss){
                    str += brandService.getBrandJsonInfo(ds)+",";
                }
                if (str.length() > 0){
                    str = str.substring(0, str.length() - 1);
                }
                String total = brandService.getBrandTotalByProduct(ptl, c);
                out.print("{\"recordsTotal\":\""+total+"\",\"recordsFiltered\":\""+total+"\",\"webList\":[" + str + "]}");
            }else if("ProductTypeExcelInit".equals(task)){
                String str = "";
                List<Object[]> dss = productTypeService.getProductPagingList(ptl, c, start, len, order, dir);
                for(Object[] ds:dss){
                    str += productTypeService.getProductJsonInfo(ds)+",";
                }
                if (str.length() > 0){
                    str = str.substring(0, str.length() - 1);
                }
                String total = productTypeService.getProductTypeTotal(ptl, c);
                out.print("{\"recordsTotal\":\""+total+"\",\"recordsFiltered\":\""+total+"\",\"webList\":[" + str + "]}");
            }
        }
    }
    public String getProductType(String items){
        Long pt = (long) -1;
        String ptl = "";
        if(items != ""){
            for (String item : (items.split("<>")))
            {
                String[] itemArray = item.split("\\|");
                
                Long pt1 =Long.parseLong(itemArray[0]);
                Long pt2 =Long.parseLong(itemArray[1]);
                Long pt3 =Long.parseLong(itemArray[2]);
                
                if(pt1 != 0 && pt2 == 0 && pt3 == 0){
                    pt = pt1;
                }  
                else if (pt1!=0 && pt2!=0 && pt3 == 0){
                    pt = pt2;
                }
                else if (pt1 != 0 && pt2!=0 && pt3 != 0){
                    pt = pt3;
                }
                if(pt != -1)
                    ptl += "'"+pt+"',"; 
            }
        }
        if(ptl.length() > 0)
            ptl = ptl.substring(0, ptl.length()-1);
        return ptl;
    }

}
