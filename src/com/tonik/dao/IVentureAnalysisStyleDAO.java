package com.tonik.dao;

import java.util.List;

import com.tonik.model.VentureAnalysisStyle;

public interface IVentureAnalysisStyleDAO extends IDAO
{
    public List<VentureAnalysisStyle> getVentureAnalysisStyle();

    public VentureAnalysisStyle getVentureAnalysisStyle(Long ventureAnalysisStyleId);

    public void saveVentureAnalysisStyle(VentureAnalysisStyle ventureAnalysisStyle);

    public void removeVentureAnalysisStyle(VentureAnalysisStyle ventureAnalysisStyle);
    
    public void removeVentureAnalysisStyle(Long ventureAnalysisStyleId);
    
    public List<VentureAnalysisStyle> getVentureAnalysisStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getVentureAnalysisStyleTotal(String strQuery, String strStraTime, String strEndTime);

}
