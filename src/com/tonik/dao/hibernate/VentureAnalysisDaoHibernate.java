package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IVentureAnalysisDAO;
import com.tonik.model.VentureAnalysis;


/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IVentureAnalysisDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="VentureAnalysisDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class VentureAnalysisDaoHibernate extends BaseDaoHibernate implements IVentureAnalysisDAO
{
    @Override
    public List<VentureAnalysis> getVentureAnalysis()
    {
        return getHibernateTemplate().find("from VentureAnalysis");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public VentureAnalysis getVentureAnalysis(Long ventureAnalysisId)
    {
        try{
            final String hql = "from VentureAnalysis v left join v.style left join v.websiteStyle left join v.website left join"
                    + "v.createPerson where v.id="+ventureAnalysisId;
//                    "from VentureAnalysis where name like :strQuery and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc ";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (VentureAnalysis)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveVentureAnalysis(VentureAnalysis ventureAnalysis)
    {
        getHibernateTemplate().saveOrUpdate(ventureAnalysis);
    }

    @Override
    public void removeVentureAnalysis(VentureAnalysis ventureAnalysis)
    {
        getHibernateTemplate().delete(ventureAnalysis);
    }
    
    public void removeVentureAnalysis(Long ventureAnalysisId)
    {
        getHibernateTemplate().delete(getVentureAnalysis(ventureAnalysisId));
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> getVentureAnalysisPaging(final int pageIndex, final int pageSize,
            final String strQuery, final String strStraTime,final String strEndTime)
    {
        try{
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from VentureAnalysis v left join v.website left join v.style left join "
                    + "v.createPerson left join v.websiteStyle where "
                    +"(v.websiteStyle like :strQuery or v.website like :strQuery or v.style like :strQuery "
                    + "v.analysisFileName like :strQuery v.name like :strQuery  or v.illustration like :strQuery "
                    + "or v.template like :strQuery) "
                    + "and v.createTime>=:strStraTime and v.createTime<=:strEndTime order by v.createTime desc";
            
//          final String hql = "from VentureAnalysis where name=:strQuery and style=:strQuery and webStyle=:strQuery "
//                  + "and website=:strQuery and analysisFileName=:strQuery and analysisFile=:strQuery and releaseState=:strQuery "
//                  + "and illustration=:strQuery and template=:strQuery and createTime>=:strStraTime "
//                  + "and createTime<=:strEndTime order by createTime desc";

            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setParameter("strQuery", strQuery);
                query.setParameter("strStraTime", strStraTime);
                query.setParameter("strEndTime", strEndTime);
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
    public int getVentureAnalysisTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
//            Date st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime);
//            Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
//            final String hql = "select * from VentureAnalysis v, VentureAnalysisStyle s where v.websiteStyle=s.id, "
//                    + " Website w where v.website=w.id and v.webStyle=w.id and "
//                    +"(v.name like '"+strQuery+"' or v.analysisFileName like '"+strQuery+"' or v.releaseState like '"+strQuery+"' "
//                            + "or v.illustration like '"+strQuery+"'or v.template like '"+strQuery+"') "
//                    + "and createTime>='"+st+"' and createTime<='"+et+"' order by createTime desc";

            final String hql = "from VentureAnalysis where name=:strQuery and illustration=:strQuery "
//                    + "and websiteStyle=:strQuery and website=:strQuery and analysisFileName=:strQuery "
//                    + "and analysisFile=:strQuery and releaseState=:strQuery and style=:strQuery and template=:strQuery "
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
