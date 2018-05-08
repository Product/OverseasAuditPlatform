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
import com.tonik.model.DetectingEvaluation;
import com.tonik.model.UserInfo;
import com.tonik.service.DetectingEvaluationService;


/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description:检测评价模块  Servlet层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @web.servlet name="detectingEvaluationServlet"
 * @web.servlet-mapping url-pattern="/servlet/DetectingEvaluationServlet"
 */
public class DetectingEvaluationServlet extends BaseServlet
{

    private DetectingEvaluationService DetectingEvaluationService;
    private SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式

    public DetectingEvaluationService getDetectingEvaluationService()
    {
        return DetectingEvaluationService;
    }

    public void setDetectingEvaluationService(DetectingEvaluationService DetectingEvaluationService)
    {
        this.DetectingEvaluationService = DetectingEvaluationService;
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        DetectingEvaluationService = (DetectingEvaluationService) ctx.getBean("DetectingEvaluationService");
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

                    String strTotal = DetectingEvaluationService.DetectingEvaluationTotal(strQuery, strStraTime, strEndTime);
                    List<DetectingEvaluation> ls = DetectingEvaluationService.DetectingEvaluationPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";

                    for (DetectingEvaluation item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() 
                                + "\",\"Website\":\"" + item.getWebsite().getName()
                                + "\",\"ProductNum\":\"" + item.getProductNum()
                                + "\",\"Complaints\":\"" + item.getComplaints()
                                + "\",\"OverallRating\":\"" + item.getOverallRating()                             
                                + "\",\"Remark\":\""+ item.getRemark() 
                                + "\",\"EndTime\":\""+ dateFormater.format(item.getDetectingEndTime())
                                + "\",\"CreatePerson\":\"" + item.getCreatePersonName()
                                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
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
                else if ("edit".equals(methodName))//编辑检测评价
                {
                    DetectingEvaluation ws =DetectingEvaluationService.GetDetectingEvaluationById(Long.parseLong(request.getParameter("id")));
                   
                    ws.setProductNum(Integer.parseInt(request.getParameter("productNum")));
                    ws.setComplaints(Integer.parseInt(request.getParameter("complaints")));
                    ws.setOverallRating(Integer.parseInt(request.getParameter("overallRating")));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setDetectingEndTime(dateFormater.parse(request.getParameter("endTime")));

                    DetectingEvaluationService.SaveDetecting(ws,URLDecoder.decode(request.getParameter("website"),"UTF-8"));
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName))//添加检测评价
                {
                    DetectingEvaluation ws = new DetectingEvaluation();
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    
                    ws.setProductNum(Integer.parseInt(request.getParameter("productNum")));
                    ws.setComplaints(Integer.parseInt(request.getParameter("complaints")));
                    ws.setOverallRating(Integer.parseInt(request.getParameter("overallRating")));
                    ws.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ws.setDetectingEndTime(dateFormater.parse(request.getParameter("endTime")));
                    ws.setCreateTime(new Date());
                    ws.setCreatePersonId(ui.getId());
                    ws.setCreatePersonName(ui.getRealName());
                    
                    DetectingEvaluationService.SaveDetecting(ws,URLDecoder.decode(request.getParameter("website"),"UTF-8"));
                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName))//删除检测评价
                {
                    DetectingEvaluationService.DelDetectingEvaluation(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if("init".equals(methodName))//初始化检测评价
                {
                    DetectingEvaluation ws = DetectingEvaluationService.GetDetectingEvaluationById(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("{\"Id\":\"" + ws.getId()   
                            + "\",\"WebsiteId\":\"" + ws.getWebsite().getId()
                            + "\",\"Website\":\"" + ws.getWebsite().getName()
                            + "\",\"ProductNum\":\"" + ws.getProductNum()
                            + "\",\"Complaints\":\"" + ws.getComplaints()
                            + "\",\"OverallRating\":\"" + ws.getOverallRating()                             
                            + "\",\"Remark\":\""+ ws.getRemark() 
                            + "\",\"EndTime\":\""+ dateFormater.format(ws.getDetectingEndTime())
                            + "\",\"CreatePerson\":\"" + ws.getCreatePersonName()
                            + "\",\"CreateTime\":\"" + dateFormater.format(ws.getCreateTime())+ "\"}");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }

    }
}
