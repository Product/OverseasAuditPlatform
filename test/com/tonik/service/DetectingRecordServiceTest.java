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
 * spring.bean id="DetectingRecordServiceTest"
 * spring.property name="DetectingRecordService" ref="DetectingRecordService"
 */
public class DetectingRecordServiceTest extends BaseDaoTestCase
{
    private DetectingRecordService DetectingRecordService;

    

    public DetectingRecordService getDetectingRecordService()
    {
        return DetectingRecordService;
    }

    public void setDetectingRecordService(DetectingRecordService detectingRecordService)
    {
        DetectingRecordService = detectingRecordService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(DetectingRecordServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(DetectingRecordService);
    }

}
