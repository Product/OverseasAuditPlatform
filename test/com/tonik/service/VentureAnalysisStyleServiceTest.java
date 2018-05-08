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
 * spring.bean id="VentureAnalysisStyleServiceTest"
 * spring.property name="consultiveService" ref="VentureAnalysisStyleService"
 */
public class VentureAnalysisStyleServiceTest extends BaseDaoTestCase
{
    private VentureAnalysisStyleService VentureAnalysisStyleService;

    

    public VentureAnalysisStyleService getVentureAnalysisStyleService()
    {
        return VentureAnalysisStyleService;
    }

    public void setVentureAnalysisStyleService(VentureAnalysisStyleService VentureAnalysisStyleService)
    {
        this.VentureAnalysisStyleService = VentureAnalysisStyleService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(VentureAnalysisStyleServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(VentureAnalysisStyleService);
    }

}
