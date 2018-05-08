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
import com.tonik.service.ProductTrivialNameService;

/**
 * @web.servlet name="productTrivialNameServlet"
 * @web.servlet-mapping url-pattern="/servlet/ProductTrivialNameServlet"
 */
public class ProductTrivialNameServlet extends BaseServlet
{
    private ProductTrivialNameService productTrivialNameService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        productTrivialNameService = (ProductTrivialNameService) ctx.getBean("ProductTrivialNameService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("queryList".equals(reqMap.get("methodName")))
            {
                String productTrivialName = URLDecoder.decode(reqMap.get("productTrivialName"), "UTF-8");
                String productScientificName = URLDecoder.decode(reqMap.get("productScientificName"), "UTF-8");
                String result = productTrivialNameService.getProductTrivialNames(
                        Integer.parseInt(reqMap.get("pageIndex")), Integer.parseInt(reqMap.get("pageSize")),
                        productTrivialName, productScientificName);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                Long id = (reqMap.get("id") == null || reqMap.get("id").isEmpty()) ? null
                        : Long.parseLong(reqMap.get("id"));
                String productTrivialName = URLDecoder.decode(reqMap.get("productTrivialName"), "UTF-8");
                String productScientificName = URLDecoder.decode(reqMap.get("productScientificName"), "UTF-8");
                String result = productTrivialNameService.saveProductTrivialName(id, productTrivialName,
                        productScientificName);
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = productTrivialNameService.delProductTrivialName(id);
                WebUtil.write(response, result);
            }
            else if ("init".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = productTrivialNameService.getProductTrivialName(id);
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
