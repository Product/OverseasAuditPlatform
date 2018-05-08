package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

import com.tonik.model.EvaluationIndex;

public interface IEvaluationIndexDAO extends IDAO
{
    public List<EvaluationIndex> getEvaluationIndex();

    public EvaluationIndex getEvaluationIndex(Long evaluationIndexId);

    public void saveEvaluationIndex(EvaluationIndex EvaluationIndex);

    public void removeEvaluationIndex(EvaluationIndex EvaluationIndex);
    
    public List<Object[]> getEvaluationIndexPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getEvaluationIndexTotal(String strQuery, String strStraTime, String strEndTime);

    List<EvaluationIndex> getEvaluationIndices(String strQuery);

    List<EvaluationIndex> getEvaluationIndexByStyle(Long evaluationStyleId);
    
    Object loadObject(Class clazz, Serializable id);
}
