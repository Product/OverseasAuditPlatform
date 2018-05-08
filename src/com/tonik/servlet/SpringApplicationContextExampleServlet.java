package com.tonik.servlet;

import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.dao.IStateDAO;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts
 * action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * web.servlet name="springContext"
 * web.servlet-mapping url-pattern="/servlet/*"
 */
public class SpringApplicationContextExampleServlet extends HttpServlet
{
    private static final long serialVersionUID = 7364416027533591948L;

    private WebApplicationContext ctx;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            System.out.println(Calendar.getInstance().getTimeInMillis());

            // get webApplicationContext
            ctx = WebApplicationContextUtils.getWebApplicationContext(getServletConfig().getServletContext());

            System.out.println(Calendar.getInstance().getTimeInMillis());
            System.out.println("ok1");
            System.out.println(Calendar.getInstance().getTimeInMillis());

            // get DAO object
            IStateDAO stateDAO = (IStateDAO) ctx.getBean("stateDAO");

            System.out.println(Calendar.getInstance().getTimeInMillis());
            System.out.println("ok2");
            System.out.println(stateDAO.getState(new Long(1)));
            System.out.println("ok3");
            // getServletConfig().getServletContext().getRequestDispatcher("/jsp/XX.jsp").forward(request,
            // response);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}// EOF
