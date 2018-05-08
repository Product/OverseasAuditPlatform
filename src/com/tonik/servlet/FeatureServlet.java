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
import com.tonik.service.FeatureService;
import com.tonik.service.RelEvaluationProductService;

/**
 * @web.servlet name="featureServlet"
 * @web.servlet-mapping url-pattern="/servlet/FeatureServlet"
 */
public class FeatureServlet extends BaseServlet
{
    private FeatureService featureService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        featureService = (FeatureService) ctx.getBean("FeatureService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("initList".equals(reqMap.get("methodName")))
            {
                String result = featureService.listAllFeatures();
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                String strId = reqMap.get("id");
                Long id = strId == null || "".equals(strId) ? null : Long.parseLong(strId);
                String name = URLDecoder.decode(reqMap.get("name"), "UTF-8");
                String result = featureService.saveFeature(id, name);
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
