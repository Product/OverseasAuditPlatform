package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.Constant;
import com.tonik.dao.IConsultiveDAO;
import com.tonik.model.Consultive;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IConsultiveDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="ConsultiveDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ConsultiveDAOHibernate extends BaseDaoHibernate implements IConsultiveDAO
{
    @Override
    public List<Consultive> getConsultive()
    {
        return getHibernateTemplate().find("from Consultive");
    }

    //通过ID获取咨询建议
    @Override
    public Consultive getConsultive(Long consultiveId)
    {
        return (Consultive) getHibernateTemplate().get(Consultive.class, consultiveId);
    }

    //保存咨询建议
    @Override
    public void saveConsultive(Consultive consultive)
    {
        getHibernateTemplate().saveOrUpdate(consultive);
    }

    //删除咨询建议
    @Override
    public void removeConsultive(Consultive consultive)
    {
        getHibernateTemplate().delete(consultive);
    }

    //获取某页的所有咨询建议
    @Override
    public List<Consultive> getConsultivePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        // return getHibernateTemplate().find("from Consultive");
        try
        {
            final Date StraTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strStraTime);
            final Date EndTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime);

            final String hql = "from Consultive where (title like :strQuery or content like :strQuery) and CONSULTIVE_PARENTNOD=0 and (CONSULTIVE_TYPE=1 or CONSULTIVE_TYPE=2) "
                    + "and CONSULTIVE_consultiveTime>=:strStraTime and CONSULTIVE_CONSULTIVETIME<=:strEndTime order by CONSULTIVE_consultiveTime desc";
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
    public int getConsultiveTotal(final String strQuery, final String strStraTime, final String strEndTime) //获取咨询建议总数
    {
        try
        {
            String hql = "from Consultive where (title like :strQuery or content like :strQuery) and CONSULTIVE_PARENTNOD=0 and (CONSULTIVE_TYPE=1 or CONSULTIVE_TYPE=2) and CONSULTIVE_consultiveTime>="
                    + " :strStraTime and CONSULTIVE_consultiveTime<=:strEndTime order by CONSULTIVE_CONSULTIVETIME desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strStraTime),
                    new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    public int MyConsultiveTotal(final Long consultiveId) //获取我的咨询建议总数
    {
        try
        {
            final String hql = "from Consultive where CONSULTIVE_PARENTNOD=0 and CONSULTIVE_CONSULTANT=" + consultiveId;
            List total = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return total.size();

        } catch (Exception e)
        {
            return 0;
        }
    }

    public List<Consultive> getMyConsultivePaging(final int pageIndex, final int pageSize, final Long consultiveId)//获取某页我的所有咨询建议
    {
        final String hql = "from Consultive where CONSULTIVE_PARENTNOD=0 and CONSULTIVE_CONSULTANT=" + consultiveId;
        List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex - 1) * pageSize);
                query.setMaxResults(pageSize);
                // query.setParameter("consultiveId", consultiveId);
                List list = query.list();
                return list;
            }
        });
        return listTable;
    }

    //获取某页所有投诉
    public List<Consultive> getComplainConsultivePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strStraTime);
            final Date EndTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime);

            final String hql = "from Consultive where (title like :strQuery or content like :strQuery) and CONSULTIVE_TYPE=3 "
                    + "and CONSULTIVE_consultiveTime>=:strStraTime and CONSULTIVE_CONSULTIVETIME<=:strEndTime order by CONSULTIVE_consultiveTime desc";
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

    public int getComplainConsultiveTotal(String strQuery, String strStraTime, String strEndTime)//获取投诉总数
    {
        try
        {
            String hql = "from Consultive where (title like :strQuery or content like :strQuery) and CONSULTIVE_TYPE=3 and CONSULTIVE_consultiveTime>="
                    + " :strStraTime and CONSULTIVE_consultiveTime<=:strEndTime order by CONSULTIVE_CONSULTIVETIME desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strStraTime),
                    new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    public List<Consultive> getDetailAnswer(final Long consultiveId)//通过ID获取专家回答
    {
        try
        {
            final String hql = "from Consultive where CONSULTIVE_ROOTNOD=" + consultiveId;
            // final String hql = "from Consultive where RootNod=:consultiveId";

            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    // query.setParameter("consultiveId", consultiveId);
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
