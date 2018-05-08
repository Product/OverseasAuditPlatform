package com.tonik.dao;

import java.util.List;

import com.tonik.model.Extraction;

public interface IExtractionDAO
{
    public List<Extraction> getExtraction();
    
    public void SaveExtraction(Extraction extraction);
    
    public void deleteByRulesId(Long rulesId);
    
    public List<Extraction> getExtractionListByRulesId(Long rulesId);
    
    public int getExtractionTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<Extraction> getExtractionPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,String strEndTime);
    
    public List<Extraction> getExtractionSpecial(final String strQuery, final String strStraTime,final String strEndTime);
}
