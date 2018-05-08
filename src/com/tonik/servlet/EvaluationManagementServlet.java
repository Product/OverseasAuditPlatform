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

/**
 * @web.servlet name="evaluationManagementServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationManagementServlet"
 */
public class EvaluationManagementServlet extends BaseServlet
{
    private EvaluationManagementService evaluationManagementService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        evaluationManagementService = (EvaluationManagementService) ctx.getBean("EvaluationManagementService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("add".equals(reqMap.get("methodName")))
            {
                String result = evaluationManagementService.add(Integer.parseInt(reqMap.get("type")),
                        reqMap.get("productType"), URLDecoder.decode(reqMap.get("weights"), "UTF-8"));
                WebUtil.write(response, result);
            }
            else if ("init".equals(reqMap.get("methodName")))
            {
                String result = evaluationManagementService.init(Integer.parseInt(reqMap.get("type")),
                        reqMap.get("productType"), URLDecoder.decode(reqMap.get("strQuery"), "UTF-8"));
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                String result = evaluationManagementService.del(Long.parseLong(reqMap.get("id")));
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
