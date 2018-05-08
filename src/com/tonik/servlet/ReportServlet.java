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

import com.tonik.model.Report;
import com.tonik.model.ReportTemplate;
import com.tonik.model.UserInfo;
import com.tonik.service.ReportService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="reportServlet"
 * @web.servlet-mapping url-pattern="/servlet/ReportServlet"
 */
public class ReportServlet extends BaseServlet
{
    private ReportService ReportService;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        ReportService = (ReportService) ctx.getBean("ReportService");
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
    
                    String strTotal = ReportService.ReportTotal(strQuery, strStraTime, strEndTime);
                    List<Report> ls = ReportService.ReportPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
    
                    for (Report item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"ReportTemplateId\":\"" + item.getReportTemplate().getId() + "\",\"ReportTemplateName\":\"" + item.getReportTemplate().getName() + "\",\"ReportContent\":\""
                                + item.getReportContent() + "\",\"ReportStatus\":\"" + item.getReportStatus() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
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
                //编辑保存
                else if ("edit".equalsIgnoreCase(methodName))
                {
                    Report report = (Report)ReportService.GetReportById(Long.parseLong(request.getParameter("id")));
                    report.setName(URLDecoder.decode(request.getParameter("reportName"),"UTF-8"));
                    report.setReportTemplate(ReportService.getReportTemplateById(Long.parseLong(request.getParameter("reportTemplate"))));
                    report.setReportContent(URLDecoder.decode(request.getParameter("reportContent"),"UTF-8"));
                    report.setReportStatus(URLDecoder.decode(request.getParameter("reportStatus"),"UTF-8"));
                    report.setCreateTime(new Date());
                    report.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ReportService.SaveReport(report);
                    out.print("true");
                }
                //添加保存
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Report report = new Report();
                    report.setName(URLDecoder.decode(request.getParameter("reportName"),"UTF-8"));
                    report.setReportTemplate(ReportService.getReportTemplateById(Long.parseLong(request.getParameter("reportTemplate"))));
                    report.setReportContent(URLDecoder.decode(request.getParameter("reportContent"),"UTF-8"));
                    report.setReportStatus(URLDecoder.decode(request.getParameter("reportStatus"),"UTF-8"));
                    report.setEvaluationFile(null);
                    report.setCreateTime(new Date());
                    report.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ReportService.SaveReport(report);
                    out.print("true");
                }
                //删除
                else if ("del".equalsIgnoreCase(methodName))
                {
                    ReportService.DelReport(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                //编辑、查询页面初始化
                else if("init".equalsIgnoreCase(methodName))
                {
                    Report report = ReportService.GetReportById(Long.parseLong(request.getParameter("id")));
                    out.print("{\"Id\":\"" + report.getId() + "\",\"Name\":\"" + report.getName() + "\",\"ReportTemplate\":\"" + report.getReportTemplate().getId() + "\",\"ReportContent\":\""
                                + report.getReportContent() + "\",\"ReportStatus\":\"" + report.getReportStatus() + "\",\"CreateTime\":\"" + report.getCreateTime() + "\"}");
                }
                //获取报告模板列表信息并返回
                else if ("getReportTemplate".equals(methodName))
                {
                    List<ReportTemplate> reportTemplate = ReportService.getReportTemplate();
                    String strReportTemplate = "";
                    for (ReportTemplate item : reportTemplate)
                    {
                        strReportTemplate += "{\"Id\":\"" + item.getId() + "\", \"Name\":\"" + item.getName() + "\"},";
                    }
                    if (strReportTemplate != "")
                    {
                        strReportTemplate = strReportTemplate.substring(0, strReportTemplate.length() - 1);
                    }

                    response.getWriter().write("{\"ReportTemplate\":[" + strReportTemplate + "]}");
                }
                else if ("changeFile".equals(methodName))
                {
                    ReportTemplate rp = ReportService.getReportTemplateById(Long.parseLong(request.getParameter("id")));
                    out.print("{\"Id\":\"" + rp.getId() + "\",\"Name\":\"" + rp.getName() + "\",\"TemplateStyle\":\"" + rp.getTemplateStyle() + "\",\"TemplateContent\":\""
                                + rp.getTemplateContent() + "\",\"TemplateFile\":\"" + rp.getTemplateFile() + "\",\"CreatePerson\":\"" + rp.getCreatePerson()
                                + "\",\"CreateTime\":\"" + rp.getCreateTime() + "\"}");
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