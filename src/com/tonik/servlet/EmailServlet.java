package com.tonik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Email;
import com.tonik.model.EmailGroup;
import com.tonik.model.UserInfo;
import com.tonik.service.EmailService;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: TODO:This class is an example of using spring in web layer and may be removed or replaced by struts action later.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author yekai
 * @web.servlet name="emailServlet"
 * @web.servlet-mapping url-pattern="/servlet/EmailServlet"
 */
public class EmailServlet extends BaseServlet
{
    private EmailService emailService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        emailService = (EmailService) ctx.getBean("EmailService");
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
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        strEndTime = df.format(new Date());
                    }

                    String strTotal = emailService.EmailTotal(strQuery, strStraTime, strEndTime);
                    
                    String strJson = emailService.EmailPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount),strQuery,strStraTime,strEndTime);
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
                //编辑保存
                else if ("edit".equalsIgnoreCase(methodName))
                {
                    Email email = emailService.GetEmailById(Long.parseLong(request.getParameter("id")));
                    email.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    email.setEmailAddress(request.getParameter("emailAddress"));
                    email.setCompany(URLDecoder.decode(request.getParameter("company"),"UTF-8"));
                    email.setSubscription(request.getParameter("未订阅"));
                    email.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    Set<EmailGroup> emailGroup = new HashSet<EmailGroup>();
                    for(String item : request.getParameter("emailGroup").split("_"))
                    {
                        emailGroup.add(emailService.getEmailGroupById(Long.parseLong(item)));
                    }
                    email.setEmailGroups(emailGroup);
                    email.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    email.setCreateTime(new Date());
                    emailService.SaveEmail(email);
                    String res = emailService.getEmailInfo(email);
                    out.print(res);
                }
                //添加保存
                else if ("add".equalsIgnoreCase(methodName))
                {
                    Email email = new Email();
                    email.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    email.setEmailAddress(request.getParameter("emailAddress"));
                    email.setCompany(URLDecoder.decode(request.getParameter("company"),"UTF-8"));
                    email.setSubscription("未订阅");
                    email.setRemark(URLDecoder.decode(request.getParameter("remark"),"UTF-8"));
                    email.setCreateTime(new Date());
                    Set<EmailGroup> emailGroup = new HashSet<EmailGroup>();
                    for(String item : request.getParameter("emailGroup").split("_"))
                    {
                        emailGroup.add(emailService.getEmailGroupById(Long.parseLong(item)));
                    }
                    email.setEmailGroups(emailGroup);
                    email.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                    emailService.SaveEmail(email);
                    String res = emailService.getEmailInfo(email);
                    out.print(res);
                }
                //删除
                else if ("del".equalsIgnoreCase(methodName))
                {
                    emailService.DelEmail(Long.parseLong(request.getParameter("id")));
                    out.print("true");
                }
                //编辑、查询页面初始化
                else if("init".equalsIgnoreCase(methodName))
                {
                    Email email = emailService.GetEmailById(Long.parseLong(request.getParameter("id")));
                    String strJson = "";
                    for (EmailGroup item : email.getEmailGroups())
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                    }
                    out.print("{\"Id\":\"" + email.getId() + "\",\"Name\":\"" + email.getName() + "\",\"EmailAddress\":\"" + email.getEmailAddress() + "\",\"Company\":\"" + email.getCompany() + "\",\"Subscription\":\"" + email.getSubscription() + "\",\"Remark\":\""
                                + email.getRemark()
                                + "\",\"CreateTime\":\"" + email.getCreateTime() 
                                + "\", \"webList\":[" + strJson + "]}");
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
