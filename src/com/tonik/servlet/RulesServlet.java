package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.tonik.model.Area;
import com.tonik.model.Country;
import com.tonik.model.Material;
import com.tonik.model.ProductPropertyType;
import com.tonik.model.ProductType;
import com.tonik.model.Rules;
import com.tonik.model.WebsiteStyle;
import com.tonik.service.AreasService;
import com.tonik.service.CountryService;
import com.tonik.service.MaterialsService;
import com.tonik.service.ProductPropertyTypeService;
import com.tonik.service.ProductTypeService;
import com.tonik.service.RulesService;

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
 * @web.servlet name="rulesServlet"
 * @web.servlet-mapping url-pattern="/servlet/RulesServlet"
 */
public class RulesServlet extends BaseServlet
{
    private RulesService rulesService;    
    private ProductPropertyTypeService productPropertyTypeService;
    private ProductTypeService productTypeService;    
    private MaterialsService materialsService;
    private CountryService countryService;
    private AreasService areasService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        rulesService = (RulesService) ctx.getBean("RulesService");
        productPropertyTypeService = (ProductPropertyTypeService) ctx.getBean("ProductPropertyTypeService");
        productTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
        materialsService = (MaterialsService) ctx.getBean("MaterialsService");
        countryService = (CountryService) ctx.getBean("CountryService");
        areasService = (AreasService) ctx.getBean("AreasService");
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
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {   
                //查询
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");

                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = rulesService.RulesTotal(strQuery, strStraTime, strEndTime);
                    List<Rules> ls = rulesService.RulesPaging(Integer.parseInt(strPageIndex),Integer.parseInt(strPageCount), 
                            strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for (Rules item : ls)
                    {
                        strJson += rulesService.getRulesInfo(item)+",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"rulesList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                //编辑
                else if ("edit".equals(methodName))
                {
                    Rules ws = (Rules)rulesService.GetRulesById(Long.parseLong(request.getParameter("id")));
                    ws.setId(Long.parseLong(request.getParameter("id")));
                    ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ws.setContent(URLDecoder.decode(request.getParameter("content"),"UTF-8"));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setCreateTime(new Date());
//                    HttpSession session = request.getSession();
//                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
//                    ws.setCreatePerson(ui);
                    
                    Set<WebsiteStyle> websiteStyle = new HashSet<WebsiteStyle>();
                    for(String item : request.getParameter("websiteStyle").split("_"))
                    {
                        websiteStyle.add(rulesService.getWebsiteStyleById(Long.parseLong(item)));
                    }
                    ws.setWebsiteStyles(websiteStyle);                    
             
                    int ptlength = Integer.parseInt(request.getParameter("productTypesLength"));
                    Long [] ptl = new Long [ptlength]; 
                    int i = 0;
                                        
                    if(request.getParameter("ProductTypes")!= "")
                    {
                       
                        for (String item : (request.getParameter("ProductTypes")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            Long pt3 =Long.parseLong(itemArray[2]);
                            
                            if(pt1!=0 && pt2 == 0 && pt3 == 0)
                                ptl[i++] =pt1;
                            else if (pt1!=0 && pt2!=0 && pt3 == 0)
                                ptl[i++] = pt2;
                            else if (pt1 != 0 && pt2!=0 && pt3 != 0)
                                ptl[i++] =pt3;
                            
                            
                          
                        }
                    }
                    Set<ProductType> pts = new HashSet<ProductType>();
                    for(int k = 0;k <i; k++ )
                    {
                        ProductType pt = productTypeService.GetProductTypeById(ptl[k]);
                        pts.add(pt);
                    }
                    
                    ws.setProductTypes(pts);
                    //end

                    int pptlength = Integer.parseInt(request.getParameter("productPropertiesLength"));
                    Long [] pptl = new Long [pptlength]; 
                    int j = 0;
                                        
                    if(request.getParameter("ProductProperties")!= "")
                    {
                       
                        for (String item : (request.getParameter("ProductProperties")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            Long pt3 =Long.parseLong(itemArray[2]);
                            
                            if(pt1!=0 && pt2 == 0 && pt3 == 0)
                                pptl[j++] =pt1;
                            else if (pt1!=0 && pt2!=0 && pt3 == 0)
                                pptl[j++] = pt2;
                            else if (pt1 != 0 && pt2!=0 && pt3 != 0)
                                pptl[j++] =pt3;
                            
                            
                          
                        }
                    }
                    Set<ProductPropertyType> ppts = new HashSet<ProductPropertyType>();
                    for(int k = 0;k <j; k++ )
                    {
                        ProductPropertyType ppt = productPropertyTypeService.GetProductPropertyTypeById(pptl[k]);
                        ppts.add(ppt);
                    }
                    
                    ws.setProductPropertyTypes(ppts);
                    //end
                    
                    int mtLength = Integer.parseInt(request.getParameter("materialsLength"));
                    Long [] materialsId = new Long [mtLength]; 
                    int h = 0;
                    if(request.getParameter("Materials")!= "")
                    {
                        for (String item : (request.getParameter("Materials")).split("<>"))
                        {
                            materialsId[h++] = Long.parseLong(item);
                        }
                    }
                    Set<Material> materials = new HashSet<Material>();
                    for(int k = 0;k < h; k++ )
                    {
                        Material material = materialsService.getMaterialById(materialsId[k]);
                        materials.add(material);
                    }
                    ws.setMaterials(materials);
                    //end
                    
                    int rcalength = Integer.parseInt(request.getParameter("rcaChiocesLength"));
                    //国家
                    Long [] countriesId = new Long [rcalength];
                    //地区
                    Long [] areasId = new Long [rcalength]; 
                    int r = 0;
                    int a = 0;
                                        
                    if(request.getParameter("RCAChioces")!= "")
                    {
                       
                        for (String item : (request.getParameter("RCAChioces")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            
                            if(pt2!=0){
                                countriesId[r++] =pt1;
                                areasId[a++] =pt2;
                            }
                            else{
                                countriesId[r++] = pt1;
                            }               
                        }
                    }
                    Set<Country> countries = new HashSet<Country>();
                    for(int k = 0;k <r; k++ )
                    {
                        Country country = countryService.getCountryById(countriesId[k]);
                        countries.add(country);
                    }                    
                    ws.setCountries(countries);
                    Set<Area> areas = new HashSet<Area>();
                    for(int k = 0;k <a; k++ )
                    {
                        Area area = areasService.getAreaById(areasId[k]);
                        areas.add(area);
                    }                    
                    ws.setAreas(areas);
                    //end
                    
                    ws.setRemark(request.getParameter("remark"));
                    rulesService.SaveRules(ws, request.getParameter("RulesDetail"));
                    String strRules = rulesService.getRulesInfo(ws);
                    out.print(strRules);
                }
                //新增
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Rules ws = new Rules();
                    ws.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ws.setContent(URLDecoder.decode(request.getParameter("content"),"UTF-8"));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
//                    HttpSession session = request.getSession();
//                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
//                    ws.setCreatePerson(ui);
                    ws.setCreateTime(new Date());
                    Set<WebsiteStyle> websiteStyle = new HashSet<WebsiteStyle>();
                    for(String item : request.getParameter("websiteStyle").split("_"))
                    {
                        websiteStyle.add(rulesService.getWebsiteStyleById(Long.parseLong(item)));
                    }
                    ws.setWebsiteStyles(websiteStyle);
                    
                    int ptlength = Integer.parseInt(request.getParameter("productTypesLength"));
                    Long [] ptl = new Long [ptlength]; 
                    int i = 0;
                                        
                    if(request.getParameter("ProductTypes")!= "")
                    {
                       
                        for (String item : (request.getParameter("ProductTypes")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            Long pt3 =Long.parseLong(itemArray[2]);
                            
                            if(pt1!=0 && pt2 == 0 && pt3 == 0)
                                ptl[i++] =pt1;
                            else if (pt1!=0 && pt2!=0 && pt3 == 0)
                                ptl[i++] = pt2;
                            else if (pt1 != 0 && pt2!=0 && pt3 != 0)
                                ptl[i++] =pt3;
                            
                            
                          
                        }
                    }
                    Set<ProductType> pts = new HashSet<ProductType>();
                    for(int k = 0;k <i; k++ )
                    {
                        ProductType pt = productTypeService.GetProductTypeById(ptl[k]);
                        pts.add(pt);
                    }
                    
                    ws.setProductTypes(pts);
                    //end

                    int pptlength = Integer.parseInt(request.getParameter("productPropertiesLength"));
                    Long [] pptl = new Long [pptlength]; 
                    int j = 0;
                                        
                    if(request.getParameter("ProductProperties")!= "")
                    {
                       
                        for (String item : (request.getParameter("ProductProperties")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            Long pt3 =Long.parseLong(itemArray[2]);
                            
                            if(pt1!=0 && pt2 == 0 && pt3 == 0)
                                pptl[j++] =pt1;
                            else if (pt1!=0 && pt2!=0 && pt3 == 0)
                                pptl[j++] = pt2;
                            else if (pt1 != 0 && pt2!=0 && pt3 != 0)
                                pptl[j++] =pt3;
                            
                            
                          
                        }
                    }
                    Set<ProductPropertyType> ppts = new HashSet<ProductPropertyType>();
                    for(int k = 0;k <j; k++ )
                    {
                        ProductPropertyType ppt = productPropertyTypeService.GetProductPropertyTypeById(pptl[k]);
                        ppts.add(ppt);
                    }
                    
                    ws.setProductPropertyTypes(ppts);
                    //end
                    
                    int mtLength = Integer.parseInt(request.getParameter("materialsLength"));
                    Long [] materialsId = new Long [mtLength]; 
                    int h = 0;
                    if(request.getParameter("Materials")!= "")
                    {
                        for (String item : (request.getParameter("Materials")).split("<>"))
                        {
                            materialsId[h++] = Long.parseLong(item);
                        }
                    }
                    Set<Material> materials = new HashSet<Material>();
                    for(int k = 0;k < h; k++ )
                    {
                        Material material = materialsService.getMaterialById(materialsId[k]);
                        materials.add(material);
                    }
                    ws.setMaterials(materials);
                    //end
                    
                    int rcalength = Integer.parseInt(request.getParameter("rcaChiocesLength"));
                    //国家
                    Long [] countriesId = new Long [rcalength];
                    //地区
                    Long [] areasId = new Long [rcalength]; 
                    int r = 0;
                    int a = 0;
                                        
                    if(request.getParameter("RCAChioces")!= "")
                    {
                       
                        for (String item : (request.getParameter("RCAChioces")).split("<>"))
                        {
                            String[] itemArray = item.split("\\|");
                            
                            Long pt1 =Long.parseLong(itemArray[0]);
                            Long pt2 =Long.parseLong(itemArray[1]);
                            
                            if(pt2!=0){
                                countriesId[r++] =pt1;
                                areasId[a++] =pt2;
                            }
                            else{
                                countriesId[r++] = pt1;
                            }               
                        }
                    }
                    Set<Country> countries = new HashSet<Country>();
                    for(int k = 0;k <r; k++ )
                    {
                        Country country = countryService.getCountryById(countriesId[k]);
                        countries.add(country);
                    }                    
                    ws.setCountries(countries);
                    Set<Area> areas = new HashSet<Area>();
                    for(int k = 0;k <a; k++ )
                    {
                        Area area = areasService.getAreaById(areasId[k]);
                        areas.add(area);
                    }                    
                    ws.setAreas(areas);
                    //end
                    
                    rulesService.SaveRules(ws, request.getParameter("RulesDetail"));
                    String strRules = rulesService.getRulesInfo(ws);
                    out.print(strRules);
                }
                //删除
                else if ("del".equalsIgnoreCase(methodName))
                {
                    rulesService.DelRules(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                //编辑、查询页面初始化
                else if ("init".equalsIgnoreCase(methodName))
                {
                    String rrs="";
                    Rules rs = rulesService.GetRulesById(Long.parseLong(request.getParameter("id")));
                    rrs = rulesService.getRulesInfo(rs);
                    out.print(rrs);
                    
                }
//                    //网站类型
//                    String strJson1="";
//                    for(WebsiteStyle item : rules.getWebsiteStyles())
//                    {
//                        strJson1 += "{\"Id1\":\"" + item.getId() + "\"},";
//                    }
//                    if (strJson1.length() > 0)
//                    {
//                        strJson1 = strJson1.substring(0, strJson1.length() - 1);
//                    }
//                    //商品类型
//                    String strJson2="";
//                    for(ProductStyle item : rules.getProductStyles())
//                    {
//                        strJson2 += "{\"Id2\":\"" + item.getId() + "\"},";
//                    }
//                    if (strJson2.length() > 0)
//                    {
//                        strJson2 = strJson2.substring(0, strJson2.length() - 1);
//                    }
//                    //其余字段
//                    String strRules = rulesService.getRulesInfo(rules);
//                    if (strRules.length() > 0)
//                    {
//                        strRules = strRules.substring(0, strRules.length() - 1);
//                    }
//                    out.print(strRules + ", \"wsList\":[" + strJson1 + "]" + ", \"psList\":[" + strJson2 + "]}");
                
                //获取网站类型
                else if ("getWebsiteStyle".equals(methodName))
                {
                    
                    List<WebsiteStyle> WStyle = rulesService.getWebsiteStyle();
                    String strWebStyle = "";
                    for (WebsiteStyle item : WStyle)
                    {
                        strWebStyle += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strWebStyle != "")
                    {
                        strWebStyle = strWebStyle.substring(0, strWebStyle.length() - 1);
                    }

                    response.getWriter().write("{\"WebsiteStyle\":[" + strWebStyle + "]}");
                }
//                //获取商品类别
//                else if ("getProductStyle".equals(methodName))
//                {
//                    List<ProductStyle> PType = rulesService.getProductStyle();
//                    String strProductType = "";
//                    for (ProductStyle item : PType)
//                    {
//                        strProductType += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
//                    }
//                    if (strProductType != "")
//                    {
//                        strProductType = strProductType.substring(0, strProductType.length() - 1);
//                    }
//
//                    response.getWriter().write("{\"ProductStyle\":[" + strProductType + "]}");
//                }
                else if("getMaterial".equals(methodName))
                {
                    List<Material> ls = rulesService.getMaterialsByMaterialTypeId(Long.parseLong(request.getParameter("materialTypeId")));
                    String strJson = "";
                    for(Material item : ls)
                    {
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"Name\":\"" +item.getName() +"\"},";
                    }
                    if(strJson.length()>0)
                    {
                        strJson = strJson.substring(0, strJson.length()-1);
                        response.getWriter().write("{\"webList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
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
