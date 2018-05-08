package com.tonik.dao;

import java.util.List;

import com.tonik.model.DetectingSuggest;

public interface IDetectingSuggestDAO extends IDAO
{

    public List<DetectingSuggest> getDetectingSuggest();

    public DetectingSuggest getDetectingSuggest(Long detectingSuggestId);

    public void saveDetectingSuggest(DetectingSuggest detectingSuggest);

    public void removeDetectingSuggest(DetectingSuggest detectingSuggest);
    
    public List<Object[]> getDetectingSuggestPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getDetectingSuggestTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List getDetectingSuggest(String strSql, int reRowCount);
    
    public String getDashboardJson(String strSql, int reRowCount, String strKeyWord);
    
    public void delFromDetectingSuggest(Long eventId);
    
    public String getEvaluationSuggest(String strSql, int reRowCount);
    
    public List getEvaluationRecord(String strSql, int reRowCount);
}
