package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventAffectedBrand;

/**
 * 
 * @author thinvent
 * @desc:事件影响品牌 DAO
 */
public interface IEventAffectedBrandDAO
{
    /**
     * @desc 通过事件id获取该事件影响的所有品牌
     * @param eventIds 事件id列表
     * @return List<EventAffectedBrand>
     */
    public List<EventAffectedBrand> getEventAffectedBrands(Long[] eventIds);
   
    /**
     * @desc 通过EventAffectedBrand的id获取该EventAffectedBrand
     * @param id the id of EventAffectedBrand
     * @return EventAffectedBrand
     */
    public EventAffectedBrand getEventAffectedBrandById(Long id);
     
    /**
     * @desc 保存品牌
     * @param eventEffectBrand
     */
    public void saveEventAffectedBrand(EventAffectedBrand eventEffectBrand);
     
    /**
     * @desc 删除品牌 
     * @param eventEffectBrand
     */
    public void removeEventAffectedBrand(EventAffectedBrand eventEffectBrand);
   
    /**
     * @desc 获取影响品牌分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedBrand>
     */
    public List<EventAffectedBrand> getEventAffectedBrandPaging(int pageIndex, int pageSize, String strQuery,Long eventId);

    /**
     * @desc 通过事件id获取影响的品牌总数
     * @param eventId
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long eventId);
    
    /**
     * @desc 通过事件id数组获取影响的品牌总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long[] eventIds);
    
    public List<EventAffectedBrand> getEventAffectedBrandsByEvent(Long eventId);
}
