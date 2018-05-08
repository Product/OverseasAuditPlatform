package com.tonik.dao.hibernate;

import java.util.Date;

import com.tonik.dao.IReportTemplateDAO;
import com.tonik.model.ReportTemplate;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing ReportTemplateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="reportTemplateDAOTest"
 * @spring.property name="reportTemplateDAO" ref="ReportTemplateDAO"
 */
public class ReportTemplateDAOTest extends BaseDaoTestCase
{
    private IReportTemplateDAO reportTemplateDAO;


    public void setReportTemplateDAO(IReportTemplateDAO reportTemplateDAO)
    {
        this.reportTemplateDAO = reportTemplateDAO;
    }

    public void setReportDAO(IReportTemplateDAO reportTemplateDAO)
    {
        this.reportTemplateDAO = reportTemplateDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(ReportTemplateDAOTest.class);
    }

    public void testAddAndRemovereportTemplate() throws Exception
    {
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setName("name");
        reportTemplate.setTemplateStyle("已发送");
//        reportTemplate.setTemplateStyleName("name");
        reportTemplate.setTemplateContent("content");
        reportTemplate.setTemplateFile("<p>As title, please introduce process!</p>");
        reportTemplate.setCreatePerson(null);
        reportTemplate.setCreateTime(new Date());
        reportTemplateDAO.saveReportTemplate(reportTemplate);
        setComplete();
        endTransaction();

        startNewTransaction();
        reportTemplate = reportTemplateDAO.getReportTemplate(1l);
        assertNotNull(reportTemplate.getName());
        assertNotNull(reportTemplate.getTemplateStyle());
//        assertNotNull(reportTemplate.getTemplateStyleName());
        assertNotNull(reportTemplate.getTemplateContent());
        assertNotNull(reportTemplate.getTemplateFile());
        assertNotNull(reportTemplate.getCreatePerson());
        assertNotNull(reportTemplate.getCreateTime());

        reportTemplateDAO.removeReportTemplate(reportTemplate);
        setComplete();
        endTransaction();

        reportTemplate = reportTemplateDAO.getReportTemplate(1l);
        assertNull(reportTemplate);
    }
}


