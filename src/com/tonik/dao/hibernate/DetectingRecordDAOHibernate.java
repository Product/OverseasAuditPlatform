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
import com.tonik.dao.IDetectingRecordDAO;
import com.tonik.model.DetectingRecord;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测记录模块 DAO层接口的实现
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="DetectingRecordDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class DetectingRecordDAOHibernate extends BaseDaoHibernate implements IDetectingRecordDAO
{
	private final SimpleDateFormat dateFormater = new SimpleDateFormat(Constant.DATE_FORMAT);

    @Override
    public List<DetectingRecord> getDetectingRecord()
    {
        return getHibernateTemplate().find("from DetectingRecord");
    }

    //通过ID获取检测记录
    @Override
    public DetectingRecord getDetectingRecord(Long detectingRecordId)
    {
        return (DetectingRecord) getHibernateTemplate().get(DetectingRecord.class, detectingRecordId);
    }

    //保存检测记录
    @Override
    public void saveDetectingRecord(DetectingRecord detectingRecord)
    {
        getHibernateTemplate().saveOrUpdate(detectingRecord);
    }

    //删除检测记录  
    @Override
    public void removeDetectingRecord(DetectingRecord detectingRecord)
    {
        getHibernateTemplate().delete(detectingRecord);
    }

    //获取某一页的所有检测记录
    @SuppressWarnings("unchecked")
    @Override
    public List<DetectingRecord> getDetectingRecordPaging(final int pageIndex,final int pageSize,final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try{
            final Date StraTime = dateFormater.parse(strStraTime);
            final Date EndTime = dateFormater.parse(strEndTime);

            final String hql = "from DetectingRecord dr left join fetch dr.website"
                    + " where (dr.website.name like :strQuery or dr.product like :strQuery or dr.organization like :strQuery or dr.remark like :strQuery)"
                    + " and dr.createTime>=:strStraTime and dr.createTime<=:strEndTime order by dr.createTime desc";
            List<DetectingRecord> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
        }catch(Exception e){
            return null;
        }
    }

    //获取检测记录总数
    @Override
    public int getDetectingRecordTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from DetectingRecord dr left join fetch dr.website"
                    + " where (dr.website.name like :strQuery or dr.product like :strQuery or dr.organization like :strQuery or dr.remark like :strQuery)"
                    + " and dr.createTime>=:strStraTime and dr.createTime<=:strEndTime order by dr.createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", dateFormater.parse(strStraTime),dateFormater.parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
}
