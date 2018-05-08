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

import com.tonik.model.Evaluation;
import com.tonik.model.UserInfo;
import com.tonik.service.EvaluationService;
import com.tonik.service.WebsiteService;

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
 * @web.servlet name="evaluationServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationServlet"
 */
public class EvaluationServlet extends BaseServlet
{
    private EvaluationService EvaluationService;
    private WebsiteService WebsiteService;


    public EvaluationService getEvaluationService()
    {
        return EvaluationService;
    }

    public void setEvaluationService(EvaluationService EvaluationService)
    {
        this.EvaluationService = EvaluationService;
    }

    public WebsiteService getWebsiteService()
    {
        return WebsiteService;
    }

    public void setWebsiteService(WebsiteService websiteService)
    {
        WebsiteService = websiteService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        EvaluationService = (EvaluationService) ctx.getBean("EvaluationService");
        WebsiteService = (WebsiteService) ctx.getBean("WebsiteService");
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
                    //out.println(strQuery);
                    String strTotal = EvaluationService.EvaluationTotal(strQuery, strStraTime, strEndTime);
                    //out.println(strTotal);
                    List<Evaluation> ls = EvaluationService.EvaluationPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for(Evaluation item:ls){
                        strJson += EvaluationService.getEvaluationInfo(item)+",";
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
               else if ("edit".equalsIgnoreCase(methodName))
                {
                   Evaluation e = EvaluationService.GetEvaluationById(Long.parseLong(request.getParameter("id")));
                   e.setCreateTime(new Date());
                   e.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                   e.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                   EvaluationService.SaveEvaluation(e, request.getParameter("website"));
                   
                   String res = EvaluationService.getEvaluationInfo(e);
                   
                   out.print(res);
                }
               else if ("pub".equalsIgnoreCase(methodName))
               {
                  Evaluation e = EvaluationService.GetEvaluationById(Long.parseLong(request.getParameter("id")));
                  e.setEvaluationStatus(2);
                  e.setCreateTime(new Date());
                  e.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                  EvaluationService.SaveEvaluation(e); 
                  out.print("true");
               }
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Evaluation e = new Evaluation();
                    e.setCreateTime(new Date());
                    e.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    e.setEvaluationStatus(0);
                    e.setScore(0);
                    e.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    EvaluationService.SaveEvaluation(e, request.getParameter("website"));
                    String res = EvaluationService.getEvaluationInfo(e);
                    out.print(res);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    EvaluationService.DelEvaluation(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if("init".equalsIgnoreCase(methodName))
                {
                    Evaluation e = EvaluationService.GetEvaluationById(Long.parseLong(request.getParameter("id")));
                    String res = EvaluationService.getEvaluationInfo(e);
                    out.print(res);
                }
            } catch (Exception e)
            {
                out.println("false1");
                out.println(e.getMessage());
            }
            finally
            {
                out.flush();
                out.close();
            }
        }
    }
}
