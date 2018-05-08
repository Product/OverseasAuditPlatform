package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEpidemicDAO;
import com.tonik.model.Epidemic;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEpidemicDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author Ly
 * @spring.bean id="EpidemicDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EpidemicDAOHibernate extends BaseDaoHibernate implements IEpidemicDAO
{
    @Override
    public List<Object[]> getEpidemic()
    {
        return (List<Object[]>)getHibernateTemplate().find("select id,title from Epidemic");
    }
    
    @Override
    public Epidemic getEpidemic(Long EpidemicId)
    {        
        return (Epidemic)getHibernateTemplate().find(
                "from Epidemic t left join fetch t.productTypes left join fetch t.createPerson where t.id=?",
                new Object[] { EpidemicId}).get(0);
    }

    @Override
    public void saveEpidemic(Epidemic Epidemic)
    {
        getHibernateTemplate().saveOrUpdate(Epidemic);
    }

    @Override
    public void removeEpidemic(Epidemic Epidemic)
    {
        getHibernateTemplate().delete(Epidemic);
    }

    @Override
    public void removeEpidemic(Long EpidemicId)
    {
        getHibernateTemplate().delete(getEpidemic(EpidemicId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Epidemic> getEpidemicPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "FROM Epidemic "
                    + "where (title like :strQuery or content like :strQuery or remark like :strQuery) "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List<Epidemic> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getEpidemicTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {

            final String hql =  "from Epidemic where title like :strQuery and content like :strQuery and remark like :strQuery "
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
