package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.ITestStyleDAO;
import com.tonik.model.TestStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of ITestStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="TestStyleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class TestStyleDAOHibernate extends BaseDaoHibernate implements ITestStyleDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<TestStyle> getTestStyle()
    {
        List<TestStyle> res= getHibernateTemplate().find("from TestStyle");
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TestStyle getTestStyle(Long testStyleId)
    {
        try
        {
            final String hql = "from TestStyle t left join t.createPerson where t.id="+testStyleId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (TestStyle)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveTestStyle(TestStyle testStyle)
    {
        getHibernateTemplate().saveOrUpdate(testStyle);
        
    }

    @Override
    public void removeTestStyle(TestStyle testStyle)
    {
        getHibernateTemplate().delete(testStyle);     
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getTestStylePaging(final int pageIndex,final int pageSize,final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from TestStyle t left join t.createPerson where t.name like :strQuery "
                    + "and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";
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
    public int getTestStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from TestStyle where name like :strQuery and createTime>="
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
    
}
