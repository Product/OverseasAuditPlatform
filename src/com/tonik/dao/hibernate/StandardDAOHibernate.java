package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IStandardDAO;
import com.tonik.model.Standard;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 对规则集Standard类的增删改查方法
 * </p>
 * @since July 01, 2016
 * @version 1.0
 * @author Ye
 * @spring.bean id="StandardDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class StandardDAOHibernate extends BaseDaoHibernate implements IStandardDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<Standard> getStandards()
    {
        return getHibernateTemplate().find("from Standard");
    }

    @Override
    public void saveStandard(Standard standard)
    {
        getHibernateTemplate().saveOrUpdate(standard);
    }

    @Override
    public void removeStandard(Long id)
    {
        getHibernateTemplate().delete(getStandardById(id));      
    }

    @Override
    public Standard getStandardById(Long id)
    {
        return (Standard)getHibernateTemplate().find("from Standard s where s.id=?", new Object[] {id}).get(0);
    }

    @Override
    public int getStandardsTotal(String strQuery, Integer system)
    {
        final String hql = "select count(distinct s.id) from Standard s where (s.name like '%" + strQuery + "%' or s.agency like '%" 
    + strQuery + "%' or s.remark like '%" + strQuery + "%') and s.system = " + system;
        Long count = (Long)getHibernateTemplate().find(hql).listIterator().next();
        return count.intValue();
    }

    @Override
    public List<Standard> getStandardsPaging(int pageIndex, int pageSize, String strQuery, Integer system)
    {
        try
        {
            final String hql = "from Standard s where (s.name like :strQuery or s.agency like :strQuery or s.agency like :strQuery) and s.system = :system";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setInteger("system", system);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }
}
