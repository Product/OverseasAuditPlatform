package com.tonik.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tonik.dao.IRelProductMaterialDAO;
import com.tonik.model.RelProductMaterial;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationDAO.
 * </p>
 * @since Apr 26, 2016
 * @version 1.0
 * @author liuyu
 * @spring.bean id="RelProductMaterialDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RelProductMaterialDAOHibernate extends BaseDaoHibernate implements IRelProductMaterialDAO
{
    @Override
    public List<Object[]> getProductMaterialsByProductId(Long productId)
    {
        String sql = "select rpm.Id,m.MATERIAL_ID,m.MATERIAL_NAME,rpm.MATERIAL_CONTENT,rpm.UNIT from REL_PRODUCT_MATERIAL rpm left join MATERIAL m on m.MATERIAL_ID = rpm.MATERIAL_ID where rpm.PRODUCT_ID = :id";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("id", productId);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    @Override
    public void saveRelProductMaterial(RelProductMaterial relProductmaterial)
    {
        getHibernateTemplate().saveOrUpdate(relProductmaterial);
    }

    @Override
    public RelProductMaterial getProductMaterial(Long id)
    {
        return (RelProductMaterial)getHibernateTemplate().find(
                "from RelProductMaterial r where r.id=?",
                new Object[] { id}).get(0);
    }

    @Override
    public void delProductMaterial(Long id)
    {
        getHibernateTemplate().delete(getProductMaterial(id));
    }

}
