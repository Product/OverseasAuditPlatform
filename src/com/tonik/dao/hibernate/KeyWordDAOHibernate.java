package com.tonik.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IKeyWordDAO;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IKeyWordDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="KeyWordDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class KeyWordDAOHibernate  extends BaseDaoHibernate implements IKeyWordDAO
{

    @Override
    public void DelKeyWord()     //删除关键字表中的所有数据
    {
        final String hql = "delete from KeyWord";
        getHibernateTemplate().execute(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.executeUpdate();
                return query.executeUpdate();
            }
        });
        
    }
}
