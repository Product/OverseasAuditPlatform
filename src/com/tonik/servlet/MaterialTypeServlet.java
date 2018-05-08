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
 * @web.servlet name="materialTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/MaterialTypeServlet"
 */
@SuppressWarnings("serial")
public class MaterialTypeServlet extends BaseServlet
{
    private MaterialTypeService MaterialTypeService;
    private MaterialsService MaterialsService;
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        MaterialTypeService =(MaterialTypeService)ctx.getBean("MaterialTypeService");
        MaterialsService = (MaterialsService)ctx.getBean("MaterialsService");
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
                    
                    String strTotal = MaterialTypeService.MaterialTypeTotal(strQuery);
                    List<MaterialType> ls = MaterialTypeService.MaterialTypePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery);
                    String strJson = "";
                    for(MaterialType item : ls)
                    {
                        strJson += "{\"Id\":\"" +item.getId() + "\",\"Name\":\"" +item.getName() +"\"},";
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
                    MaterialType materialtype = new MaterialType();
                    materialtype.setName(URLDecoder.decode(request.getParameter("MaterialTypeName"),"UTF-8"));
                    MaterialTypeService.SaveMaterialType(materialtype);
                    response.getWriter().write("{\"Id\":\"" +materialtype.getId() + "\",\"Name\":\"" +materialtype.getName() +"\"}");
                }
                else if("edit".equals(methodName))
                {
                    MaterialType materialtype = MaterialTypeService.getMaterialTypeById(Long.parseLong(request.getParameter("id")));
                    materialtype.setName(URLDecoder.decode(request.getParameter("MaterialTypeName"),"UTF-8"));
                    MaterialTypeService.SaveMaterialType(materialtype);
                    response.getWriter().write("{\"Id\":\"" +materialtype.getId() + "\",\"Name\":\"" +materialtype.getName() +"\"}");
                }
                else if("del".equals(methodName))
                {
                    if(MaterialsService.getMaterialsById(Long.parseLong(request.getParameter("id")))>0)
                    {
                        response.getWriter().write("error");
                    }
                    else{
                        MaterialTypeService.RemoveMaterialType(Long.parseLong(request.getParameter("id")));
                        response.getWriter().write("true");
                    }
                }
                else if("init".equals(methodName))
                {
                    MaterialType materialtype = MaterialTypeService.getMaterialTypeById(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("{\"Id\":\"" +materialtype.getId() + "\",\"Name\":\"" +materialtype.getName() +"\"}");
                }
                else if("getMaterialTypeList".equals(methodName))
                {
                    List<MaterialType> ls = MaterialTypeService.getMaterialTypeList();
                    String strJson = "";
                    for(MaterialType item : ls)
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
               
            }catch(Exception e){
                response.getWriter().write("false");
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
