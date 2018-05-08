package com.tonik.dao.hibernate;

import java.text.SimpleDateFormat;

import com.tonik.dao.IEmailDAO;
import com.tonik.model.Email;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing EmailDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="emailDAOTest"
 * @spring.property name="emailDAO" ref="EmailDAO"
 */
public class EmailDAOTest extends BaseDaoTestCase
{
    private IEmailDAO emailDAO;


    public void setEmailDAO(IEmailDAO emailDAO)
    {
        this.emailDAO = emailDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(EmailDAOTest.class);
    }

    public void testAddAndRemoveemail() throws Exception
    {
        Email email = new Email();
        email.setEmailAddress("seller@ymatou.com");
        email.setCompany("ymatou");
        email.setRemark("nice");
        email.setCreatePerson(null);
        email.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-03-06 11:27:50"));
        emailDAO.saveEmail(email);
        setComplete();
        endTransaction();

        startNewTransaction();
        email = emailDAO.getEmail(1l);
        assertNotNull(email.getEmailAddress());
        assertNotNull(email.getCompany());
        assertNotNull(email.getRemark());
        assertNotNull(email.getCreatePerson());
        assertNotNull(email.getCreateTime());

        emailDAO.removeEmail(email);
        setComplete();
        endTransaction();

        email = emailDAO.getEmail(1l);
        assertNull(email);
    }
}

