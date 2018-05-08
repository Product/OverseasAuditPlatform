package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.Constant;
import com.tonik.dao.IExtractionDAO;
import com.tonik.model.Extraction;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IExtractionDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="ExtractionDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ExtractionHibernate extends BaseDaoHibernate implements IExtractionDAO
{
    @SuppressWarnings("unchecked")
    public List<Extraction> getExtraction()
    {
        return getHibernateTemplate().find("from Extraction t left join fetch t.product left join fetch t.rules");
    }
    
    public void SaveExtraction(Extraction extraction){
        getHibernateTemplate().saveOrUpdate(extraction);
    }
    
    public void deleteByRulesId(Long rulesId)
    {
        List<Extraction> extractions=getExtractionListByRulesId(rulesId);
        for(Extraction item:extractions)
        {
            getHibernateTemplate().delete(item);
        }
    }
    
    public int getExtractionTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            String hql = "from Extraction t left join t.product left join t.rules where t.product.name like :strQuery and t.extractionTime>="
                    + " :strStraTime and t.extractionTime<=:strEndTime order by t.extractionTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strStraTime),
                    new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Extraction> getExtractionPaging(final int pageIndex, final int pageSize, final String strQuery, final String strStraTime,final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strStraTime);
            final Date EndTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime);

            final String hql = "from Extraction t left join t.product left join t.rules where t.rules.name like :strQuery "
                    + "and t.extractionTime>=:strStraTime and t.extractionTime<=:strEndTime order by t.extractionTime desc";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            List<Extraction> extractions = new ArrayList<Extraction>();
            for(Object[] item:listTable)
            {
                Extraction et=(Extraction)item[0];
                extractions.add(et);
            }
            return extractions;
        } catch (Exception e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Extraction> getExtractionSpecial(final String strQuery, final String strStraTime,final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strStraTime);
            final Date EndTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime);

            final String hql = "from Extraction t left join t.product left join t.rules where t.rules.name like :strQuery "
                    + "and t.extractionTime>=:strStraTime and t.extractionTime<=:strEndTime order by t.extractionTime desc";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    List list = query.list();
                    return list;
                }
            });
            List<Extraction> extractions = new ArrayList<Extraction>();
            for(Object[] item:listTable)
            {
                Extraction et=(Extraction)item[0];
                extractions.add(et);
            }
            return extractions;
        } catch (Exception e)
        {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    public List<Extraction> getExtractionListByRulesId(Long rulesId){
        try
        {
           final String hql = "from Extraction t left join t.product left join t.rules where t.rules.id = "+rulesId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            List<Extraction> extraction = new ArrayList<Extraction>();
            for(Object[] item:listTable)
            {
                Extraction ec=(Extraction)item[0];
                extraction.add(ec);
            }
            return extraction;
        } catch (Exception e)
        {
            return null;
        }
    }
}
