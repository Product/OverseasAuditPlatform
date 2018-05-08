package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IReportDAO;
import com.tonik.model.Report;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IReportDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author yekai
 * @spring.bean id="ReportDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ReportDAOHibernate extends BaseDaoHibernate implements IReportDAO
{
    @Override
    public List<Report> getReport()
    {
        return getHibernateTemplate().find("from Report");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Report getReport(Long reportId)
    {
        try{
            final String hql ="from Report r left join r.reportTemplate where r.id="+reportId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Report)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveReport(Report report)
    {
        getHibernateTemplate().saveOrUpdate(report);
    }

    @Override
    public void removeReport(Report report)
    {
        getHibernateTemplate().delete(report);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getReportPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try{
        final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
        final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
        
        final String hql = "from Report r left join r.reportTemplate where "
                +"(r.reportTemplate.name like :strQuery or r.name like :strQuery)"
                + "and r.createTime>=:strStraTime and r.createTime<=:strEndTime order by r.createTime desc";
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
        }catch (Exception e)
        {
            return null;
        }
        
    }

    @Override
    public int getReportTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from Report where name like :strQuery and createTime>="
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
