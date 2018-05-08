package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IUserDAO;
import com.tonik.model.UserInfo;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing UserDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="userDaoTest"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class UserDAOTest extends BaseDaoTestCase
{
    private IUserDAO userDAO;


    public void setUserDAO(IUserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(UserDAOTest.class);
    }

    public void testAddAndRemoveuser() throws Exception
    {
        UserInfo user = new UserInfo();
        user.setUserCode("123");
        user.setUserPwd("123456");
        user.setRealName("realname");
        //user.setUserRola(null);
        user.setMobile("13364582069");
        user.setCreatePersonid(1l);
        user.setCreateTime(new Date());
        userDAO.saveUser(user);
        setComplete();
        endTransaction();

        startNewTransaction();
        user = userDAO.getUser(1l);
        assertNotNull(user.getUserCode());
        assertNotNull(user.getUserPwd());
        assertNotNull(user.getRealName());
//        assertNotNull(user.getUserRole());
        assertNotNull(user.getMobile());
        assertNotNull(user.getCreatePersonid());
        assertNotNull(user.getCreateTime());

        userDAO.removeUser(user);
        setComplete();
        endTransaction();

        user = userDAO.getUser(1l);
        assertNull(user);
    }
}


