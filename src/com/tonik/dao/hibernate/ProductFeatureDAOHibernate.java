package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductFeatureDAO;
import com.tonik.model.Country;
import com.tonik.model.ProductFeature;

/**
 * @spring.bean id="ProductFeatureDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ProductFeatureDAOHibernate extends BaseDaoHibernate implements IProductFeatureDAO
{
    @Override
    public List<ProductFeature> getProductFeaturePaging(final int pageIndex, final int pageSize)
    {
        try
        {
            final String hql = "from ProductFeature pf order by pf.updateTime desc";
            List<ProductFeature> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
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
    public Integer getProductFeatureTotal()
    {
        try
        {
            final String sql = "SELECT COUNT(*) FROM PRODUCT_FEATURE";
            Integer result = (Integer) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
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
    public void saveProductFeature(ProductFeature productFeature)
    {
        getHibernateTemplate().saveOrUpdate(productFeature);
    }
    
    @Override
    public void removeProductFeature(Long id)
    {
        getHibernateTemplate().delete(new ProductFeature().setId(id));
    }
}