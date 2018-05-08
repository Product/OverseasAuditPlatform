package com.tonik.dao.hibernate;

import java.util.List;

import com.tonik.dao.IEventTypeDAO;
import com.tonik.model.EventType;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件概览模块 DAO层接口的实现
 * </p>
 * @since Nov 03, 2015
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventTypeDAOHibernate extends BaseDaoHibernate implements IEventTypeDAO
{

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventTypeDAO#getEventTypeById(java.lang.Long)
     * @desc 通过id获得事件类型
     * @Override
     * @param id the id of EventType
     * @return EventType
     */
    public EventType getEventTypeById(Long id)
    {
        return (EventType) getHibernateTemplate().find("from EventType where id =" + id).get(0);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventTypeDAO#getEventTypes()
     * @Override
     * @desc 获得所有非疫情事件类型
     * @return List<EventType>
     */
    @SuppressWarnings("unchecked")
    public List<EventType> getEventTypes()
    {
        return getHibernateTemplate().find("from EventType");
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public List<EventType> getEventTypesExceptEpidemic()
    {
        return getHibernateTemplate().find("from EventType where name <>'疫情事件'");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<EventType> getEventTypesAboutArea(){
        return getHibernateTemplate().find("from EventType where affectArea=1");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventType> getEventTypesAboutBrand(){
        return getHibernateTemplate().find("from EventType where affectBrand=1");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<EventType> getEventTypesAboutProductType(){
        return getHibernateTemplate().find("from EventType where affectProductType=1");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventType> getEventTypesAboutMaterial()
    {
        return getHibernateTemplate().find("from EventType where affectMaterial=1");
    }
}
