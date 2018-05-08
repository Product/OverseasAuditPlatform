package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.ITestDAO;
import com.tonik.model.Test;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of ITestDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="TestDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class TestDAOHibernate extends BaseDaoHibernate implements ITestDAO
{

    @Override
    public List<Test> getTest()
    {
        return getHibernateTemplate().find("from Test");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Test getTest(Long testId)
    {
        try{
            final String hql = "from Test t left join t.testtype left join t.createperson where t.id="+testId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Test)listTable.get(0)[0];
        }catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveTest(Test test)
    {
        getHibernateTemplate().saveOrUpdate(test);
    }

    @Override
    public void removeTest(Test test)
    {
        getHibernateTemplate().delete(test);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getTestPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Test t left join t.testtype left join t.createperson where t.name like :strQuery "
                    + "and t.starttime>=:strStraTime and t.endtime<=:strEndTime order by t.starttime desc";
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
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int getTestTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            final String hql = "from Test where name like :strQuery and starttime>="
                    + " :strStraTime and endtime<=:strEndTime order by starttime desc";

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
