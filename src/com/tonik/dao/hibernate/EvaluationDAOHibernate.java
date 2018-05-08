package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEvaluationDAO;
import com.tonik.model.Evaluation;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="EvaluationDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EvaluationDAOHibernate extends BaseDaoHibernate implements IEvaluationDAO
{
    @Override
    public List<Evaluation> getEvaluation()
    {
        return getHibernateTemplate().find("from Evaluation");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Evaluation getEvaluation(Long evaluationId)
    {
        try{
            final String hql = "from Evaluation e left join e.website left join e.createPerson where e.id="+evaluationId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Evaluation)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveEvaluation(Evaluation evaluation)
    {
        getHibernateTemplate().saveOrUpdate(evaluation);
    }

    @Override
    public void removeEvaluation(Evaluation evaluation)
    {
        getHibernateTemplate().delete(evaluation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getEvaluationPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        try{
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from Evaluation e left join e.website left join e.createPerson where "
                    +"(e.website.name like :strQuery or e.website.location like :strQuery or e.remark like :strQuery) "
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
    public int getEvaluationTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from Evaluation e left join e.website left join e.createPerson where "
                    +"(e.website.name like :strQuery or e.website.location like :strQuery or e.remark like :strQuery) "
                    + "and e.createTime>=:strStraTime and e.createTime<=:strEndTime order by e.createTime desc";


            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime)};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
            
        } catch (Exception e)
        {
            return 0;
        }
    }
}
