package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventAffectedArea;

/**
 * @desc 事件影响地区 DAO
 * @author thinvent
 *
 */
public interface IEventAffectedAreaDAO
{   
    /**
     * @desc: 通过id获取该事件影响的所有区域
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreas(Long eventId);
    
    /**
     * @desc: 通过id获取影响的特定区域
     * @param id the id of EventAffectedArea
     * @return EventAffectedArea
     */
    public EventAffectedArea getEventAffectedAreaById(Long id);
    
    /**
     * @desc 保存影响区域
     * @param eventAffectedArea
     */
    public void saveEventAffectedArea(EventAffectedArea eventAffectedArea);
    
    /**
     * @desc  删除影响区域
     * @param eventAffectedArea
     */
    public void removeEventAffectedArea(EventAffectedArea eventAffectedArea);
    
    /**
     * @desc: 获取受影响的区域分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreaPaging(int pageIndex, int pageSize, String strQuery,Long eventId);
    
    /**
     * @desc: 获取事件影响的区域总数
     * @param eventId 事件id
     * @return int
     */
    public int getEventAffectedAreaTotal(Long eventId);

    /**
     * @desc: 获取事件影响的所有国家id
     * @param eventid 事件id
     * @return List<Long>
     */
    public List<Long> getCountryIds(Long[] eventid);
}
