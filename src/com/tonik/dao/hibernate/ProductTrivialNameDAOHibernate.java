package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.model.ProductTrivialName;

/**
 * @spring.bean id="ProductTrivialNameDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ProductTrivialNameDAOHibernate extends BaseDaoHibernate implements IProductTrivialNameDAO
{
    @Override
    public List<ProductTrivialName> getProductTrivialNamePaging(final int pageIndex, final int pageSize,
            final String productTrivialName, final String productScientificName)
    {
        try
        {
            final String hql = "from ProductTrivialName ptn "
                    + "where ptn.productTrivialName like :productTrivialName "
                    + "and ptn.productScientificName like :productScientificName "
                    + "order by ptn.createTime desc";
            List<ProductTrivialName> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setString("productTrivialName", "%" + productTrivialName + "%");
                    query.setString("productScientificName", "%" + productScientificName + "%");
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
    public Integer getProductTrivialNameTotal(final String productTrivialName, final String productScientificName)
    {
        try
        {
            final String sql = "SELECT COUNT(*) FROM PRODUCT_TRIVIALNAME "
                    + "WHERE PRODUCT_TRIVIALNAME.PRODUCT_TRIVIALNAME LIKE :productTrivialName "
                    + "AND PRODUCT_TRIVIALNAME.PRODUCT_SCIENTIFICNAME LIKE :productScientificName ";
            Integer result = (Integer) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setString("productTrivialName", "%" + productTrivialName + "%");
                    query.setString("productScientificName", "%" + productScientificName + "%");
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
    public void saveProductTrivialName(ProductTrivialName productTrivialName)
    {
        getHibernateTemplate().saveOrUpdate(productTrivialName);
    }

    @Override
    public void removeProductTrivialName(Long id)
    {
        getHibernateTemplate().delete(new ProductTrivialName().setId(id));
    }
}