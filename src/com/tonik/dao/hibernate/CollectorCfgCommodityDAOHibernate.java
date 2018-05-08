package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.ICollectorCfgCommodityDAO;
import com.tonik.model.CollectorCfgCommodity;

/**
 * 
 * @author fuzhi
 * @spring.bean id="CollectorCfgCommodityDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class CollectorCfgCommodityDAOHibernate extends HibernateDaoSupport implements ICollectorCfgCommodityDAO
{

    @Override
    public int findCount()
    {
        String hql="select count(*) from CollectorCfgCommodity";
        Long size = (Long)getHibernateTemplate().find(hql).listIterator().next();
        return size.intValue();
    }

    @Override
    public CollectorCfgCommodity findById(Long id)
    {
        String hql="from CollectorCfgCommodity where id = " + id;
        CollectorCfgCommodity collectorCfgCommodity = (CollectorCfgCommodity)getHibernateTemplate().find(hql).get(0);
        return collectorCfgCommodity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findList(int pageIndex, int pageSize)
    {
        String hql="from CollectorCfgCommodity c left join c.website left join c.productStyle left join c.firstlevelType left join c.secondlevelType left join c.thirdlevelType";
        List<Object[]> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1)*pageSize);
                query.setMaxResults(pageSize);
                List<Object> queryList = query.list();
                return queryList;
            }
        });
        return list;
    }

    @Override
    public void addOrUpdate(CollectorCfgCommodity collectorCfgCommodity)
    {
        getHibernateTemplate().saveOrUpdate(collectorCfgCommodity);
    }

    @Override
    public void delete(CollectorCfgCommodity collectorCfgCommodity)
    {
        getHibernateTemplate().delete(collectorCfgCommodity);
    }
}
