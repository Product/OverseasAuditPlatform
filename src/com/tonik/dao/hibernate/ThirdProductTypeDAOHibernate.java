package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IThirdProductTypeDAO;
import com.tonik.model.ThirdProductType;

/**
 * @spring.bean id="ThirdProductTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ThirdProductTypeDAOHibernate extends BaseDaoHibernate implements IThirdProductTypeDAO
{
    public List<Object[]> listThirdProductTypes(Long parent, Long thirdWebsite) throws Exception
    {
        StringBuilder sb = new StringBuilder(
                "select tpt.ID, tpt.Name, case (select count(*) from ThirdProductType where Parent = tpt.ID) when 0 then 0 else 1 end as isVisible from ThirdProductType as tpt where tpt.ThirdWebsite = :thirdWebsite and tpt.Parent ");
        if (parent == null)
            sb.append("is NULL");
        else
            sb.append("= " + parent);

        final String sql = sb.toString();
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("thirdWebsite", thirdWebsite);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    public int removeThirdProductTypes(Long thirdWebsite) throws Exception
    {
        StringBuilder sb = new StringBuilder(
                "delete from ThirdProductType tpt where tpt.thirdWebsite.id = :thirdWebsite");
        final String hql = sb.toString();
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setParameter("thirdWebsite", thirdWebsite);
        int count = query.executeUpdate();
        releaseSession(session);
        return count;
    }

    @Override
    public List<Object[]> listRelTypes(final int pageIndex, final int pageSize, final Long thirdWebsite,
            final String strQuery) throws Exception
    {
        StringBuilder sb = new StringBuilder(
                "SELECT TPT.ID AS id, dbo.AggregateThirdTypeName(TPT.ID) AS thirdTypeName, dbo.AggregateProductTypeName(TPT.ProductType) AS productTypeName, TW.Name AS thirdWebsiteName FROM ThirdProductType AS TPT, ThirdWebsite AS TW WHERE TPT.ThirdWebsite = TW.ID AND TPT.ProductType IS NOT NULL AND (dbo.AggregateThirdTypeName(TPT.ID) LIKE :strQuery OR dbo.AggregateProductTypeName(TPT.ProductType) LIKE :strQuery) ");
        if (thirdWebsite != null)
            sb.append("AND TPT.ThirdWebsite = " + thirdWebsite);
        final String sql = sb.toString();
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setString("strQuery", "%" + strQuery + "%");
        List list = query.list();
        releaseSession(session);
        return list;
    }

    @Override
    public Integer getlistRelTypesTotal(final Long thirdWebsite, final String strQuery)
    {
        try
        {
            StringBuilder sb = new StringBuilder(
                    "SELECT COUNT(*) FROM ThirdProductType AS TPT, ThirdWebsite AS TW WHERE TPT.ThirdWebsite = TW.ID AND TPT.ProductType IS NOT NULL AND (dbo.AggregateThirdTypeName(TPT.ID) LIKE :strQuery OR dbo.AggregateProductTypeName(TPT.ProductType) LIKE :strQuery) ");
            if (thirdWebsite != null)
                sb.append("AND TPT.ThirdWebsite = " + thirdWebsite);
            final String sql = sb.toString();
            Integer result = (Integer) getHibernateTemplate().execute(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
                    query.setString("strQuery", "%" + strQuery + "%");
                    return query.uniqueResult();
                }
            });
            return result;
        } catch (Exception e)
        {
            throw e;
        }
    }
}
