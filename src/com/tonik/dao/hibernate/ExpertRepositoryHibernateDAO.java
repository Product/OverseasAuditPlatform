package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tonik.dao.IExpertRepositoryDAO;
import com.tonik.model.ExpertRepository;

/**
 * @desc: 专家知识库DAO层
 * @author fuzhi
 * @spring.bean id="ExpertRepositoryDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ExpertRepositoryHibernateDAO extends HibernateDaoSupport implements IExpertRepositoryDAO
{
    // 数据初始化
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpertRepository> initList(int pageIndex, int pageSize)
    {
        String hql = "from ExpertRepository";
        List<ExpertRepository> list = getHibernateTemplate().executeFind(new HibernateCallback()
        {
            @Override
            public List<ExpertRepository> doInHibernate(Session arg0) throws HibernateException, SQLException
            {
                Query query = arg0.createQuery(hql);
                query.setFirstResult((pageIndex - 1) * pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return list;
    }

    @Override
    public int initCount()
    {
        String hql = "select count(id) from ExpertRepository";
        Long count = (Long) getHibernateTemplate().find(hql).listIterator().next();
        return count.intValue();
    }

    // 根据关键字查询
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> queryListByKey(int pageIndex, int pageSize, String keyword, Long typeId)
    {
        int typeIdInt = typeId.intValue();
        String sql = "SELECT * FROM ( SELECT TOP " + (pageSize*pageIndex-1) + " ROW_NUMBER () OVER "
                + "(ORDER BY er.ExpertRepository_ID ASC) AS ROWID, * FROM ExpertRepository er "
                + "WHERE er.ProductType_Id IN (SELECT id FROM GetProductTypeInExpertRepository (" + typeIdInt +")) "
                + "and  er.ExpertRepository_Title like '%" + keyword + "%') AS TEMP1 "
                + "WHERE ROWID > "+(pageSize*(pageIndex-1)-1);
        Session session = getSession();
        List<Object[]> list = session.createSQLQuery(sql).list();
        releaseSession(session);
        return list;
    }

    @Override
    public int queryCountByKey(String keyword, Long typeId)
    {
        String sql = "select count(er.ProductType_Id) count from ExpertRepository er "
                + "where er.ProductType_Id IN (SELECT id from GetProductTypeInExpertRepository("
                + typeId + ")) and  er.ExpertRepository_Title like '%" + keyword + "%'";
        Session session = getSession();
        Object query = session.createSQLQuery(sql).addScalar("count", Hibernate.INTEGER).uniqueResult();
        int count = Integer.valueOf(query.toString());
        releaseSession(session);
        return count;
    }

    @Override
    public void save(ExpertRepository expertRepository)
    {
        getHibernateTemplate().saveOrUpdate(expertRepository);
    }

    @Override
    public ExpertRepository queryById(Long id)
    {
        String hql = "from ExpertRepository where id=" + id;
        ExpertRepository expertRepository = (ExpertRepository) getHibernateTemplate().find(hql).get(0);
        return expertRepository;
    }

    @Override
    public void del(ExpertRepository expertRepository)
    {
        getHibernateTemplate().delete(expertRepository);
    }
}
