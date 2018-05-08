package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEmailDAO;
import com.tonik.model.Email;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEmailDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author yekai
 * @spring.bean id="EmailDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EmailDAOHibernate extends BaseDaoHibernate implements IEmailDAO
{

    @Override
    public List<Email> getEmail()
    {
        return getHibernateTemplate().find("from Email");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Email getEmail(Long emailId)
    {
        try{
            final String hql = "from Email e left join e.createPerson where e.id="+emailId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Email)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveEmail(Email email)
    {
        getHibernateTemplate().saveOrUpdate(email);
    }

    @Override
    public void removeEmail(Email email)
    {
        getHibernateTemplate().delete(email);
    }
    
    @Override
    public void removeEmail(Long emailId)
    {
        getHibernateTemplate().delete(getEmail(emailId));
    }

    @Override
    public List<Email> getEmailPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Email e left join fetch e.createPerson where e.emailAddress like :strQuery or e.company like :strQuery "
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

    //邮箱群组管理获取某一群组下所有邮件地址
    @Override
    public List<Email> getEmailManage(Long emailGroupId)
    {
        try
        {
            final String hql = "SELECT e FROM Email e join e.emailGroups eg where eg.id = :emailGroupId";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
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
    public int getEmailTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from Email where emailAddress like :strQuery or company like :strQuery or createTime>="
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
