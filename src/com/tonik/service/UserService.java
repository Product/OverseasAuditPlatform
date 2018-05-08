package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IMenuDAO;
import com.tonik.dao.IRoleMenuDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;
import com.tonik.model.UserInfo;

/**
 * @spring.bean id="UserService"
 * @spring.property name="userDAO" ref="UserDAO"
 * @spring.property name="menuDAO" ref="MenuDAO"
 * @spring.property name="roleMenuDAO" ref="RoleMenuDAO"
 */
public class UserService
{
    private IUserDAO userDAO;
    private IMenuDAO menuDAO;
    private IRoleMenuDAO roleMenuDAO;


    public void setRoleMenuDAO(IRoleMenuDAO roleMenuDAO)
    {
        this.roleMenuDAO = roleMenuDAO;
    }

    public void setMenuDAO(IMenuDAO menuDAO)
    {
        this.menuDAO = menuDAO;
    }

    public void setUserDAO(IUserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * 登录判断
     * @param strUserCode 账户 strUserPwd 密码
     * @return
     */
    public List<UserInfo> CheckLogin(String strUserCode, String strUserPwd)
    {
        return userDAO.checkUser(strUserCode, strUserPwd);
    }

    public List<Menu> getMenuByUserId(Long userId, Integer system)
    {
//        UserInfo ui = userDAO.getUser(userId);
//        List<Menu> m = new ArrayList<Menu>();
//
//        for (RoleMenu item : ui.getUserRole())
//        {
//            List<Menu> ms = roleMenuDAO.getMenusByRoleId(item.getId());
//            for (Menu items : ms)
//            {
//                m.add(items);
//            }
//        }
//        return m;
        return menuDAO.getMenuByUserId(userId, system);
    }

    public String UserInfoTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(userDAO.getUserInfoTotal(strQuery, strStraTime, strEndTime));
    }

    public List<UserInfo> UserInfoPageing(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
//        List<Object[]> ls= userDAO.getUserInfoPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
//        List<UserInfo> ui = new ArrayList<UserInfo>();
//        for(Object[] item:ls){
//            UserInfo u = (UserInfo)item[0];
//            ui.add(u);
//        }
        return userDAO.getUserInfoPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);

    }

    public UserInfo getUserInfoById(Long id)
    {
        return userDAO.getUser(id);
    }

    public void SaveUserInfo(UserInfo ui)
    {
        userDAO.saveUser(ui);
    }

    public void DelUserInfo(Long id)
    {
        userDAO.removeUser(id);
    }

    public RoleMenu getRoleMenuById(Long id)
    {
        return roleMenuDAO.getRoleMenu(id);
    }
}
