package com.tonik.dao;

import java.util.List;

import com.tonik.model.DetectingEvaluation;
//检测评价 DAO层接口
public interface IDetectingEvaluationDAO extends IDAO
{
    public List<DetectingEvaluation> getDetectingEvaluation();

    public DetectingEvaluation getDetectingEvaluation(Long detectingEvaluationId);

    public void saveDetectingEvaluation(DetectingEvaluation detectingEvaluation);

    public void removeDetectingEvaluation(DetectingEvaluation detectingEvaluation);
    
    public List<DetectingEvaluation> getDetectingEvaluationPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getDetectingEvaluationTotal(String strQuery, String strStraTime, String strEndTime);
}
