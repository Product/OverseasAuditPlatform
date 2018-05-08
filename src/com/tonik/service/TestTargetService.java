package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IProductDAO;
import com.tonik.dao.ITestDAO;
import com.tonik.dao.ITestTargetDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.Product;
import com.tonik.model.TestTarget;
import com.tonik.model.Website;

/**
 * @spring.bean id="TestTargetService"
 * @spring.property name="testDAO" ref="TestDAO"
 * @spring.property name="testTargetDAO" ref="TestTargetDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="productDAO" ref="ProductDAO"
 */
public class TestTargetService
{
    private ITestDAO TestDAO;
    private ITestTargetDAO TestTargetDAO;
    private IWebsiteDAO WebsiteDAO;
    private IProductDAO ProductDAO;
    public ITestDAO getTestDAO()
    {
        return TestDAO;
    }
    public void setTestDAO(ITestDAO testDAO)
    {
        TestDAO = testDAO;
    }
    public ITestTargetDAO getTestTargetDAO()
    {
        return TestTargetDAO;
    }
    public void setTestTargetDAO(ITestTargetDAO testTargetDAO)
    {
        TestTargetDAO = testTargetDAO;
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
    public List<TestTarget> GetTestTargetsByTest(String id){
        List<Object[]> objs = TestTargetDAO.getTestTargetByTest(Long.parseLong(id));
        List<TestTarget> tts = new ArrayList<TestTarget>();
        for(Object[] obj:objs){
            TestTarget tt = (TestTarget)obj[0];
            tts.add(tt);
        }
        return tts;
    }
    /*public List<TestTarget> GetTestTargetsByTest(String id, String type){
        Test t = TestDAO.getTest(Long.parseLong(id));
        return TestTargetDAO.getTestTargetByTest(t, Integer.parseInt(type));
    }*/
    
    public List<TestTarget> TestTargetPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<TestTarget> ls= TestTargetDAO.getTestTargetPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }
    
    public String TestTargetTotal(String strQuery, String strStraTime, String strEndTime){
        return Integer.toString(TestTargetDAO.getTestTargetTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void SaveTestTarget(TestTarget test, String objId)
    {
        switch(test.getType()){
            case 1:
                Website web = WebsiteDAO.getWebsite(Long.parseLong(objId));
                test.setWebsite(web);
                break;
            case 2:
                Product pro = ProductDAO.getProduct(Long.parseLong(objId));
                test.setProduct(pro);
                break;
        }
        TestTargetDAO.saveTestTarget(test);
    }
    
    public void DelTestTarget(Long Id)
    {
        TestDAO.removeObject(TestTarget.class, Id);
    }
    
    public TestTarget GetTestTargetById(Long Id)
    {
        return TestTargetDAO.getTestTarget(Id);
    }
}
