package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedProductTypeDAO;
import com.tonik.model.EventAffectedProductType;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响商品类别 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedProductTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedProductTypeDAOHibernate extends BaseDaoHibernate implements IEventAffectedProductTypeDAO
{
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypes(java.lang.Long)
     * @Override
     * @desc:获取影响的所有商品种类
     * @param eventId the eventId of EventAffectedProductType
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypes(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedProductType where eventId ="+eventId);
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypes(java.lang.Long[])
     * @Override
     * @desc: 通过事件id数组获取事件影响的所有商品种类
     * @param eventIds 事件id数组
     * @return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypes(Long[] eventIds)
    {
        final String hql="from EventAffectedProductType where eventId in (:eventIds)";
        try{
            List<EventAffectedProductType> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameterList("eventIds", eventIds);
                    List list = query.list();
                    return list;
                }
            });           
            return listTable;
        }catch(Exception e)
        {
        return null;
        }
    }
      
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypeById(java.lang.Long)
     * @Override
     * @desc:通过影响id获取某事件影响的特定商品类型
     * @param id the id of EventAffectedProductType
     * @return EventAffectedProductType
     */
    public EventAffectedProductType getEventAffectedProductTypeById(Long id)
    {
        return (EventAffectedProductType) this.getHibernateTemplate().get(EventAffectedProductType.class,id);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#saveEventAffectedProductType(com.tonik.model.EventAffectedProductType)
     * @Override
     * @desc:保存事件影响的商品类型
     * @param EventAffectedProductType
     * @return void
     */
    public void saveEventAffectedProductType(EventAffectedProductType eventAffectedProductType)
    {
        //检查数据是否重复
        Long eventId = eventAffectedProductType.getEventId();
        Long firstId = eventAffectedProductType.getProductFirstTypeId();
        Long secondId = eventAffectedProductType.getProductSecondTypeId();
        Long thirdId = eventAffectedProductType.getProductThirdTypeId();
        
        if(getHibernateTemplate().find("from EventAffectedProductType where eventId="+eventId
                +" and productFirstTypeId="+firstId+" and productSecondTypeId="+secondId+" and productThirdTypeId="+thirdId).size()==0){
            getHibernateTemplate().saveOrUpdate(eventAffectedProductType);
        }else{
            throw new RuntimeException("Same Area!");
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#removeEventAffectedProductType(com.tonik.model.EventAffectedProductType)
     * @Override
     * @desc:删除事件影响的商品类型
     * @param EventAffectedProductType
     * @return void
     */
    public void removeEventAffectedProductType(EventAffectedProductType eventAffectedProductType)
    {
        getHibernateTemplate().delete(eventAffectedProductType);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypePaging(int, int, java.lang.String, java.lang.Long)
     * @Override
     * @desc:获取某页所有受影响的商品类型
     * @param:pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId EventAffectedProductType 的 eventId
     * return List<EventAffectedProductType>
     */
    public List<EventAffectedProductType> getEventAffectedProductTypePaging(int pageIndex, int pageSize,String strQuery,Long eventId)
    {
        try{
            final String hql = "from EventAffectedProductType where eventId=:strEventId and(productFirstTypeName like :strQuery or productSecondTypeName like :strQuery or productThirdTypeName like :strQuery) order by id desc";
                     
            List<EventAffectedProductType> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                    {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%"+strQuery+"%");
                    query.setParameter("strEventId", eventId);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List<EventAffectedProductType> list = query.list();
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
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypesTotal(java.lang.Long)
     * @Override
     * @desc:获取受影响的商品类型总数
     * @param eventId the eventId of EventAffectedProductType
     * @return int
     */
    public int getEventAffectedProductTypesTotal(Long eventId)
    {
        return getEventAffectedProductTypes(eventId).size();
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedProductTypeDAO#getEventAffectedProductTypesTotal(java.lang.Long[])
     * @Override
     * @desc 通过事件id数组获得商品类别总数
     * @return int
     */
    public int getEventAffectedProductTypesTotal(Long[] eventIds)
    {
        return getEventAffectedProductTypes(eventIds).size();
    }
}
