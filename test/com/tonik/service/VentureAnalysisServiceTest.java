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
 * spring.bean id="VentureAnalysisServiceTest"
 * spring.property name="consultiveService" ref="VentureAnalysisService"
 */
public class VentureAnalysisServiceTest extends BaseDaoTestCase
{
    private VentureAnalysisService VentureAnalysisService;

    

    public VentureAnalysisService getVentureAnalysisService()
    {
        return VentureAnalysisService;
    }

    public void setVentureAnalysisService(VentureAnalysisService VentureAnalysisService)
    {
        this.VentureAnalysisService = VentureAnalysisService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(VentureAnalysisServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(VentureAnalysisService);
    }

}
