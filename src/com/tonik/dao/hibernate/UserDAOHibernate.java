package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.Constant;
import com.tonik.dao.IUserDAO;
import com.tonik.model.UserInfo;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IUserDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="UserDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class UserDAOHibernate extends BaseDaoHibernate implements IUserDAO
{
    @Override
    public List<UserInfo> getUser()
    {
        return getHibernateTemplate().find("from UserInfo fetch all properties");
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserInfo getUser(Long userId)
    {
        return (UserInfo) getHibernateTemplate().find(
                "from UserInfo fetch all properties where id=?",
                new Object[] { userId }).get(0);
    }

    @Override
    public void saveUser(UserInfo user)
    {
        getHibernateTemplate().saveOrUpdate(user);
    }

    public void updateUser(UserInfo us)
    {
        getHibernateTemplate().update(us);
    }

    @Override
    public void removeUser(UserInfo user)
    {
        getHibernateTemplate().delete(user);
    }

    public void removeUser(Long userid)
    {
        getHibernateTemplate().delete(getUser(userid));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> checkUser(String userCode, String userPwd)
    {
        return getHibernateTemplate().find( "from UserInfo where userCode=? and userPwd=?", new Object[] { userCode, userPwd });
    }

    public int getUserInfoTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            String hql = "from UserInfo where userCode like :strQuery and createTime>="
                    + ":strStraTime and createTime<=:strEndTime order by createTime desc";
            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strStraTime),
                    new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime) };
            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public List<UserInfo> getUserInfoPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strStraTime);
            final Date EndTime = new SimpleDateFormat(Constant.DATE_FORMAT).parse(strEndTime);

            final String hql = "from UserInfo t left join fetch t.userRole where t.userCode like :strQuery "
                    + "and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";
            List<UserInfo> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
}
