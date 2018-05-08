package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventAffectedWebsiteDAO;
import com.tonik.model.EventAffectedWebsite;

/**
 * @desc:事件影响网站，service层
 * @spring.bean id="eventAffectedWebsiteService"
 * @spring.property name="eventAffectedWebsiteDAO" ref="EventAffectedWebsiteDAO"
 */
public class EventAffectedWebsiteService
{
    private IEventAffectedWebsiteDAO EventAffectedWebsiteDAO;
        
    public IEventAffectedWebsiteDAO getEventAffectedWebsiteDAO()
    {
        return EventAffectedWebsiteDAO;
    }

    public void setEventAffectedWebsiteDAO(IEventAffectedWebsiteDAO eventAffectedWebsiteDAO)
    {
        EventAffectedWebsiteDAO = eventAffectedWebsiteDAO;
    }
    
    //获取该事件影响的所有网站
    public List<EventAffectedWebsite> getEventAffectedWebsites(Long eventId)
    {
        return EventAffectedWebsiteDAO.getEventAffectedWebsites(eventId);
    }

    //通过影响id获取某事件影响的特定网站
    public EventAffectedWebsite getEventAffectedWebsiteById(Long id)
    {
        return  EventAffectedWebsiteDAO.getEventAffectedWebsiteById(id);
    }
    
    //保存受影响的网站
    public void saveEventAffectedWebsite(EventAffectedWebsite eventAffectedWebsite)
    {
        EventAffectedWebsiteDAO.saveEventAffectedWebsite(eventAffectedWebsite);
    }

    //删除受影响的网站
    public void removeEventAffectedWebsite(EventAffectedWebsite eventAffectedWebsite)
    {
        EventAffectedWebsiteDAO.removeEventAffectedWebsite(eventAffectedWebsite);
    }

    //获取某页所有受影响的网站
    public List<EventAffectedWebsite> getEventAffectedWebsitePaging(int pageIndex, int pageSize, String strQuery,Long eventId)
    {
        return EventAffectedWebsiteDAO.getEventAffectedWebsitePaging(pageIndex, pageSize, strQuery,eventId);
    }

    //获取受影响的网站总数
    public String getEventAffectedWebsiteTotal(Long eventId)
    {
        return Integer.toString(EventAffectedWebsiteDAO.getEventAffectedWebsitesTotal(eventId));
    }
}
