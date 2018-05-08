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

import com.tonik.model.EvaluationType;
import com.tonik.model.UserInfo;
import com.tonik.service.EvaluationTypeService;

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
 * @web.servlet name="evaluationTypeServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationTypeServlet"
 */
public class EvaluationTypeServlet extends BaseServlet
{
    private EvaluationTypeService evaluationTypeService;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        evaluationTypeService = (EvaluationTypeService) ctx.getBean("EvaluationTypeService");
    }
    public EvaluationTypeService getEvaluationTypeService()
    {
        return evaluationTypeService;
    }

    public void setEvaluationTypeService(EvaluationTypeService evaluationTypeService)
    {
        this.evaluationTypeService = evaluationTypeService;
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
               if("QueryList".equals(methodName))
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
                
                String strTotal = evaluationTypeService.EvaluationTypeTotal(strQuery, strStraTime,strEndTime);
                List<EvaluationType> ls = evaluationTypeService.EvaluationTypePaging(
                        Integer.parseInt(strPageIndex), Integer.parseInt(strPageCount), strQuery, strStraTime,strEndTime);
                String strJson = "";
                for (EvaluationType item : ls)
                {
                    strJson += evaluationTypeService.getEvaluationTypeInfo(item)+",";
                }
                if (strJson.length() > 0)
                {
                    strJson = strJson.substring(0, strJson.length() - 1);
                    out.print("{\"total\":\"" + strTotal + "\",\"webList\":[" + strJson + "]}");
                }
                else
                    out.print("false");
               }
               //编辑保存
               else if ("edit".equals(methodName))
               {
                   EvaluationType evaluationType = evaluationTypeService.getEvaluationTypeById(Long.parseLong(request.getParameter("id")));
                   evaluationType.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                   evaluationType.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                   evaluationType.setCreateTime(new Date());
                   evaluationType.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));                   
                   evaluationTypeService.SaveEvaluationType(evaluationType);
                   String res = evaluationTypeService.getEvaluationTypeInfo(evaluationType);
                   out.print(res);
               }
               //添加保存
               else if ("add".equals(methodName))
               {
                   EvaluationType evaluationType = new EvaluationType();
                   evaluationType.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                   evaluationType.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                   evaluationType.setCreateTime(new Date());
                   evaluationType.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));                   
                   evaluationTypeService.SaveEvaluationType(evaluationType);
                   String res = evaluationTypeService.getEvaluationTypeInfo(evaluationType);
                   out.print(res);
               }
               //删除
               else if ("del".equals(methodName))
               {
                   evaluationTypeService.DelEvaluationType(Long.parseLong(request.getParameter("id")));
                   out.print("true");
               }
               //编辑、查询页面初始化
               else if("init".equals(methodName))
               {
                   EvaluationType evaluationType = evaluationTypeService.getEvaluationTypeById(Long.parseLong(request.getParameter("id")));
                   String res = evaluationTypeService.getEvaluationTypeInfo(evaluationType);
                   out.print(res);
               }
               else if ("getEvaluationType".equals(methodName))
               {
                   List<EvaluationType> evaluationType = evaluationTypeService.GetEvaluationTypes();
                   String strevaluationType = "";//, name;
                   for(EvaluationType item: evaluationType){
                       strevaluationType+="{\"Id\":\""+item.getId() +"\", \"Name\":\""+item.getName() + "\"},";
                   }
                   if(strevaluationType.length() > 0){
                       strevaluationType = strevaluationType.substring(0, strevaluationType.length() - 1);
                       out.print("{\"total\":\"" + evaluationType.size() + "\",\"webList\":[" + strevaluationType + "]}");
                   }
               }
            } catch (Exception e)
            {
                out.print("false");
            }
            finally
            {
                out.flush();
                out.close();
            }
        }

    }
}
