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
 * spring.bean id="ReportServiceTest"
 * spring.property name="consultiveService" ref="ReportService"
 */
public class ReportServiceTest extends BaseDaoTestCase
{
    private ReportService ReportService;

    

    public ReportService getReportService()
    {
        return ReportService;
    }

    public void setReportService(ReportService ReportService)
    {
        this.ReportService = ReportService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ReportServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(ReportService);
    }

}
