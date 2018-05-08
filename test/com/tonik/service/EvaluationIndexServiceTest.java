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
 * spring.bean id="EvaluationIndexServiceTest"
 * spring.property name="consultiveService" ref="EvaluationIndexService"
 */
public class EvaluationIndexServiceTest extends BaseDaoTestCase
{
    private EvaluationIndexService EvaluationIndexService;

    

    public EvaluationIndexService getEvaluationIndexService()
    {
        return EvaluationIndexService;
    }

    public void setEvaluationIndexService(EvaluationIndexService EvaluationIndexService)
    {
        this.EvaluationIndexService = EvaluationIndexService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(EvaluationIndexServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(EvaluationIndexService);
    }

}
