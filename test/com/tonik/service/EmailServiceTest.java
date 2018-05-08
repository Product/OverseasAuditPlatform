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
 * spring.bean id="EmailServiceTest"
 * spring.property name="consultiveService" ref="EmailService"
 */
public class EmailServiceTest extends BaseDaoTestCase
{
    private EmailService EmailService;

    

    public EmailService getEmailService()
    {
        return EmailService;
    }

    public void setEmailService(EmailService EmailService)
    {
        this.EmailService = EmailService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(EmailServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(EmailService);
    }

}
