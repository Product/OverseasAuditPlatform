package com.tonik.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tonik.dao.IEvaluationManagementDAO;

/**
 * @spring.bean id="EvaluationManagementDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EvaluationManagementDAOHibernate extends BaseDaoHibernate implements IEvaluationManagementDAO
{
    @Override
    public void delEvaluationManagements(Integer type, String productType) throws Exception {
        String hql;
        if(type == 3)
            hql = "delete from EvaluationManagement as em "
                    + "where em.evaluationType = :type and em.evaluationObjectId = " + Long.parseLong(productType);
        else
            hql = "delete from EvaluationManagement as em where em.evaluationType = :type ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setParameter("type", type);
        int code = query.executeUpdate();
        releaseSession(session);
    }
    
    @Override
    public List<Object[]> initEvaluationManagement(Integer type, String productType, String strQuery) throws Exception {
        String sql;
        if(type == 3)
            sql = "SELECT EI.EVALUATIONINDEX_ID, "
                    + "EI.EVALUATIONINDEX_NAME, "
                    + "EM.EVALUATIONINDEX_WEIGHT, "
                    + "EM.ID "
                    + "FROM EVALUATIONINDEX AS EI "
                    + "LEFT JOIN EVALUATION_MANAGEMENT AS EM ON EM.EVALUATIONINDEX_ID = EI.EVALUATIONINDEX_ID "
                    + "AND EM.EVALUATION_TYPE = :type AND EM.EVALUATIONOBJECT_ID = " + Long.parseLong(productType)
                    + " WHERE EI.EVALUATIONINDEX_NAME LIKE :strQuery ";
        else
            sql = "SELECT EI.EVALUATIONINDEX_ID, "
                    + "EI.EVALUATIONINDEX_NAME, "
                    + "EM.EVALUATIONINDEX_WEIGHT, "
                    + "EM.ID "
                    + "FROM EVALUATIONINDEX AS EI "
                    + "LEFT JOIN EVALUATION_MANAGEMENT AS EM ON EM.EVALUATIONINDEX_ID = EI.EVALUATIONINDEX_ID "
                    + "AND EM.EVALUATION_TYPE = :type "
                    + "WHERE EI.EVALUATIONINDEX_NAME LIKE :strQuery ";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("type", type);
        query.setParameter("strQuery", "%" + strQuery + "%");
        List list = query.list();
        releaseSession(session);
        return list;
    }
}