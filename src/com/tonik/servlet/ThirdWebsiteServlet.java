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
import com.tonik.service.ThirdWebsiteService;

/**
 * @web.servlet name="thirdWebsiteServlet"
 * @web.servlet-mapping url-pattern="/servlet/ThirdWebsiteServlet"
 */
public class ThirdWebsiteServlet extends BaseServlet
{
    private ThirdWebsiteService thirdWebsiteService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        thirdWebsiteService = (ThirdWebsiteService) ctx.getBean("ThirdWebsiteService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("list".equals(reqMap.get("methodName")))
            {
                String result = thirdWebsiteService.listThirdWebsites();
                WebUtil.write(response, result);
            }
            else if ("del".equals(reqMap.get("methodName")))
            {
                Long id = Long.parseLong(reqMap.get("id"));
                String result = thirdWebsiteService.removeThirdWebsite(id);
                WebUtil.write(response, result);
            }
            else if ("save".equals(reqMap.get("methodName")))
            {
                String strId = reqMap.get("id");
                Long id = (strId == null || strId.isEmpty()) ? null : Long.parseLong(strId);
                String name = URLDecoder.decode(reqMap.get("name"), "UTF-8");
                String result = thirdWebsiteService.saveThirdWebsite(id, name);
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
