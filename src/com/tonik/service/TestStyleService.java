package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.ITestStyleDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.model.TestStyle;
import com.tonik.model.UserInfo;

/**
 * @spring.bean id="TestStyleService"
 * @spring.property name="testStyleDAO" ref="TestStyleDAO"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class TestStyleService
{
    private ITestStyleDAO TestStyleDAO;
    private IUserDAO UserDAO;
    public ITestStyleDAO getTestStyleDAO()
    {
        return TestStyleDAO;
    }

    public void setTestStyleDAO(ITestStyleDAO TestStyleDAO)
    {
        this.TestStyleDAO = TestStyleDAO;
    }

    public IUserDAO getUserDAO()
    {
        return UserDAO;
    }

    public void setUserDAO(IUserDAO UserDAO)
    {
        this.UserDAO = UserDAO;
    }
    
    public List<TestStyle> TestStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls = TestStyleDAO.getTestStylePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<TestStyle> tss= new ArrayList<TestStyle>();
        for (Object[] obj: ls)
        {
            TestStyle item = (TestStyle)obj[0];
            tss.add(item);
        }
        return tss;
    }
    
    public String TestStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(TestStyleDAO.getTestStyleTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void SaveTestStyle(TestStyle testStyle, String userId)
    {
        userId = "1";
        UserInfo user = UserDAO.getUser(Long.parseLong(userId));
        
        testStyle.setCreatePerson(user);
        TestStyleDAO.saveTestStyle(testStyle);
    }
    
    public void DelTestStyle(Long Id)
    {
        TestStyleDAO.removeObject(TestStyle.class, Id);
    }
    
    public TestStyle GetTestStyleById(Long Id)
    {
        return TestStyleDAO.getTestStyle(Id);
    }

    public List<TestStyle> GetTestStyles()
    {
        return TestStyleDAO.getTestStyle();
    }
    
    public String GetTestStyleInfo(TestStyle ts){
        String res = "";
        res += "{\"Id\":\"" + ts.getId() + "\",\"Name\":\"" + ts.getName() + "\",\"Remark\":\""
                + ts.getRemark() + "\",\"CreatePerson\":\"" + ts.getCreatePersonName()
                + "\",\"Type\":\"" + ts.getType() + "\",\"TypeName\":\"" + ts.getTypeName()
                + "\",\"CreateTime\":\"" + ts.getFormatCreateTime() + "\"}";
        return res;
    }
    
}
