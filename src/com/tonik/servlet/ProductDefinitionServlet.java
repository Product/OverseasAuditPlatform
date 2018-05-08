package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.tonik.model.Material;
import com.tonik.model.ProductDefinition;
import com.tonik.model.ProductType;
import com.tonik.service.AreasService;
import com.tonik.service.BrandService;
import com.tonik.service.CountryService;
import com.tonik.service.MaterialsService;
import com.tonik.service.ProductDefinitionService;
import com.tonik.service.ProductTypeService;

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
 * @web.servlet name="productDefinitionServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductDefinitionServlet"
 */
@SuppressWarnings("serial")
public class ProductDefinitionServlet extends BaseServlet
{
    private ProductDefinitionService ProductDefinitionService;
    private BrandService BrandService;
    private ProductTypeService ProductTypeService;
    private MaterialsService MaterialsService;
    private CountryService CountryService;
    private AreasService AreasService;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ProductDefinitionService = (ProductDefinitionService) ctx.getBean("ProductDefinitionService");
        BrandService = (BrandService) ctx.getBean("BrandService");
        ProductTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
        MaterialsService = (MaterialsService) ctx.getBean("MaterialsService");
        CountryService = (CountryService) ctx.getBean("CountryService");
        AreasService = (AreasService) ctx.getBean("AreasService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
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
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
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

                    String strTotal = ProductDefinitionService.ProductDefinitionTotal(strQuery, strStraTime,
                            strEndTime);
                    List<ProductDefinition> ls = ProductDefinitionService.ProductDefinitionPaging(
                            Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime,
                            strEndTime);
                    String strJson = "";
                    for (ProductDefinition item : ls)
                    {
                        String Name_EN = "";
                        String Name_CN = "";
                        String Name_other = "";
                        String ProductDefinitionFeature_one = "";
                        String ProductDefinitionFeature_two = "";
                        String ProductDefinitionFeature_three = "";
                        if (item.getFeature_one() != null)
                            ProductDefinitionFeature_one = item.getFeature_one();
                        if (item.getFeature_two() != null)
                            ProductDefinitionFeature_two = item.getFeature_two();
                        if (item.getFeature_three() != null)
                            ProductDefinitionFeature_three = item.getFeature_three();
                        if (item.getName_en() != null)
                            Name_EN = item.getName_en();
                        if (item.getName_cn() != null)
                            Name_CN = item.getName_cn();
                        if (item.getName_other() != null)
                            Name_other = item.getName_other();
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name_EN\":\"" + Name_EN + "\","
                                + "\"Name_CN\":\"" + Name_CN + "\"," + "\"Name_other\":\"" + Name_other + "\","
                                + "\"ProductDefinitionFeature_one\":\"" + ProductDefinitionFeature_one + "\","
                                + "\"ProductDefinitionFeature_two\":\"" + ProductDefinitionFeature_two + "\","
                                + "\"ProductDefinitionFeature_three\":\"" + ProductDefinitionFeature_three + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
                }
                else if ("add".equals(methodName))
                {
                    ProductDefinition productdefinition = new ProductDefinition();
                    productdefinition.setName_cn(URLDecoder.decode(request.getParameter("name_cn"), "UTF-8"));
                    productdefinition.setName_en(URLDecoder.decode(request.getParameter("name_en"), "UTF-8"));
                    productdefinition.setName_other(URLDecoder.decode(request.getParameter("name_other"), "UTF-8"));
                    productdefinition.setFeature_one(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_one"), "UTF-8"));
                    productdefinition.setFeature_two(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_two"), "UTF-8"));
                    productdefinition.setFeature_three(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_three"), "UTF-8"));
                    productdefinition.setFeature_four(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_four"), "UTF-8"));
                    productdefinition.setFeature_five(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_five"), "UTF-8"));
                    productdefinition.setFeature_six(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_six"), "UTF-8"));
                    productdefinition.setManufactor(
                            URLDecoder.decode(request.getParameter("ProductDefinitionManufactor"), "UTF-8"));
                    if (!"0".equals(request.getParameter("ProductDefinitionCountryId")))
                    {
                        Country country = CountryService
                                .getCountryById(Long.parseLong(request.getParameter("ProductDefinitionCountryId")));
                        productdefinition.setCountry(country);
                    }

                    if (!"0".equals(request.getParameter("ProductDefinitionAreaId")))
                    {
                        Area area = AreasService
                                .getAreaById(Long.parseLong(request.getParameter("ProductDefinitionAreaId")));
                        productdefinition.setArea(area);
                    }
                    Brand brand = BrandService.getBrandById(Long.parseLong(request.getParameter("brandId")));
                    ProductType producttype = ProductTypeService
                            .GetProductTypeById(Long.parseLong(request.getParameter("producttypeId")));
                    productdefinition.setBrand(brand);
                    productdefinition.setProducttype(producttype);
                    productdefinition.setCreateTime(new Date());
                    productdefinition.setGrade(new Double(0));
                    // add By YeKai
                    int choicesLength = Integer.parseInt(request.getParameter("choicesLength"));
                    Long[] materialsId = new Long[choicesLength];
                    int i = 0;
                    if (request.getParameter("materialChoices") != "")
                    {
                        for (String item : (request.getParameter("materialChoices")).split("<>"))
                        {
                            materialsId[i++] = Long.parseLong(item);
                        }
                    }
                    Set<Material> materials = new HashSet<Material>();
                    for (int k = 0; k < i; k++)
                    {
                        Material material = MaterialsService.getMaterialById(materialsId[k]);
                        materials.add(material);
                    }
                    productdefinition.setMaterials(materials);
                    // end
                    ProductDefinitionService.SaveProductDefinition(productdefinition);
                    response.getWriter().write("{\"Id\":\"" + productdefinition.getId() + "\"," + "\"Name_EN\":\""
                            + productdefinition.getName_en() + "\"," + "\"Name_CN\":\"" + productdefinition.getName_cn()
                            + "\"," + "\"Name_other\":\"" + productdefinition.getName_other() + "\","
                            + "\"ProductDefinitionFeature_one\":\"" + productdefinition.getFeature_one() + "\","
                            + "\"ProductDefinitionFeature_two\":\"" + productdefinition.getFeature_two() + "\","
                            + "\"ProductDefinitionFeature_three\":\"" + productdefinition.getFeature_three() + "\"}");
                }
                else if ("edit".equals(methodName))
                {
                    ProductDefinition productdefinition = ProductDefinitionService
                            .getProductDefinitionById(Long.parseLong(request.getParameter("id")));
                    productdefinition.setName_cn(URLDecoder.decode(request.getParameter("name_cn"), "UTF-8"));
                    productdefinition.setName_en(URLDecoder.decode(request.getParameter("name_en"), "UTF-8"));
                    productdefinition.setName_other(URLDecoder.decode(request.getParameter("name_other"), "UTF-8"));
                    productdefinition.setFeature_one(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_one"), "UTF-8"));
                    productdefinition.setFeature_two(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_two"), "UTF-8"));
                    productdefinition.setFeature_three(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_three"), "UTF-8"));
                    productdefinition.setFeature_four(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_four"), "UTF-8"));
                    productdefinition.setFeature_five(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_five"), "UTF-8"));
                    productdefinition.setFeature_six(
                            URLDecoder.decode(request.getParameter("ProductDefinitionFeature_six"), "UTF-8"));
                    productdefinition.setManufactor(
                            URLDecoder.decode(request.getParameter("ProductDefinitionManufactor"), "UTF-8"));
                    if (!"0".equals(request.getParameter("ProductDefinitionCountryId")))
                    {
                        Country country = CountryService
                                .getCountryById(Long.parseLong(request.getParameter("ProductDefinitionCountryId")));
                        productdefinition.setCountry(country);
                    }
                    if (!"0".equals(request.getParameter("ProductDefinitionAreaId")))
                    {
                        Area area = AreasService
                                .getAreaById(Long.parseLong(request.getParameter("ProductDefinitionAreaId")));
                        productdefinition.setArea(area);
                    }
                    Brand brand = BrandService.getBrandById(Long.parseLong(request.getParameter("brandId")));
                    ProductType producttype = ProductTypeService
                            .GetProductTypeById(Long.parseLong(request.getParameter("producttypeId")));
                    productdefinition.setBrand(brand);
                    productdefinition.setProducttype(producttype);
                    productdefinition.setCreateTime(new Date());
                    // add By YeKai
                    int choicesLength = Integer.parseInt(request.getParameter("choicesLength"));
                    Long[] materialsId = new Long[choicesLength];
                    int i = 0;
                    if (request.getParameter("materialChoices") != "")
                    {
                        for (String item : (request.getParameter("materialChoices")).split("<>"))
                        {
                            materialsId[i++] = Long.parseLong(item);
                        }
                    }
                    Set<Material> materials = new HashSet<Material>();
                    for (int k = 0; k < i; k++)
                    {
                        Material material = MaterialsService.getMaterialById(materialsId[k]);
                        materials.add(material);
                    }
                    productdefinition.setMaterials(materials);
                    // end
                    ProductDefinitionService.SaveProductDefinition(productdefinition);
                    String Name_EN = "";
                    String Name_CN = "";
                    String Name_other = "";
                    String ProductDefinitionFeature_one = "";
                    String ProductDefinitionFeature_two = "";
                    String ProductDefinitionFeature_three = "";
                    if (productdefinition.getFeature_one() != null)
                        ProductDefinitionFeature_one = productdefinition.getFeature_one();
                    if (productdefinition.getFeature_two() != null)
                        ProductDefinitionFeature_two = productdefinition.getFeature_two();
                    if (productdefinition.getFeature_three() != null)
                        ProductDefinitionFeature_three = productdefinition.getFeature_three();
                    if (productdefinition.getName_en() != null)
                        Name_EN = productdefinition.getName_en();
                    if (productdefinition.getName_cn() != null)
                        Name_CN = productdefinition.getName_cn();
                    if (productdefinition.getName_other() != null)
                        Name_other = productdefinition.getName_other();
                    response.getWriter()
                            .write("{\"Id\":\"" + productdefinition.getId() + "\"," + "\"Name_EN\":\"" + Name_EN + "\","
                                    + "\"Name_CN\":\"" + Name_CN + "\"," + "\"Name_other\":\"" + Name_other + "\","
                                    + "\"ProductDefinitionFeature_one\":\"" + ProductDefinitionFeature_one + "\","
                                    + "\"ProductDefinitionFeature_two\":\"" + ProductDefinitionFeature_two + "\","
                                    + "\"ProductDefinitionFeature_three\":\"" + ProductDefinitionFeature_three + "\"}");
                }
                else if ("del".equals(methodName))
                {
                    ProductDefinitionService.RemoveProductDefinition(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName))
                {
                    ProductDefinition productdefinition = ProductDefinitionService
                            .getProductDefinitionById(Long.parseLong(request.getParameter("id")));
                    String materials = "";
                    for (Material item : productdefinition.getMaterials())
                    {
                        Long materialId = item.getId();
                        String materialName = item.getName();
                        materials += "{\"materialId\":\"" + materialId + "\",\"materialName\":\"" + materialName
                                + "\"},";
                    }
                    if (materials != "")
                    {
                        materials = "[" + materials.substring(0, materials.length() - 1) + "]";
                    }
                    else
                    {
                        materials = "[]";
                    }
                    String areaName = "";
                    Long areaId = 0l;
                    if (productdefinition.getArea() != null)
                    {
                        areaName = productdefinition.getArea().getName();
                        areaId = productdefinition.getArea().getId();
                    }
                    String brandId = "";
                    String brandName = "";
                    if (productdefinition.getBrand() != null)
                    {
                        brandId = Long.toString(productdefinition.getBrand().getId());
                        brandName = productdefinition.getBrand().getName_cn();
                    }
                    String ProductTypeId = "";
                    String ProductTypeName = "";
                    if (productdefinition.getProducttype() != null)
                    {
                        ProductTypeId = Long.toString(productdefinition.getProducttype().getId());
                        ProductTypeName = productdefinition.getProducttype().getName();
                    }
                    String countryId = "";
                    String countryName = "";
                    if (productdefinition.getCountry() != null)
                    {
                        countryId = Long.toString(productdefinition.getCountry().getId());
                        countryName = productdefinition.getCountry().getName();
                    }
                    String Name_EN = "";
                    String Name_CN = "";
                    String Name_other = "";
                    String ProductDefinitionFeature_one = "";
                    String ProductDefinitionFeature_two = "";
                    String ProductDefinitionFeature_three = "";
                    if (productdefinition.getFeature_one() != null)
                        ProductDefinitionFeature_one = productdefinition.getFeature_one();
                    if (productdefinition.getFeature_two() != null)
                        ProductDefinitionFeature_two = productdefinition.getFeature_two();
                    if (productdefinition.getFeature_three() != null)
                        ProductDefinitionFeature_three = productdefinition.getFeature_three();
                    if (productdefinition.getName_en() != null)
                        Name_EN = productdefinition.getName_en();
                    if (productdefinition.getName_cn() != null)
                        Name_CN = productdefinition.getName_cn();
                    if (productdefinition.getName_other() != null)
                        Name_other = productdefinition.getName_other();
                    String ProductDefinitionFeature_four = "";
                    String ProductDefinitionFeature_five = "";
                    String ProductDefinitionFeature_six = "";
                    String Manufactor = "";
                    if (productdefinition.getFeature_four() != null)
                        ProductDefinitionFeature_four = productdefinition.getFeature_four();
                    if (productdefinition.getFeature_five() != null)
                        ProductDefinitionFeature_five = productdefinition.getFeature_five();
                    if (productdefinition.getFeature_six() != null)
                        ProductDefinitionFeature_six = productdefinition.getFeature_six();
                    if (productdefinition.getManufactor() != null)
                        Manufactor = productdefinition.getManufactor();
                    response.getWriter().write("{\"Id\":\"" + productdefinition.getId() + "\"," + "\"Name_EN\":\""
                            + Name_EN + "\"," + "\"Name_CN\":\"" + Name_CN + "\"," + "\"Name_other\":\"" + Name_other
                            + "\"," + "\"ProductDefinitionFeature_one\":\"" + ProductDefinitionFeature_one + "\","
                            + "\"ProductDefinitionFeature_two\":\"" + ProductDefinitionFeature_two + "\","
                            + "\"ProductDefinitionFeature_three\":\"" + ProductDefinitionFeature_three + "\","
                            + "\"BrandId\":\"" + brandId + "\"," + "\"BrandName\":\"" + brandName + "\","
                            + "\"ProductTypeId\":\"" + ProductTypeId + "\"," + "\"ProductTypeName\":\""
                            + ProductTypeName + "\"," + "\"CreateTime\":\"" + productdefinition.getFormatCreateTime()
                            + "\"," + "\"materials\":" + materials + "," + "\"ProductDefinitionFeature_four\":\""
                            + ProductDefinitionFeature_four + "\"," + "\"ProductDefinitionFeature_five\":\""
                            + ProductDefinitionFeature_five + "\"," + "\"ProductDefinitionFeature_six\":\""
                            + ProductDefinitionFeature_six + "\"," + "\"Manufactor\":\"" + Manufactor + "\","
                            + "\"CountryName\":\"" + countryName + "\"," + "\"CountryId\":\"" + countryId + "\","
                            + "\"AreaId\":\"" + areaId + "\"," + "\"AreaName\":\"" + areaName + "\"" + "}");
                }
                else if ("getProDefinitionList".equals(methodName))
                {
                    List<ProductDefinition> pdl = ProductDefinitionService.getProductDefinitionList();
                    String strProDef = "";
                    for (ProductDefinition item : pdl)
                    {
                        strProDef += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName_cn() + "\"},";
                    }
                    if (strProDef != "")
                    {
                        strProDef = strProDef.substring(0, strProDef.length() - 1);
                    }

                    response.getWriter().write("{\"proList\":[" + strProDef + "]}");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
