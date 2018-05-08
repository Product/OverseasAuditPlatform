package com.tonik.dao;

import java.util.List;

import com.tonik.model.VentureAnalysis;

public interface IVentureAnalysisDAO
{
    public List<VentureAnalysis> getVentureAnalysis();

    public VentureAnalysis getVentureAnalysis(Long ventureAnalysisId);

    public void saveVentureAnalysis(VentureAnalysis ventureAnalysis);
    
    public void removeVentureAnalysis(VentureAnalysis ventureAnalysis);

    public void removeVentureAnalysis(Long ventureAnalysisId);
    
    public List<Object[]> getVentureAnalysisPaging(final int pageIndex, final int pageSize,
            final String strQuery, final String strStraTime,final String strEndTime);
    
    public int getVentureAnalysisTotal(String strQuery, String strStraTime, String strEndTime);
    
}
