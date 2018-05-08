package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IRelEvaluationProductDAO;
import com.tonik.model.RelEvaluationProduct;
import com.tonik.model.RelProductTypeStyle;

/**
 * @spring.bean id="RelEvaluationProductDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RelEvaluationProductDAOHibernate extends BaseDaoHibernate implements IRelEvaluationProductDAO
{
    @Override
    public List<Object[]> getRelEvaluationProductPaging(final int pageIndex, final int pageSize,
            final Integer evaluationType, final Long productType, final String strQuery)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "SELECT P.PRODUCT_ID AS id, P.PRODUCT_NAME AS name, P.PRODUCT_LOCATION AS location, EM.EVALUATION_TYPE AS type, EM.EVALUATIONOBJECT_ID AS productTypeId, PT.PRODUCTTYPE_NAME AS productTypeName, SUM(REP.GRADE * EM.EVALUATIONINDEX_WEIGHT) AS score FROM REL_EVALUATION_PRODUCT AS REP LEFT JOIN PRODUCT AS P ON REP.PRODUCT_ID = P.PRODUCT_ID INNER JOIN EVALUATION_MANAGEMENT AS EM ON REP.EVALUATION_MANAGEMENT_ID = EM.ID LEFT JOIN PRODUCTTYPE AS PT ON PT.PRODUCTTYPE_ID = EM.EVALUATIONOBJECT_ID WHERE P.PRODUCT_NAME LIKE :strQuery ");
            if (evaluationType != null)
            {
                sb.append(" AND EM.EVALUATION_TYPE = " + evaluationType);
                if (evaluationType == 3)
                    sb.append(" AND EM.EVALUATIONOBJECT_ID IN ( SELECT ID FROM F_GetProductTypeTree(" + productType
                            + ") ) ");
            }
            sb.append(
                    " GROUP BY P.PRODUCT_ID, P.PRODUCT_NAME, P.PRODUCT_LOCATION, EM.EVALUATION_TYPE, EM.EVALUATIONOBJECT_ID, PT.PRODUCTTYPE_NAME ");
            final String sql = sb.toString();
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setString("strQuery", "%" + strQuery + "%");
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
    public Integer getRelEvaluationProductTotal(final Integer evaluationType, final Long productType,
            final String strQuery)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "SELECT COUNT(*) FROM V_RelEvaluationProduct AS REP WHERE REP.PRODUCT_NAME LIKE :strQuery");
            if (evaluationType != null)
            {
                sb.append(" AND REP.EVALUATION_TYPE = " + evaluationType);
                if (evaluationType == 3)
                    sb.append(" AND REP.EVALUATIONOBJECT_ID IN ( SELECT ID FROM F_GetProductTypeTree(" + productType
                            + ") ) ");
            }
            final String sql = sb.toString();
            Integer result = (Integer) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setString("strQuery", "%" + strQuery + "%");
                    return query.uniqueResult();
                }
            });
            return result;
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void saveRelEvaluationProduct(RelEvaluationProduct relEvaluationProduct)
    {
        getHibernateTemplate().saveOrUpdate(relEvaluationProduct);
    }

    @Override
    public void removeRelEvaluationProduct(Long id)
    {
        getHibernateTemplate().delete(
                new RelEvaluationProduct().setId(id).setEvaluationManagementId(0l).setProductId(0l).setGrade(0.0));
    }

    @Override
    public void delRelEvaluationProducts(Integer type, Long productTypeId, Long productId) throws Exception
    {
        StringBuilder sb = new StringBuilder(
                "DELETE REP FROM REL_EVALUATION_PRODUCT AS REP LEFT JOIN EVALUATION_MANAGEMENT AS EM ON REP.EVALUATION_MANAGEMENT_ID = EM.ID WHERE EM.EVALUATION_TYPE = :type AND REP.PRODUCT_ID = :productId ");
        if (type == 3 && productTypeId != null)
            sb.append("AND EM.EVALUATIONOBJECT_ID = " + productTypeId);
        final String sql = sb.toString();Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("type", type);
        query.setParameter("productId", productId);
        int code = query.executeUpdate();
        releaseSession(session);
    }

    @Override
    public List<Object[]> getRelEvaluationProductDetail(final Integer evaluationType, final Long productType,
            Long productId)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "SELECT EM.ID, EI.EVALUATIONINDEX_NAME, REP.GRADE, EM.EVALUATIONINDEX_WEIGHT FROM REL_EVALUATION_PRODUCT AS REP LEFT JOIN EVALUATION_MANAGEMENT AS EM ON EM.ID = REP.EVALUATION_MANAGEMENT_ID LEFT JOIN PRODUCT AS P ON REP.PRODUCT_ID = P.PRODUCT_ID LEFT JOIN EVALUATIONINDEX AS EI ON EI.EVALUATIONINDEX_ID = EM.EVALUATIONINDEX_ID WHERE REP.PRODUCT_ID = :productId AND EM.EVALUATION_TYPE = :evaluationType");
            if (evaluationType == 3)
                sb.append(" AND EM.EVALUATIONOBJECT_ID = " + productType);
            final String sql = sb.toString();
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setInteger("evaluationType", evaluationType);
                    query.setLong("productId", productId);
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
}