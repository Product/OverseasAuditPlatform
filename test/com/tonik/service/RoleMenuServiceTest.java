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
 * spring.bean id="RoleMenuServiceTest"
 * spring.property name="consultiveService" ref="RoleMenuService"
 */
public class RoleMenuServiceTest extends BaseDaoTestCase
{
    private RoleMenuService RoleMenuService;

    

    public RoleMenuService getRoleMenuService()
    {
        return RoleMenuService;
    }

    public void setRoleMenuService(RoleMenuService RoleMenuService)
    {
        this.RoleMenuService = RoleMenuService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(RoleMenuServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(RoleMenuService);
    }

}
