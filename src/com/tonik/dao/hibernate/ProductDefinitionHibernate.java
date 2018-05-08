package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductDefinitionDAO;
import com.tonik.model.Country;
import com.tonik.model.ProductDefinition;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IProductDefinitionDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="ProductDefinitionDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */

public class ProductDefinitionHibernate extends BaseDaoHibernate implements IProductDefinitionDAO
{

    @Override
    public List<ProductDefinition> getProductDefinition()
    {
        return getHibernateTemplate().find("from ProductDefinition");
    }
    @Override
    public int getProductDefTotal()
    {
        return getHibernateTemplate().find("from ProductDefinition").size();
    }

    @Override
    public int getProductDefinitionTotal(final String strQuery,final String strStraTime,final String strEndTime)
    {
        try
        {
            String hql = "from ProductDefinition where (name_en like :strQuery or name_cn like :strQuery "
                    + "or name_other like :strQuery or feature_one like :strQuery or feature_two like :strQuery "
                    + "or feature_three like :strQuery) "
                    +"and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery","strQuery","strQuery","strStraTime", "strEndTime"};

            Object[] args = { "%" + strQuery + "%","%" + strQuery + "%","%" + strQuery + "%",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<ProductDefinition> getProductDefinitionPaging(final int pageIndex,final int pageSize,final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            final String hql = "from ProductDefinition where (name_en like :strQuery or name_cn like :strQuery "
                    + "or name_other like :strQuery or feature_one like :strQuery or feature_two like :strQuery "
                    + "or feature_three like :strQuery) "
                    +"and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
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
    public void SaveProductDefinition(ProductDefinition productdefinition)
    {
        getHibernateTemplate().saveOrUpdate(productdefinition);
    }

    @Override
    public ProductDefinition getProductDefinitionById(Long ProductDefinitionId)
    {
        return (ProductDefinition)getHibernateTemplate().find(
                "from ProductDefinition t left join fetch t.brand left join fetch t.producttype left join fetch t.materials left join fetch t.country left join fetch t.area where t.id=?",
                new Object[] { ProductDefinitionId}).get(0);
    }

    @Override
    public void RemoveProductDefinition(Long ProductDefinitionId)
    {
        getHibernateTemplate().delete(getProductDefinitionById(ProductDefinitionId));
    }

    @Override
    public int ProductDefinitionByBrandTotal(Long id)
    {
        try
        {
            final String hql = "from ProductDefinition t left join fetch t.brand where t.brand.id=" +id;
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
    @Override
    public List<ProductDefinition> getProductDefinitionByBrand(Long brandId)
    {
        try
        {
            final String hql = "from ProductDefinition t left join fetch t.brand where t.brand.id=" + brandId;
            List list = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return list;

        } catch (Exception e)
        {
            return null;
        }
    }
    //add by lxt
    //返回ProductDefinition用于匹配的字段
    @Override
    public List<Object[]> getProductDefinitionFeaturesPaging(final int pageIndex,final int pageSize){
        try
        {
          
            final String hql = "select id, feature_one, feature_two, feature_three from ProductDefinition";
            List listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
            return null;
        }
    }
    @Override
    public List<Object[]> getWorldMapProductDefinitionTotal(String ptl)
    {
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select country, country_name, count(PRODUCTDEFINITION_id) from PRODUCTDEFINITION left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country is not null group by country, COUNTRY_NAME";
            }
            else
            {
                hql1 = "select country, country_name, count(PRODUCTDEFINITION_id) from PRODUCTDEFINITION left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where country is not null and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) group by country, COUNTRY_NAME";
            
            }
           String sql =hql1;
           
           Session session = getSession();
           List ls = session.createSQLQuery(sql).list();
           releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public List<Object[]> getWorldMapProductDefinitionNameList(String ptl)
    {
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select country_name, isnull(PRODUCTDEFINITION_NAMECN,PRODUCTDEFINITION_NAMEEN) from PRODUCTDEFINITION a left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country is not null and country_name is not null and (select count(*) from PRODUCTDEFINITION b where a.COUNTRY = b.COUNTRY and a.GRADE < b.GRADE"
                        +" ) < 10 order by country";
            }
            else
            {
                hql1 = "select country_name, isnull(PRODUCTDEFINITION_NAMECN,PRODUCTDEFINITION_NAMEEN) from PRODUCTDEFINITION a left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where country is not null and country_name is not null and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and (select count(*) from PRODUCTDEFINITION b left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID" 
                        +" where a.COUNTRY = b.COUNTRY and a.GRADE < b.GRADE and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))) < 10 order by country";
            }
           String sql = hql1;
           
           Session session = getSession();
           List ls = session.createSQLQuery(sql).list();
           releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public Integer ProductDefinitionTotalByCountry(Long id)
    {
        try
        {
           String sql = "select count(*) from productdefinition where country = "+id;
           
           Session session = getSession();
           List ls = session.createSQLQuery(sql).list();
           releaseSession(session);
            return (Integer) ls.get(0);
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public Integer getProductTypeTotal(Long id)
    {
        try
        {
           String sql = "select count(distinct(producttype)) from productdefinition where country = "+id;
           
           Session session = getSession();
           List ls = session.createSQLQuery(sql).list();
           releaseSession(session);
            return (Integer) ls.get(0);
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public Integer getProductDefinitTotal(){
        try
        {
            String hql = "select count(*) from productdefinition ";
            
            Session session = getSession();
            Integer res = (Integer)session.createSQLQuery(hql).list().get(0);
            releaseSession(session);
            return res != null ? res : (Integer)0;
        
        } catch (Exception e)
        {
            return  0;
        }
    }
    
    @Override
    public Integer getProductDefinitionTotalByCountryId(Long id)
    {
        String sql = " select count(*) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where country = "+id; 
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }
    @Override
    public Integer getProductDefinitionTotalByType(String ptl)
    {
        String sql = " select count(*) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")";
        
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }
    @Override
    public Integer getProductDefinitionTotalByTypeAndCountry(String ptl, Long id)
    {
        String sql = " select count(*) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where country = "+id+" and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))";
        
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }
    @Override
    public List<Object[]> getProductDefinitionLists(Country c, String start, String len, String strOrder, String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 =  "select top("+len+") isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN) as name, PRODUCTDEFINITION_PICTURE, ISNULL(PRODUCTDEFINITION_FEATUREONE, ISNULL(PRODUCTDEFINITION_FEATURETWO, PRODUCTDEFINITION_FEATURETHREE)) from PRODUCTDEFINITION"
                           +" where country="+c.getId()+" and PRODUCTDEFINITION_ID not in (select top("+start+")PRODUCTDEFINITION_ID from PRODUCTDEFINITION where country="+c.getId()+" order by "+ strOrder + " "+dir+") order by "+ strOrder + " "+dir;
            }
            else
            {
                hql1 = " select top("+len+") isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN) as name, PRODUCTDEFINITION_PICTURE, ISNULL(PRODUCTDEFINITION_FEATUREONE, ISNULL(PRODUCTDEFINITION_FEATURETWO, PRODUCTDEFINITION_FEATURETHREE)) from PRODUCTDEFINITION"
                        +" where PRODUCTDEFINITION_ID not in (select top("+start+")PRODUCTDEFINITION_ID from PRODUCTDEFINITION order by "+ strOrder + " "+dir+") order by "+ strOrder + " "+dir;       
            }
            String sql = hql1; 
            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public List<Object[]> getProductDefinitionListsByFeature(String ptl, Country c, String start, String len,
            String strOrder, String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = " select top("+len+") isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN) as name, PRODUCTDEFINITION_PICTURE, ISNULL(PRODUCTDEFINITION_FEATUREONE, ISNULL(PRODUCTDEFINITION_FEATURETWO, PRODUCTDEFINITION_FEATURETHREE))" 
                        +" from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where country = "+c.getId()+" and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and PRODUCTDEFINITION_ID not in" 
                        +" (select top("+start+")PRODUCTDEFINITION_ID from PRODUCTDEFINITION left join producttype on PRODUCTTYPE = PRODUCTTYPE_ID where country = "+c.getId()+" and (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))" 
                        +" order by "+strOrder+" "+dir+") order by "+strOrder+" "+dir;
            }
            else
            {
                hql1 = " select top("+len+") isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN) as name, PRODUCTDEFINITION_PICTURE, ISNULL(PRODUCTDEFINITION_FEATUREONE, ISNULL(PRODUCTDEFINITION_FEATURETWO, PRODUCTDEFINITION_FEATURETHREE))" 
                        +" from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and PRODUCTDEFINITION_ID not in" 
                        +" (select top("+start+")PRODUCTDEFINITION_ID from PRODUCTDEFINITION left join producttype on PRODUCTTYPE = PRODUCTTYPE_ID where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))" 
                        +" order by  "+strOrder+" "+dir+") order by "+strOrder+" "+dir;       
            }
            String sql = hql1; 
            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public ProductDefinition getProductDefinitionByFeature(String first, String second)
    {
        return (ProductDefinition)getHibernateTemplate().find(
                "from ProductDefinition t left join fetch t.brand left join fetch t.producttype left join fetch t.materials left join fetch t.country left join fetch t.area where t.feature_one=? or t.feature_two=? and t.feature_three=? or t.feature_four=?",
                new Object[] { first, first, second, second}).get(0);
    }
}
