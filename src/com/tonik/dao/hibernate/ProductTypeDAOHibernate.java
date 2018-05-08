package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductTypeDAO;
import com.tonik.model.Country;
import com.tonik.model.ProductType;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IEvaluationDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author zby
 * @spring.bean id="ProductTypeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class ProductTypeDAOHibernate extends BaseDaoHibernate implements IProductTypeDAO
{

    @Override
    public List<ProductType> getProductType()
    {
        return getHibernateTemplate().find("from ProductType");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductType getProductType(Long productTypeId)
    {
        try{
            final String hql = "from ProductType e left join e.createPerson left join e.propertyTypes where e.id="+productTypeId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (ProductType)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveProductType(ProductType productType)
    {
        getHibernateTemplate().saveOrUpdate(productType);
    }

    @Override
    public void removeProductType(ProductType productType)
    {
        getHibernateTemplate().delete(productType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getProductTypePaging(final int pageIndex, final int pageSize, final String strQuery, final String strStraTime,
            final String strEndTime, final int level)
    {
        try{
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from ProductType e left join e.createPerson left join e.propertyTypes where "
                    +"(e.name like :strQuery or e.remark like :strQuery) and e.level="+level
                    + "and e.createTime>=:strStraTime and e.createTime<=:strEndTime order by e.createTime desc";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
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
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public int getProductTypeTotal(String strQuery, String strStraTime, String strEndTime, int level)
    {
        try
        {
            Date st = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime);
            Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            final String hql = "from ProductType e left join e.createPerson left join e.propertyTypes where "
                    +"(e.name like :strQuery or e.remark like :strQuery) and e.level="+level
                    + "and e.createTime>=:strStraTime and e.createTime<=:strEndTime order by e.createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime)};

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
            
        } catch (Exception e)
        {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getChildrenProductType(Long productTypeId)
    {
        try{
            final String hql = "from ProductType e left join e.createPerson left join e.propertyTypes where e.ptid="+productTypeId;
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
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductType> getRootProductType()
    {
        try{
            final String hql = "from ProductType e where e.ptid=0";
            List<ProductType> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List<ProductType> list = query.list();
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
    public Integer getProductTypeMaxTotal()
    {
        try{
            final String hql = "select max(level) from ProductType";
            List<Object> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Integer)listTable.get(0);
        }catch(Exception e)
        {
            return null;
        }
    }
    //add by lxt
    //按国家统计商品分类数量
    @Override
    public int getProductTypeTotalByCountryId(Long countryid,int level)
    {
      
        try
        {
            String hql1="";
            if(level == 1)
                
                hql1 = "select distinct p.firstlevelType from Product p "
                        + "left join p.firstlevelType left join p.country left join p.area where p.country.id="+ countryid;
            
            else if(level == 2)
                
                hql1 = "select distinct p.secondlevelType from Product p "
                        + "left join p.secondlevelType left join p.country left join p.area where p.country.id="+ countryid;
            else
                
                hql1 = "select distinct p.thirdlevelType from Product p "
                        + "left join p.thirdlevelType left join p.country left join p.area where p.country.id="+ countryid;
            
            String hql = hql1;
                

          
            return this.getHibernateTemplate().find(hql).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    //add by lxt
    //按国家统计商品类别
    @Override
    public List<Object[]> getProductTypeListByCountryId(Long countryid,int level)
    {
        try
        {
            String hql1="";
            if(level == 1)
                
                hql1 = "select  p.firstlevelType.name, sum(p.sales) from Product p "
                        + "left join p.firstlevelType left join p.country left join p.area where p.country.id="+ countryid + " group by p.firstlevelType.name order by sum(p.sales) desc";
            
            else if(level == 2)
                
                hql1 = "select  p.secondlevelType.name, sum(p.sales) from Product p "
                        + "left join p.secondlevelType left join p.country left join p.area where p.country.id="+ countryid + " group by p.firstlevelType.name order by sum(p.sales) desc";
            else
                
                hql1 = "select  p.thirdlevelType.name, sum(p.sales) from Product p "
                        + "left join p.thirdlevelType left join p.country left join p.area where p.country.id="+ countryid + " group by p.firstlevelType.name order by sum(p.sales) desc";
            

            final String hql = hql1;
            
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
        } catch (Exception e)
        {
            return null;
        }
    }
   
    //获得世界地图中各个国家中所有的商品类别信息列表
    @Override
    public List<Object[]> getWorldMapProductTypeInfo(String ptl){
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select distinct PRODUCTTYPE_NAME, country_name from PRODUCTDEFINITION a left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where country is not null and country_name is not null and (select count(distinct(PRODUCTTYPE_NAME)) from PRODUCTDEFINITION b left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where a.COUNTRY = b.COUNTRY and a.GRADE < b.GRADE) < 10 order by country_name";
            }
            else
            {
                hql1 = "select distinct PRODUCTTYPE_NAME, country_name from PRODUCTDEFINITION a left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where country is not null and country_name is not null and (producttype_ptid in ("+ptl+") or producttype_id in ("+ptl+")) and (select count(distinct(PRODUCTTYPE_NAME)) from PRODUCTDEFINITION b left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                        +" where a.COUNTRY = b.COUNTRY and (producttype_ptid in ("+ptl+") or producttype_id in ("+ptl+")) and a.GRADE < b.GRADE) < 10 order by country_name";
            
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
    //add by lxt
    @Override
    public Long getProductTypeTotal()
    {
        try
        {
            String hql = "select count(*) from ProductType ";
            
            Long res = (Long)this.getHibernateTemplate().find(hql).get(0);
            return res != null ? res : (long)0;
        
        } catch (Exception e)
        {
            return  0L;
        }
    }

    @Override
    public List<Object[]> getWorldMapProductTypeTotal(String ptl)
    {
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select country, COUNTRY_NAME, count(distinct(producttype)) from PRODUCTDEFINITION left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country is not null group by country, COUNTRY_NAME";
            }
            else
            {
                hql1 = "select country, COUNTRY_NAME, count(distinct(producttype)) from PRODUCTDEFINITION a left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where country is not null and (PRODUCTTYPE_PTID in ("+ptl+") or producttype_id in("+ptl+")) group by country, COUNTRY_NAME";
            
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
    public Integer getProductTypeByCountry(Long id)
    {
        String sql = "select count(distinct(producttype)) from PRODUCTDEFINITION where COUNTRY="+id;
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public Integer getProductTypeByProduct(String ptl)
    {
        String sql = "select count(distinct(producttype)) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID" 
                +" where (PRODUCTTYPE in("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public Integer getProductTypeByProductAndCountry(String ptl, Long id)
    {
        String sql = "select count(distinct(producttype)) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID" 
                +" where (PRODUCTTYPE in("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and country = "+id;
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public List<Object[]> getProductTypeLists(Country c, String start, String len, String strOrder, String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = "select top("+len+")PRODUCTTYPE_NAME, PRODUCTTYPE_REMARK from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION where COUNTRY = "+c.getId()+") and PRODUCTTYPE_ID not in (select top("+start+")PRODUCTTYPE_ID from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION where COUNTRY = "+c.getId()+") order by "+strOrder+" "+dir+")  order by "+strOrder+" "+dir;
            }
            else
            {
                hql1 = "select top("+len+")PRODUCTTYPE_NAME, PRODUCTTYPE_REMARK from PRODUCTTYPE where PRODUCTTYPE_ID not in (select top("+start+")PRODUCTTYPE_ID from PRODUCTTYPE order by "+strOrder+" "+dir+")  order by "+strOrder+" "+dir;       
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
    public List<Object[]> getProductTypeListsByProduct(String ptl, Country c, String start, String len,
            String strOrder, String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = "select top("+len+")PRODUCTTYPE_NAME, PRODUCTTYPE_REMARK from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and COUNTRY = "+c.getId()+") and PRODUCTTYPE_ID not in (select top("+start+")PRODUCTTYPE_ID from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")) and COUNTRY = "+c.getId()+") order by "+strOrder+" "+dir+")  order by "+strOrder+" "+dir;
            }
            else
            {
                hql1 = "select top("+len+")PRODUCTTYPE_NAME, PRODUCTTYPE_REMARK from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))) and PRODUCTTYPE_ID not in (select top("+start+")PRODUCTTYPE_ID from PRODUCTTYPE where PRODUCTTYPE_ID in"
                        +" (select distinct(PRODUCTTYPE) from PRODUCTDEFINITION where (PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))) order by "+strOrder+" "+dir+")  order by "+strOrder+" "+dir;       
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
    public List<Object[]> getChildProductType(Long productTypeId)
    {
        try
        {
            final String sql = "SELECT PT.PRODUCTTYPE_ID AS id, "
                    + "PT.PRODUCTTYPE_NAME AS name, "
                    + "CASE ("
                    + "SELECT count(*) "
                    + "FROM PRODUCTTYPE "
                    + "WHERE "
                    + "PRODUCTTYPE_PTID = PT.PRODUCTTYPE_ID"
                    + ") "
                    + "WHEN 0 THEN 0 "
                    + "ELSE 1 "
                    + "END AS iSVisible "
                    + "FROM PRODUCTTYPE AS PT "
                    + "WHERE "
                    + "PT.PRODUCTTYPE_PTID = " + productTypeId;
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
    public List<Object[]> getChildProductTypeWithPDNum(Long productTypeId)
    {
        try
        {
            final String sql = " SELECT PT.PRODUCTTYPE_ID AS id, "
                    + "PT.PRODUCTTYPE_NAME AS name, "
                    + "CASE (SELECT count(*) "
                    + "FROM PRODUCTTYPE "
                    + "WHERE PRODUCTTYPE_PTID = PT.PRODUCTTYPE_ID ) "
                    + "WHEN 0 THEN 0 "
                    + "ELSE 1 "
                    + "END AS iSVisible, "
                    + "(SELECT COUNT(*) "
                    + "FROM ExpertRepository "
                    + "WHERE ExpertRepository.ProductType_Id IN "
                    + "(select ID from GetProductTypeInExpertRepository(PT.PRODUCTTYPE_ID))) AS num "
                    + "FROM PRODUCTTYPE AS PT "
                    + "WHERE PRODUCTTYPE_PTID = " + productTypeId
                    + "GROUP BY PT.PRODUCTTYPE_ID, PT.PRODUCTTYPE_NAME";
            Session session = getSession();
            List ls = session.createSQLQuery(sql).list();
            releaseSession(session);
            return ls;
        } catch (Exception e)
        {
            return null;
        }        
    }

}
