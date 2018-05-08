package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventAffectedBrandDAO;
import com.tonik.model.EventAffectedBrand;

/**
* @desc:事件影响品牌，service层
* @spring.bean id="eventAffectedBrandService"
* @spring.property name="eventAffectedBrandDAO" ref="EventAffectedBrandDAO"
*/
public class EventAffectedBrandService
{
    private IEventAffectedBrandDAO EventAffectedBrandDAO;
    
    public IEventAffectedBrandDAO getEventAffectedBrandDAO()
    {
        return EventAffectedBrandDAO;
    }

    public void setEventAffectedBrandDAO(IEventAffectedBrandDAO eventAffectedBrandDAO)
    {
        EventAffectedBrandDAO = eventAffectedBrandDAO;
    }
    
    //获取某些事件对应的所有品牌
    public List<EventAffectedBrand> getEventAffectedBrands(Long[] eventId)
    {
        return EventAffectedBrandDAO.getEventAffectedBrands(eventId);
    }
    
    public List<EventAffectedBrand> getEventAffectedBrandsByEvent(Long eventId)
    {
        return EventAffectedBrandDAO.getEventAffectedBrandsByEvent(eventId);
    }
    
    //通过影响id获取某事件影响的特定品牌
    public EventAffectedBrand getEventAffectedBrandById(Long id)
    {
        return EventAffectedBrandDAO.getEventAffectedBrandById(id);
    }

    //保存事件影响的品牌
    public void saveEventAffectedBrand(EventAffectedBrand eventAffectedBrand)
    {
        EventAffectedBrandDAO.saveEventAffectedBrand(eventAffectedBrand);
    }

    //删除事件影响的品牌
    public void removeEventAffectedBrand(EventAffectedBrand eventAffectedBrand)
    {
        EventAffectedBrandDAO.removeEventAffectedBrand(eventAffectedBrand);
    }

    //获取事件影响品牌分页列表
    public List<EventAffectedBrand> getEventAffectedBrandPaging(int strPageIndex, int strPageCount, String strQuery,Long eventId)
    {
        return EventAffectedBrandDAO.getEventAffectedBrandPaging(strPageIndex, strPageCount, strQuery,eventId);
    }

    //获取某事件影响的品牌总数
    public String getEventAffectedBrandTotal(Long eventId)
    {
        return Integer.toString(EventAffectedBrandDAO.getEventAffectedBrandsTotal(eventId));
    }
}
