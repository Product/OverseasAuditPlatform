package com.tonik.service;

import java.util.List;

import com.tonik.dao.IReportTemplateDAO;
import com.tonik.model.ReportTemplate;

/**
 * @spring.bean id="ReportTemplateService"
 * @spring.property name="reportTemplateDAO" ref="ReportTemplateDAO"
 */
public class ReportTemplateService  
{
    private IReportTemplateDAO reportTemplateDAO;
    
    public IReportTemplateDAO getReportTemplateDAO()
    {
        return reportTemplateDAO;
    }
    
    public void setReportTemplateDAO(IReportTemplateDAO ReportTemplateDAO)
    {
        this.reportTemplateDAO = ReportTemplateDAO;
    }
    
    public void DelReportTemplate(Long Id)
    {
        reportTemplateDAO.removeObject(ReportTemplate.class, Id);
    }
    

    public void SaveReportTemplate(ReportTemplate reportTemplate)
    {
        reportTemplateDAO.saveReportTemplate(reportTemplate);
    }
    
    public ReportTemplate GetReportTemplateById(Long Id)
    {
        ReportTemplate rt=reportTemplateDAO.getReportTemplate(Id);
        return rt;
    }

    public List<ReportTemplate> ReportTemplatePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return reportTemplateDAO.getReportTemplatePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String ReportTemplateTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(reportTemplateDAO.getReportTemplateTotal(strQuery, strStraTime, strEndTime));
    }
    
    //返回单条ReportTemplate信息
    public String getReportTemplateInfo(ReportTemplate reportTemplate){
        String res = "";
        res += "{\"Id\":\"" + reportTemplate.getId() + "\",\"Name\":\"" + reportTemplate.getName()
                + "\",\"TemplateStyle\":\"" + reportTemplate.getTemplateStyle()
                + "\",\"TemplateFile\":\"" + reportTemplate.getTemplateFile()
                + "\",\"TemplateContent\":\"" + reportTemplate.getTemplateContent()
                + "\",\"CreatePerson\":\"" + reportTemplate.getCreatePersonName()
                + "\",\"CreateTime\":\"" + reportTemplate.getFormatCreateTime() + "\"}";
        return res;
    }

}