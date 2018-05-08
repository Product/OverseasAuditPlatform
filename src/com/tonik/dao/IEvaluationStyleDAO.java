package com.tonik.dao;

import java.util.List;

import com.tonik.model.EvaluationStyle;

public interface IEvaluationStyleDAO extends IDAO
{
    public List<EvaluationStyle> getEvaluationStyle();

    public EvaluationStyle getEvaluationStyle(Long evaluationStyleId);

    public void saveEvaluationStyle(EvaluationStyle evaluationStyle);

    public void removeEvaluationStyle(EvaluationStyle evaluationStyle);

    List<Object[]> listEvaluationStyles(Long parent, Long tree);
}
