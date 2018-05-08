package com.tonik.dao;

import java.util.List;

import com.tonik.model.Evaluation;

public interface IEvaluationDAO extends IDAO
{
    public List<Evaluation> getEvaluation();

    public Evaluation getEvaluation(Long evaluationId);

    public void saveEvaluation(Evaluation evaluation);

    public void removeEvaluation(Evaluation evaluation);
    
    public List<Object[]> getEvaluationPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getEvaluationTotal(String strQuery, String strStraTime, String strEndTime);
}
