package com.tonik.servlet;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonik.model.UserInfo;

public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = -2904377730883672631L;
    
    private static Logger logger = Logger.getLogger(BaseServlet.class);
    
    public boolean sessionCheck(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        UserInfo ui = (UserInfo) WebUtils.getSessionAttribute(request, "userInfo");
        return ui == null ? true : false;
//        return false;
    }
    
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}
