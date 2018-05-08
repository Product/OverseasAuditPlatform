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
 * spring.bean id="DetectingServiceTest"
 * spring.property name="DetectingService" ref="DetectingService"
 */
public class DetectingServiceTest extends BaseDaoTestCase
{
    private DetectingService DetectingService;

    

    public DetectingService getDetectingService()
    {
        return DetectingService;
    }

    public void setDetectingService(DetectingService DetectingService)
    {
        this.DetectingService = DetectingService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(DetectingServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(DetectingService);
    }

}
