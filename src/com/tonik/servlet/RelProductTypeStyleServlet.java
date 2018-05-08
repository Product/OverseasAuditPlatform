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
import com.tonik.service.RelProductTypeStyleService;

/**
 * @web.servlet name="relProductTypeStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/RelProductTypeStyleServlet"
 */
public class RelProductTypeStyleServlet extends BaseServlet
{
    private RelProductTypeStyleService relProductTypeStyleService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        relProductTypeStyleService = (RelProductTypeStyleService) ctx.getBean("RelProductTypeStyleService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("queryList".equals(reqMap.get("methodName")))
            {
                Long productType = request.getParameter("productType") == null
                        || "".equals(request.getParameter("productType")) ? null
                                : Long.parseLong(request.getParameter("productType"));
                String result = relProductTypeStyleService.getRelProductTypeStyles(
                        Integer.parseInt(reqMap.get("pageIndex")), Integer.parseInt(reqMap.get("pageSize")),
                        productType);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                Long id = (reqMap.get("id") == null || reqMap.get("id").isEmpty()) ? null
                        : Long.parseLong(reqMap.get("id"));
                Long productType = Long.parseLong(reqMap.get("productType"));
                Long productStyle = Long.parseLong(reqMap.get("productStyle"));
                Integer status = Integer.parseInt(reqMap.get("status"));
                String result = relProductTypeStyleService.saveRelProductTypeStyle(id, productType, productStyle,
                        status);
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = relProductTypeStyleService.delRelProductTypeStyle(id);
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
