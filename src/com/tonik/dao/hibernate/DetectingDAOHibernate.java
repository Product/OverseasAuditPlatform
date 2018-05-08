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
import com.tonik.dao.IDetectingDAO;
import com.tonik.model.DetectingEvent;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测事件模块 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="DetectingDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class DetectingDAOHibernate extends BaseDaoHibernate implements IDetectingDAO
{
    private final SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);
    
    @Override
    public List<DetectingEvent> getDetecting()
    {
        return getHibernateTemplate().find("from Detecting");
    }

    //通过ID获取检测事件
    @Override
    public DetectingEvent getDetecting(Long detectingId)
    {
        return (DetectingEvent) getHibernateTemplate().get(DetectingEvent.class, detectingId);
    }

    //保存检测事件
    @Override
    public void saveDetecting(DetectingEvent detecting)
    {
        getHibernateTemplate().saveOrUpdate(detecting);
    }

    //删除检测事件
    @Override
    public void removeDetecting(DetectingEvent detecting)
    {
        getHibernateTemplate().delete(detecting);
    }
    
    //获取某一页的检测事件
    @SuppressWarnings("unchecked")
    @Override
    public List<DetectingEvent> getDetectingPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        try{
            final Date StraTime = dateFormater.parse(strStraTime);
            final Date EndTime = dateFormater.parse(strEndTime);

            final String hql = "from DetectingEvent t left join fetch t.website"
                    + " where (t.website.name like :strQuery or t.remark like :strQuery or t.eventType like :strQuery)"
                    + " and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";
            List<DetectingEvent> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List<DetectingEvent> list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e){
            return null;
        }
    }
    
    //获取检测信息总数
    @Override
    public int getDetectingTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql =  "from DetectingEvent t left join t.website"
                    + " where (t.website.name like :strQuery  or t.remark like :strQuery or t.eventType like :strQuery)"
                    + " and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", dateFormater.parse(strStraTime),dateFormater.parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
}
