package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IRegionDAO;
import com.tonik.model.Region;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IRegionDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="RegionDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RegionHibernate extends BaseDaoHibernate  implements IRegionDAO
{
    @SuppressWarnings("unchecked")
    public List<Region> getRegion()
    {
        return getHibernateTemplate().find("from Region fetch all properties");
    }
    
    public Region getRegion(final Long id)
    {
        return (Region) getHibernateTemplate().find(
                "from Region fetch all properties where id=?",
                new Object[] { id}).get(0);
    }
    
    public void savaRegion(Region region)
    {
        getHibernateTemplate().saveOrUpdate(region);
    }

    @Override
    public int getRegionTotal(final String strQuery)
    {
        try
        {
            final String hql = "from Region where name like :strQuery or regionCode like :strQuery";

            final String[] params = { "strQuery","strQuery"};

            final Object[] args = { "%" + strQuery + "%","%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Region> getRegionPaging(final int pageIndex, final int pageSize, final String strQuery)
    {
        try
        {
            final String hql = "from Region t left join fetch t.countries where t.name like :strQuery or t.regionCode like :strQuery";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
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
