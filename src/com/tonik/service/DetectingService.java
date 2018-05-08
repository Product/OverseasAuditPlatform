package com.tonik.service;

import java.util.List;

import com.tonik.dao.IDetectingDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.DetectingEvent;
import com.tonik.model.Website;
//检测事件模块 service层
/**
 * @spring.bean id="DetectingService"
 * @spring.property name="detectingDAO" ref="DetectingDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 */
public class DetectingService
{
    private IDetectingDAO DetectingDAO;
    private IWebsiteDAO websiteDAO;


    public void setDetectingDAO(IDetectingDAO DetectingDAO)
    {
        this.DetectingDAO = DetectingDAO;
    }

    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }
    
    //获取某页所有检测事件
    public List<DetectingEvent> DetectingPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {  

        List<DetectingEvent> ls = DetectingDAO.getDetectingPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    //获取检测事件总数
    public String DetectingTotal(String strQuery, String strStraTime, String strEndTime)
    {
       return Integer.toString(DetectingDAO.getDetectingTotal(strQuery, strStraTime, strEndTime));
    }

    //保存检测事件
    public void SaveDetecting(DetectingEvent d,String wid)
    {
        Website w=websiteDAO.getWebsite(Long.parseLong(wid));
        d.setWebsite(w);
        DetectingDAO.saveDetecting(d);
    }

    //通过ID查找检测事件
    public DetectingEvent GetDetectingById(long Id)
    {
        
        DetectingEvent d= DetectingDAO.getDetecting(Id);
        Website w=websiteDAO.getWebsite(d.getWebsite().getId());
        d.setWebsite(w);
        return d;
    }

    //通过ID删除检测事件
    public void DelDetecting(long parseLong)
    {
        DetectingDAO.removeDetecting(this.GetDetectingById(parseLong));
    }

}
