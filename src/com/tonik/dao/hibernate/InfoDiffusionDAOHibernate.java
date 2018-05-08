package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.IInfoDiffusionDAO;
import com.tonik.model.InfoDiffusion;
/**
 * <p>
 *  desc: this is search info about InfoDiffusion
 * </p>
 * @author fuzhi
 * @spring.bean id = "InfoDiffusionDAOHibernate"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class InfoDiffusionDAOHibernate extends HibernateDaoSupport implements IInfoDiffusionDAO
{
    @SuppressWarnings("unchecked")
    @Override
    public List<InfoDiffusion> getInfoDiffusion(int pageIndex, int pageSize)
    {
        String hql = "from InfoDiffusion";
        return getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                // TODO Auto-generated method stub
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1) * pageSize);
                query.setMaxResults(pageSize);
                List<InfoDiffusion> list = query.list();
                return list;
            }
        });
    }
    @Override
    public int getInfoDiffusionTotal()
    {
        String hql = "select count(*) from InfoDiffusion";
        Long total = (Long)getHibernateTemplate().find(hql).listIterator().next();
        return total.intValue();
    }
    public InfoDiffusion getInfoDiffusionById(int id)
    {
        // TODO Auto-generated method stub
        String hql = "from InfoDiffusion where id=" + id;
        InfoDiffusion infoDiffusion = (InfoDiffusion)getHibernateTemplate().find(hql).get(0);
        return infoDiffusion;
    }
    @SuppressWarnings("unchecked")
    public List<InfoDiffusion> getInfoDiffusionByKey(String key, int pageIndex, int pageSize)
    {
        // TODO Auto-generated method stub
        String hql = "from InfoDiffusion where title like '%" + key + "%' order by date desc";
        List<InfoDiffusion> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                // TODO Auto-generated method stub
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex-1) * pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }
    public int getInfoDiffusionTotalByKey(String key)
    {
        String hql = "select count(*) from InfoDiffusion where title like '%" + key + "%'";
        Long total = (Long)getHibernateTemplate().find(hql).listIterator().next();
        return total.intValue();
    }
    @Override
    public void saveInfoDiffusion(InfoDiffusion infoDiffusion)
    {
        getHibernateTemplate().saveOrUpdate(infoDiffusion);
    }
}
