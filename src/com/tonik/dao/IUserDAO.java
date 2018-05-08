package com.tonik.dao;

import java.util.List;

import com.tonik.model.UserInfo;

public interface IUserDAO extends IDAO
{
    public List<UserInfo> getUser();

    public UserInfo getUser(Long userId);

    public void saveUser(UserInfo user);
    
    public void updateUser(UserInfo us);

    public void removeUser(UserInfo user);
    
    public void removeUser(Long userid);
    
    public List<UserInfo> checkUser(String userCode, String userPwd);
    
    public int getUserInfoTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<UserInfo> getUserInfoPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
}
