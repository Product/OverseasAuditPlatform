package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.ICollectorCfgDAO;
import com.tonik.model.CollectorCfg;
/**
 * Desc : 采集器持久层配置
 * @author fuzhi
 * @spring.bean id = "CollectorCfgDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class CollectorCfgDAOHibernate extends HibernateDaoSupport implements ICollectorCfgDAO
{
    //国内第三方采集配置
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectorCfg> getListInternal(int pageIndex, int pageSize)
    {
        String hql = "from CollectorCfg where type = 1";
        List<CollectorCfg> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }
    @Override
    public int getCountInternal()
    {
        String hql = "select count(*) from CollectorCfg where type = 1";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }

    //国外第三方采集配置
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectorCfg> getListAbroad(int pageIndex, int pageSize)
    {
        String hql = "from CollectorCfg where type = 2";
        List<CollectorCfg> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }
    @Override
    public int getCountAbroad()
    {
        String hql = "select count(*) from CollectorCfg where type = 2";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }
    
    //舆情采集配置
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectorCfg> getListPublicOpinion(int pageIndex, int pageSize)
    {
        String hql = "from CollectorCfg where type = 3";
        List<CollectorCfg> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }

    @Override
    public int getCountWebsite()
    {
        String hql = "select count(*) from CollectorCfg where type = 4";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }

    //网站采集配置
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectorCfg> getListWebsite(int pageIndex, int pageSize)
    {
        String hql = "from CollectorCfg where type = 4";
        List<CollectorCfg> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }
    
    @Override
    public int getCountExpertRepository()
    {
        String hql = "select count(*) from CollectorCfg where type = 5";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }
    //专家知识采集配置
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectorCfg> getListExpertRepository(int pageIndex, int pageSize)
    {
        String hql = "from CollectorCfg where type = 5";
        List<CollectorCfg> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }
    
    @Override
    public int getCountPublicOpinion()
    {
        String hql = "select count(*) from CollectorCfg where type = 3";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }

    @Override
    public void addOrUpdate(CollectorCfg collectorCfg)
    {
        getHibernateTemplate().saveOrUpdate(collectorCfg);
    }
    
    @Override
    public CollectorCfg queryById(int id)
    {
        String hql = "from CollectorCfg where id=" + id;
        CollectorCfg collectorCfg = (CollectorCfg)getHibernateTemplate().find(hql).get(0);
        return collectorCfg;
    }
    @Override
    public void delById(CollectorCfg collectorCfg)
    {
        getHibernateTemplate().delete(collectorCfg);
    }
}
