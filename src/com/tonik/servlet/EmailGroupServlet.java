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

import com.tonik.model.Email;
import com.tonik.model.EmailGroup;
import com.tonik.model.UserInfo;
import com.tonik.service.EmailGroupService;

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
 * @web.servlet name="emailGroupServlet"
 * @web.servlet-mapping url-pattern="/servlet/EmailGroupServlet"
 */
public class EmailGroupServlet extends BaseServlet
{
    private EmailGroupService EmailGroupService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        EmailGroupService = (EmailGroupService) ctx.getBean("EmailGroupService");
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
    
                    String strTotal = EmailGroupService.EmailGroupTotal(strQuery, strStraTime, strEndTime);
                    
                    List<EmailGroup> ls = EmailGroupService.EmailGroupPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
    
                    for (EmailGroup item : ls)
                    {
                        strJson += EmailGroupService.getEmailGroupInfo(item)+",";                    
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
            //编辑保存
            else if ("edit".equalsIgnoreCase(methodName))
            {
                EmailGroup emailGroup = EmailGroupService.GetEmailGroupById(Long.parseLong(request.getParameter("id")));
                emailGroup.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                EmailGroupService.SaveEmailGroup(emailGroup);
                String res = EmailGroupService.getEmailGroupInfo(emailGroup);
                out.print(res);
            }
            //添加保存
            else if ("add".equalsIgnoreCase(methodName))
            {
                EmailGroup emailGroup = new EmailGroup();
                emailGroup.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                emailGroup.setCreatePerson((UserInfo) request.getSession().getAttribute("userInfo"));
                emailGroup.setCreateTime(new Date());
                EmailGroupService.SaveEmailGroup(emailGroup);
                String res = EmailGroupService.getEmailGroupInfo(emailGroup);
                out.print(res);
            }
            //删除
            else if ("del".equalsIgnoreCase(methodName))
            {
                EmailGroupService.DelEmailGroup(Long.parseLong(request.getParameter("id")));
                out.print("true");
            }
            //编辑、查询页面初始化
            else if("init".equalsIgnoreCase(methodName))
            {
                EmailGroup rp = EmailGroupService.GetEmailGroupById(Long.parseLong(request.getParameter("id")));
                out.print("{\"Id\":\"" + rp.getId() + "\",\"Name\":\"" + rp.getName() + "\"}");
            }
            //群组管理，显示群组下所有邮件地址
            else if ("GroupManage".equals(methodName))
            {
                EmailGroup emailgroup=EmailGroupService.GetEmailGroupById(Long.parseLong(request.getParameter("id")));
                String strJson = "";
                for (Email item : emailgroup.getEmails())
                {
                    strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"EmailAddress\":\"" + item.getEmailAddress() + "\",\"Company\":\"" + item.getCompany() + "\",\"Subscription\":\""
                            + item.getSubscription() + "\",\"Remark\":\"" + item.getRemark() +"\",\"CreatePerson\":\"" + "aaa"
                            + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
                }
                if (strJson.length() > 0)
                {
                    strJson = strJson.substring(0, strJson.length() - 1);
                    out.print("{\"webList\":[" + strJson + "]}");
                }
            }
            //获取邮件群组列表信息
            else if("getEmailGroup".equals(methodName))
            {
                List<EmailGroup> emailGroup=EmailGroupService.getGroupList();
                String strJson = "";
                for (EmailGroup item : emailGroup)
                {
                    strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                            + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
                }
                if (strJson.length() > 0)
                {
                    strJson = strJson.substring(0, strJson.length() - 1);
                    out.print("{\"webList\":[" + strJson + "]}");
                }
                else
                {
                    out.print("false");
                }
                
            }    
            } catch (Exception e)
            {
                out.print("false");
            }
        }

        out.flush();
        out.close();

    }
}