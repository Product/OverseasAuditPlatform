package com.tonik.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.service.ThirdReportService;

/**
 * DESC:
 * @author fuzhi
 * @web.servlet name="ThirdReport"
 * @web.servlet-mapping url-pattern="/servlet/ThirdReport"
 */
@SuppressWarnings("serial")
public class ThirdReportServlet extends BaseServlet
{
    private ThirdReportService thirdReportService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ApplicationContext apc = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        thirdReportService = (ThirdReportService) apc.getBean("ThirdReportService");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        Writer writer = response.getWriter();
        if (sessionCheck(request, response))
        {
            writer.write("sessionOut");
            return;
        }
        try
        {
            String method = request.getParameter("method");
            if (null != method && !"".equals(method))
            {
                if (method.equals("list"))
                {
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String out = thirdReportService.getList(index, size);
                    writer.write(out);
                }else if(method.equals("listAbroad")){
                    String index = request.getParameter("pageIndex");
                    String size = request.getParameter("pageSize");
                    String out = thirdReportService.getListAbroad(index, size);
                    writer.write(out);
                }else if(method.equals("queryById")){
                    String id = request.getParameter("id");
                    String out = thirdReportService.queryById(id);
                    writer.write(out);
                }else{
                    writer.write("没有此方法名");
                }
            }
            else
            {
                writer.write("方法名不能为空");
            }
        } catch (Exception e)
        {
            writer.write("查询出错");
        }
        finally
        {
            writer.flush();
            writer.close();
        }
    }
}
