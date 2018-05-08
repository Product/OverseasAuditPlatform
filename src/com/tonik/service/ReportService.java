package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IReportDAO;
import com.tonik.dao.IReportTemplateDAO;
import com.tonik.model.Report;
import com.tonik.model.ReportTemplate;

/**
 * @spring.bean id="ReportService"
 * @spring.property name="reportDAO" ref="ReportDAO"
 * @spring.property name="reportTemplateDAO" ref="ReportTemplateDAO"
 */
public class ReportService  
{
    private IReportDAO reportDAO;
    private IReportTemplateDAO reportTemplateDAO;
    
    public IReportDAO getReportDAO()
    {
        return reportDAO;
    }
    
    public IReportTemplateDAO getReportTemplateDAO()
    {
        return reportTemplateDAO;
    }
    
    public void setReportTemplateDAO(IReportTemplateDAO ReportTemplateDAO)
    {
        this.reportTemplateDAO = ReportTemplateDAO;
    }
    
    public void setReportDAO(IReportDAO ReportDAO)
    {
        this.reportDAO = ReportDAO;
    }
    
    public void DelReport(Long Id)
    {
        reportDAO.removeObject(Report.class,Id);
    }
    
    public ReportTemplate getReportTemplateById(Long Id)
    {
        return reportTemplateDAO.getReportTemplate(Id);
    }
    
    public void SaveReport(Report report)
    {
        reportDAO.saveReport(report);
    }
    
    public List<ReportTemplate> getReportTemplate()
    {
        return reportTemplateDAO.getReportTemplate();
    }
    
    public Report GetReportById(Long Id)
    {
//        Report rp=new Report();
//        rp.setId(1l);
//        rp.setName("name111");
//        rp.setReportTemplateId(2l);
//        rp.setReportTemplateName("reportTemplateName333");
//        rp.setReportFile(null);
//        rp.setReportContent("reportContent444");
//        rp.setReportStatus("已发布");
//        rp.setCreatePerson("createPerson555");
//        rp.setCreateTime(new Date());
        Report report = reportDAO.getReport(Id);
        return report;
    }
    
    public List<Report> ReportPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime)
    {
        List<Object[]> ls = reportDAO.getReportPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Report> report = new ArrayList<Report>();
       // String strJson="";
        for(Object[] item : ls)
        {
            Report r = (Report) item[0];
            report.add(r);
       // strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Content\":\""
       // + item.getContent() + "\",\"WebStyle\":\"" + item.getWebStyle() + "\",\"ProductType\":\""
       // + item.getProductType() + "\",\"Amount\":\"" + item.getAmount() + "\",\"Remark\":\""
       // + item.getRemark() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
       // + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
        }
       //return rulesDAO.getRulesPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return report;
//        return reportDAO.getReportPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String ReportTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(reportDAO.getReportTotal(strQuery, strStraTime, strEndTime));
    }
    
}
