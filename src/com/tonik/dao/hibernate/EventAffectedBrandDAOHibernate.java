package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedBrandDAO;
import com.tonik.model.EventAffectedBrand;
/**
 * <p>
 * Title: Tonik Candidates
 * </p>
 * <p>
 * Description: 事件影响品牌 DAO层接口的实现
 * </p>
 * @since Oct 30, 2015
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedBrandDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedBrandDAOHibernate extends BaseDaoHibernate implements IEventAffectedBrandDAO 
{

    public List<EventAffectedBrand> getEventAffectedBrands(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedBrand where eventId ="+eventId);
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrands(java.util.List)
     * @Override
     * @desc: 通过事件id获取所有受影响的品牌
     * @param eventId, eventId of EventAfectedBrand
     * @return List<EventAffectedBrand>
     */
    @SuppressWarnings("unchecked")
    public List<EventAffectedBrand> getEventAffectedBrands(Long[] eventId)
    {
        try{
            final String hql ="from EventAffectedBrand where eventId in (:eventId)";
            
            List<EventAffectedBrand> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameterList("eventId",eventId);
                    List list=query.list();
                    
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
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrandById(java.lang.Long)
     * @Override 
     * @desc:通过EventAffectedBrand的id获取事件影响品牌对象
     * @param id, id of EventAffectedBrand
     * @return EventAffectedBrand
     */
    public EventAffectedBrand getEventAffectedBrandById(Long id)
    {
        return (EventAffectedBrand) this.getHibernateTemplate().get(EventAffectedBrand.class,id);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrandById(java.lang.Long)
     * @Override
     * 
     * @desc: 保存事件影响的品牌
     * @param EventAffectedBrand
     * @return void
     */
    public void saveEventAffectedBrand(EventAffectedBrand eventAffectedBrand)
    {
        //检查数据是否重复
        Long eventId = eventAffectedBrand.getEventId();
        Long brandId = eventAffectedBrand.getBrandId();
        
        if(getHibernateTemplate().find("from EventAffectedBrand where eventId="+eventId+" and brandId="+brandId).size()==0){
            getHibernateTemplate().saveOrUpdate(eventAffectedBrand);
        }else{
            throw new RuntimeException("Same Brand!");
        }    
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedBrandDAO#removeEventAffectedBrand(com.tonik.model.EventAffectedBrand)
     * @Override
     * @desc: 删除事件影响的品牌
     * @param EventAffectedBrand
     * @return void
     */
    public void removeEventAffectedBrand(EventAffectedBrand eventAffectedBrand)
    {
        getHibernateTemplate().delete(eventAffectedBrand);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrandPaging(int, int, java.lang.String, java.lang.Long)
     * @Override
     * @desc: 获取某分页品牌列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId ,eventId of EventAffectedBrand 
     * @return List<EventAffectedBrand>
     */
    public List<EventAffectedBrand> getEventAffectedBrandPaging(int pageIndex, int pageSize,String strQuery,Long eventId)
    {
        try{
            final String hql = "from EventAffectedBrand where eventId=:eventId and brandName like :strQuery order by id desc";
                     
            List<EventAffectedBrand> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                    {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("eventId", eventId);
                    query.setParameter("strQuery", "%"+strQuery+"%");
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
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrandsTotal(java.lang.Long)
     * @Override
     * @desc:获取某个事件影响的品牌总数
     * @param eventId the eventId of EventAffectedBrands
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long eventId)
    {
        return getEventAffectedBrands(eventId).size();
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedBrandDAO#getEventAffectedBrandsTotal(java.lang.Long)
     * @Override
     * @desc: 通过事件id数组影响的品牌总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long[] eventIds)
    {
        return getEventAffectedBrands(eventIds).size();
    }
    
    @SuppressWarnings("unchecked")
    public List<EventAffectedBrand> getEventAffectedBrandsByEvent(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedBrand where eventId="+eventId);
    }
}