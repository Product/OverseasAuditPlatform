package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.KeyWord;
import com.tonik.service.KeyWordService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @web.servlet name="keyWordServlet"
 * @web.servlet-mapping url-pattern="/servlet/KeyWordServlet"
 */
public class KeyWordServlet extends BaseServlet
{
    private KeyWordService keyWordService;

    public KeyWordService getKeyWordService()
    {
        return keyWordService;
    }

    public void setKeyWordService(KeyWordService keyWordService)
    {
        this.keyWordService = keyWordService;
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        keyWordService = (KeyWordService) ctx.getBean("KeyWordService");
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
                if("init".equalsIgnoreCase(methodName)){
                        String res="";
                        String strId = request.getParameter("id");
                        KeyWord kw = keyWordService.GetKeyWordById(Long.parseLong(strId));
                    
                        res = "{\"keyWord\":"+keyWordService.getKeyWordInfo(kw)+", \"eventTypes\":"+keyWordService.GetKeyWordsRelationEventType(kw)+"}";
                        
                        out.print(res);
                    }
                } catch (Exception e)
                {
                    out.println("false");
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
