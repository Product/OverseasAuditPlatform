package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IRulesDAO;
import com.tonik.model.Rules;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IRulesDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author Ly
 * @spring.bean id="RulesDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RulesDAOHibernate extends BaseDaoHibernate implements IRulesDAO
{
    @Override
    public List<Rules> getRules()
    {
        return getHibernateTemplate().find("from Rules");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Rules getRules(Long rulesId)
    {
        try{
            final String hql = "from Rules r left join r.websiteStyles left join r.productStyles left join r.productTypes "
                    + "left join r.productPropertyTypes left join r.countries left join r.areas left join r.materials "
                    + "left join r.createPerson where r.id="+rulesId;
//                    "SELECT r FROM Rules r join r.websiteStyles ws join r.productStyles ps "
//                    + "where ws.id= :websiteStylesId and ps.id= :productStylesId ";
                
//                "from Rules where name like :strQuery and content like :strQuery "
//                + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Rules)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveRules(Rules rules)
    {
        getHibernateTemplate().saveOrUpdate(rules);
    }

    @Override
    public void removeRules(Rules rules)
    {
        getHibernateTemplate().delete(rules);
    }

    @Override
    public void removeRules(Long rulesId)
    {
        getHibernateTemplate().delete(getRules(rulesId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Rules> getRulesPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "FROM Rules "
                    + "where (name like :strQuery or content like :strQuery or remark like :strQuery) "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List<Rules> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getRulesTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {

            final String hql =  "from Rules where name like :strQuery and content like :strQuery and remark like :strQuery "
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
