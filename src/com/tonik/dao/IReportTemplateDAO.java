package com.tonik.dao;

import java.util.List;

import com.tonik.model.ReportTemplate;

public interface IReportTemplateDAO extends IDAO
{
    public List<ReportTemplate> getReportTemplate();

    public ReportTemplate getReportTemplate(Long reportTemplateId);

    public void saveReportTemplate(ReportTemplate reportTemplate);

    public void removeReportTemplate(ReportTemplate reportTemplate);
    
    public List<ReportTemplate> getReportTemplatePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getReportTemplateTotal(String strQuery, String strStraTime, String strEndTime);
}
