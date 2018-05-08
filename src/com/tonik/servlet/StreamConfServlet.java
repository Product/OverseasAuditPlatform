package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.service.StreamConfService;
/**
 * 
 * @author fuzhi
 *
 * @web.servlet name="StreamConfServlet"
 * @web.servlet-mapping url-pattern="/servlet/StreamConfServlet"
 */
@SuppressWarnings("serial")
public class StreamConfServlet extends BaseServlet
{
    private StreamConfService streamConfService;
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        streamConfService = (StreamConfService)webApplicationContext.getBean("StreamConfService");
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        
        String methodName = request.getParameter("methodName");
        if(null != methodName && !"".equals(methodName)){
            if(methodName.equals("queryList")){
                int pageSize = Integer.parseInt(URLDecoder.decode(request.getParameter("pageSize"), "UTF-8"));
                int pageIndex = Integer.parseInt(URLDecoder.decode(request.getParameter("pageIndex"), "UTF-8"));
                String result = streamConfService.queryList(pageIndex, pageSize);
                response.getWriter().write(result);
            }else if(methodName.equals("addOrUpdate")){
                String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
                String value = URLDecoder.decode(request.getParameter("value"), "UTF-8");
                String result = streamConfService.AddOrUpdate(id, value);
                response.getWriter().write(result);
            }else if(methodName.equals("queryById")){
                String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
                String result = streamConfService.queryById(id);
                response.getWriter().write(result);
            }else{
                response.getWriter().write("没有此方法名");
            }
        }else{
            response.getWriter().write("方法名不能为空");
        }
    }
}
