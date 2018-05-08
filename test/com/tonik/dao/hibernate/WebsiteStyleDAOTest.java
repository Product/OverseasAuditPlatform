package com.tonik.dao.hibernate;

import java.text.SimpleDateFormat;

import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.WebsiteStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing WebsiteStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="websiteStyleDAOTest"
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 */
public class WebsiteStyleDAOTest extends BaseDaoTestCase
{
    private IWebsiteStyleDAO websiteStyleDAO;


    public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
    {
        this.websiteStyleDAO = websiteStyleDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(WebsiteStyleDAOTest.class);
    }

    public void testAddAndRemovewebsiteStyle() throws Exception
    {
        WebsiteStyle websiteStyle = new WebsiteStyle();
        websiteStyle.setName("seller@ymatou.com");
        websiteStyle.setRemark("Nike Coach");
        websiteStyle.setCreatePerson("person");
        websiteStyle.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-03-06 11:27:50"));
        websiteStyleDAO.saveWebsiteStyle(websiteStyle);
        setComplete();
        endTransaction();

        startNewTransaction();
        websiteStyle = websiteStyleDAO.getWebsiteStyle(1l);
        assertNotNull(websiteStyle.getName());
        assertNotNull(websiteStyle.getRemark());
        assertNotNull(websiteStyle.getCreatePerson());
        assertNotNull(websiteStyle.getCreateTime());

        websiteStyleDAO.removeWebsiteStyle(websiteStyle);
        setComplete();
        endTransaction();

        websiteStyle = websiteStyleDAO.getWebsiteStyle(1l);
        assertNull(websiteStyle);
    }
}

