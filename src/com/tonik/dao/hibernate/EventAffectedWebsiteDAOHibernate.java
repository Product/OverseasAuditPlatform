package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedWebsiteDAO;
import com.tonik.model.EventAffectedWebsite;
/**
 * <p>
 * Title: Tonik Candidates
 * </p>
 * <p>
 * Description: 事件影响网站模块 DAO层接口的实现
 * </p>
 * @since Oct 30, 2015
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedWebsiteDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedWebsiteDAOHibernate extends BaseDaoHibernate implements IEventAffectedWebsiteDAO
{

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#getEventAffectedWebsites(java.lang.Long)
     * @Override
     * @desc:获取该事件影响的所有网站
     * @param eventId the eventId of EventAffectedWebsite
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsites(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedWebsite where eventId="+eventId);
    }
 
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#getEventAffectedWebsiteById(java.lang.Long)
     * @Override
     * @desc:通过影响id获取受影响的特定网站
     * @param id the id of EventAffectedWebsite
     * @return EventAffectedWebsite
     */
    public EventAffectedWebsite getEventAffectedWebsiteById(Long id)
    {
        return  (EventAffectedWebsite) getHibernateTemplate().get(EventAffectedWebsite.class,id);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#saveEventAffectedWebsite(com.tonik.model.EventAffectedWebsite)
     * @Override
     * @desc:保存受影响的网站
     * @param EventAffectedWebsite
     * @return void
     */
    public void saveEventAffectedWebsite(EventAffectedWebsite eventAffectedWebsite)
    {
        //检查数据是否重复
        Long eventId = eventAffectedWebsite.getEventId();
        Long websiteId = eventAffectedWebsite.getWebsiteId();
        
        if(getHibernateTemplate().find("from EventAffectedWebsite where eventId="+eventId+" and websiteId="+websiteId).size()==0){
            getHibernateTemplate().saveOrUpdate(eventAffectedWebsite);
        }else{
            throw new RuntimeException("Same Website!");
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#removeEventAffectedWebsite(com.tonik.model.EventAffectedWebsite)
     * @Override
     * @desc:删除受影响的网站
     * @param EventAffectedWebsite
     * @return void
     */
    public void removeEventAffectedWebsite(EventAffectedWebsite eventAffectedWebsite)
    {
        getHibernateTemplate().delete(eventAffectedWebsite);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#getEventAffectedWebsitePaging(int, int, java.lang.String, java.lang.Long)
     * @Override
     * @desc:获取某页所有受影响的网站
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId EventAffectedWebsite 的 id
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsitePaging(int pageIndex, int pageSize,String strQuery,Long eventId)
    {
        try{
            final String hql = "from EventAffectedWebsite where eventId=:strEventId and websiteName like :strQuery order by id desc";
                     
            List<EventAffectedWebsite> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                    {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery","%"+strQuery+"%");
                    query.setParameter("strEventId",eventId);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List<EventAffectedWebsite> list = query.list();
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
     * @see com.tonik.dao.IEventAffectedWebsiteDAO#getEventAffectedWebsitesTotal(java.lang.Long)
     * @Override
     * @desc:通过事件id获取受影响的网站总数
     * @param eventId the eventId of EventAffectedWebsite
     * @return int
     */
    public int getEventAffectedWebsitesTotal(Long eventId)
    {
        return getEventAffectedWebsites(eventId).size();
    }

    /**
     * @Override
     * @desc 通过事件id数组获得受影响的网站
     * @param eventIds 事件id数组
     * @return List<EventAffectedWebsite>
     */
    public List<EventAffectedWebsite> getEventAffectedWebsites(Long[] eventIds)
    {
        try{
            final String hql="from EventAffectedWebsite where eventId in (:eventIds)";
                
            List<EventAffectedWebsite> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
}
