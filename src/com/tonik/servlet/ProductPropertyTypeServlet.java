package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.ProductPropertyType;
import com.tonik.service.ProductPropertyTypeService;



/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuxutong
 * @web.servlet name="productPropertyTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductPropertyTypeServlet"
 */

public class ProductPropertyTypeServlet extends BaseServlet
{

   private ProductPropertyTypeService productPropertyTypeService;


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
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
                if("sel".equalsIgnoreCase(methodName)){//返回ID为id的类型的所有子类型列表
                    Long id = Long.parseLong(request.getParameter("id"));
                    List<ProductPropertyType> pts = new ArrayList<ProductPropertyType>();
                    String strJson = "";
                    pts = productPropertyTypeService.getChildProductPropertyType(id);
                    for (ProductPropertyType item : pts)
                    {
                        strJson += productPropertyTypeService.getProductPropertyTypeInfo(item) + ",";
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
                }
                else if("getRootType".equalsIgnoreCase(methodName))//获取第一级类型列表
                {
                    String strJson = "";
                    List<ProductPropertyType> ls = productPropertyTypeService.getRootProductPropertyType();
                    for (ProductPropertyType item : ls)
                    {
                        strJson += productPropertyTypeService.getProductPropertyTypeInfo(item) + ",";
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
                response.getWriter().write("false");
            }
        }

    }
}
