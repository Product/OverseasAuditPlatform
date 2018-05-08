package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IChattingMessageDAO;
import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.model.ChattingMessage;
import com.tonik.model.ProductTrivialName;

/**
 * @spring.bean id="ChattingMessageDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ChattingMessageDAOHibernate extends BaseDaoHibernate implements IChattingMessageDAO
{
    @Override
    public List<ChattingMessage> getChattingMessagePaging(final int pageIndex, final int pageSize,
            final String strQuery, final Date startTime)
    {
        try
        {
            final String hql = "from ChattingMessage cm "
                    + "where cm.answerTime >= :startTime and "
                    + "(cm.answer like :strQuery or cm.question like :strQuery) "
                    + "order by cm.answerTime desc";
            List<ChattingMessage> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setString("strQuery", "%" + strQuery + "%");
                    query.setDate("startTime", startTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public Integer getChattingMessageTotal(final String strQuery, final Date startTime)
    {
        try
        {
            final String hql = "select count(*) from ChattingMessage cm "
                    + "where cm.answerTime >= :startTime and "
                    + "(cm.answer like :strQuery or cm.question like :strQuery) ";
            Long count = (Long) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setString("strQuery", "%" + strQuery + "%");
                    query.setDate("startTime", startTime);
                    Object result = query.uniqueResult();
                    return result;
                }
            });
            return count.intValue();
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void saveChattingMessage(ChattingMessage chattingMessage)
    {
        getHibernateTemplate().saveOrUpdate(chattingMessage);
    }

    @Override
    public void removeChattingMessage(Long id)
    {
        getHibernateTemplate().delete(getObject(ChattingMessage.class, id));
    }
}