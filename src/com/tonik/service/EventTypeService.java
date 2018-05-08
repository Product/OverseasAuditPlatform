package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventTypeDAO;
import com.tonik.model.EventType;

/**
 * @desc 事件类别 service
 * @spring.bean id="eventTypeService"
 * @spring.property name="eventTypeDAO" ref="EventTypeDAO"
 */
public class EventTypeService
{
    private IEventTypeDAO eventTypeDAO;
    
    public IEventTypeDAO getEventTypeDAO()
    {
        return eventTypeDAO;
    }

    public void setEventTypeDAO(IEventTypeDAO eventTypeDAO)
    {
        this.eventTypeDAO = eventTypeDAO;
    }
    /**
     * @desc 通过id获得对应事件类型
     * @param id 事件类型id
     * @return EventType
     */
    public EventType getEventTypeById(Long id){
        return eventTypeDAO.getEventTypeById(id);
    }
    
    /**
     * @desc 获得所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypes(){
        return eventTypeDAO.getEventTypes();
    }
    
    /**
     * @desc 获得疫情事件外的所有所有事件类型
     * @return List<EventType>
     */
    public List<EventType> getEventTypesExceptEpidemic(){
        return eventTypeDAO.getEventTypesExceptEpidemic();
    }
}
