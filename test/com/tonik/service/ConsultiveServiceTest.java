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
 * spring.bean id="ConsultiveServiceTest"
 * spring.property name="consultiveService" ref="ConsultiveService"
 */
public class ConsultiveServiceTest extends BaseDaoTestCase
{
    private ConsultiveService ConsultiveService;

    

    public ConsultiveService getConsultiveService()
    {
        return ConsultiveService;
    }

    public void setConsultiveService(ConsultiveService ConsultiveService)
    {
        this.ConsultiveService = ConsultiveService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ConsultiveServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(ConsultiveService);
    }

}
