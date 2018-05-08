package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IVentureAnalysisStyleDAO;
import com.tonik.model.VentureAnalysisStyle;

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
 * @spring.bean id="VentureAnalysisStyleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class VentureAnalysisStyleDAOHibernate extends BaseDaoHibernate implements IVentureAnalysisStyleDAO
{
    @Override
    public List<VentureAnalysisStyle> getVentureAnalysisStyle()
    {
        return getHibernateTemplate().find("from VentureAnalysisStyle");
    }

    @Override
    public VentureAnalysisStyle getVentureAnalysisStyle(Long ventureAnalysisStyleId)
    {
        return (VentureAnalysisStyle) getHibernateTemplate().get(VentureAnalysisStyle.class, ventureAnalysisStyleId);
    }

    @Override
    public void saveVentureAnalysisStyle(VentureAnalysisStyle ventureAnalysisStyle)
    {
        getHibernateTemplate().saveOrUpdate(ventureAnalysisStyle);
    }

    @Override
    public void removeVentureAnalysisStyle(VentureAnalysisStyle ventureAnalysisStyle)
    {
        getHibernateTemplate().delete(ventureAnalysisStyle);
    }

    public void removeVentureAnalysisStyle(Long ventureAnalysisStyleId)
    {
        getHibernateTemplate().delete(getVentureAnalysisStyle(ventureAnalysisStyleId));
    }
    @Override
    public List<VentureAnalysisStyle> getVentureAnalysisStylePaging(final int pageIndex, final int pageSize,
            final String strQuery, final String strStraTime, final String strEndTime)
    {
      try{  
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
          
            final String hql = "from VentureAnalysisStyle where name like :strQuery and remark like :strQuery and createTime>="
                    + ":strStraTime and createTime<=:strEndTime order by createTime desc";
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
    public int getVentureAnalysisStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {

        try{
            String hql = "from VentureAnalysisStyle where name like :strQuery and remark like :strQuery and createTime>="
                    + ":strStraTime and createTime<=:strEndTime order by createTime desc";        
            
            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        }catch(Exception e)
        {return 0;}
    }

}