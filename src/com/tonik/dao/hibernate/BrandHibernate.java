package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IBrandDAO;
import com.tonik.model.Brand;
import com.tonik.model.Country;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IBrandDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="BrandDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class BrandHibernate extends BaseDaoHibernate implements IBrandDAO
{
    @SuppressWarnings("unchecked")
    public List<Brand> getBrandList()
    {
        return getHibernateTemplate().find("from Brand fetch all properties");
    }

    @Override
    public int getBrandTotal(final String strQuery,final String strStraTime,final String strEndTime)
    {
        try
        {
            String hql = "from Brand t left join t.area where (t.name_en like :strQuery or t.name_cn like :strQuery or t.name_other like :strQuery) "
                        +"and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";

            String[] params = { "strQuery","strStraTime", "strEndTime"};

            Object[] args = { "%" + strQuery + "%",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Brand> getBrandPaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,final String strEndTime)
    {
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            final String hql = "from Brand t left join fetch t.area left join fetch t.country where (t.name_en like :strQuery or t.name_cn like :strQuery or t.name_other like :strQuery) "
                             + "and t.createTime>=:strStraTime and t.createTime<=:strEndTime order by t.createTime desc";
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
    public void SaveBrand(Brand brand)
    {
        getHibernateTemplate().saveOrUpdate(brand);
    }

    @Override
    public Brand getBrandById(Long brandId)
    {
        return (Brand)getHibernateTemplate().find(
                "from Brand t left join fetch t.area left join fetch t.country where t.id=?",
                new Object[] { brandId}).get(0);
    }

    @Override
    public void RemoveBrand(Long brandId)
    {
        getHibernateTemplate().delete(getBrandById(brandId));
    }
    
    
    //add by lxt
    //按国家统计品牌总数
    @Override
    public int getBrandTotalByCountryId(Long countryid)
    {
        try
        {
            String hql = "from Brand b left join b.country where b.country.id = " + countryid + " order by b.marketShare desc, b.popularity desc";

          
            return this.getHibernateTemplate().find(hql).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    //add by lxt
    //按国家统计品牌信息
    @Override
    public List<Object[]> getBrandListByCountryId(Long countryid)
    {
        try
        {
           

            final String hql = "from Brand b left join b.country where b.country.id = " + countryid + " order by b.marketShare desc, b.popularity desc";
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
    //add by lxt
    //获得各个国家的品牌总数信息
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getWorldMapBrandTotal(String ptl)
    {
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select COUNTRY, COUNTRY_NAME, count(BRAND_ID) from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID where country is not null and country_name is not null group by COUNTRY, COUNTRY_NAME";
            }
            else
            {
                hql1 = "select country, country_name, count(BRAND_ID) from BRAND left join country on country=country_id where brand_id in "
                        +"(select distinct brand from productdefinition left join PRODUCTTYPE on PRODUCTTYPE=PRODUCTTYPE_ID where producttype in ("+ptl+") or producttype_ptid in ("+ptl+")) group by country, COUNTRY_NAME";
            
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
    //add by lxt
    //获得世界地图中各个国家中的品牌名字列表
    @Override
    public List<Object[]> getWorldMapBrandNameList(String ptl){
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select COUNTRY_NAME, isnull(BRAND_NAMECN,BRAND_NAMEEN) as brand from BRAND a left join COUNTRY on COUNTRY_ID = COUNTRY"+
                        " where COUNTRY is not null and (select count(*) from BRAND b where a.COUNTRY = b.COUNTRY and a.POPULARITY < b.POPULARITY) < 10 order by COUNTRY";
            }
            else
            {
                hql1 = "select COUNTRY_NAME, isnull(BRAND_NAMECN,BRAND_NAMEEN) as brand from BRAND a left join COUNTRY on COUNTRY_ID = COUNTRY"
                        +" where COUNTRY is not null and brand_id in (select distinct brand from productdefinition left join producttype on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))"
                        +" and (select count(*) from BRAND b left join COUNTRY on COUNTRY_ID = COUNTRY"
                        +" where COUNTRY is not null and a.COUNTRY = b.COUNTRY and a.POPULARITY < b.POPULARITY"
                        +" and brand_id in (select distinct brand from productdefinition left join producttype on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))) < 10";
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
    //add by lxt 
    //统计品牌总数
    @Override
    public Long getBrandTotal()
    {
        try
        {
            String hql = "select count(*) from Brand ";
            
            Long res = (Long)this.getHibernateTemplate().find(hql).get(0);
            return res != null ? res : (long)0;
           
        
        } catch (Exception e)
        {
            return  0L;
        }
    }

    @Override

    public Integer getBrandTotalByProduct(String ptl)
    {
        String sql = "select count(distinct(brand)) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+")";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override

    public Integer getBrandTotalByProductAndCountry(String ptl, Long id)
    {
        String sql = "select count(distinct(brand)) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID left join brand on brand=brand_id where brand.country = "+id+" and (PRODUCTTYPE in("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public List<Object[]> getBrandLists(Country c, String start, String len, String strOrder, String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = "select top("+len+")BRAND_NAMECN, BRAND_NAMEEN, COUNTRY_NAME from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country = "+c.getId()+" and BRAND_ID not in(select top("+start+")BRAND_ID from BRAND where country = "+c.getId()+" order by "+strOrder+" "+dir+") order by "+strOrder+" "+dir;
            }
            else
            {
                hql1 = "select top("+len+")BRAND_NAMECN, BRAND_NAMEEN, COUNTRY_NAME from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where BRAND_ID not in(select top("+start+")BRAND_ID from BRAND order by "+strOrder+" "+dir+") order by "+strOrder+" "+dir;      
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
    public List<Object[]> getBrandListsByProduct(String ptl, Country c, String start, String len, String strOrder,
            String dir)
    {
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = "select top("+len+")BRAND_NAMECN, BRAND_NAMEEN, COUNTRY_NAME from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country = "+c.getId()+" and BRAND_ID in (select distinct(BRAND) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))"
                        +" and BRAND_ID not in (select top("+start+")BRAND_ID from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where country = "+c.getId()+" and BRAND_ID in (select distinct(BRAND) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))" 
                        +" order by "+strOrder+" "+dir+") order by "+strOrder+" "+dir;
            }
            else
            {
                hql1 = "select top("+len+")BRAND_NAMECN, BRAND_NAMEEN, COUNTRY_NAME from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where BRAND_ID in (select distinct(BRAND) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))"
                        +" and BRAND_ID not in (select top("+start+")BRAND_ID from BRAND left join COUNTRY on COUNTRY = COUNTRY_ID"
                        +" where BRAND_ID in (select distinct(BRAND) from PRODUCTDEFINITION left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID where PRODUCTTYPE in ("+ptl+") or PRODUCTTYPE_PTID in ("+ptl+"))" 
                        +" order by "+strOrder+" "+dir+") order by "+strOrder+" "+dir;      
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

}
