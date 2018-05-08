package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IReportTemplateDAO;
import com.tonik.model.ReportTemplate;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IReportTemplateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="ReportTemplateDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ReportTemplateDAOHibernate extends BaseDaoHibernate implements IReportTemplateDAO
{

    @Override
    public List<ReportTemplate> getReportTemplate()
    {
        return getHibernateTemplate().find("from ReportTemplate");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReportTemplate getReportTemplate(Long reportTemplateId)
    {
        return (ReportTemplate) getHibernateTemplate().get(ReportTemplate.class, reportTemplateId);
    }

    @Override
    public void saveReportTemplate(ReportTemplate reportTemplate)
    {
        getHibernateTemplate().saveOrUpdate(reportTemplate);
    }

    @Override
    public void removeReportTemplate(ReportTemplate reportTemplate)
    {
        getHibernateTemplate().delete(reportTemplate);
    }

    @Override
    public List<ReportTemplate> getReportTemplatePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        // return getHibernateTemplate().find("from Report");
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
        
            final String hql = "from ReportTemplate rt left join fetch rt.createPerson where (rt.name like :strQuery or rt.templateContent like :strQuery) "
                + "and rt.createTime>=:strStraTime and rt.createTime<=:strEndTime order by rt.createTime desc";
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
    public int getReportTemplateTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from ReportTemplate where name like :strQuery or templateContent like :strQuery and createTime>="
                    + " :strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
//        return getHibernateTemplate().find("from ReportTemplate where name like" + strQuery + " and templateStyle like"
//                + strQuery + " and templateContent=" + strQuery
//                + " and createTime>=" + strStraTime + " and createTime<=" + strEndTime + " order by createTime desc")
//                .size();
    }

}
