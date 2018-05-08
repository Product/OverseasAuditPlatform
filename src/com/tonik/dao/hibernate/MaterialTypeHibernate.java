package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IMaterialTypeDAO;
import com.tonik.model.MaterialType;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMaterialTypeDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="MaterialTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MaterialTypeHibernate extends BaseDaoHibernate implements IMaterialTypeDAO
{

    @SuppressWarnings("unchecked")
    @Override
    public List<MaterialType> getMaterialType()
    {
        return getHibernateTemplate().find("from MaterialType");
    }

    @Override
    public MaterialType getMaterialTypeById(Long id)
    {
        return (MaterialType) getHibernateTemplate().find(
                "from MaterialType where id=?",
                new Object[] { id}).get(0);
    }

    @Override
    public void saveMaterialType(MaterialType materialtype)
    {
        getHibernateTemplate().saveOrUpdate(materialtype);       
    }

    @Override
    public void removeMaterialTpe(Long id)
    {
        getHibernateTemplate().delete(getMaterialTypeById(id));
    }
    
    @Override
    public int getMaterialTypeTotal(final String strQuery)
    {
        try
        {
            String hql = "from MaterialType where name like :strQuery";

            String[] params = { "strQuery"};

            Object[] args = { "%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<MaterialType> getMaterialTypePaging(final int pageIndex, final int pageSize, final String strQuery)
    {
        try
        {
            final String hql = "from MaterialType where name like :strQuery";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
            } catch (Exception e)
            {
                return null;
            }
    }

}
