package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IArticleDAO;
import com.tonik.model.Article;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="ArticleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ArticleDAOHibernate extends BaseDaoHibernate implements IArticleDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticle()
    {
        return getHibernateTemplate().find("from Article");
    }
    
    @SuppressWarnings("unchecked")
    public List<Article> getArticleRecent(final String RecentTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(RecentTime);
            final String hql = "from Article where gatherTime>=:StraTime";
            //return getHibernateTemplate().find(hql);
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("StraTime", StraTime);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Article getArticle(Long articleId)
    {
        try
        {
            final String hql = "from Article e left join e.createPerson where e.id=" + articleId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Article) listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveArticle(Article article)
    {
        getHibernateTemplate().saveOrUpdate(article);
    }

    @Override
    public void removeArticle(Article article)
    {
        getHibernateTemplate().delete(article);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getArticlePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Article e left join e.createPerson where "
                    + "(e.title like :strQuery or e.remark like :strQuery) "
                    + "and e.gatherTime>=:strStraTime and e.gatherTime<=:strEndTime order by e.createTime desc";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
    public int getArticleTotal(final String strQuery, final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime);
            final Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            final String hql = "from Article e left join e.createPerson where "
                    + "(e.title like :strQuery or e.remark like :strQuery) "
                    + "and e.gatherTime>=:strStraTime and e.gatherTime<=:strEndTime order by e.gatherTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();

        } catch (Exception e)
        {
            return 0;
        }
    }
    
    public List<Article> matchAllByBrand(final String name_cn)
    {
        try
        {
            final String hql = "from Article where content like :name_cn";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("name_cn", "%" + name_cn + "%");
//                    query.setParameter("name_en", "%" + name_en + "%");
//                    query.setParameter("name_other", "%" + name_other + "%");
//                    query.setFirstResult((pageIndex - 1) * pageSize);
//                    query.setMaxResults(pageSize);
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
    
    public List<Article> matchAllByPT(String name)
    {
        try
        {
            final String hql = "from Article where content like :name";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("name", "%" + name+ "%");
//                    query.setParameter("name_en", "%" + name_en + "%");
//                    query.setParameter("name_other", "%" + name_other + "%");
//                    query.setFirstResult((pageIndex - 1) * pageSize);
//                    query.setMaxResults(pageSize);
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
    public Article getArticleByLocation(String location)
    {
        try{
            return (Article)getHibernateTemplate().find(
                    "from Article where location=?",
                    new Object[] { location}).get(0);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public int getArticleTotalByEvent(String strQuery, String strStraTime, String strEndTime, long parseLong)
    {
        try
        {
            //final Date st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime);
            //final Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            final String hql = "from ArticleEvent ae left join ae.event left join ae.article where ae.event.id=:strEventId and "
                    + "(ae.article.title like :strQuery or ae.article.remark like :strQuery) "
                    + "and ae.article.gatherTime>=:strStraTime and ae.article.gatherTime<=:strEndTime";

            String[] params = {"strEventId", "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { parseLong, "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strEndTime) };

            return (Integer)this.getHibernateTemplate().findByNamedParam(hql, params, args).size();

        } catch (Exception e)
        {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getArticleByEventPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime, Long[] artsId)
    {
        try{
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            String hql="from Article e left join e.createPerson where e.id in (:articleIds) and "
                    + "(e.title like :strQuery or e.remark like :strQuery) "
                    + "and e.gatherTime>=:strStraTime and e.gatherTime<=:strEndTime order by e.createTime ASC";
                    
            final String finalHql=hql;
            
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(finalHql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setParameterList("articleIds", artsId);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });           
            return listTable;
        }catch(Exception e)
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticleByPage(int pageIndex, int pageSize)
    {
        final String hql = "from Article e order by e.gatherTime desc";
        List<Article> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex - 1) * pageSize);
                query.setMaxResults(pageSize);
                List<Article> list = query.list();
                return list;
            }
        });
        return listTable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getArticleManageByPage(int pageIndex, int pageSize)
    {
        final String hql = "select e.id, e.title, e.createTime, e.location from Article e order by e.gatherTime desc";
        List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = session.createQuery(hql);
                query.setFirstResult((pageIndex - 1) * pageSize);
                query.setMaxResults(pageSize);
                List<Object> list = query.list();
                return list;
            }
        });
        return listTable;
    }
    public int getArticleCount()
    {
        String hql = "select count(*) from Article";  
        Long count = (Long)getHibernateTemplate().find(hql).listIterator().next();  
        return count.intValue();

    }
}
