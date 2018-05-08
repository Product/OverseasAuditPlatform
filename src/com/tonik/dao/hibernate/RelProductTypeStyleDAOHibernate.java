package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.model.ProductTrivialName;
import com.tonik.model.RelProductTypeStyle;

/**
 * @spring.bean id="RelProductTypeStyleDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RelProductTypeStyleDAOHibernate extends BaseDaoHibernate implements IRelProductTypeStyleDAO
{
    @Override
    public List<Object[]> getRelProductTypeStylePaging(final int pageIndex, final int pageSize,
            final Long productTypeId)
    {
        try
        {
            StringBuilder sb = new StringBuilder("SELECT R.ID AS id, CASE PT.PRODUCTTYPE_LEVEL WHEN 3 THEN "
                    + "PT.PRODUCTTYPE_NAME ELSE '' END AS thirdLevelType, CASE PT.PRODUCTTYPE_LEVEL "
                    + "WHEN 3 THEN t.fatherName WHEN 2 THEN PT.PRODUCTTYPE_NAME ELSE '' "
                    + "END AS secondLevelType, CASE PT.PRODUCTTYPE_LEVEL WHEN 3 THEN t.grandFatherName "
                    + "WHEN 2 THEN t.fatherName ELSE PT.PRODUCTTYPE_NAME END AS firstLevelType, "
                    + "PS.PRODUCTSTYLE_NAME AS styleName, R.RELATE_TIME AS relateTime, "
                    + "R.IS_EANABLE AS status FROM REL_PRODUCTTYPE_STYLE AS R "
                    + "LEFT JOIN PRODUCTSTYLE AS PS ON PS.PRODUCTSTYLE_ID = R.PRODUCTSTYLE_ID "
                    + "LEFT JOIN PRODUCTTYPE AS PT ON PT.PRODUCTTYPE_ID = R.PRODUCTTYPE_ID "
                    + "LEFT JOIN ( SELECT tPT.PRODUCTTYPE_ID AS id, tPT.PRODUCTTYPE_NAME AS name, "
                    + "father.PRODUCTTYPE_ID AS fatherId, father.PRODUCTTYPE_NAME AS fatherName, "
                    + "grandFather.PRODUCTTYPE_ID AS grandFatherId, "
                    + "grandFather.PRODUCTTYPE_NAME AS grandFatherName FROM PRODUCTTYPE AS tPT "
                    + "LEFT JOIN PRODUCTTYPE AS father ON tPT.PRODUCTTYPE_PTID = father.PRODUCTTYPE_ID "
                    + "LEFT JOIN PRODUCTTYPE AS grandFather ON father.PRODUCTTYPE_PTID = grandFather.PRODUCTTYPE_ID "
                    + ") AS t ON t.id = PT.PRODUCTTYPE_ID ");
            if (productTypeId != null)
                sb.append("WHERE PT.PRODUCTTYPE_ID IN ( SELECT ID FROM F_GetProductTypeTree(" + productTypeId + ") ) ");
            sb.append("ORDER BY R.RELATE_TIME DESC");
            final String sql = sb.toString();
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(sql);
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
    public Integer getRelProductTypeStyleTotal(final Long productTypeId)
    {
        try
        {
            StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM REL_PRODUCTTYPE_STYLE ");
            if (productTypeId != null)
                sb.append("WHERE REL_PRODUCTTYPE_STYLE.PRODUCTTYPE_ID IN ( SELECT ID FROM F_GetProductTypeTree("
                        + productTypeId + ") ) ");
            final String sql = sb.toString();
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
    public void saveRelProductTypeStyle(RelProductTypeStyle relProductTypeStyle)
    {
        getHibernateTemplate().saveOrUpdate(relProductTypeStyle);
    }

    @Override
    public void removeRelProductTypeStyle(Long id)
    {
        getHibernateTemplate().delete(new RelProductTypeStyle().setId(id).setProductStyleId(0l).setProductTypeId(0l)
                .setRelateTime(new Date()).setStatus(0));
    }
}