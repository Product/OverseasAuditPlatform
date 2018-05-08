package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.IThirdReportDAO;
import com.tonik.model.ThirdReport;
/**
 * Desc: ThirdReport持久层
 * @author fuzhi
 * @spring.bean id="ThirdReportDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ThirdReportDAOHibernate extends HibernateDaoSupport implements IThirdReportDAO
{
    @SuppressWarnings("unchecked")
    @Override
    public List<ThirdReport> getList(int pageIndex, int pageSize)
    {
        String hql = "from ThirdReport tr where ThirdReport_AddressType=0 order by tr.createTime desc";
        List<ThirdReport> list = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getCount()
    {
        String hql = "select count(*) from ThirdReport where ThirdReport_AddressType=0";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ThirdReport> getListAbroad(int pageIndex, int pageSize)
    {
        String hql = "from ThirdReport where ThirdReport_AddressType=1";
        List<ThirdReport> list = getHibernateTemplate().executeFind(new HibernateCallback()
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
        String hql = "select count(*) from ThirdReport where ThirdReport_AddressType=1";
        Long count = (Long)getHibernateTemplate().find(hql).iterator().next();
        return count.intValue();
    }
    @Override
    public void save(ThirdReport thirdReport)
    {
        getHibernateTemplate().saveOrUpdate(thirdReport);
    }
    @Override
    public ThirdReport queryById(Long id)
    {
        String hql = "from ThirdReport where id = " + id;
        ThirdReport thirdReport = (ThirdReport)getHibernateTemplate().find(hql).get(0);
        return thirdReport;        
    }
}
