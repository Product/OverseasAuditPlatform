package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventAffectedWebsite;

/**
 * 
 * @author thinvent
 * @desc: 事件影响网站 DAO
 */
public interface IEventAffectedWebsiteDAO
{
    /**
     * @desc:通过事件id获取影响的所有网站
     * @param eventId 事件id
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsites(Long eventId);
    
    /**
     * @desc:通过事件id数组获取影响的所有网站
     * @param eventIds 事件id数组
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsites(Long[] eventIds);
    
    /**
     * @desc:通过EventAffectedWebsite的id获取EventAffectedWebsite
     * @param id the id of EventAffectedWebsite
     * @return EventAffectedWebsite
     */
    public EventAffectedWebsite getEventAffectedWebsiteById(Long id);
    
    /**
     * @desc 保存影响的网站
     * @param eventEffectWebsite
     */
    public void saveEventAffectedWebsite(EventAffectedWebsite eventEffectWebsite);
    
    /**
     * @desc 删除影响网站
     * @param eventEffectWebsite
     */
    public void removeEventAffectedWebsite(EventAffectedWebsite eventEffectWebsite);
    
    /**
     * @desc 获取受影响的网站分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsitePaging(int pageIndex, int pageSize,  String strQuery,Long eventId);

    /**
     * @desc 通过事件id数组获取影响网站总数   
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedWebsitesTotal(Long eventIds);
}
