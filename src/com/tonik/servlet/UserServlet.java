package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.Constant;
import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;
import com.tonik.model.UserInfo;
import com.tonik.service.UserService;

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
 * @web.servlet name="userServlet"
 * @web.servlet-mapping url-pattern="/servlet/userServlet"
 */
public class UserServlet extends BaseServlet
{
    private UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        userService = (UserService) ctx.getBean("UserService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("checkuser".equals(methodName))
                {
                    List<UserInfo> ls = userService.CheckLogin(request.getParameter("userCode"),
                            request.getParameter("userPwd"));
                    if (ls.size() > 0)
                    {
                        List<Menu> m = userService.getMenuByUserId(ls.get(0).getId(),0);
                        Collections.sort(m, new Comparator<Menu>()
                        {
                            public int compare(Menu arg0, Menu arg1)
                            {
                                return arg0.getSort().compareTo(arg1.getSort());
                            }
                        });
                        
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(-1);
                        session.setAttribute("userInfo", ls.get(0));
                        String strJson = "";
                        for (Menu item : m)
                        {
                            if (item.getLevel() == 1)
                            {
                                strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                        + "\",\"NodeId\":\"" + item.getNodeId() + "\",\"Level\":\"" + item.getLevel()
                                        + "\",\"Sort\":\"" + item.getSort() + "\",\"ParentId\":\"" + item.getParentId()
                                        + "\",\"PicUrl\":\"" + item.getPicUrl() + "\"},";
                            }
                        }
                        for (Menu item : m)
                        {
                            if (item.getLevel() == 2)
                            {
                                strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                        + "\",\"NodeId\":\"" + item.getNodeId() + "\",\"Level\":\"" + item.getLevel()
                                        + "\",\"Sort\":\"" + item.getSort() + "\",\"ParentId\":\"" + item.getParentId()
                                        + "\",\"PicUrl\":\"" + item.getPicUrl() + "\"},";
                            }
                        }
                        for (Menu item : m)
                        {
                            if (item.getLevel() == 3)
                            {
                                strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                                        + "\",\"NodeId\":\"" + item.getNodeId() + "\",\"Level\":\"" + item.getLevel()
                                        + "\",\"Sort\":\"" + item.getSort() + "\",\"ParentId\":\"" + item.getParentId()
                                        + "\",\"PicUrl\":\"" + item.getPicUrl() + "\"},";
                            }
                        }
                        if (strJson.length() > 0)
                        {
                            strJson = strJson.substring(0, strJson.length() - 1);
                            response.getWriter().write("{\"menuList\":[" + strJson + "]}");
                        }
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }

                if (this.sessionCheck(request, response))
                {
                    response.getWriter().write("sessionOut");
                    return;
                }
                if ("QueryList".equals(methodName))
                {
                    String strQuery = URLDecoder.decode(request.getParameter("strQuery"),"UTF-8");
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
                        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT);// 设置日期格式
                        strEndTime = df.format(new Date());
                    }
                    String strTotal = userService.UserInfoTotal(strQuery, strStraTime, strEndTime);
                    List<UserInfo> ls = userService.UserInfoPageing(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);
                    String strJson = "";
                    for (UserInfo item : ls)
                    {
                        String strRolaName = "";
                        Set<RoleMenu> rl = item.getUserRole();
                        for (RoleMenu u : rl)
                        {
                            strRolaName += u.getName() + "_";
                        }
                        if (strRolaName != "")
                        {
                            strRolaName = strRolaName.substring(0, strRolaName.length() - 1);
                        }
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"userCode\":\"" + item.getUserCode()
                                + "\",\"Name\":\"" + item.getRealName() + "\",\"RoleName\":\"" + strRolaName
                                + "\",\"Mobile\":\"" + item.getMobile() + "\",\"CreatePerson\":\""
                                + item.getCreatePersonid() + "\",\"CreateTime\":\"" + item.getFormatCreateTime() + "\"},";

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
                else if ("init".equals(methodName))
                {
                    String id = request.getParameter("id");
                    UserInfo us = userService.getUserInfoById(Long.parseLong(id));
                    String roles="";
                    for(RoleMenu item:us.getUserRole())
                    {
                        roles += "{\"Role\":\""+ item.getId() +"\"},";
                    }
                    if(roles.length()>0)
                    {
                        roles = roles.substring(0, roles.length()-1);
                    }
                    
                    response.getWriter().write(
                            "{\"Id\":\"" + us.getId() + "\",\"code\":\"" + us.getUserCode() + "\", \"Name\":\""
                                    + us.getRealName() + "\",\"Mobile\":\"" + us.getMobile() + "\",\"Roles\":["+ roles + "]}");
                }
                else if ("edit".equals(methodName))
                {
                    UserInfo us = new UserInfo();
                    UserInfo ui = userService.getUserInfoById(Long.parseLong(request.getParameter("id")));
                    us.setId(Long.parseLong(request.getParameter("id")));
                    us.setRealName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    us.setMobile(request.getParameter("mobile"));
                    us.setUserCode(URLDecoder.decode(request.getParameter("userCode"),"UTF-8"));
                    us.setUserPwd(request.getParameter("passWord"));
                    us.setCreateTime(ui.getCreateTime());
                    Set<RoleMenu> rm = new HashSet<RoleMenu>();
                    String strRolaName = "";
                    for (String item : request.getParameter("strMenu").split("_"))
                    {
                        rm.add(userService.getRoleMenuById(Long.parseLong(item)));
                        strRolaName += userService.getRoleMenuById(Long.parseLong(item)).getName() + "_";
                    }
                    if (strRolaName != "")
                    {
                        strRolaName = strRolaName.substring(0, strRolaName.length() - 1);
                    }
                    us.setUserRole(rm);
                    // UserInfo uo=(UserInfo) (request.getSession().getAttribute("userInfo"));
                    us.setCreatePersonid(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    userService.SaveUserInfo(us);
                    response.getWriter().write("{\"Id\":\"" + us.getId() + "\",\"userCode\":\"" + us.getUserCode()
                            +"\",\"RoleName\":\""+ strRolaName +"\",\"CreatePerson\":\""+ us.getCreatePersonid() 
                            + "\", \"Name\":\"" + us.getRealName() + "\",\"Mobile\":\"" + us.getMobile() + "\",\"CreateTime\":\"" + us.getFormatCreateTime() + "\"}");
                }
                else if ("edit_self".equals(methodName))
                {
                    String number = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";
                    Pattern regex = Pattern.compile(number);
                    Matcher matcher = regex.matcher(request.getParameter("mobile"));
                    if (!matcher.matches())
                    {
                        response.getWriter().write("wrongNumber");
                    }
                    else
                    {
                        UserInfo ui = userService.getUserInfoById(Long.parseLong(request.getParameter("id")));
                        ui.setRealName(URLDecoder.decode(request.getParameter("realName"),"UTF-8"));
                        ui.setMobile(request.getParameter("mobile"));
                        userService.SaveUserInfo(ui);
                        response.getWriter().write("true");
                    }
                }
                else if ("ChangePassword".equals(methodName))
                {
                    UserInfo ui = userService.getUserInfoById(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    if (ui.getUserPwd().equals(request.getParameter("old_password")))
                    {
                        if (request.getParameter("new_password").equals(request.getParameter("confirm_password")))
                        {
                            ui.setUserPwd(request.getParameter("new_password"));
                            userService.SaveUserInfo(ui);
                            response.getWriter().write("true");
                        }
                        else
                            response.getWriter().write("false2");
                    }
                    else
                        response.getWriter().write("false1");
                }
                else if ("add".equals(methodName))
                {
                    UserInfo us = new UserInfo();
                    us.setUserCode(URLDecoder.decode(request.getParameter("userCode"),"UTF-8"));
                    us.setRealName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    us.setMobile(request.getParameter("mobile"));
                    us.setUserPwd(request.getParameter("passWord"));
                    us.setCreateTime(new Date());
                    Set<RoleMenu> rm = new HashSet<RoleMenu>();
                    String strRolaName = "";
                    for (String item : request.getParameter("strMenu").split("_"))
                    {
                        rm.add(userService.getRoleMenuById(Long.parseLong(item)));
                        strRolaName += userService.getRoleMenuById(Long.parseLong(item)).getName() + "_";
                    }
                    if (strRolaName != "")
                    {
                        strRolaName = strRolaName.substring(0, strRolaName.length() - 1);
                    }
                    us.setUserRole(rm);
                    // UserInfo uo=(UserInfo) (request.getSession().getAttribute("userInfo"));
                    us.setCreatePersonid(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    userService.SaveUserInfo(us);
                    response.getWriter().write("{\"Id\":\"" + us.getId() + "\",\"userCode\":\"" + us.getUserCode()
                            +"\",\"RoleName\":\"" + strRolaName + "\",\"CreatePerson\":\""+ us.getCreatePersonid() 
                            + "\", \"Name\":\"" + us.getRealName() + "\",\"Mobile\":\"" + us.getMobile() + "\",\"CreateTime\":\"" + us.getFormatCreateTime() + "\"}");
                }
                else if ("del".equals(methodName))
                {
                    userService.DelUserInfo(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("Modify".equals(methodName))
                {
                    UserInfo us = userService
                            .getUserInfoById(((UserInfo) request.getSession().getAttribute("userInfo")).getId());
                    response.getWriter().write("{\"Id\":\"" + us.getId() + "\",\"userCode\":\"" + us.getUserCode()
                            + "\", \"Name\":\"" + us.getRealName() + "\",\"Mobile\":\"" + us.getMobile() + "\"}");
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
