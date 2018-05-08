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
import com.tonik.service.EvaluationManagementService;
import com.tonik.service.ProductFeatureService;

/**
 * @web.servlet name="productFeatureServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductFeatureServlet"
 */
public class ProductFeatureServlet extends BaseServlet
{
    private ProductFeatureService productFeatureService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        productFeatureService = (ProductFeatureService) ctx.getBean("ProductFeatureService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("queryList".equals(reqMap.get("methodName")))
            {
                String result = productFeatureService.getProductFeatures(Integer.parseInt(reqMap.get("pageIndex")),
                        Integer.parseInt(reqMap.get("pageSize")));
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                Long id = (reqMap.get("id") == null||reqMap.get("id").isEmpty()) ? null : Long.parseLong(reqMap.get("id"));
                String productType = URLDecoder.decode(reqMap.get("proType"),"UTF-8");
                Integer majorFeature = Integer.parseInt(reqMap.get("features"));
                String optionalFeatures = reqMap.get("option");
                String result = productFeatureService.saveProductFeatures(id, majorFeature, optionalFeatures, productType);
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = productFeatureService.delProductFeatures(id);
                WebUtil.write(response, result);
            }
            else if ("init".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = productFeatureService.getProductFeatures(id);
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
