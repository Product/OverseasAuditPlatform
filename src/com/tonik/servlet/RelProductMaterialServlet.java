package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.RelProductMaterial;
import com.tonik.service.RelProductMaterialService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Apr 26, 2016
 * @version 1.0
 * @author liuyu
 * @web.servlet name="relProductMaterialServlet"
 * @web.servlet-mapping url-pattern="/servlet/RelProductMaterialServlet"
 */
public class RelProductMaterialServlet extends BaseServlet
{
    private static final long serialVersionUID = 1L;
    private RelProductMaterialService relProductMaterialService;
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        relProductMaterialService = (RelProductMaterialService) ctx.getBean("relProductMaterialService");
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
                {
                    Long productId = Long.parseLong(request.getParameter("productId"));
                    List<Object[]> productMaterialList =relProductMaterialService.getProductMaterialsByProductId(productId);
                    String str = "";
                    for (Object[] relProductMaterial : productMaterialList)
                    {
                        DecimalFormat df = new DecimalFormat("#.0000");

                        str += "{\"Id\":\"" + relProductMaterial[0] + "\",\"MaterialId\":\"" + relProductMaterial[1] + "\",\"MaterialName\":\""
                            +  relProductMaterial[2] + "\",\"MaterialContent\":\"" + df.format(relProductMaterial[3])
                            +  "\",\"Unit\":\"" + relProductMaterial[4] + "\"},";
                    }
                    if(str.length()> 0){
                        str = str.substring(0, str.length()-1);
                    }
                    response.getWriter().print("{\"DataList\":[" + str + "]}");
                }else if("add".equals(methodName)){//新增
                    Long productId = Long.parseLong(request.getParameter("productId"));
                    Long materialId = Long.parseLong(request.getParameter("materialId"));
                    float materialContent = Float.parseFloat(request.getParameter("materialContent"));
                    String unit = URLDecoder.decode(request.getParameter("unit"), "UTF-8");
                    RelProductMaterial relProductmaterial = new RelProductMaterial();
                    relProductmaterial.setProductId(productId);
                    relProductmaterial.setMaterialId(materialId);
                    relProductmaterial.setContent(materialContent);
                    relProductmaterial.setUnit(unit);
                    relProductMaterialService.saveRelProductMaterial(relProductmaterial);
                    response.getWriter().print("true");
                }else if("edit".equals(methodName)){//编辑
                    Long id = Long.parseLong(request.getParameter("id"));
                    Long productId = Long.parseLong(request.getParameter("productId"));
                    Long materialId = Long.parseLong(request.getParameter("materialId"));
                    float materialContent = Float.parseFloat(request.getParameter("materialContent"));
                    String unit = URLDecoder.decode(request.getParameter("unit"), "UTF-8");
                    RelProductMaterial relProductmaterial = relProductMaterialService.getProductMaterial(id);
                    relProductmaterial.setProductId(productId);
                    relProductmaterial.setMaterialId(materialId);
                    relProductmaterial.setContent(materialContent);
                    relProductmaterial.setUnit(unit);
                    relProductMaterialService.saveRelProductMaterial(relProductmaterial);
                    response.getWriter().print("true");
                }else if("del".equals(methodName)){
                    Long id = Long.parseLong(request.getParameter("id"));
                    relProductMaterialService.delProductMaterial(id);
                    response.getWriter().print("true");
                }
                    
            }catch(Exception e){
                response.getWriter().print("false");
            }
        }
    }
}
