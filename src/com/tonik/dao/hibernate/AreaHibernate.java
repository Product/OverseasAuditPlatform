package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IAreaDAO;
import com.tonik.model.Area;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IAreaDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="AreaDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class AreaHibernate extends BaseDaoHibernate implements IAreaDAO
{
    @SuppressWarnings("unchecked")
    public List<Area> getAreas()
    {
        return getHibernateTemplate().find("from Area");
    }
    
    public Area getAreaById(Long id)
    {
        return (Area)getHibernateTemplate().find(
                "from Area t left join fetch t.country where t.id=?",
                new Object[] { id}).get(0);
    }
    
    public void saveArea(Area area)
    {
        getHibernateTemplate().saveOrUpdate(area);
    }
    
    public void removeArea(Long id)
    {
        getHibernateTemplate().delete(getAreaById(id));
    }
    
    
    public int getAreasTotal(final String strQuery,final Long countryId)
    {
        try
        {
            String hql = "from Area t left join t.country where (t.name like :strQuery or t.areaCode like :strQuery) and t.country.id="+countryId;

            String[] params = { "strQuery","strQuery"};

            Object[] args = { "%" + strQuery + "%","%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    public List<Area> getAreasPaging(final int pageIndex,final int pageSize,final String strQuery,final Long countryId)
    {
        try
        {
            final String hql = "from Area t left join fetch t.country where (t.name like :strQuery or t.areaCode like :strQuery) and t.country.id="+countryId;
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
    @Override
    public Area getAreaByCountryId(Long id)
    {
        return (Area)getHibernateTemplate().find( "from Area t left join fetch t.country where t.country.id=?", new Object[] { id}).get(0);
    }
}
