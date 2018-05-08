package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.ICountryDAO;
import com.tonik.model.Country;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of ICountryDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="CountryDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class CountryHibernate extends BaseDaoHibernate implements ICountryDAO
{
    @SuppressWarnings("unchecked")
    public List<Country> getCountry()
    {
        return getHibernateTemplate().find("from Country fetch all properties");
    }
    
    public Country getCountryById(Long countryId)
    {
        return (Country) getHibernateTemplate().find(
                "from Country fetch all properties where id=?",
                new Object[] { countryId}).get(0);
    }
    public void removeCountry(Long id)
    {
        getHibernateTemplate().delete(getCountryById(id));
    }
    
    public void saveCountry(Country country)
    {
        getHibernateTemplate().saveOrUpdate(country);
    }
    
    public int getCountryTotal(final String strQuery,final String strRegionId)
    {
        try
        {
            String hql="";
            if(strRegionId==null)
                hql = "from Country where name like :strQuery or countryCode like :strQuery";
            else
                hql ="from Country t left join fetch t.regions where (t.name like :strQuery or t.countryCode like :strQuery) and t.regions.id="+Long.parseLong(strRegionId);

            String[] params = { "strQuery"};

            Object[] args = { "%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    public List<Country> getCountryPaging(final int pageIndex,final int pageSize,final String strQuery,final String strRegionId)
    {
        try
        {
            String hq="";
            if(strRegionId==null)
                hq = "from Country t left join fetch t.regions where t.name like :strQuery or t.countryCode like :strQuery";
            else
                hq ="from Country t left join fetch t.regions where (t.name like :strQuery or t.countryCode like :strQuery) and t.regions.id="+Long.parseLong(strRegionId);
            final String hql=hq;
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
    public Country getCountryByName(String country)
    {
        try
        {
            String hql="";
            hql ="from Country t where (t.name = :strQuery)";

            String[] params = { "strQuery"};

            Object[] args = { country };

            return (Country)this.getHibernateTemplate().findByNamedParam(hql, params, args).get(0);
        } catch (Exception e)
        {
            return  null;
        }
    }
    
}
