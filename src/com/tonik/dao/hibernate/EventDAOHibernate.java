package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.Constant;
import com.tonik.dao.IEventDAO;
import com.tonik.model.Event;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件概览模块 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventDAOHibernate extends BaseDaoHibernate implements IEventDAO
{
    private final SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);//时间格式化 yyyy-MM-dd HH:mm:ss

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEvents()
     * @Override
     * @desc:获取所有事件
     * @return:List<Event>
     */
    public List<Event> getEvents()
    {
        return getHibernateTemplate().find("from Event");
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEvent(java.lang.Long)
     * @Override
     * @desc:通过id获取某个事件
     * @param eventId the eventId of Event
     * @return Event
     */
    public Event getEvent(Long eventId)
    {
        return (Event) getHibernateTemplate().get(Event.class,eventId);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#saveEvent(com.tonik.model.Event)
     * @Override
     * @desc:保存或更新事件
     * @param Event
     * @return void
     */
    public void saveEvent(Event event)
    {
        getHibernateTemplate().saveOrUpdate(event);       
    }
 
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#removeEvent(com.tonik.model.Event)
     * @Override
     * @desc:删除事件
     * @param Event
     * @return void
     */
    public void removeEvent(Event event)
    {
        getHibernateTemplate().delete(event);  
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEventPaging(int, int, java.lang.String, java.lang.String, java.lang.String)
     * @Override
     * @desc:获取分页事件列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param strStartTime 时间范围，最小时间
     * @param strEndTime 时间范围，最大时间
     * @return List<Event>
     */
    public List<Event> getEventPaging(int pageIndex, int pageSize, String strQuery, String strStartTime,
            String strEndTime)
    {
        try{
            final Date StartTime = dateFormater.parse(strStartTime);
            final Date EndTime = dateFormater.parse(strEndTime);

            final String hql = "from Event where (name like :strQuery or remark like :strQuery)"
                   + " and beginDate>=:strStartTime and beginDate<=:strEndTime order by beginDate desc";
            List<Event> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStartTime", StartTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
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
     * @see com.tonik.dao.IEventDAO#getEventTotal(java.lang.String, java.lang.String, java.lang.String)
     * @Override
     * @desc：获取符合条件的事件总数
     * @param strQuery 关键字
     * @param strStartTime 时间范围，最小时间
     * @param strEndTime 时间范围，最大时间
     * @return int
     */
    public int getEventTotal(String strQuery, String strStartTime, String strEndTime)
    {
        return getEvents(strQuery,strStartTime,strEndTime).size();
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEvents(java.lang.String, java.lang.String, java.lang.String)
     * @Override
     * @desc: 获取符合条件的所有事件
     * @param strQuery 关键字
     * @param strStartTime 时间范围，最小时间
     * @param strEndTime 时间范围,最大时间
     * @return List<Event>
     */
    public List<Event> getEvents(String strQuery, String strStartTime, String strEndTime)
    {
        try
        {
            String hql =  "from Event where (name like :strQuery  or remark like :strQuery)"
                    + " and beginDate>=:strStartTime and beginDate<=:strEndTime order by beginDate desc";

            String[] params = { "strQuery", "strStartTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", dateFormater.parse(strStartTime),dateFormater.parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args);
        } catch (Exception e)
        {
            return null;
        }
    }
 
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEvents(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     * @Override
     * @desc:获取符合条件的非疫情事件
     * @param strQuery 关键字
     * @param eventType 事件类型
     * @param strStartTime 时间范围，最小时间
     * @param strEndTime 时间范围,最大时间
     * @return List<Event>
     */
    public List<Event> getEvents(String strQuery, Long eventTypeId, String strStartTime, String strEndTime)
    {
        try
        {
            if(eventTypeId==null){
                String hql =  "from Event where (name like :strQuery  or remark like :strQuery) and typeName <>'疫情事件'"
                        + " and beginDate>=:strStartTime and beginDate <=:strEndTime order by beginDate desc";
    
                String[] params = { "strQuery","strStartTime", "strEndTime" };
    
                Object[] args = { "%" + strQuery + "%",dateFormater.parse(strStartTime),dateFormater.parse(strEndTime) };
    
                return this.getHibernateTemplate().findByNamedParam(hql, params, args);
            
            }else{
                String hql =  "from Event where (name like :strQuery  or remark like :strQuery) and typeId = :typeId"
                        + " and beginDate>=:strStartTime and beginDate <=:strEndTime order by beginDate desc";
    
                String[] params = { "strQuery","typeId","strStartTime", "strEndTime" };
    
                Object[] args = { "%" + strQuery + "%",eventTypeId,dateFormater.parse(strStartTime),dateFormater.parse(strEndTime) };
    
                return this.getHibernateTemplate().findByNamedParam(hql, params, args);
            }
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventDAO#getEpidemicEvent(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
     * @Override
     * @desc 获取符合条件的疫情事件
     * @param strQuery 关键字
     * @param epidemicTypeId 疫情类型
     * @param strStartTime 时间范围，最小时间
     * @param strEndTime 时间范围,最大时间
     * @return List<Event>
     */
    public List<Event> getEpidemicEvent(String strQuery, Long epidemicTypeId, String strStartTime, String strEndTime)
    {
        try
        {
            if(epidemicTypeId==null){
                String hql =  "from Event where (name like :strQuery  or remark like :strQuery) and typeName ='疫情事件'"
                        + " and beginDate>=:strStartTime and beginDate <=:strEndTime order by beginDate desc";
    
                String[] params = { "strQuery","strStartTime", "strEndTime" };
    
                Object[] args = { "%" + strQuery + "%",dateFormater.parse(strStartTime),dateFormater.parse(strEndTime) };
    
                return this.getHibernateTemplate().findByNamedParam(hql, params, args);
            
            }else{
                String hql =  "select e from Event e left join e.epidemic ep where (e.name like :strQuery  or e.remark like :strQuery) and ep.id = :epidemicTypeId"
                        + " and e.beginDate>=:strStartTime and e.beginDate <=:strEndTime order by e.beginDate desc";
    
                String[] params = { "strQuery","epidemicTypeId","strStartTime", "strEndTime" };
    
                Object[] args = { "%" + strQuery + "%",epidemicTypeId,dateFormater.parse(strStartTime),dateFormater.parse(strEndTime) };
    
                return this.getHibernateTemplate().findByNamedParam(hql, params, args);
            }
        } catch (Exception e)
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getUnrelatedEventPaging(List<Event> aet, int pageIndex, int pageSize, String strQuery,
            String strStartTime, String strEndTime)
    {
        try{
            final Date StartTime = dateFormater.parse(strStartTime);
            final Date EndTime = dateFormater.parse(strEndTime);
            String judge = "";
            for(Event et:aet){
                judge += " id != "+et.getId()+" and";
            }
            final String hql = "from Event where "+judge+"(name like :strQuery or remark like :strQuery)"
                   + " and beginDate>=:strStartTime and beginDate <=:strEndTime order by beginDate desc";
            
            List<Event> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStartTime", StartTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e){
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int getUnrelatedEventsTotal(List<Event> aet, String strQuery, String strStartTime, String strEndTime)
    {
        try{
            final Date StartTime = dateFormater.parse(strStartTime);
            final Date EndTime = dateFormater.parse(strEndTime);
            String judge = "";
            for(Event et:aet){
                judge += " id != "+et.getId()+" and";
            }
            final String hql = "from Event where "+judge+"(name like :strQuery or remark like :strQuery)"
                    + " and beginDate>=:strStartTime and beginDate <=:strEndTime";
            
            List<Event> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStartTime", StartTime);
                    query.setParameter("strEndTime", EndTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable.size();
        }catch(Exception e){
            return 0;
        }
    }
}
