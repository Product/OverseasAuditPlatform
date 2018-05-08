package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.WebUtil;
import com.tonik.service.ThirdProductTypeService;

/**
 * @web.servlet name="thirdProductTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/ThirdProductTypeServlet"
 */
public class ThirdProductTypeServlet extends BaseServlet
{
    private ThirdProductTypeService thirdProductTypeService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        thirdProductTypeService = (ThirdProductTypeService) ctx.getBean("ThirdProductTypeService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("list".equals(reqMap.get("methodName")))
            {
                String strParent = reqMap.get("parent");
                Long parent = strParent == null || "".equals(strParent) ? null : Long.parseLong(strParent);
                Long thirdWebsite = Long.parseLong(reqMap.get("thirdWebsite"));
                String result = thirdProductTypeService.listThirdProductTypes(parent, thirdWebsite);
                WebUtil.write(response, result);
            }
            else if ("get".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = thirdProductTypeService.getThirdProductType(id);
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = thirdProductTypeService.removeThirdProductType(id);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                String strId = reqMap.get("id");
                Long id = (strId == null || strId.isEmpty()) ? null : Long.parseLong(strId);
                String strParent = reqMap.get("parent");
                Long parent = strParent == null || "".equals(strParent) ? null : Long.parseLong(strParent);
                String name = URLDecoder.decode(reqMap.get("name"), "UTF-8");
                String remark = URLDecoder.decode(reqMap.get("remark"), "UTF-8");
                Long thirdWebsite = Long.parseLong(reqMap.get("thirdWebsite"));
                String result = thirdProductTypeService.saveThirdProductTypes(id, name, parent, remark, thirdWebsite);
                WebUtil.write(response, result);
            }
            else if ("listRelTypes".equals(reqMap.get("methodName")))
            {
                String strThirdWebsite = reqMap.get("thirdWebsite");
                Long thirdWebsite = strThirdWebsite == null || "".equals(strThirdWebsite) ? null
                        : Long.parseLong(strThirdWebsite);
                String strQuery = URLDecoder.decode(reqMap.get("strQuery"), "UTF-8");
                String result = thirdProductTypeService.listRelTypes(thirdWebsite,
                        Integer.parseInt(reqMap.get("pageIndex")), Integer.parseInt(reqMap.get("pageSize")), strQuery);
                WebUtil.write(response, result);
            }
            else if ("saveRelType".equals(reqMap.get("methodName")))
            {
                Long thirdProductType = Long.parseLong(reqMap.get("thirdProductType"));
                Long productType = Long.parseLong(reqMap.get("productType"));
                String result = thirdProductTypeService.saveRelType(thirdProductType, productType);
                WebUtil.write(response, result);
            }
            else if ("delRelType".equals(reqMap.get("methodName")))
            {
                Long thirdProductType = Long.parseLong(reqMap.get("thirdProductType"));
                String result = thirdProductTypeService.delRelType(thirdProductType);
                WebUtil.write(response, result);
            }
            else
            {
                response.getWriter().write("false");
            }
        } catch (Exception e)
        {
            response.getWriter().write("false");
        }
    }

}
