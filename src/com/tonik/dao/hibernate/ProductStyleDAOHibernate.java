package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductStyleDAO;
import com.tonik.model.ProductStyle;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IProductStyleDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="ProductStyleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ProductStyleDAOHibernate extends BaseDaoHibernate implements IProductStyleDAO
{
    @Override
    public List<ProductStyle> getProductStyle()
    {
        return getHibernateTemplate().find("from ProductStyle");
    }

    @Override
    public ProductStyle getProductStyle(Long productStyleId)
    {
        return (ProductStyle) getHibernateTemplate().get(ProductStyle.class, productStyleId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductStyle getProductStyleByName(final String productStyleName)
    {
        try
        {
            final String hql = "from ProductStyle where name = '" + productStyleName + "'";
            List<ProductStyle> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable.get(0);
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveProductStyle(ProductStyle productStyle)
    {
        getHibernateTemplate().saveOrUpdate(productStyle);
    }

    @Override
    public void removeProductStyle(ProductStyle productStyle)
    {
        getHibernateTemplate().delete(productStyle);
    }

    @Override
    public void removeProductStyle(Long productStyleId)
    {
        getHibernateTemplate().delete(getProductStyle(productStyleId));
    }

    @Override

    public List<ProductStyle> getProductStylePaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime, final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from ProductStyle where (name like :strQuery or remark like :strQuery) "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int getProductStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from ProductStyle where (name like :strQuery or remark like :strQuery) and createTime>="
                    + " :strStraTime and createTime<=:strEndTime order by createTime desc";
            String[] params = { "strQuery", "strStraTime", "strEndTime" };
            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };
            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Long> getStyleIdsByTypeIds(List<Long> typeIds)
    {
        String str = "";
        for (Long item : typeIds)
        {
            str += item.toString() + ",";
        }
        if (str != "")
        {
            str = str.substring(0, str.length() - 1);
        }
        List<Long> styleIds = new ArrayList<Long>();
        Session session = getSession();
        List<Object[]> list = session
                .createSQLQuery("SELECT DISTINCT PRODUCTSTYLE_ID FROM STYLETYPES WHERE 1=1 "
                        + (str == "" ? "" : "and PRODUCTTYPE_ID IN (" + str + ")")).list();
        releaseSession(session);
                
      
        for(Object[] obj : list){
            Long i = (Long)obj[0];
            styleIds.add(i);
        }
        return styleIds;
    }

}
