package com.tonik.dao;

import java.util.List;

import com.tonik.model.Event;

//事件概览，DAO层
public interface IEventDAO extends IDAO
{
    public List<Event> getEvents();
    
    /**
     * @desc 通过关键字和时间查找事件
     * @param strQuery 关键字
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return List<Event>
     */
    public List<Event> getEvents(String strQuery,String strStartTime,String strEndTime);
    
    /**
     * @desc 获得符合条件的非疫情事件
     * @param strQuery 关键字
     * @param eventTypeId 
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return List<Event>
     */
    public List<Event> getEvents(String strQuery,Long eventTypeId,String strStartTime,String strEndTime);
    
    /**
     * @desc 通过事件id获取事件
     * @param eventId 事件id
     * @return Event
     */
    public Event getEvent(Long eventId);
    
    /**
     * @desc 保存事件
     * @param event Event
     */
    public void saveEvent(Event event);

    /**
     * @desc 删除事件
     * @param event Event
     */
    public void removeEvent(Event event);
    
    /**
     * @desc 获取事件分页列表
     * @param pageIndex 页码数
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return List<Event>
     */
    public List<Event> getEventPaging(int pageIndex, int pageSize, String strQuery, String strStartTime, String strEndTime);
    
    /**
     * @desc 获取事件总数
     * @param strQuery 关键字 
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return int
     */
    public int getEventTotal(String strQuery, String strStartTime, String strEndTime);

    /**
     * @desc 获取符合条件的疫情事件
     * @param strQuery 关键字
     * @param epidemicTypeId 疫情类别id
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return List<Event>
     */
    public List<Event> getEpidemicEvent(String strQuery, Long epidemicTypeId, String strStartTime, String strEndTime);

    public List<Event> getUnrelatedEventPaging(List<Event> aet, int pageIndex, int pageSize, String strQuery,
            String strStartTime, String strEndTime);

    int getUnrelatedEventsTotal(List<Event> aet, String strQuery, String strStartTime, String strEndTime);
}
