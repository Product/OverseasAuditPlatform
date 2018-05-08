package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.Website;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing WebsiteDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="websiteDAOTest"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 */
public class WebsiteDAOTest extends BaseDaoTestCase
{
    private IWebsiteDAO websiteDAO;


    public void setwebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(WebsiteDAOTest.class);
    }

    public void testGetWebsiteList() throws Exception
    {

    }
    
    public void testAddAndRemoveWebsite() throws Exception
    {
        Website website = new Website();
        website.setName("ymatou");
        website.setLocation("http://www.ymatou.com/");
     
        //website.setWebStyle("webstyle");
        website.setAddress("usa");
        website.setRemark("Nike Coach");
        website.setCreatePerson("person");
        website.setCreateTime(new Date());
        websiteDAO.saveWebsite(website);
        setComplete();
        endTransaction();

        startNewTransaction();
        website = websiteDAO.getWebsite(350l);
        assertNotNull(website.getName());
        //assertNotNull(website.getLocation());
        //assertNotNull(website.getIntegrity());
        //assertNotNull(website.getWebStyle());
        //assertNotNull(website.getAddress());
        //assertNotNull(website.getRemark());
        assertNotNull(website.getCreatePerson());
        assertNotNull(website.getCreateTime());

        websiteDAO.removeWebsite(website);
        setComplete();
        endTransaction();

        website = websiteDAO.getWebsite(1l);
        assertNull(website);
    }
}

