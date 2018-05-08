package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IRulesDetailDAO;
import com.tonik.model.RulesDetail;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IRulesDetailDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="RulesDetailDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RulesDetailDAOHibernate extends BaseDaoHibernate implements IRulesDetailDAO
{
    @Override
    public List<RulesDetail> getRulesDetail()
    {
        return getHibernateTemplate().find("from RulesDetail");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RulesDetail> GetRulesDetailById(Long rulesId)
    {
        try
        {
            final String hql = "from RulesDetail where RULESDETAIL_RULESID=" + rulesId;

            List<RulesDetail> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
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

    public RulesDetail getRulesDetail(Long rulesDetailId)
    {
        return (RulesDetail) getHibernateTemplate().get(RulesDetail.class, rulesDetailId);
    }
    
    @Override
    public void saveRulesDetail(RulesDetail rules)
    {
        getHibernateTemplate().saveOrUpdate(rules);
    }

    @Override
    public void removeRulesDetail(RulesDetail rules)
    {
        getHibernateTemplate().delete(rules);
    }

    @Override
    public void removeRulesDetail(Long rulesId)
    {
        getHibernateTemplate().delete(getRulesDetail(rulesId));
    }

    @Override
    public List<RulesDetail> getRulesDetailPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from RulesDetail where content like :strQuery "
                    + "and condition like :strQuery and relationship like :strQuery and value like :strQuery "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
            return listTable;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int getRulesDetailTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from RulesDetail where content like :strQuery"
                    + "and condition like :strQuery and relationship like :strQuery and value like :strQuery "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

}
