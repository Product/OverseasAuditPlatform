package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thinvent.utils.WebUtil;
import com.tonik.model.EvaluationStyle;
import com.tonik.service.EvaluationStyleService;

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
 * @web.servlet name="evaluationStyleServlet"
 * @web.servlet-mapping url-pattern="/servlet/EvaluationStyleServlet"
 */
public class EvaluationStyleServlet extends BaseServlet
{
    private EvaluationStyleService EvaluationStyleService;

    public EvaluationStyleService getEvaluationStyleService()
    {
        return EvaluationStyleService;
    }

    public void setEvaluationStyleService(EvaluationStyleService evaluationStyleService)
    {
        EvaluationStyleService = evaluationStyleService;
    }
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        EvaluationStyleService = (EvaluationStyleService) ctx.getBean("EvaluationStyleService");
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
                if ("style".equalsIgnoreCase(methodName))
                {
                    String res="";
                    List<EvaluationStyle> ls = EvaluationStyleService.GetEvaluationStyles();
                    for(EvaluationStyle es:ls){
                        res += "{\"Id\":\"" + es.getId() + "\",\"Name\":\"" + es.getName() + "\"},";
                    }
                    if(res.length() > 0){
                        res = res.substring(0, res.length() - 1);
                        out.print("["+res+"]");
                    }else{
                        out.print("false");
                    }
                }
                else if ("save".equalsIgnoreCase(methodName))
                {
                    String strId = request.getParameter("id");
                    Long id = (strId == null || strId.isEmpty()) ? null : Long.parseLong(strId);
                    String strParent = request.getParameter("parent");
                    Long parent = strParent == null || "".equals(strParent) ? null : Long.parseLong(strParent);
                    String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
                    Long tree = Long.parseLong(request.getParameter("tree"));
                    String result = EvaluationStyleService.saveEvaluationStyle(id, name, parent, tree);
                    WebUtil.write(response, result);
                }
                else if ("del".equalsIgnoreCase(methodName))
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    String result = EvaluationStyleService.delEvaluationStyle(id);
                    WebUtil.write(response, result);
                }
                else if ("list".equalsIgnoreCase(methodName))
                {
                    String strParent = request.getParameter("parent");
                    Long parent = (strParent == null || strParent.isEmpty()) ? null : Long.parseLong(strParent);
                    Long tree = Long.parseLong(request.getParameter("tree"));
                    String result = EvaluationStyleService.listEvaluationStyles(parent, tree);
                    WebUtil.write(response, result);
                }
                else if ("get".equalsIgnoreCase(methodName))
                {
                    Long id = Long.parseLong(request.getParameter("id"));
                    String result = EvaluationStyleService.getEvaluationStyle(id);
                    WebUtil.write(response, result);
                }
            }catch (Exception e)
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
