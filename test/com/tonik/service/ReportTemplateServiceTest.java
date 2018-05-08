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
 * spring.bean id="ReportTemplateServiceTest"
 * spring.property name="consultiveService" ref="ReportTemplateService"
 */
public class ReportTemplateServiceTest extends BaseDaoTestCase
{
    private ReportTemplateService ReportTemplateService;

    

    public ReportTemplateService getReportTemplateService()
    {
        return ReportTemplateService;
    }

    public void setReportTemplateService(ReportTemplateService ReportTemplateService)
    {
        this.ReportTemplateService = ReportTemplateService;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ReportTemplateServiceTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        assertNotNull(ReportTemplateService);
    }

}
