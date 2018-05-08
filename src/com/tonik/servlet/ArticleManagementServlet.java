package com.tonik.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.thinvent.utils.WebUtil;
import com.tonik.service.ArticleManagementService;


/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since 2017.3.28
 * @version 1.0
 * @author xiaoyu
 * @web.servlet name="ArticleManagementServlet"
 * @web.servlet-mapping url-pattern="/servlet/ArticleManagementServlet"
 */
@SuppressWarnings("serial")
public class ArticleManagementServlet extends BaseServlet
{
    private ArticleManagementService articleManagementService;
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        articleManagementService= (ArticleManagementService) ctx.getBean("ArticleManagementService");
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
        PrintWriter out = response.getWriter();
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("context".equalsIgnoreCase(methodName))
                {
                    String pageIndex = request.getParameter("pageIndex");
                    String pageSize = request.getParameter("pageSize");
                    HashMap<String,Object> res = new HashMap<String,Object>();
                    res= articleManagementService.getArticleManagementPagelist(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
                    WebUtil.writeJSON(response, res);
                }else if("queryById".equalsIgnoreCase(methodName)){
                    Long id = Long.valueOf(request.getParameter("id"));
                    Object aVo = articleManagementService.getArticle(id);
                    WebUtil.writeJSON(response, aVo);
                }
            } catch (Exception e)
            {
                out.println("false");
            }
           
        }
    }
}
