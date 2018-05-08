package com.tonik.dao;

import java.util.List;

import com.tonik.model.EvaluationType;

public interface IEvaluationTypeDAO extends IDAO
{
    public List<EvaluationType> getEvaluationType();

    public EvaluationType getEvaluationType(Long evaluationTypeId);

    public void saveEvaluationType(EvaluationType evaluationType);

    public void removeEvaluationType(EvaluationType evaluationType);
    
    public void removeEvaluationType(Long evaluationTypeId);
    
    public List<EvaluationType> getEvaluationTypePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getEvaluationTypeTotal(String strQuery, String strStraTime, String strEndTime);

}
