package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Material;
import com.tonik.model.MaterialType;
import com.tonik.service.MaterialTypeService;
import com.tonik.service.MaterialsService;

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
 * @web.servlet name="materialsServlet"
 * @web.servlet-mapping url-pattern="/servlet/MaterialsServlet"
 */
@SuppressWarnings("serial")
public class MaterialsServlet extends BaseServlet
{
    private MaterialsService MaterialsService;
    private MaterialTypeService MaterialTypeService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        MaterialsService = (MaterialsService)ctx.getBean("MaterialsService");
        MaterialTypeService = (MaterialTypeService)ctx.getBean("MaterialTypeService");
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
                    String strTotal=""; 
                    strTotal += MaterialsService.MaterialsTotal(strQuery);
                    List<Material> ls = MaterialsService.MaterialPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery);
                    String strJson = "";
                    for(Material item : ls)
                    {
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"Name\":\"" +item.getName() +"\","
                                + "\"MaterialType\":\""+item.getMaterialtype().getName() +"\","
                                + "\"remark\":\"" + item.getRemark() +"\"},";
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
                    Material material = new Material();
                    material.setName(URLDecoder.decode(request.getParameter("MaterialName"),"UTF-8"));
                    material.setRemark(URLDecoder.decode(request.getParameter("MaterialRemark"),"UTF-8"));
                    MaterialType materialType = MaterialTypeService.getMaterialTypeById(Long.parseLong(request.getParameter("materialTypeid")));
                    material.setMaterialtype(materialType);
                    MaterialsService.SaveMaterial(material);
                    response.getWriter().write("{\"Id\":\"" +material.getId() + "\",\"Name\":\"" +material.getName() +"\","
                            + "\"MaterialType\":\""+material.getMaterialtype().getName() +"\","
                            + "\"remark\":\"" + material.getRemark() +"\"}");
                }
                else if("edit".equals(methodName))
                {
                    Material material = MaterialsService.getMaterialById(Long.parseLong(request.getParameter("id")));
                    material.setName(URLDecoder.decode(request.getParameter("MaterialName"),"UTF-8"));
                    material.setRemark(URLDecoder.decode(request.getParameter("MaterialRemark"),"UTF-8"));
                    MaterialType materialType = MaterialTypeService.getMaterialTypeById(Long.parseLong(request.getParameter("materialTypeid")));
                    material.setMaterialtype(materialType);
                    MaterialsService.SaveMaterial(material);
                    response.getWriter().write("{\"Id\":\"" +material.getId() + "\",\"Name\":\"" +material.getName() +"\","
                            + "\"MaterialType\":\""+material.getMaterialtype().getName() +"\","
                            + "\"remark\":\"" + material.getRemark() +"\"}");
                }
                else if("del".equals(methodName))
                {
                    MaterialsService.RemoveMaterial(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if("init".equals(methodName))
                {
                    Material material = MaterialsService.getMaterialById(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("{\"Id\":\"" +material.getId() + "\",\"Name\":\"" +material.getName() +"\","
                                + "\"MaterialType\":\""+material.getMaterialtype().getName() +"\","
                                + "\"MaterialTypeId\":\"" + material.getMaterialtype().getId() +"\","
                                + "\"remark\":\"" + material.getRemark() +"\"}");
                }
                else if("getMaterialList".equals(methodName))//通过原料类别id获得对应的原料列表
                {
                    Long id=Long.parseLong(request.getParameter("id"));
                    List<Material> ls = MaterialsService.getMaterialListByTypeId(id);
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
                }else if("AllQueryList".equals(methodName))
                {
                    List<Material> ls = MaterialsService.getMaterial();
                    String strJson = "";
                    for(Material item : ls)
                    {
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"Name\":\"" +item.getName() +"\"},";
                    }
                    if(strJson.length()>0)
                    {
                        strJson = strJson.substring(0, strJson.length()-1);
                        response.getWriter().write("{\"DataList\":[" + strJson + "]}");
                    }
                    else
                        response.getWriter().write("false");
                }
            }catch(Exception e){
                response.getWriter().write("false");}
            }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
