package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.INewsExtractDAO;
import com.tonik.model.KeyWord;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of INewsExtractDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="NewsExtractDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class NewsExtractHibernate extends BaseDaoHibernate implements INewsExtractDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<KeyWord> getKeyWord()
    {
        return getHibernateTemplate().find("form KeyWord");
    }

    @Override
    public int getKeyWordTotal(final String strQuery,final int category)
    {
        try
        {
            final String hql = "from KeyWord where name like :strQuery and category="+category;

            String[] params = { "strQuery"};

            Object[] args = { "%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public KeyWord getKeyWord(String strQuery,final int category)
    {
        try
        {
            final String hql = "from KeyWord where name like :strQuery and category="+category;
            List<KeyWord> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    List list = query.list();
                    return list;
                }
            });
            return (KeyWord) listTable.get(0);
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void SaveKeyWord(KeyWord keyword)
    {
        getHibernateTemplate().saveOrUpdate(keyword);
    }

}
