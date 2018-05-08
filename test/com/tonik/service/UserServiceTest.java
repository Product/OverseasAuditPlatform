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
 * spring.bean id="UserServiceTest"
 * spring.property name="consultiveService" ref="UserService"
 */
public class UserServiceTest extends BaseDaoTestCase
{
    private UserService UserService;

    

    public UserService getUserService()
    {
        return UserService;
    }

    public void setUserService(UserService UserService)
    {
        this.UserService = UserService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(UserServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        //assertNotNull(UserService);
    }

}
