package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.DateUtil;
import com.thinvent.utils.WebUtil;
import com.tonik.service.ChattingMessageService;
import com.tonik.service.EvaluationManagementService;
import com.tonik.service.ProductFeatureService;
import com.tonik.service.ProductTrivialNameService;

/**
 * @web.servlet name="chattingMessageServlet"
 * @web.servlet-mapping url-pattern="/servlet/ChattingMessageServlet"
 */
public class ChattingMessageServlet extends BaseServlet
{
    private ChattingMessageService chattingMessageService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        chattingMessageService = (ChattingMessageService) ctx.getBean("ChattingMessageService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("chattingRecord".equals(reqMap.get("methodName")))
            {
                String strQuery = URLDecoder.decode(reqMap.get("strQuery"), "UTF-8");
                Date startTime = DateUtil.parseDate(DateUtil.DEFAULT_FORMAT, reqMap.get("startTime"));
                String result = chattingMessageService.getChattingMessages(Integer.parseInt(reqMap.get("pageIndex")),
                        Integer.parseInt(reqMap.get("pageSize")), strQuery, startTime);
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
