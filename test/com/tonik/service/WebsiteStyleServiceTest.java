package com.tonik.service;

import com.tonik.dao.hibernate.BaseDaoTestCase;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing StateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="WebsiteStyleServiceTest"
 * @spring.property name="websiteStyleService" ref="WebsiteStyleService"
 */
public class WebsiteStyleServiceTest extends BaseDaoTestCase
{
    private WebsiteStyleService websiteStyleService;

    public WebsiteStyleService getWebsiteStyleService()
    {
        return websiteStyleService;
    }

    public void setWebsiteStyleService(WebsiteStyleService websiteStyleService)
    {
        this.websiteStyleService = websiteStyleService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(WebsiteStyleServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(websiteStyleService);
    }

}
