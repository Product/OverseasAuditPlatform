package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.tonik.model.ProductPropertyType;
import com.tonik.model.ProductType;
import com.tonik.model.UserInfo;
import com.tonik.service.ProductPropertyTypeService;
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
 * @author zby
 * @web.servlet name="productTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductTypeServlet"
 */
public class ProductTypeServlet extends BaseServlet
{
    private ProductTypeService productTypeService;
    
    private ProductPropertyTypeService productPropertyTypeService;

    public ProductTypeService getProductTypeService()
    {
        return productTypeService;
    }

    public void setProductTypeService(ProductTypeService productTypeService)
    {
        this.productTypeService = productTypeService;
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        productTypeService = (ProductTypeService) ctx.getBean("ProductTypeService");
        productPropertyTypeService = (ProductPropertyTypeService) ctx.getBean("ProductPropertyTypeService");
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
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    String strLevel = request.getParameter("strLevel");

                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
                    String strTotal = productTypeService.ProductTypeTotal(strQuery, strStraTime, strEndTime, Integer.parseInt(strLevel));
        
                    String strJson = "";
                    List<ProductType> ls = productTypeService.ProductTypePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime, Integer.parseInt(strLevel));
                    for (ProductType item : ls)
                    {
                        strJson += productTypeService.getProductTypeInfo(item) + ",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                else if("typeDIR".equalsIgnoreCase(methodName))
                {
                    Long productTypeId = Long.parseLong(request.getParameter("productTypeId"));
                    String strJson = productTypeService.getProductTypeDirectory(productTypeId);
                    out.print(strJson);
                }
                else if("typeDIRWithNum".equalsIgnoreCase(methodName))
                {
                    Long productTypeId = Long.parseLong(request.getParameter("productTypeId"));
                    String strJson = productTypeService.getChildProductTypeWithPDNum(productTypeId);
                    out.print(strJson);
                }
               else if ("edit".equalsIgnoreCase(methodName))
                {
                   ProductType pt = productTypeService.GetProductTypeById(Long.parseLong(request.getParameter("id")));
                    
                    pt.setId(Long.parseLong(request.getParameter("id")));
                    pt.setCreateTime(new Date());
                    pt.setPtid(Long.parseLong(URLDecoder.decode(request.getParameter("ptid"),"UTF-8")));
                    pt.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    pt.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    pt.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    
                    int clength = Integer.parseInt(request.getParameter("choiceslength"));
                    Long [] ptl = new Long [clength]; 
                    int i = 0;
                                        
                    if(request.getParameter("PropertyTypeChoices")!= "")
                    {
                       
                        for (String item : (request.getParameter("PropertyTypeChoices")).split("<>"))
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
                    Set<ProductPropertyType> ppts = new HashSet<ProductPropertyType>();
                    for(int k = 0;k <i; k++ )
                    {
                        ProductPropertyType ppt = productPropertyTypeService.GetProductPropertyTypeById(ptl[k]);
                        if(ppts.contains(ppt))
                            continue;
                        ppts.add(ppt);
                    }
                    
                    pt.setPropertyTypes(ppts);
                   
                    productTypeService.SaveProductType(pt);
                    String res = productTypeService.getProductTypeInfo(pt);
                    
                    out.print(res);
                }
                else if ("add".equalsIgnoreCase(methodName))
                {
                    ProductType pt = new ProductType();
                    pt.setCreateTime(new Date());
                    pt.setPtid(Long.parseLong(URLDecoder.decode(request.getParameter("ptid"),"UTF-8")));
                    pt.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    pt.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    pt.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    
                    //add by lxt
                    int clength = Integer.parseInt(request.getParameter("choiceslength"));
                    Long [] ptl = new Long [clength]; 
                    int i = 0;
                                        
                    if(request.getParameter("PropertyTypeChoices")!= "")
                    {
                       
                        for (String item : (request.getParameter("PropertyTypeChoices")).split("<>"))
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
                    Set<ProductPropertyType> ppts = new HashSet<ProductPropertyType>();
                    for(int k = 0;k <i; k++ )
                    {
                        ProductPropertyType ppt = productPropertyTypeService.GetProductPropertyTypeById(ptl[k]);
                        if(ppts.contains(ppt))
                            continue;
                        
                        ppts.add(ppt);
                    }
                    
                    pt.setPropertyTypes(ppts);
                   
                    
                    productTypeService.SaveProductType(pt);
                    String res = productTypeService.getProductTypeInfo(pt);
                    
                    out.print(res);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    Boolean res = productTypeService.DelProductType(Long.parseLong(request.getParameter("id")));
                    
                    out.print(res.toString());
                }
                else if("init".equalsIgnoreCase(methodName))
                {
                    String res="";
                    String strId = request.getParameter("id");
                    ProductType pt = productTypeService.GetProductTypeById(Long.parseLong(strId));
                
                    res = productTypeService.getProductTypeInfo(pt);
                    
                    out.print(res);
                }
                else if("level".equalsIgnoreCase(methodName))
                {
                    String res = productTypeService.getProductTypeTotal();
                    
                    out.print(res);
                }
                else if("sel".equalsIgnoreCase(methodName)){
                    Long id = Long.parseLong(request.getParameter("id"));
                    List<ProductType> pts = new ArrayList<ProductType>();
                    String strJson = "";
                    pts = productTypeService.getChildProductType(id);
                    for (ProductType item : pts)
                    {
                        strJson += productTypeService.getProductTypeInfo(item) + ",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("{\"webList\":[]}");
                    }
                }else if("selfa".equalsIgnoreCase(methodName)){
                    Long id = Long.parseLong(request.getParameter("id"));
                    List<ProductType> pts = new ArrayList<ProductType>();
                    ProductType pt = productTypeService.GetProductTypeById(id);
                    String str = "";
                    String strJson = "[", strSel="";
                    while(pt.getPtid() != 0){
                        pt = productTypeService.GetProductTypeById(pt.getPtid());
                        pts = productTypeService.getChildProductType(pt.getPtid());
                        str = "";
                        for (ProductType item : pts){
                            str += productTypeService.getProductTypeInfo(item) + ",";
                        }
                        if (str.length() > 1){
                            str = str.substring(0, str.length() - 1);
                            strJson += "[" + str + "],";
                            strSel += pt.getId()+",";
                        }
                    }
                    if (strJson.length() > 1){
                        strJson = strJson.substring(0, strJson.length() - 1);   
                    }
                    if (strSel.length() > 1){
                        strSel = strSel.substring(0, strSel.length() - 1);   
                    }
                    strJson += "]";
                    out.print("{\"data\":"+strJson+", \"selected\":["+strSel+"]}");
                }
                
                //add by lxt(获取商品分类的第一级菜单)
                else if("getRootType".equalsIgnoreCase(methodName))
                {
                    String strJson = "";
                    List<ProductType> ls = productTypeService.getRootProductType();
                    for (ProductType item : ls)
                    {
                        strJson += productTypeService.getProductTypeInfo(item) + ",";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                
                }
            } catch (Exception e)
            {
                out.println("false");
                out.println(e.getMessage());
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
