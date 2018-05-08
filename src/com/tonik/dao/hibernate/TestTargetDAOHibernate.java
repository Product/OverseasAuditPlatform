package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.ITestTargetDAO;
import com.tonik.model.TestTarget;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of ITestTargetDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="TestTargetDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class TestTargetDAOHibernate extends BaseDaoHibernate implements ITestTargetDAO
{

    @Override
    public List<TestTarget> getTestTargets()
    {
        return getHibernateTemplate().find("from TestTarget");
    }

    @Override
    public TestTarget getTestTarget(Long testTargetId)
    {
        return (TestTarget) getHibernateTemplate().get(TestTarget.class, testTargetId);
    }

    @Override
    public void saveTestTarget(TestTarget testTarget)
    {
        getHibernateTemplate().saveOrUpdate(testTarget);
    }

    @Override
    public void removeTestTarget(TestTarget testTarget)
    {
        getHibernateTemplate().delete(testTarget);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TestTarget> getTestTargetPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from TestTarget where name like :strQuery "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List<TestTarget> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getTestTargetTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            final String hql = "from TestTarget where name like :strQuery and createTime>="
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
    public List<Object[]> getTestTargetByTest(final Long id)
    {
        try{
            final String hql = "from TestTarget t left join t.product left join t.website left join t.website.webStyle left join t.product.productType where t.test.id="+id +"order by t.type";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch (Exception e)
        {
            return null;
        }
    }
}
