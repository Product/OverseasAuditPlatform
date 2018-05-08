package com.tonik.dao;

import java.util.List;

import com.tonik.model.Report;

public interface IReportDAO extends IDAO
{
    public List<Report> getReport();

    public Report getReport(Long reportId);

    public void saveReport(Report report);

    public void removeReport(Report report);
    
    public List<Object[]> getReportPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getReportTotal(String strQuery, String strStraTime, String strEndTime);
}
