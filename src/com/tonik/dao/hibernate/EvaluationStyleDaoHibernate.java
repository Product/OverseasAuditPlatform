package com.tonik.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tonik.dao.IEvaluationStyleDAO;
import com.tonik.model.EvaluationStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="EvaluationStyleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EvaluationStyleDaoHibernate extends BaseDaoHibernate implements IEvaluationStyleDAO
{
    @Override
    public List<EvaluationStyle> getEvaluationStyle()
    {
        return getHibernateTemplate().find("from EvaluationStyle");
    }

    @Override
    public EvaluationStyle getEvaluationStyle(Long evaluationStyleId)
    {
        return (EvaluationStyle) getHibernateTemplate().get(EvaluationStyle.class, evaluationStyleId);
    }

    @Override
    public void saveEvaluationStyle(EvaluationStyle evaluationStyle)
    {
        getHibernateTemplate().saveOrUpdate(evaluationStyle);

    }

    @Override
    public void removeEvaluationStyle(EvaluationStyle evaluationStyle)
    {
        getHibernateTemplate().delete(evaluationStyle);

    }

    @Override
    public List<Object[]> listEvaluationStyles(Long parent, Long tree)
    {
        StringBuilder sb = new StringBuilder(
                "SELECT ES.EVALUATIONSTYLE_ID, ES.EVALUATIONSTYLE_NAME, 0 AS FLAG FROM EVALUATIONSTYLE AS ES WHERE ES.Tree = :tree ");
        if (parent == null)
            sb.append("and es.parent is null");
        else
            sb.append("AND ES.Parent = " + parent
                    + " UNION ALL SELECT EI.EVALUATIONINDEX_ID, EI.EVALUATIONINDEX_NAME, 1 AS FLAG FROM EVALUATIONINDEX AS EI WHERE EI.EVALUATIONSTYLE_ID = "
                    + parent);
        final String sql = sb.toString();
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("tree", tree);
        List list = query.list();
        releaseSession(session);
        return list;
    }
}
