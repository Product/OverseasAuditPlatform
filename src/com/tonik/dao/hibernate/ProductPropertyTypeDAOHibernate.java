package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductPropertyTypeDAO;
import com.tonik.model.ProductPropertyType;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IProductPropertyTypeDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuxutong
 * @spring.bean id="ProductPropertyTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */

public class ProductPropertyTypeDAOHibernate extends BaseDaoHibernate implements IProductPropertyTypeDAO
{
    
    @Override
    public List<ProductPropertyType> getProductPropertyType()
    {
        return getHibernateTemplate().find("from ProductPropertyType");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductPropertyType getProductPropertyType(Long productPropertyTypeId)
    {
        try{
            final String hql = "from ProductPropertyType e left join e.createPerson where e.id="+productPropertyTypeId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (ProductPropertyType)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductPropertyType> getRootProductPropertyType()
    {
        try{
            final String hql = "from ProductPropertyType e where e.ptid=0";
            List<ProductPropertyType> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e)
        {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getChildrenProductPropertyType(Long productPropertyTypeId)
    {
        try{
            final String hql = "from ProductPropertyType e left join e.createPerson where e.ptid="+productPropertyTypeId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e)
        {
            return null;
        }
    }
    
  

}
