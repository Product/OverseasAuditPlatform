package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventType;
/**
 * @desc 事件类型 DAO层
 * @author thinvent
 *
 */
public interface IEventTypeDAO
{
    /**
     * @desc 通过事件类型id获得对应事件类型
     * @param id
     * @return EventType
     */
    public EventType getEventTypeById(Long id);
    
    /**
     * @desc 获得所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypes();
    
    /**
     * @desc 获得疫情事件外的所有所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypesExceptEpidemic();

    
    /**
     * @desc 获得有关地区的所有所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypesAboutArea();

    /**
     * @desc 获得有关品牌的所有所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypesAboutBrand();

    /**
     * @desc 获得有关商品类型的所有所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypesAboutProductType();

    public List<EventType> getEventTypesAboutMaterial();
}
