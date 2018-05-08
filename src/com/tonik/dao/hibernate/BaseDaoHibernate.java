package com.tonik.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.IDAO;
import com.tonik.model.Article;

public class BaseDaoHibernate extends HibernateDaoSupport implements IDAO
{
    protected final Log log = LogFactory.getLog(getClass());


    public void saveObject(Object o)
    {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public Object getObject(Class clazz, Serializable id)
    {
        Object o = getHibernateTemplate().get(clazz, id);

        if (o == null)
        {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    public Object loadObject(Class clazz, Serializable id)
    {
        return getSession().load(clazz, id);
    }

    public List getObjects(Class clazz)
    {
        return getHibernateTemplate().loadAll(clazz);
    }

    public void removeObject(Class clazz, Serializable id)
    {
        getHibernateTemplate().delete(getObject(clazz, id));
    }

}// EOF
