package com.tonik.service;

import java.util.List;

import com.google.gson.Gson;
import com.thinvent.utils.JsonUtil;
import com.tonik.dao.hibernate.BaseDaoTestCase;
import com.tonik.model.Website;

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
 * spring.bean id="WebsiteServiceTest"
 * spring.property name="consultiveService" ref="WebsiteService"
 */
public class WebsiteServiceTest extends BaseDaoTestCase
{
    private WebsiteService WebsiteService;

    public WebsiteService getWebsiteService()
    {
        return WebsiteService;
    }

    public void setWebsiteService(WebsiteService WebsiteService)
    {
        this.WebsiteService = WebsiteService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(WebsiteServiceTest.class);
    }
    
    public void testGetWebSiteList() throws Exception
    {
//        List<Website> ls = WebsiteService.getWebsitePagingList(1, 10, start, len, order, dir);
//        System.out.println(JsonUtil.listToJson(ls));
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(WebsiteService);
    }

}
