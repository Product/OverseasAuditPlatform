package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IRoleMenuDAO;
import com.tonik.model.RoleMenu;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing RoleMenuDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="roleMenuDAOTest"
 * @spring.property name="roleMenuDAO" ref="RoleMenuDAO"
 */
public class RoleMenuDAOTest extends BaseDaoTestCase
{
    private IRoleMenuDAO roleMenuDAO;


    public void setroleMenuDAO(IRoleMenuDAO roleMenuDAO)
    {
        this.roleMenuDAO = roleMenuDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(RoleMenuDAOTest.class);
    }

    public void testAddAndRemoveroleMenu() throws Exception
    {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setName("name");
        roleMenu.setSort(1);
        //roleMenu.setMenus("menu");
        roleMenu.setCreatePerson("Person");
        roleMenu.setCreateTime(new Date());
        roleMenuDAO.saveRoleMenu(roleMenu);
        setComplete();
        endTransaction();

        startNewTransaction();
        roleMenu = roleMenuDAO.getRoleMenu(1l);
        assertNotNull(roleMenu.getName());
        assertNotNull(roleMenu.getSort());
        //assertNotNull(roleMenu.getMenus());
        assertNotNull(roleMenu.getCreatePerson());
        assertNotNull(roleMenu.getCreateTime());

        roleMenuDAO.removeRoleMenu(roleMenu);
        setComplete();
        endTransaction();

        roleMenu = roleMenuDAO.getRoleMenu(1l);
        assertNull(roleMenu);
    }
}


