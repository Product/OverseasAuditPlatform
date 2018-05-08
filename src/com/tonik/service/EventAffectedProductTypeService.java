package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventAffectedProductTypeDAO;
import com.tonik.model.EventAffectedProductType;
/**
 * @desc:事件影响商品类别 Service层
 * @spring.bean id="eventAffectedProductTypeService"
 * @spring.property name="eventAffectedProductTypeDAO" ref="EventAffectedProductTypeDAO"
 */
public class EventAffectedProductTypeService
{
    private IEventAffectedProductTypeDAO EventAffectedProductTypeDAO;
    
    public IEventAffectedProductTypeDAO getEventAffectedProductTypeDAO()
    {
        return EventAffectedProductTypeDAO;
    }

    public void setEventAffectedProductTypeDAO(IEventAffectedProductTypeDAO eventAffectedProductTypeDAO)
    {
        EventAffectedProductTypeDAO = eventAffectedProductTypeDAO;
    }
    
    /**
     * @desc 获取影响的所有商品种类
     * @param eventId 事件id
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypes(Long eventId)
    {
        return EventAffectedProductTypeDAO.getEventAffectedProductTypes(eventId);
    }
    
    /**
     * @desc 通过EventAffectedProductType的id获取EventAffectedProductType
     * @param id the id of EventAffectedProductType
     * @return EventAffectedProductType
     */
    public EventAffectedProductType getEventAffectedProductTypeById(Long id)
    {
        return EventAffectedProductTypeDAO.getEventAffectedProductTypeById(id);
    }

    /**
     * @desc 保存事件影响的商品类型
     * @param eventAffectedProductType
     */
    public void saveEventAffectedProductType(EventAffectedProductType eventAffectedProductType)
    {
        EventAffectedProductTypeDAO.saveEventAffectedProductType(eventAffectedProductType);
    }

    /**
     * @desc 删除事件影响的商品类型
     * @param eventAffectedProductType
     */
    public void removeEventAffectedProductType(EventAffectedProductType eventAffectedProductType)
    {
        EventAffectedProductTypeDAO.removeEventAffectedProductType(eventAffectedProductType);
    }

    /**
     * @desc 获取事件影响的商品类型分页列表
     * @param pageIndex 页面
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypePaging(int pageIndex, int pageSize,String strQuery,Long eventId)
    {
        return EventAffectedProductTypeDAO.getEventAffectedProductTypePaging(pageIndex, pageSize, strQuery,eventId);
    }

    /**
     * @desc 获取事件影响的商品类型总数
     * @param eventId 事件id
     * @return String
     */
    public String getEventAffectedProductTypeTotal(Long eventId)
    {
        return Integer.toString(EventAffectedProductTypeDAO.getEventAffectedProductTypesTotal(eventId));
    }
}
