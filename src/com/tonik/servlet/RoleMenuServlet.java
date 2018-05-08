package com.tonik.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;
import com.tonik.model.UserInfo;
import com.tonik.service.RoleMenuService;

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
 * @web.servlet name="roleMenuServlet"
 * @web.servlet-mapping url-pattern="/servlet/RoleMenuServlet"
 */
public class RoleMenuServlet extends BaseServlet
{
    private RoleMenuService RoleMenuService;


    public RoleMenuService getRoleMenuService()
    {
        return RoleMenuService;
    }

    public void setRoleMenuService(RoleMenuService RoleMenuService)
    {
        this.RoleMenuService = RoleMenuService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        RoleMenuService = (RoleMenuService) ctx.getBean("RoleMenuService");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        if (this.sessionCheck(request, response))
        {
            response.getWriter().write("sessionOut");
            return;
        }
        String methodName = request.getParameter("methodName");
        if (methodName != "")
        {
            try
            {
                if ("QueryList".equals(methodName))
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

                    String strTotal = RoleMenuService.RoleMenuTotal(strQuery, strStraTime, strEndTime);
                    List<RoleMenu> ls = RoleMenuService.RoleMenuPaging(Integer.parseInt(strPageIndex),
                            Integer.parseInt(strPageCount), strQuery, strStraTime, strEndTime);

                    String strJson = "";
                    for (RoleMenu item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Sort\":\""
                                + item.getSort() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
                                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
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
                else if ("edit".equals(methodName))
                {
                    RoleMenu rm = new RoleMenu();
                    rm.setId(Long.parseLong(request.getParameter("id")));
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    rm.setCreatePerson(ui.getId().toString());
                    rm.setCreateTime(new Date());
                    List<Menu> ls = new ArrayList<Menu>();
                    for (String item : URLDecoder.decode(request.getParameter("menuIds"),"UTF-8").split(","))
                    {
                        Menu m = new Menu();
                        m.setId(Long.parseLong(item));
                        ls.add(m);
                    }
                    rm.setMenus(ls);
                    rm.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    rm.setSort(Integer.parseInt(URLDecoder.decode(request.getParameter("sort"),"UTF-8")));
                    RoleMenuService.SaveRoleMenu(rm);
                    response.getWriter().write("true");
                }
                else if ("add".equals(methodName))
                {
                    RoleMenu rm = new RoleMenu();
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    rm.setCreatePerson(ui.getId().toString());
                    rm.setCreateTime(new Date());
                    List<Menu> ls = new ArrayList<Menu>();
                    for (String item : URLDecoder.decode(request.getParameter("menuIds"),"UTF-8").split(","))
                    {
                        Menu m = new Menu();
                        m.setId(Long.parseLong(item));
                        ls.add(m);
                    }
                    rm.setMenus(ls);
                    rm.setName(URLDecoder.decode(request.getParameter("name"),"UTF-8"));
                    rm.setSort(Integer.parseInt(URLDecoder.decode(request.getParameter("sort"),"UTF-8")));
                    RoleMenuService.SaveRoleMenu(rm);
                    response.getWriter().write("true");
                }
                else if ("del".equals(methodName))
                {
                    RoleMenuService.DelRoleMenu(Long.parseLong(request.getParameter("id")));
                    response.getWriter().write("true");
                }
                else if ("init".equals(methodName))
                {
                    RoleMenu rm = RoleMenuService.GetRoleMenuById(Long.parseLong(request.getParameter("id")));
                    String strMenus = "";
                    for (Menu item : rm.getMenus())
                    {
                        strMenus += item.getId().toString() + "_";
                    }
                    if (strMenus != "")
                    {
                        strMenus = strMenus.substring(0, strMenus.length() - 1);
                    }
                    response.getWriter()
                            .write("{\"Id\":\"" + rm.getId() + "\",\"Name\":\"" + rm.getName() + "\",\"Sort\":\""
                                    + rm.getSort() + "\",\"Menus\":\"" + strMenus + "\",\"CreatePerson\":\""
                                    + rm.getCreatePerson() + "\"}");//
                }
                else if ("getMenuList".equals(methodName))
                {
                    HttpSession session = request.getSession();
                    UserInfo ui = (UserInfo) session.getAttribute("userInfo");
                    response.getWriter().write(RoleMenuService.getJson(ui.getUserRole()));
                }
                else if ("getRoleList".equals(methodName))
                {
                    List<RoleMenu> ls = RoleMenuService.getRoleList();
                    String strJson = "";
                    for (RoleMenu item : ls)
                    {
                        strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Sort\":\""
                                + item.getSort() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
                                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
                    }
                    if (strJson.length() > 0)
                    {
                        strJson = strJson.substring(0, strJson.length() - 1);
                        response.getWriter().write("{\"webList\":[" + strJson + "]}");
                    }
                    else
                    {
                        response.getWriter().write("false");
                    }
                }
            } catch (Exception e)
            {
                response.getWriter().write("false");
            }
        }
    }
}
