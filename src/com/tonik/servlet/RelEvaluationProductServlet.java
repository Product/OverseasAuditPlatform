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
import com.tonik.service.RelEvaluationProductService;

/**
 * @web.servlet name="relEvaluationProductServlet"
 * @web.servlet-mapping url-pattern="/servlet/RelEvaluationProductServlet"
 */
public class RelEvaluationProductServlet extends BaseServlet
{
    private RelEvaluationProductService relEvaluationProductService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        relEvaluationProductService = (RelEvaluationProductService) ctx.getBean("RelEvaluationProductService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("queryList".equals(reqMap.get("methodName")))
            {
                String strQuery = URLDecoder.decode(reqMap.get("strQuery"), "UTF-8");
                String tempET = reqMap.get("evaluationType");
                Integer evaluationType =tempET == null || "".equals(tempET) ? null : Integer.parseInt(tempET);
                String productType = reqMap.get("productTypeId");
                Long productTypeId = productType == null || "".equals(productType) ? null : Long.parseLong(productType);
                String result = relEvaluationProductService.getRelEvaluationProducts(
                        Integer.parseInt(reqMap.get("pageIndex")), Integer.parseInt(reqMap.get("pageSize")),
                        productTypeId, evaluationType, strQuery);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                Long productId = Long.parseLong(reqMap.get("productId"));
                Integer evaluationType = Integer.parseInt(reqMap.get("evaluationType"));
                String productType = reqMap.get("productTypeId");
                Long productTypeId = productType == null || "".equals(productType) ? null : Long.parseLong(productType);
                String score = URLDecoder.decode(reqMap.get("score"), "UTF-8");
                String result = relEvaluationProductService.saveRelEvaluationProduct(evaluationType, productTypeId, productId, score);
                WebUtil.write(response, result);
            }
            else if ("init".equals(reqMap.get("methodName")))
            {
                String productType = reqMap.get("productTypeId");
                Long productTypeId = productType == null || "".equals(productType) ? null : Long.parseLong(productType);
                String result = relEvaluationProductService.initRelEvaluationProduct(Integer.parseInt(reqMap.get("evaluationType")), productTypeId, Long.parseLong(reqMap.get("productId")));
                WebUtil.write(response, result);
            }
            else if ("delete".equals(reqMap.get("methodName")))
            {
                String productType = reqMap.get("productTypeId");
                Long productTypeId = productType == null || "".equals(productType) ? null : Long.parseLong(productType);
                String result = relEvaluationProductService.delRelEvaluationProducts(Integer.parseInt(reqMap.get("evaluationType")), productTypeId, Long.parseLong(reqMap.get("productId")));
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
