package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.ReportTemplate;
import com.tonik.model.UserInfo;
import com.tonik.service.ReportTemplateService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author yekai
 * @web.servlet name="reportTemplateServlet"
 * @web.servlet-mapping url-pattern="/servlet/ReportTemplateServlet"
 */
public class ReportTemplateServlet extends BaseServlet
{
    private ReportTemplateService ReportTemplateService;

    public ReportTemplateService getReportTemplateService()
    {
        return ReportTemplateService;
    }

    public void setReportTemplateService(ReportTemplateService reportTemplateService)
    {
        ReportTemplateService = reportTemplateService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ReportTemplateService = (ReportTemplateService) ctx.getBean("ReportTemplateService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if(this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                //查询
                if ("QueryList".equalsIgnoreCase(methodName))
                {
                    String strQuery = request.getParameter("strQuery");
                    String strStraTime = request.getParameter("strStraTime");
                    String strEndTime = request.getParameter("strEndTime");
                    String strPageIndex = request.getParameter("strPageIndex");
                    String strPageCount = request.getParameter("strPageCount");
                    
                    if ("".equals(strStraTime))
                    {
                        strStraTime = "1980-01-01 00:00:01";
                    }
                    if ("".equals(strEndTime))
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
    
                    String strTotal = ReportTemplateService.ReportTemplateTotal(strQuery, strStraTime, strEndTime);
                    
                    List<ReportTemplate> ls = ReportTemplateService.ReportTemplatePaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for (ReportTemplate item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"TemplateStyle\":\"" + item.getTemplateStyle() + "\",\"TemplateContent\":\""
                                + item.getTemplateContent() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
                                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        out.print("false");
                    }
                }
                //编辑
                else if ("edit".equalsIgnoreCase(methodName))
                {
                    ReportTemplate rp = new ReportTemplate();
                    rp.setId(Long.parseLong(request.getParameter("id")));
                    rp.setName(URLDecoder.decode(request.getParameter("templateName"),"UTF-8"));
                    rp.setTemplateStyle(URLDecoder.decode(request.getParameter("templateStyle"),"UTF-8"));
                    rp.setTemplateContent(URLDecoder.decode(request.getParameter("templateContent"),"UTF-8"));
                    rp.setTemplateFile(URLDecoder.decode(request.getParameter("templateFile"),"UTF-8"));
                    rp.setCreateTime(new Date());
                    ReportTemplateService.SaveReportTemplate(rp);
                    String res = ReportTemplateService.getReportTemplateInfo(rp);
                    out.print(res);
                }
                //添加
                else if ("add".equalsIgnoreCase(methodName))
                {
                    ReportTemplate rp = new ReportTemplate();
                    rp.setName(URLDecoder.decode(request.getParameter("templateName"),"UTF-8"));
                    rp.setTemplateStyle(URLDecoder.decode(request.getParameter("templateStyle"),"UTF-8"));
                    rp.setTemplateContent(URLDecoder.decode(request.getParameter("templateContent"),"UTF-8"));
                    rp.setTemplateFile(URLDecoder.decode(request.getParameter("templateFile"),"UTF-8"));
                    rp.setCreateTime(new Date());
                    rp.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ReportTemplateService.SaveReportTemplate(rp);
                    String res = ReportTemplateService.getReportTemplateInfo(rp);
                    out.print(res);
                }
                //删除
                else if ("del".equalsIgnoreCase(methodName))
                {
                    ReportTemplateService.DelReportTemplate(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                //编辑、查看页面初始化
                else if("init".equalsIgnoreCase(methodName))
                {
                    ReportTemplate rp = ReportTemplateService.GetReportTemplateById(Long.parseLong(request.getParameter("id")));
                    out.print("{\"Id\":\"" + rp.getId() + "\",\"Name\":\"" + rp.getName() + "\",\"TemplateStyle\":\"" + rp.getTemplateStyle() + "\",\"TemplateContent\":\""
                                + rp.getTemplateContent() + "\",\"TemplateFile\":\"" + rp.getTemplateFile() + "\",\"CreatePerson\":\"" + rp.getCreatePerson()
                                + "\",\"CreateTime\":\"" + rp.getCreateTime() + "\"}");
                }
                //获取报告模板文件，可能要删！
                else if ("getRTFile".equals(methodName))
                {
                    ReportTemplate rp = ReportTemplateService.GetReportTemplateById(Long.parseLong(request.getParameter("id")));
                    String strRTFile = "";
                    strRTFile = rp.getTemplateFile();
                    out.print("{\"TemplateFile\":\"" + rp.getTemplateFile() + "\"}");
                }
            } catch (Exception e)
            {
                out.print("false");
            }
        }
        out.flush();
        out.close();
    }
}
