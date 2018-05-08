package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedAreaDAO;
import com.tonik.model.EventAffectedArea;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响地区 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedAreaDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedAreaDAOHibernate extends BaseDaoHibernate implements IEventAffectedAreaDAO
{

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#getEventAffectedAreas(java.lang.Long)
     * @Override
     * 
     * @desc:通过事件id获取某事件影响的所有区域
     * @param eventId the eventId of EventAffectedArea
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreas(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedArea where eventId="+eventId);
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#getEventAffectedAreaById(java.lang.Long)
     * @Override
     * @desc:通过EventAffectedArea的id获取事件影响区域对象
     * @param id,id of EventAffectedArea
     * @return EventAffectedArea
     */
    public EventAffectedArea getEventAffectedAreaById(Long id)
    {
        return (EventAffectedArea) this.getHibernateTemplate().get(EventAffectedArea.class,id);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#saveEventAffectedArea(com.tonik.model.EventAffectedArea)
     * @Override
     * @desc:保存对应区域
     * @param EventAffectedArea
     * @return void
     */
    public void saveEventAffectedArea(EventAffectedArea eventAffectedArea)
    {
        //检查数据是否重复，目前不允许国家重复
        Long eventId = eventAffectedArea.getEventId();
        Long regionId = eventAffectedArea.getRegionId();
        Long countryId = eventAffectedArea.getCountryId();
        
        if(getHibernateTemplate().find("from EventAffectedArea where eventId="+eventId+" and regionId="+regionId+" and countryId="+countryId).size()==0){
            getHibernateTemplate().saveOrUpdate(eventAffectedArea);
        }else{
            throw new RuntimeException("Same Area!");
        }
    }


    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#removeEventAffectedArea(com.tonik.model.EventAffectedArea)
     * @Override
     * @desc:删除对应区域
     * @param EventAffectedArea
     * @return void
     */
    public void removeEventAffectedArea(EventAffectedArea eventAffectedArea)
    {
       getHibernateTemplate().delete(eventAffectedArea);     
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#getEventAffectedAreaPaging(int, int, java.lang.String, java.lang.Long)
     * @Override
     * @desc:获取某页所有对应区域
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId eventId of EventAffectedArea
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedArea> getEventAffectedAreaPaging(int pageIndex, int pageSize,String strQuery,Long eventId){
            
        try{
           final String hql = "from EventAffectedArea where eventId=:strEventId and( regionName like :strQuery or countryName like :strQuery or areaName like :strQuery)";
                    
           List<EventAffectedArea> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                   {
               public Object doInHibernate(Session session) throws HibernateException, SQLException
               {
                   Query query = session.createQuery(hql);
                   query.setParameter("strEventId", eventId);
                   query.setParameter("strQuery", "%"+strQuery+"%");
                   query.setFirstResult((pageIndex - 1) * pageSize);
                   query.setMaxResults(pageSize);
                   List<EventAffectedArea> list = query.list();
                   return list;
                   }
               });
           return listTable;
           }catch(Exception e){
               return null;
               }
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#getEventAffectedAreaTotal(java.lang.Long)
     * @Override
     * @desc:获取对应区域总数
     */
    public int getEventAffectedAreaTotal(Long eventId)
    {
        return getEventAffectedAreas(eventId).size();
    }  
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedAreaDAO#getCountryIds(java.lang.Long[])
     * @Override
     * @desc:通过事件id列表获取对应的国家id列表
     * @param eventList, EventAffectedArea的eventId数组
     * @return List<Long> countryId
     */
    public List<Long> getCountryIds(Long[] eventList)
    {   
        try{
            final String hql="select distinct countryId from EventAffectedArea where eventId in (:events)";
                    
            List<Long> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    if(eventList.length > 0)
                    {
                        query.setParameterList("events",eventList);
                    }
                    List<Long> list=query.list();
                            
                    return list;
                }
            });
           
            return listTable;
        }catch(Exception e){
            return null;
        }
    }
}
