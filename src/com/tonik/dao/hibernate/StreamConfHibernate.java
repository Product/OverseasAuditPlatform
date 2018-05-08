package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.IStreamConfDAO;
import com.tonik.model.StreamConf;

/**
 * @spring.bean id="StreamConfHibernate"
 * @author fuzhi
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class StreamConfHibernate extends HibernateDaoSupport implements IStreamConfDAO
{
    @Override
    public int Count(){
        String hql = "select count(*) from StreamConf";
        Long size = (Long)getHibernateTemplate().find(hql).listIterator().next();
        return size.intValue();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<StreamConf> queryList(int pageIndex, int pageSize)
    {
        String hql="from StreamConf";
        List<StreamConf> list = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public StreamConf queryById(Long id)
    {
        String hql="from StreamConf where id = " + id;
        StreamConf streamConf = (StreamConf)getHibernateTemplate().find(hql).get(0);
        return streamConf;
    }
    @Override
    public void AddOrUpdate(StreamConf streamConf){
        getHibernateTemplate().saveOrUpdate(streamConf);
    }
}
