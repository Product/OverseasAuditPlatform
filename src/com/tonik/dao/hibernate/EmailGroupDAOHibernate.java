package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEmailGroupDAO;
import com.tonik.model.EmailGroup;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEmailDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="EmailGroupDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EmailGroupDAOHibernate extends BaseDaoHibernate implements IEmailGroupDAO
{

    @Override
    public List<EmailGroup> getEmailGroup()
    {
        return getHibernateTemplate().find("from EmailGroup");
    }

    @SuppressWarnings("unchecked")
    @Override
    public EmailGroup getEmailGroup(Long emailGroupId)
    {
        
        try
        {
           final String hql = "from EmailGroup t left join t.emails left join t.createPerson where t.id = "+emailGroupId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (EmailGroup)listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
        //return (EmailGroup) getHibernateTemplate().get(EmailGroup.class, emailGroupId);
    }

    @Override
    public void saveEmailGroup(EmailGroup emailGroup)
    {
        getHibernateTemplate().saveOrUpdate(emailGroup);
    }

    @Override
    public void removeEmailGroup(EmailGroup emailGroup)
    {
        getHibernateTemplate().delete(emailGroup);
    }
    
    @Override
    public void removeEmailGroup(Long emailGroupId)
    {
        getHibernateTemplate().delete(getEmailGroup(emailGroupId));
    }

    @Override
    public List<EmailGroup> getEmailGroupPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        // return getHibernateTemplate().find("from Email");
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from EmailGroup e left join fetch e.createPerson where e.name like :strQuery "
                    + "and e.createTime>=:strStraTime and e.createTime<=:strEndTime order by e.createTime desc";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery","%" + strQuery + "%");
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
    public int getEmailGroupTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from EmailGroup where name like :strQuery and createTime>="
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
