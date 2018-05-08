package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IMatchDAO;
import com.tonik.model.Article;
import com.tonik.model.KeyWord;
import com.tonik.model.Match;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMatchDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="MatchDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MatchDAOHibernate extends BaseDaoHibernate implements IMatchDAO
{

    @Override
    public List<Match> Match()
    {
        return getHibernateTemplate().find("from Match");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Match getMatch(Long matchId)
    {
        try
        {
            final String hql = "from Match e left join e.article left join e.keyWord where e.id=" + matchId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Match) listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveMatch(Match match)
    {
        getHibernateTemplate().saveOrUpdate(match);
    }

    @Override
    public void removeMatch(Match match)
    {
        getHibernateTemplate().delete(match);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticlePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Article a where a.id in "
                    + "(select distinct e.article.id from Match e left join e.article where "
                    + "(e.article.title like :strQuery or e.article.remark like :strQuery or e.article.content like :strQuery) "
                    + "and e.article.gatherTime>=:strStraTime and e.article.gatherTime<=:strEndTime) order by a.gatherTime desc";
//                    "select e.article.id e.article from Match e left join e.article where "
//                    + "(e.article.title like :strQuery or e.article.remark like :strQuery or e.article.content like :strQuery) "
//                    + "and e.article.gatherTime>=:strStraTime and e.article.gatherTime<=:strEndTime group by e.article ";
            List<Article> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getMatchTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime);
            final Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "select distinct e.article.id from Match e left join e.article where "
                    + "(e.article.title like :strQuery or e.article.remark like :strQuery or e.article.content like :strQuery) "
                    + "and e.article.gatherTime>=:strStraTime and e.article.gatherTime<=:strEndTime";
            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();

        } catch (Exception e)
        {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<KeyWord> getArticleKeyWords(Long articleId)
    {
        try
        {
            final String hql = "select e.keyWord from Match e left join e.keyWord where e.keyWord is not null and "
                    + "e.article="+articleId+" order by e.keyWord.category";

            return this.getHibernateTemplate().find(hql);
        } catch (Exception e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Match> getArticleMatches(Long articleId)
    {
        try
        {
            final String hql = "from Match e where "
                    + "e.article="+articleId;

            return this.getHibernateTemplate().find(hql);
        } catch (Exception e)
        {
            return null;
        }
    }
    
    public void DelMatch()
    {
        final String hql = "delete from Match";
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
