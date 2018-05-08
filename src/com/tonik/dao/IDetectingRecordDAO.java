package com.tonik.dao;

import java.util.List;

import com.tonik.model.DetectingRecord;
//检测记录 DAO层接口
public interface IDetectingRecordDAO extends IDAO
{
    public List<DetectingRecord> getDetectingRecord();

    public DetectingRecord getDetectingRecord(Long detectingRecordId);

    public void saveDetectingRecord(DetectingRecord detectingRecord);

    public void removeDetectingRecord(DetectingRecord detectingRecord);
    
    public List<DetectingRecord> getDetectingRecordPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getDetectingRecordTotal(String strQuery, String strStraTime, String strEndTime);
}
