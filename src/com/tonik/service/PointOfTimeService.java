package com.tonik.service;

import com.tonik.dao.IPointOfTimeDAO;
import com.tonik.model.PointOfTime;

/**
 * @spring.bean id="PointOfTimeService"
 * @spring.property name="pointoftimeDAO" ref="PointOfTimeDAO"
 */
public class PointOfTimeService
{
    private IPointOfTimeDAO pointoftimeDAO;

    public IPointOfTimeDAO getPointoftimeDAO()
    {
        return pointoftimeDAO;
    }

    public void setPointoftimeDAO(IPointOfTimeDAO pointoftimeDAO)
    {
        this.pointoftimeDAO = pointoftimeDAO;
    }
    
    public void UpdateTime(PointOfTime NewDate)   //每次舆情抽取完后进行时间点的更新操作
    {
        pointoftimeDAO.UpdateTime(NewDate);
    }
    
    public PointOfTime getPointOfTime()   //获取最新一次的舆情抽取时间点
    {
        return pointoftimeDAO.getPointOfTime();
    }
}
