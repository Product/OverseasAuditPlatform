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

import com.thinvent.utils.WebUtil;
import com.tonik.model.EvaluationIndex;
import com.tonik.model.UserInfo;
import com.tonik.service.EvaluationIndexService;

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
 * @web.servlet name="evaluationIndexServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationIndexServlet"
 */
public class EvaluationIndexServlet extends BaseServlet
{
    private EvaluationIndexService EvaluationIndexService;


    public EvaluationIndexService getEvaluationIndexService()
    {
        return EvaluationIndexService;
    }

    public void setEvaluationIndexService(EvaluationIndexService EvaluationIndexService)
    {
        this.EvaluationIndexService = EvaluationIndexService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        EvaluationIndexService = (EvaluationIndexService) ctx.getBean("EvaluationIndexService");
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
                    String strTotal = EvaluationIndexService.EvaluationIndexTotal(strQuery, strStraTime, strEndTime);
        
                    String strJson = "";
                    List<EvaluationIndex> ls = EvaluationIndexService.EvaluationIndexPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    for (EvaluationIndex item : ls)
                    {
                        strJson += EvaluationIndexService.getEvaluationIndexInfo(item) + ",";
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
                   EvaluationIndex ei = new EvaluationIndex();
                    ei.setId(Long.parseLong(request.getParameter("id")));
                    ei.setCreateTime(new Date());
                    ei.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ei.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ei.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ei.setNone_max(Integer.parseInt(request.getParameter("nonemax")));
                    ei.setNone_min(Integer.parseInt(request.getParameter("nonemin")));
                    ei.setOne_max(Integer.parseInt(request.getParameter("onemax")));
                    ei.setOne_min(Integer.parseInt(request.getParameter("onemin")));
                    ei.setTwo_max(Integer.parseInt(request.getParameter("twomax")));
                    ei.setTwo_min(Integer.parseInt(request.getParameter("twomin")));
                    ei.setThree_max(Integer.parseInt(request.getParameter("threemax")));
                    ei.setThree_min(Integer.parseInt(request.getParameter("threemin")));
                    ei.setScore_no(Integer.parseInt(request.getParameter("scoreno")));
                    ei.setScore_yes(Integer.parseInt(request.getParameter("scoreyes")));
                    ei.setType(Integer.parseInt(request.getParameter("type")));
                    ei.setWeight(Integer.parseInt(request.getParameter("weight")));
                    EvaluationIndexService.SaveEvaluationIndex(ei, request.getParameter("style"));
                    String res = EvaluationIndexService.getEvaluationIndexInfo(ei);
                    
                    out.print(res);
                }
                else if ("add".equalsIgnoreCase(methodName))
                {
                    EvaluationIndex ei = new EvaluationIndex();
                    ei.setCreateTime(new Date());
                    ei.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    ei.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    ei.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    ei.setNone_max(Integer.parseInt(request.getParameter("nonemax")));
                    ei.setNone_min(Integer.parseInt(request.getParameter("nonemin")));
                    ei.setOne_max(Integer.parseInt(request.getParameter("onemax")));
                    ei.setOne_min(Integer.parseInt(request.getParameter("onemin")));
                    ei.setTwo_max(Integer.parseInt(request.getParameter("twomax")));
                    ei.setTwo_min(Integer.parseInt(request.getParameter("twomin")));
                    ei.setThree_max(Integer.parseInt(request.getParameter("threemax")));
                    ei.setThree_min(Integer.parseInt(request.getParameter("threemin")));
                    ei.setScore_no(Integer.parseInt(request.getParameter("scoreno")));
                    ei.setScore_yes(Integer.parseInt(request.getParameter("scoreyes")));
                    ei.setType(Integer.parseInt(request.getParameter("type")));
                    ei.setWeight(Integer.parseInt(request.getParameter("weight")));
                    EvaluationIndexService.SaveEvaluationIndex(ei, request.getParameter("style"));
                    String res = EvaluationIndexService.getEvaluationIndexInfo(ei);
                    
                    out.print(res);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    EvaluationIndexService.DelEvaluationStyle(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                else if("init".equalsIgnoreCase(methodName))
                {
                    String res="";
                    String strId = request.getParameter("id");
                    EvaluationIndex ei = EvaluationIndexService.GetEvaluationStyleById(Long.parseLong(strId));
                
                    res = EvaluationIndexService.getEvaluationIndexInfo(ei);
                    
                    out.print(res);
                }
                else if("allIndices".equalsIgnoreCase(methodName))
                {
                    String result = EvaluationIndexService.getAllIndices();
                    out.println(result);
                }
                else if ("save".equalsIgnoreCase(methodName))
                {
                    String strId = request.getParameter("id");
                    Long id = (strId == null || strId.isEmpty()) ? null : Long.parseLong(strId);
                    String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
                    String evaluationStyle =request.getParameter("evaluationStyle");
                    EvaluationIndex ei = new EvaluationIndex();
                    ei.setId(id);
                    ei.setName(name);
                    EvaluationIndexService.SaveEvaluationIndex(ei, evaluationStyle);
                    WebUtil.write(response, "true");
                }
                else if ("get".equalsIgnoreCase(methodName))
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    String result = EvaluationIndexService.getEvaluationIndex(id);
                    WebUtil.write(response, result);
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
