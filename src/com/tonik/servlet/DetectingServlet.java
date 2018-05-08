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
import com.tonik.model.DetectingEvent;
import com.tonik.model.UserInfo;
import com.tonik.service.DetectingService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测事件模块 Servlet层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="detectingServlet"
 * @web.servlet-mapping url-pattern="/servlet/DetectingServlet"
 */
public class DetectingServlet extends BaseServlet
{
    private DetectingService DetectingService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式


    public DetectingService getDetectingService()
    {
        return DetectingService;
    }

    public void setDetectingService(DetectingService DetectingService)
    {
        this.DetectingService = DetectingService;
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        DetectingService = (DetectingService) ctx.getBean("DetectingService");
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
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"), "UTF-8");
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

                    String strTotal = DetectingService.DetectingTotal(strQuery, strStraTime, strEndTime);
                    List<DetectingEvent> ls = DetectingService.DetectingPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (DetectingEvent item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Website\":\"" + item.getWebsite().getName()
                                + "\",\"EventType\":\"" + item.getEventType() + "\",\"ProductNum\":\""
                                + item.getProductNum() + "\",\"DetectingDate\":\""
                                + dateFormater.format(item.getDetectingDate()) + "\",\"Remark\":\"" + item.getRemark()
                                + "\",\"CreatePerson\":\"" + item.getCreatePersonName() + "\",\"CreateTime\":\""
                                + item.getCreateTime() + "\"},";
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
                else if ("edit".equals(methodName)) // 编辑检测事件
                {
                    DetectingEvent d = DetectingService.GetDetectingById(Long.parseLong(request.getParameter("id")));

                    d.setEventType(URLDecoder.decode(request.getParameter("eventType"), "UTF-8"));
                    d.setProductNum(Integer.parseInt(request.getParameter("productNum")));
                    d.setDetectingDate(dateFormater.parse(request.getParameter("detectingTime")));// 将字符串格式成Date
                    d.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    DetectingService.SaveDetecting(d, URLDecoder.decode(request.getParameter("website"), "UTF-8"));// 保存website
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName)) // 添加检测事件
                {
                    DetectingEvent d = new DetectingEvent();

                    d.setEventType(URLDecoder.decode(request.getParameter("eventType"), "UTF-8"));
                    d.setProductNum(Integer.parseInt(request.getParameter("productNum")));
                    d.setDetectingDate(dateFormater.parse(request.getParameter("detectingTime")));// 将字符串格式成Date
                    d.setRemark(URLDecoder.decode(request.getParameter("remark"), "UTF-8"));
                    d.setCreateTime(new Date());
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    d.setCreatePersonId(ui.getId());
                    d.setCreatePersonName(ui.getRealName());
                    DetectingService.SaveDetecting(d, URLDecoder.decode(request.getParameter("website"), "UTF-8"));// 保存website

                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName)) // 删除检测事件
                {
                    DetectingService.DelDetecting(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");

                }
                else if ("init".equals(methodName)) // 初始化检测事件
                {
                    DetectingEvent ws = DetectingService.GetDetectingById(Long.parseLong(request.getParameter("id")));
                    String str = "{\"Id\":\"" + ws.getId() + "\",\"WebsiteId\":\"" + ws.getWebsite().getId()
                            + "\",\"Website\":\"" + ws.getWebsite().getName() + "\",\"EventType\":\""
                            + ws.getEventType() + "\",\"ProductNum\":\"" + ws.getProductNum()
                            + "\",\"DetectingDate\":\"" + dateFormater.format(ws.getDetectingDate())
                            + "\",\"Remark\":\"" + ws.getRemark() + "\",\"CreatePerson\":\"" + ws.getCreatePersonName()
                            + "\",\"CreateTime\":\"" + dateFormater.format(ws.getCreateTime()) + "\"}";
                    response.getWriter().write(str);
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
