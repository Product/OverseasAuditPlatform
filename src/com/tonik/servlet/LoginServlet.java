package com.tonik.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.WebUtil;
import com.tonik.model.Menu;
import com.tonik.model.UserInfo;
import com.tonik.service.UserService;

/**
 * @web.servlet name="loginServlet"
 * @web.servlet-mapping url-pattern="/servlet/LoginServlet"
 */

public class LoginServlet extends BaseServlet
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
        Map<String, String> reqMap = WebUtil.getRequestParamMap(request);
        try
        {
            if ("login".equals(reqMap.get("methodName")))
            {
                List<UserInfo> user = userService.CheckLogin((String) reqMap.get("userCode"),
                        (String) reqMap.get("userPwd"));
                if (user.size() > 0)
                {
                    List<Menu> menus = userService.getMenuByUserId(
                            user.get(0).getId(), 
                            Integer.parseInt(reqMap.get("system")));
                    
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(-1);
                    WebUtils.setSessionAttribute(request, "userInfo", user.get(0));
                    WebUtils.setSessionAttribute(request, "system", 1);
                    List<Map<String, String>> ls = Lists.newArrayList();
                    for(Menu item : menus)
                    {
                        Map<String, String> map = Maps.newHashMap();
                        map.put("Id", valueOf(item.getId()));
                        map.put("Name", item.getName());
                        map.put("NodeId", item.getNodeId());
                        map.put("Level", valueOf(item.getLevel()));
                        map.put("Sort", valueOf(item.getSort()));
                        map.put("ParentId", valueOf(item.getParentId()));
                        map.put("PicUrl", item.getPicUrl());
                        ls.add(map);
                    }
                    WebUtil.writeJSON(response, this.new MenuJson(ls));
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
    
    public class MenuJson{
        
        private List<Map<String, String>> menuList;
    
        public MenuJson(List<Map<String, String>> ls){
            this.menuList = ls;
        }
    }
}

