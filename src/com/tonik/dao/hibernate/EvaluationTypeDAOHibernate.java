package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEvaluationTypeDAO;
import com.tonik.model.EvaluationType;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IVentureAnalysisStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="EvaluationTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EvaluationTypeDAOHibernate extends BaseDaoHibernate implements IEvaluationTypeDAO
{
    @Override
    public List<EvaluationType> getEvaluationType()
    {
        return getHibernateTemplate().find("from EvaluationType");
    }

    @SuppressWarnings("unchecked")
    @Override
    public EvaluationType getEvaluationType(Long evaluationTypeId)
    {
        try{
            final String hql = "from EvaluationType e left join e.createPerson where e.id="+evaluationTypeId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (EvaluationType)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
//        return (EvaluationType) getHibernateTemplate().get(EvaluationType.class, evaluationTypeId);
    }

    @Override
    public void saveEvaluationType(EvaluationType evaluationType)
    {
        getHibernateTemplate().saveOrUpdate(evaluationType);
    }

    @Override
    public void removeEvaluationType(EvaluationType evaluationType)
    {
        getHibernateTemplate().delete(evaluationType);
    }

    public void removeEvaluationType(Long evaluationTypeId)
    {
        getHibernateTemplate().delete(getEvaluationType(evaluationTypeId));
    }
    @Override
    public List<EvaluationType> getEvaluationTypePaging(final int pageIndex, final int pageSize,
            final String strQuery, final String strStraTime, final String strEndTime)
    {
      try{  
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
          
            final String hql = "from EvaluationType e left join fetch e.createPerson where e.name like :strQuery and e.remark like :strQuery and e.createTime>="
                    + ":strStraTime and e.createTime<=:strEndTime order by e.createTime desc";
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
      }
      catch(Exception e)
      {
          return null;
      }
    }

    @Override
    public int getEvaluationTypeTotal(String strQuery, String strStraTime, String strEndTime)
    {

        try{
            String hql = "from EvaluationType where name like :strQuery and remark like :strQuery and createTime>="
                    + ":strStraTime and createTime<=:strEndTime order by createTime desc";        
            
            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        }catch(Exception e)
        {
            return 0;
        }
    }

}