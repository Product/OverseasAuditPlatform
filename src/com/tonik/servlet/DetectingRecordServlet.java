package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.model.DetectingRecord;
import com.tonik.model.UserInfo;
import com.tonik.service.DetectingRecordService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测记录模块 Servlet层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="detectingRecordServlet"
 * @web.servlet-mapping url-pattern="/servlet/DetectingRecordServlet"
 */
public class DetectingRecordServlet extends BaseServlet
{
    private DetectingRecordService DetectingRecordService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式


    public DetectingRecordService getDetectingRecordService()
    {
        return DetectingRecordService;
    }

    public void setDetectingRecordService(DetectingRecordService DetectingRecordService)
    {
        this.DetectingRecordService = DetectingRecordService;
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        DetectingRecordService = (DetectingRecordService) ctx.getBean("DetectingRecordService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
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
                        strEndTime = dateFormater.format(new Date());
                    }

                    String strTotal = DetectingRecordService.DetectingRecordTotal(strQuery, strStraTime, strEndTime);
                    List<DetectingRecord> ls = DetectingRecordService.DetectingRecordPaging(
                            Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime,
                            strEndTime);
                    String strJson = "";

                    for (DetectingRecord item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Website\":\"" + item.getWebsite().getName()
                                + "\",\"Product\":\"" + item.getProduct() + "\",\"Organization\":\""
                                + item.getOrganization() + "\",\"Sample\":\"" + item.getSample() + "\",\"Qualified\":\""
                                + item.getQualified() + "\",\"DetectingTime\":\""
                                + dateFormater.format(item.getDetectingTime()) + "\",\"Remark\":\"" + item.getRemark()
                                + "\"},";

                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
                else if ("edit".equals(methodName)) // 编辑检测记录
                {
                    DetectingRecord d = DetectingRecordService
                            .GetDetectingRecordById(Long.parseLong(request.getParameter("id")));

                    d.setProduct(URLDecoder.decode(request.getParameter("product"), "UTF-8"));
                    d.setOrganization(URLDecoder.decode(request.getParameter("organization"), "UTF-8"));
                    d.setSample(Integer.parseInt(request.getParameter("sample")));
                    d.setQualified(Integer.parseInt(request.getParameter("qualified")));
                    d.setDetectingTime(dateFormater.parse(request.getParameter("detectingTime")));// 将字符串格式成Date
                    d.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));

                    DetectingRecordService.SaveDetectingRecord(d,
                            URLDecoder.decode(request.getParameter("website"), "UTF-8"));
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName)) // 添加检测记录
                {
                    DetectingRecord d = new DetectingRecord();
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");

                    d.setProduct(URLDecoder.decode(request.getParameter("product"), "UTF-8"));
                    d.setOrganization(URLDecoder.decode(request.getParameter("organization"), "UTF-8"));
                    d.setSample(Integer.parseInt(request.getParameter("sample")));
                    d.setQualified(Integer.parseInt(request.getParameter("qualified")));
                    d.setDetectingTime(dateFormater.parse(request.getParameter("detectingTime")));// 将字符串格式成Date
                    d.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    d.setCreateTime(new Date());
                    d.setCreatePersonId(ui.getId());
                    d.setCreatePersonName(ui.getRealName());

                    DetectingRecordService.SaveDetectingRecord(d,
                            URLDecoder.decode(request.getParameter("website"), "UTF-8"));
                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName)) // 删除检测记录
                {
                    DetectingRecordService.DelDetectingRecord(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName)) // 初始化检测记录
                {
                    DetectingRecord ws = DetectingRecordService
                            .GetDetectingRecordById(Long.parseLong(request.getParameter("id")));
                    response.getWriter()
                            .write("{\"Id\":\"" + ws.getId() + "\",\"WebsiteId\":\"" + ws.getWebsite().getId()
                                    + "\",\"Website\":\"" + ws.getWebsite().getName() + "\",\"Product\":\""
                                    + ws.getProduct() + "\",\"Organization\":\"" + ws.getOrganization()
                                    + "\",\"Sample\":\"" + ws.getSample() + "\",\"Qualified\":\"" + ws.getQualified()
                                    + "\",\"DetectingTime\":\"" + dateFormater.format(ws.getDetectingTime())
                                    + "\",\"Remark\":\"" + ws.getRemark() + "\",\"CreateTime\":\""
                                    + dateFormater.format(ws.getCreateTime()) + "\"}");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
