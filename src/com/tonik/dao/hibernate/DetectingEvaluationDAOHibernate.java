package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.Constant;
import com.tonik.dao.IDetectingEvaluationDAO;
import com.tonik.model.DetectingEvaluation;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测评价模块 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="DetectingEvaluationDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */

public class DetectingEvaluationDAOHibernate extends BaseDaoHibernate implements IDetectingEvaluationDAO
{
    private final SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);
    
	
    @Override
    public List<DetectingEvaluation> getDetectingEvaluation()
    {
        return getHibernateTemplate().find("from DetectingEvaluation");
    }

    //通过ID获取检测评价
    @Override
    public DetectingEvaluation getDetectingEvaluation(Long detectingEvaluationId)
    {
        return (DetectingEvaluation) getHibernateTemplate().get(DetectingEvaluation.class, detectingEvaluationId);
    }

    //保存检测评价
    @Override
    public void saveDetectingEvaluation(DetectingEvaluation detectingEvaluation)
    {
        getHibernateTemplate().saveOrUpdate(detectingEvaluation);
    }

    //删除检测评价
    @Override
    public void removeDetectingEvaluation(DetectingEvaluation detectingEvaluation)
    {
        getHibernateTemplate().delete(detectingEvaluation);
    }

    //获取符合条件的某页所有检测评价
    @Override
    public List<DetectingEvaluation> getDetectingEvaluationPaging(final int pageIndex,final int pageSize,final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try{
            final Date StraTime = dateFormater.parse(strStraTime);
            final Date EndTime = dateFormater.parse(strEndTime);
    
            final String hql = "from DetectingEvaluation de left join fetch de.website"
                   + " where (de.website.name like :strQuery or de.remark like :strQuery) "
                   + " and de.createTime>=:strStraTime and de.createTime<=:strEndTime order by de.createTime desc";
            List<DetectingEvaluation> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List<DetectingEvaluation> list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e){
            return null;
        }
    }

    //获取符合条件的检测评价总数
    @Override
    public int getDetectingEvaluationTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from DetectingEvaluation de left join fetch de.website"
                    + " where (de.website.name like :strQuery or de.remark like :strQuery)"
                    + " and de.createTime>=:strStraTime and de.createTime<=:strEndTime order by de.createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%",dateFormater.parse(strStraTime),dateFormater.parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
}
