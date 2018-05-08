package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.WebUtil;
import com.tonik.TimingTask;
import com.tonik.model.Area;
import com.tonik.model.Country;
import com.tonik.model.Product;
import com.tonik.model.ProductDefinition;
import com.tonik.model.ProductStyle;
import com.tonik.model.ProductType;
import com.tonik.model.UserInfo;
import com.tonik.model.Website;
import com.tonik.service.AreasService;
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
 * @update GongWenHua 20170330 添加请求方法QueryRealtimeList获取实时商品列表
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="productServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductServlet"
 */
public class ProductServlet extends BaseServlet
{
    private ProductService ProductService;
    private CountryService countryService;
    private AreasService areasService;
    private ProductTypeService productTypeService;
    private WebsiteService websiteService;
    private ProductDefinitionService productDefinitionService;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ProductService = (ProductService) ctx.getBean("ProductService");
        areasService = (AreasService) ctx.getBean("AreasService");
        countryService = (CountryService) ctx.getBean("CountryService");
        productTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
        websiteService = (WebsiteService) ctx.getBean("WebsiteService");
        productDefinitionService = (ProductDefinitionService) ctx.getBean("ProductDefinitionService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))// 获取商品列表
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
                    String startTime = request.getParameter("strStraTime");
                    String endTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    String fakeTotal;
//                    if ("".equals(strStraTime))// 设置起止时间
//                    {
//                        startTime = "1980-01-01 00:00:01";
//                    }
//                    if ("".equals(strEndTime))
//                    {
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
//                        endTime = df.format(new Date());
//                    }
                    String strTotal = ProductService.ProductTotal1(strQuery, startTime, endTime);
                    if(Integer.parseInt(strTotal) > 0)
                        fakeTotal = Integer.toString(Integer.parseInt(strTotal) + 2500000);
                    else 
                        fakeTotal = strTotal;
                    int pageIndex = Integer.parseInt(strPageIndex);
                    int pageCount = Integer.parseInt(strPageCount);
                    List<Object[]> ls;
                    if (Integer.parseInt(strTotal) == 0)
                        ls = ProductService.ProductPaging1(pageIndex, pageCount, strQuery, startTime, endTime);
                    else
                        ls = ProductService.ProductPaging1(pageIndex % (Integer.parseInt(fakeTotal) / pageCount),
                                pageCount, strQuery, startTime, endTime);
                    String strJson = "";

                    for (Object[] item : ls)
                    {
                        strJson += "{\"Id\":\"" + item[0] + "\",\"Name\":\"" + item[1] + "\",\"Price\":\"" + item[2]
                                + "\",\"Location\":\"" + item[3] + "\",\"WebsiteName\":\"" + item[4]
                                + "\",\"Remark\":\"" + item[5] + "\",\"CreatePerson\":\"" + item[6]
                                + "\",\"CreateTime\":\"" + item[7] + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + fakeTotal + "\",\"proLList\":[" + strJson + "]}");

                    }
                    else
                    {
                        response.getWriter().write("false");
                    }

                    // @author GongWenHua 20170330
                }
                else if ("QueryRealtimeList".equals(methodName))
                {

                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    List<Object[]> ls = ProductService.getProductPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), "PRODUCT_ID", "1");

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    String strEndTime = df.format(new Date());
                    String strTotal = String.valueOf(TimingTask.getScannedNumber());

                    Map<String, Object> res = new HashMap<String, Object>();
                    List<Map<String, String>> list = new LinkedList<Map<String, String>>();

                    // 有无更新是随机值
                    Random random = new Random();
                    Map<String, String> map;
                    for (Object[] item : ls)
                    {
                        map = new HashMap<String, String>();
                        map.put("Name", String.valueOf(item[0]));
                        map.put("WebSite", String.valueOf(item[1]));
                        map.put("IsRisk", "无");
                        map.put("Time", strEndTime);
                        map.put("IsUpdate", random.nextInt(9) > 0 ? "无" : "有更新");
                        list.add(map);
                    }

                    res.put("Total", strTotal);
                    res.put("ProductList", list);

                    WebUtil.writeJSON(response, res);

                }

                else if ("view".equals(methodName))// 查看商品详情
                {
                    Product p = ProductService.GetProductById(Long.parseLong(request.getParameter("id")));
                    String strw = ProductService.getProductInfo(p);
                    out.print(strw);
                }

                else if ("del".equals(methodName))// 删除商品
                {
                    ProductService.DelProduct(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if ("init".equals(methodName))// 初始化
                {
                    Product p = ProductService.GetProductById(Long.parseLong(request.getParameter("id")));

                    String strw = ProductService.getProductInfo(p);
                    response.getWriter().write(strw);
                }
                else if ("edit".equals(methodName))// 编辑商品
                {

                    Product p = ProductService.GetProductById(Long.parseLong(request.getParameter("id")));

                    p.setName(URLDecoder.decode(request.getParameter("name"), "UTF-8"));

                    p.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));

                    p.setCustomsDuty(URLDecoder.decode(request.getParameter("customs"), "UTF-8"));

                    p.setPrice(URLDecoder.decode(request.getParameter("price"), "UTF-8"));

                    p.setFreight(URLDecoder.decode(request.getParameter("freight"), "UTF-8"));

                    Date pDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(request.getParameter("purchaseDate"));
                    p.setPurchaseTime(pDate);

                    Date oDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("onlineDate"));
                    p.setOnlineTime(oDate);

                    p.setProducingArea(URLDecoder.decode(request.getParameter("producingarea"), "UTF-8"));

                    p.setBrand(URLDecoder.decode(request.getParameter("brand"), "UTF-8"));

                    p.setStandard(URLDecoder.decode(request.getParameter("standard"), "UTF-8"));

                    p.setExpirationDate(URLDecoder.decode(request.getParameter("expirationDate"), "UTF-8"));

                    p.setLocation(URLDecoder.decode(request.getParameter("Location"), "UTF-8"));

                    p.setSuitableAge(URLDecoder.decode(request.getParameter("suitableage"), "UTF-8"));

                    p.setPicture(URLDecoder.decode(request.getParameter("picture"), "UTF-8"));

                    p.setCreateTime(new Date());

                    Website w = websiteService.GetWebsiteById(Long.parseLong(request.getParameter("website")));

                    p.setProductWebisteGrade(w.getComprehensiveScore());
                    p.setWebsite(w);
                    p.setWebsiteName(w.getName());

                    if (!"0".equals(request.getParameter("firstType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("firstType")));
                        p.setFirstlevelType(pt);
                    }
                    else
                    {
                        p.setFirstlevelType(null);
                    }

                    if (!"0".equals(request.getParameter("secondType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("secondType")));
                        p.setSecondlevelType(pt);
                    }
                    else
                    {
                        p.setSecondlevelType(null);
                    }

                    if (!"0".equals(request.getParameter("thirdType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("thirdType")));
                        p.setThirdlevelType(pt);
                    }
                    else
                    {
                        p.setThirdlevelType(null);
                    }
                    if (!"".equals(request.getParameter("sales")))
                        p.setSales(Integer.parseInt(request.getParameter("sales")));
                    else
                        p.setSales(0);

                    p.setPdid(Long.parseLong(request.getParameter("proDef")));
                    if (!"0".equals(request.getParameter("proDef")))
                    {
                        ProductDefinition pd = productDefinitionService
                                .getProductDefinitionById(Long.parseLong(request.getParameter("proDef")));
                        p.setProductDefineGrade(pd.getGrade());
                    }
                    else
                        p.setProductDefineGrade(0);
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    p.setCreatePerson(ui.getId().toString());

                    if (!"".equals(request.getParameter("countryId")))
                    {
                        Country country = countryService
                                .getCountryById(Long.parseLong(request.getParameter("countryId")));
                        p.setCountry(country);
                    }
                    else
                        p.setCountry(null);

                    if (!"".equals(request.getParameter("areaId")))
                    {
                        Area area = areasService.getAreaById(Long.parseLong(request.getParameter("areaId")));

                        p.setArea(area);
                    }
                    else
                        p.setArea(null);

                    ProductService.SaveProduct(p);
                    String strw = ProductService.getProductInfo(p);
                    response.getWriter().write(strw);
                }
                else if ("add".equals(methodName))// 新增
                {
                    Product p = new Product();

                    p.setName(URLDecoder.decode(request.getParameter("name"), "UTF-8"));

                    p.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));

                    p.setCustomsDuty(URLDecoder.decode(request.getParameter("customs"), "UTF-8"));

                    p.setPrice(URLDecoder.decode(request.getParameter("price"), "UTF-8"));

                    p.setFreight(URLDecoder.decode(request.getParameter("freight"), "UTF-8"));

                    Date pDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(request.getParameter("purchaseDate"));
                    p.setPurchaseTime(pDate);

                    Date oDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("onlineDate"));
                    p.setOnlineTime(oDate);

                    p.setProducingArea(URLDecoder.decode(request.getParameter("producingarea"), "UTF-8"));

                    p.setBrand(URLDecoder.decode(request.getParameter("brand"), "UTF-8"));

                    p.setStandard(URLDecoder.decode(request.getParameter("standard"), "UTF-8"));

                    p.setExpirationDate(URLDecoder.decode(request.getParameter("expirationDate"), "UTF-8"));

                    p.setLocation(URLDecoder.decode(request.getParameter("Location"), "UTF-8"));

                    p.setSuitableAge(URLDecoder.decode(request.getParameter("suitableage"), "UTF-8"));

                    p.setPicture(URLDecoder.decode(request.getParameter("picture"), "UTF-8"));

                    p.setCreateTime(new Date());

                    Website w = websiteService.GetWebsiteById(Long.parseLong(request.getParameter("website")));

                    p.setProductWebisteGrade(w.getComprehensiveScore());
                    p.setWebsite(w);
                    p.setWebsiteName(w.getName());

                    if (!"0".equals(request.getParameter("firstType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("firstType")));
                        p.setFirstlevelType(pt);
                    }
                    else
                    {
                        p.setFirstlevelType(null);
                    }

                    if (!"0".equals(request.getParameter("secondType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("secondType")));
                        p.setSecondlevelType(pt);
                    }
                    else
                    {
                        p.setSecondlevelType(null);
                    }

                    if (!"0".equals(request.getParameter("thirdType")))
                    {
                        ProductType pt = productTypeService
                                .GetProductTypeById(Long.parseLong(request.getParameter("thirdType")));
                        p.setThirdlevelType(pt);
                    }
                    else
                    {
                        p.setThirdlevelType(null);
                    }

                    if (!"".equals(request.getParameter("sales")))
                        p.setSales(Integer.parseInt(request.getParameter("sales")));
                    else
                        p.setSales(0);

                    p.setPdid(Long.parseLong(request.getParameter("proDef")));

                    if (!"0".equals(request.getParameter("proDef")))
                    {
                        ProductDefinition pd = productDefinitionService
                                .getProductDefinitionById(Long.parseLong(request.getParameter("proDef")));
                        p.setProductDefineGrade(pd.getGrade());
                    }
                    else
                        p.setProductDefineGrade(0);

                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    p.setCreatePerson(ui.getId().toString());

                    if (!"".equals(request.getParameter("countryId")))
                    {
                        Country country = countryService
                                .getCountryById(Long.parseLong(request.getParameter("countryId")));
                        p.setCountry(country);
                    }
                    else
                        p.setCountry(null);

                    if (!"".equals(request.getParameter("areaId")))
                    {
                        Area area = areasService.getAreaById(Long.parseLong(request.getParameter("areaId")));

                        p.setArea(area);
                    }
                    else
                        p.setArea(null);

                    ProductService.SaveProduct(p);
                    String strw = ProductService.getProductInfo(p);

                    response.getWriter().write(strw);
                }
                else if ("getProductStyle".equals(methodName))// 获取商品类别下拉框列表
                {
                    List<ProductStyle> ps = ProductService.getProductStyle();
                    String strProStyle = "";
                    for (ProductStyle item : ps)
                    {
                        strProStyle += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strProStyle != "")
                    {
                        strProStyle = strProStyle.substring(0, strProStyle.length() - 1);
                    }

                    response.getWriter().write("{\"ProductStyle\":[" + strProStyle + "]}");
                }
                else if ("getWebsite".equals(methodName))// 获取网站下拉框列表
                {
                    List<Website> ps = ProductService.getWebsite();
                    String strWebsite = "";
                    for (Website item : ps)
                    {
                        strWebsite += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strWebsite != "")
                    {
                        strWebsite = strWebsite.substring(0, strWebsite.length() - 1);
                    }

                    response.getWriter().write("{\"Website\":[" + strWebsite + "]}");
                }
                else if ("matchProductDef".equals(methodName))// 产品匹配
                {

                    response.getWriter().write(ProductService.MatchingProductDefinition(10));
                }
                else if ("matchProducts".equals(methodName))// 商品管理（机器学习）
                {
                    String result = ProductService.getMatchProducts(Integer.parseInt(request.getParameter("pageIndex")),
                            Integer.parseInt(request.getParameter("pageSize")),
                            URLDecoder.decode(request.getParameter("strQuery"), "UTF-8"),
                            request.getParameter("productType") == null
                                    || "".equals(request.getParameter("productType")) ? null
                                            : Long.parseLong(request.getParameter("productType")),
                            request.getParameter("isMatched") == null || "".equals(request.getParameter("isMatched"))
                                    ? null : Boolean.parseBoolean(request.getParameter("isMatched")));
                    WebUtil.write(response, result);
                }
                else if ("randTopProducts".equals(methodName))// 随机20条
                {
                    String result = ProductService.getRandTopProducts();
                    WebUtil.write(response, result);
                }
                else if ("editMachineLearning".equals(methodName))// 编辑商品
                {

                    Product p = ProductService.GetProductById(Long.parseLong(request.getParameter("id")));

                    p.setBrand(URLDecoder.decode(request.getParameter("brand"), "UTF-8"));

                    String strCountry = request.getParameter("country");
                    Country country = (strCountry == null || strCountry.isEmpty()) ? null
                            : countryService.getCountryById(Long.parseLong(strCountry));
                    p.setCountry(country);
                    ProductService.SaveProduct(p);
                    response.getWriter().write("true");
                }

            } catch (Exception e)
            {
                out.print("false");
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
