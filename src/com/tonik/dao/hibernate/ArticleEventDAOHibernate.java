package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IArticleEventDAO;
import com.tonik.model.Article;
import com.tonik.model.ArticleEvent;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationDAO.
 * </p>
 * @since Nov 17, 2015
 * @version 1.0
 * @author zby
 * @spring.bean id="ArticleEventDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ArticleEventDAOHibernate extends BaseDaoHibernate implements IArticleEventDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getEventsByArticle(Article article)
    {
        try
        {
            final String hql = "from ArticleEvent ae left join ae.article left join ae.event where "
                    + "ae.article.id="+article.getId();
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public void saveArticleEvent(ArticleEvent articleEvent)
    {
        getHibernateTemplate().saveOrUpdate(articleEvent);
    }

    @Override
    public void removeArticleEvent(ArticleEvent articleEvent)
    {
        getHibernateTemplate().delete(articleEvent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArticleEvent getArticleEvent(Long articleEventId)
    {
        try
        {
            final String hql = "from ArticleEvent ae left join ae.article left join ae.event where "
                    + "ae.id="+articleEventId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (ArticleEvent)listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ArticleEvent getArticleEventByArticleAndEvent(Long articleId, Long eventId)
    {
        try
        {
            final String hql = "from ArticleEvent ae left join ae.article left join ae.event where "
                    + "ae.article.id="+articleId+" and ae.event.id="+eventId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (ArticleEvent)listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public List<Object[]> getArticlesByEvent(String strEventId)
    {
        try
        {
            String hql =  "from ArticleEvent ae left join ae.article left join ae.event where ae.event.id =:strEventId";

            String[] params = { "strEventId" };

            Object[] args = { Long.parseLong(strEventId) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args);
        } catch (Exception e)
        {
            return null;
        }
    }

}
