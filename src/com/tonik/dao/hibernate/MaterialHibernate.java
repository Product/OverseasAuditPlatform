package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IMaterialDAO;
import com.tonik.model.Material;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMaterialDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="MaterialDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MaterialHibernate extends BaseDaoHibernate implements IMaterialDAO
{
    @SuppressWarnings("unchecked")
    public List<Object[]> getMaterialsByMaterialTypeId(Long materialTypeId)
    {
        if(materialTypeId.equals(0L))
        {
            return getHibernateTemplate().find("from Material t left join t.materialtype");
        }
        else
            return getHibernateTemplate().find("from Material t left join t.materialtype where t.materialtype.id="+materialTypeId);
    }
    @SuppressWarnings("unchecked")
    public List<Material> getMaterial()
    {
        return getHibernateTemplate().find("from Material fetch all properties");
    }
    
    public int getMateialTotal(String strQuery)
    {
        try
        {
            String hql = "from Material t left join t.materialtype where t.name like :strQuery or t.materialtype.name like :strQuery";

            String[] params = { "strQuery","strQuery"};

            Object[] args = { "%" + strQuery + "%","%" + strQuery + "%"};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Material> getMaterialPaging(final int pageIndex, final int pageSize, final String strQuery)
    {
        try
        {
            final String hql = "from Material t left join fetch t.materialtype where t.name like :strQuery or t.materialtype.name like :strQuery";
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

    @Override
    public void SaveMaterial(Material material)
    {
        getHibernateTemplate().saveOrUpdate(material);
    }

    @Override
    public Material getMaterialById(Long materialId)
    {
        return (Material)getHibernateTemplate().find(
                "from Material t left join fetch t.materialtype where t.id=?",
                new Object[] { materialId}).get(0);
    }

    @Override
    public void RemoveMaterial(Long materialId)
    {
        getHibernateTemplate().delete(getMaterialById(materialId));
    }

    public int getMaterialsById(final Long id)
    {
        try
        {
            final String hql = "from Material t left join fetch t.materialtype where t.materialtype.id=" + id;
            List total = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return total.size();

        } catch (Exception e)
        {
            return 0;
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IMaterialDAO#getMaterialByTypeId(java.lang.Long)
     * @Override
     * @desc 通过配方原料类别获得原料列表
     * @return 配方原料列表
     */
    public List<Material> getMaterialByTypeId(Long typeId)
    {
        try
        {
            final String hql = "from Material t left join fetch t.materialtype where t.materialtype.id=" + typeId;
            List<Material> materialList = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return materialList;

        } catch (Exception e)
        {
            return null;
        }
    }
}
