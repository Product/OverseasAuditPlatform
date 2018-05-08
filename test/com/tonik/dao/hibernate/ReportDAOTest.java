package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IReportDAO;
import com.tonik.model.Report;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing ReportDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="reportDAOTest"
 * @spring.property name="reportDAO" ref="ReportDAO"
 */
public class ReportDAOTest extends BaseDaoTestCase
{
    private IReportDAO reportDAO;


    public void setReportDAO(IReportDAO reportDAO)
    {
        this.reportDAO = reportDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ReportDAOTest.class);
    }

    public void testAddAndRemovereport() throws Exception
    {
        Report report = new Report();
        report.setName("name");
//        report.setReportTemplateId(6L);
//        report.setReportTemplateName("name");
        report.setReportContent("content");
        //report.setReportFile(null);
        report.setReportStatus("no");
        report.setCreatePerson(null);
        report.setCreateTime(new Date());
        reportDAO.saveReport(report);
        setComplete();
        endTransaction();

        startNewTransaction();
        report = reportDAO.getReport(1l);
        assertNotNull(report.getName());
//        assertNotNull(report.getReportTemplateId());
        //assertNotNull(report.getReportTemplateName());
        assertNotNull(report.getReportContent());
        //assertNotNull(report.getReportFile());
        assertNotNull(report.getReportStatus());
        assertNotNull(report.getCreatePerson());
        assertNotNull(report.getCreateTime());

        reportDAO.removeReport(report);
        setComplete();
        endTransaction();

        report = reportDAO.getReport(1l);
        assertNull(report);
    }
}


