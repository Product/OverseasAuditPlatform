package com.tonik.dao;

import java.util.List;

import com.tonik.model.DetectingEvent;

//检测事件 DAO层接口
public interface IDetectingDAO extends IDAO
{
    public List<DetectingEvent> getDetecting();

    public DetectingEvent getDetecting(Long detectingId);

    public void saveDetecting(DetectingEvent detecting);

    public void removeDetecting(DetectingEvent detecting);
    
    public List<DetectingEvent> getDetectingPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getDetectingTotal(String strQuery, String strStraTime, String strEndTime);
}
