package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventAffectedAreaDAO;
import com.tonik.model.EventAffectedArea;

/**
* @desc:事件影响地区
* @spring.bean id="eventAffectedAreaService"
* @spring.property name="eventAffectedAreaDAO" ref="EventAffectedAreaDAO"
*/
public class EventAffectedAreaService
{
    private IEventAffectedAreaDAO EventAffectedAreaDAO;
    
    public IEventAffectedAreaDAO getEventAffectedAreaDAO()
    {
        return EventAffectedAreaDAO;
    }

    public void setEventAffectedAreaDAO(IEventAffectedAreaDAO eventAffectedAreaDAO)
    {
        EventAffectedAreaDAO = eventAffectedAreaDAO;
    }

    /**
     * @desc:通过事件id获取某事件影响的所有区域
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreas(Long eventId)
    {
        return EventAffectedAreaDAO.getEventAffectedAreas(eventId);
    }

    /**
     * @desc 通过EventAffectedArea的id获取EventAffectedArea
     * @param id the id of EventAffectedArea
     * @return EventAffectedArea
     */
    public EventAffectedArea getEventAffectedAreaById(Long id)
    {
        return EventAffectedAreaDAO.getEventAffectedAreaById(id);
    }

    /**
     * @desc 保存事件影响区域
     * @param eventAffectedArea
     */
    public void saveEventAffectedArea(EventAffectedArea eventAffectedArea)
    {
        EventAffectedAreaDAO.saveEventAffectedArea(eventAffectedArea);
    }

    /**
     * @desc 删除对应区域
     * @param eventAffectedArea
     */
    public void removeEventAffectedArea(EventAffectedArea eventAffectedArea)
    {
        EventAffectedAreaDAO.removeEventAffectedArea(eventAffectedArea);     
    }

    /**
     * @desc 获取事件影响的地区分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreaPaging(int pageIndex, int pageSize,String strQuery,Long eventId){
 
        return EventAffectedAreaDAO.getEventAffectedAreaPaging(pageIndex, pageSize, strQuery,eventId);
    }
    
    /**
     * @desc 获取对应区域总数
     * @param eventId 事件id
     * @return String
     */
    public String getEventAffectedAreaTotal(Long eventId)
    {
        return Integer.toString(EventAffectedAreaDAO.getEventAffectedAreaTotal(eventId));
    }

    /**
     * @desc :通过事件id获取受影响的国家列表
     * @param eventList 事件id数组
     * @return List<Long>
     */
    public List<Long> getEventCountryIds(Long[] eventList)
    {
        return EventAffectedAreaDAO.getCountryIds(eventList);
    }
}
