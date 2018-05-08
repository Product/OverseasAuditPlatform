package com.tonik.service;

import java.util.List;

import com.tonik.dao.IDetectingRecordDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.DetectingRecord;
import com.tonik.model.Website;
//检测记录模块 service层
/**
 * @spring.bean id="DetectingRecordService"
 * @spring.property name="detectingRecordDAO" ref="DetectingRecordDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 */
public class DetectingRecordService
{
    private IDetectingRecordDAO DetectingRecordDAO;
    private IWebsiteDAO WebsiteDAO;


    public void setDetectingRecordDAO(IDetectingRecordDAO DetectingRecordDAO)
    {
        this.DetectingRecordDAO = DetectingRecordDAO;
    }
    
    public IWebsiteDAO getWebsiteDAO()
    {
        return WebsiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.WebsiteDAO = websiteDAO;
    }

    //获取符合条件的所有检测记录
    public List<DetectingRecord> DetectingRecordPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        List<DetectingRecord> ls = DetectingRecordDAO.getDetectingRecordPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    //获取检测评价总数
    public String DetectingRecordTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(DetectingRecordDAO.getDetectingRecordTotal(strQuery, strStraTime, strEndTime));
    }

    //通过ID获取检测记录
    public DetectingRecord GetDetectingRecordById(long parseLong)
    {
        DetectingRecord dr= DetectingRecordDAO.getDetectingRecord(parseLong);
        Website w=WebsiteDAO.getWebsite(dr.getWebsite().getId());
        dr.setWebsite(w);
        return dr;
    }

    //删除检测记录 
    public void DelDetectingRecord(long parseLong)
    {
        DetectingRecordDAO.removeDetectingRecord(this.GetDetectingRecordById(parseLong));
    }

    //保存检测记录
    public void SaveDetectingRecord(DetectingRecord detectingRecord,String wid)
    {
        Website w=WebsiteDAO.getWebsite(Long.parseLong(wid));
        detectingRecord.setWebsite(w);
        DetectingRecordDAO.saveDetectingRecord(detectingRecord);
    }
}