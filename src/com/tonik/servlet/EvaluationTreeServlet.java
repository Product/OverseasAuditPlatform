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
import com.tonik.service.EvaluationTreeService;
import com.tonik.service.ThirdWebsiteService;

/**
 * @web.servlet name="evaluationTreeServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationTreeServlet"
 */
public class EvaluationTreeServlet extends BaseServlet
{
    private EvaluationTreeService evaluationTreeService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        evaluationTreeService = (EvaluationTreeService) ctx.getBean("EvaluationTreeService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("list".equals(reqMap.get("methodName")))
            {
                String result = evaluationTreeService.listEvaluationTrees();
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = evaluationTreeService.removeEvaluationTree(id);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                String strId = reqMap.get("id");
                Long id = (strId == null || strId.isEmpty()) ? null : Long.parseLong(strId);
                String name = URLDecoder.decode(reqMap.get("name"), "UTF-8");
                String result = evaluationTreeService.saveEvaluationTree(id, name);
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
