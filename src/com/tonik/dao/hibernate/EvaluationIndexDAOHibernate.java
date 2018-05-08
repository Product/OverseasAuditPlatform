package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEvaluationIndexDAO;
import com.tonik.model.EvaluationIndex;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationIndexDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="EvaluationIndexDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EvaluationIndexDAOHibernate extends BaseDaoHibernate implements IEvaluationIndexDAO
{
    @Override
    public List<EvaluationIndex> getEvaluationIndex()
    {
        return getHibernateTemplate().find("from EvaluationIndex");
    }

    @SuppressWarnings("unchecked")
    @Override
    public EvaluationIndex getEvaluationIndex(Long evaluationIndexId)
    {
        try
        {
            final String hql = "from EvaluationIndex e left join e.evaluationStyle left join e.createPerson where e.id="+evaluationIndexId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (EvaluationIndex)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveEvaluationIndex(EvaluationIndex evaluationIndex)
    {
        getHibernateTemplate().saveOrUpdate(evaluationIndex);
    }

    @Override
    public void removeEvaluationIndex(EvaluationIndex evaluationIndex)
    {
        getHibernateTemplate().delete(evaluationIndex);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getEvaluationIndexPaging(final int pageIndex,final int pageSize,final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try
        {// return getHibernateTemplate().find("from RoleMenu");
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from EvaluationIndex e left join e.evaluationStyle left join e.createPerson where e.name like :strQuery "
                    + "and e.createTime>=:strStraTime and e.createTime<=:strEndTime order by e.createTime desc";
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
            return listTable;
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public int getEvaluationIndexTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from EvaluationIndex where name like :strQuery and createTime>="
                    + " :strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
            
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<EvaluationIndex> getEvaluationIndices(String strQuery)
    {
        try
        {
            final String hql = "from EvaluationIndex e where e.name like :strQuery ";
            List<EvaluationIndex> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e)
        {
            throw e;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<EvaluationIndex> getEvaluationIndexByStyle(Long evaluationStyleId)
    {
        try
        {
            final String hql = "select e from EvaluationIndex e left join e.evaluationStyle es where es.id = " + evaluationStyleId;
            List<EvaluationIndex> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch(Exception e) {
            throw e;
        }
    }

}