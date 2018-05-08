package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventAffectedProductType;
/**
 * @desc 事件影响商品类别 DAO
 * @author thinvent
 *
 */
public interface IEventAffectedProductTypeDAO
{   

    /**
     * @desc 通过id获取该事件影响的所有商品种类
     * @param eventId 事件id
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypes(Long eventId);
    
    /**
     * @desc 通过id获得特定商品种类
     * @param id the id of EventAffectedProductType
     * @return EventAffectedProductType
     */
    public EventAffectedProductType getEventAffectedProductTypeById(Long id);
  
    /**
     * @desc 保存商品种类
     * @param eventEffectProductType
     */
    public void saveEventAffectedProductType(EventAffectedProductType eventEffectProductType);
     
    /**
     * @desc 删除商品种类 
     * @param eventEffectProductType
     */
    public void removeEventAffectedProductType(EventAffectedProductType eventEffectProductType);
    
    /**
     * @desc 获取商品种类分页列表
     * @param pageIndex 页码数
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypePaging(int pageIndex, int pageSize,String strQuery,Long eventId);
    
    /**
     * @desc 获取影响商品种类总数
     * @param eventId
     * @return int
     */
    public int getEventAffectedProductTypesTotal(Long eventId);
}
