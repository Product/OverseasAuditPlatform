package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IProductDAO;
import com.tonik.dao.ITestDAO;
import com.tonik.dao.ITestStyleDAO;
import com.tonik.dao.ITestTargetDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.Test;
import com.tonik.model.TestStyle;
import com.tonik.model.TestTarget;

/**
 * @spring.bean id="TestService"
 * @spring.property name="testDAO" ref="TestDAO"
 * @spring.property name="testTargetDAO" ref="TestTargetDAO"
 * @spring.property name="testStyleDAO" ref="TestStyleDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="productDAO" ref="ProductDAO"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class TestService
{
    private ITestDAO TestDAO;
    private ITestStyleDAO TestStyleDAO;
    private IUserDAO UserDAO;
    private ITestTargetDAO TestTargetDAO;
    private IWebsiteDAO WebsiteDAO;
    private IProductDAO ProductDAO;
    
    public ITestTargetDAO getTestTargetDAO()
    {
        return TestTargetDAO;
    }
    public void setTestTargetDAO(ITestTargetDAO testTargetDAO)
    {
        TestTargetDAO = testTargetDAO;
    }
    public ITestDAO getTestDAO()
    {
        return TestDAO;
    }
    public void setTestDAO(ITestDAO testDAO)
    {
        TestDAO = testDAO;
    }
    
    public IUserDAO getUserDAO()
    {
        return UserDAO;
    }
    public void setUserDAO(IUserDAO userDAO)
    {
        UserDAO = userDAO;
    }
    public ITestStyleDAO getTestStyleDAO()
    {
        return TestStyleDAO;
    }
    public void setTestStyleDAO(ITestStyleDAO testStyleDAO)
    {
        TestStyleDAO = testStyleDAO;
    }
    
    public IWebsiteDAO getWebsiteDAO()
    {
        return WebsiteDAO;
    }
    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        WebsiteDAO = websiteDAO;
    }
    public IProductDAO getProductDAO()
    {
        return ProductDAO;
    }
    public void setProductDAO(IProductDAO productDAO)
    {
        ProductDAO = productDAO;
    }
    public List<Test> TestPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls= TestDAO.getTestPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Test> ts = new ArrayList<Test>();
        String strJson = "";
        for (Object[] obj : ls)
        {
            Test item = (Test)obj[0];
            ts.add(item);
            /*strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                    +"\",\"Style\":\"1\",\"Num\":\"" + item.getNum()
                    +"\",\"Score\":\"" + item.getScore()  +"\",\"StartTime\":\"" + item.getStarttime()
                    +"\",\"EndTime\":\"" + item.getEndtime() + "\",\"CreatePerson\":\"" + "tmp"
                    +"\",\"Remark\":\"" + item.getRemark() + "\"},";*/
        }
        return ts;
    }
    
    public String TestTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(TestDAO.getTestTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void SaveTest(Test test, String styleId, String webTarget, String proTarget)
    {
        TestStyle style = TestStyleDAO.getTestStyle(Long.parseLong(styleId));
        test.setTesttype(style);
        TestDAO.saveTest(test);
        String[] webTestTarget=webTarget.split("\\|"); 
        for(int i = 0; i < webTestTarget.length; i++){
            if(webTestTarget[i] != ""){
                TestTarget tt = new TestTarget();
                tt.setType(1);
                tt.setWebsite(WebsiteDAO.getWebsite(Long.parseLong(webTestTarget[i])));
                tt.setTest(test);
                tt.setProduct(null);
                TestTargetDAO.saveTestTarget(tt);
            }
        }
        
        String[] proTestTarget = proTarget.split("\\|");
        for(int i = 0; i < proTestTarget.length; i++){
            if(proTestTarget[i] != ""){
                TestTarget tt = new TestTarget();
                tt.setType(2);
                tt.setProduct(ProductDAO.getProduct(Long.parseLong(proTestTarget[i])));
                tt.setTest(test);
                tt.setWebsite(null);
                TestTargetDAO.saveTestTarget(tt);
            }
        }
    }
    
    public void DelTest(Long Id)
    {
        List<Object[]> ls = TestTargetDAO.getTestTargetByTest(Id);
        for(Object[] obj:ls){
            TestTarget tt = (TestTarget)obj[0];
            TestTargetDAO.removeTestTarget(tt);
        }
        TestDAO.removeObject(Test.class, Id);
    }
    
    public Test GetTestById(Long Id)
    {
        return TestDAO.getTest(Id);
    }
    
    public String getTestInfo(Test ts){
        String res = "";
        res = "{\"Id\":\"" + ts.getId() + "\",\"Name\":\"" + ts.getName()
                +"\",\"Style\":\"" + ts.getTestTypeId()
                +"\",\"Num\":\"" + ts.getNum()+ "\",\"Type\":\"" + ts.getTestStyleType()
                +"\",\"Score\":\"" + ts.getScore()  +"\",\"StartTime\":\"" + ts.getFormatStartTime()
                +"\",\"EndTime\":\"" + ts.getFormatEndTime() + "\",\"CreatePerson\":\"" + ts.getCreatePersonName()
                +"\",\"Remark\":\"" + ts.getRemark() + "\"}";
        return res;
    }
}
