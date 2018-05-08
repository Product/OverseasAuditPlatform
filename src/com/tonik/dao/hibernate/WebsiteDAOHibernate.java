package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.Country;
import com.tonik.model.Website;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IWebsiteDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="WebsiteDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class WebsiteDAOHibernate extends BaseDaoHibernate implements IWebsiteDAO
{
    @Override
    public List<Website> getWebsite()
    {
        return getHibernateTemplate().find("from Website");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Website getWebsite(Long websiteId)
    {
        try
        {
            final String hql = "from Website w left join w.webStyle left join w.country left join w.area where w.id = " + websiteId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return (Website)listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveWebsite(Website website)
    {
        getHibernateTemplate().saveOrUpdate(website);
    }

    @Override
    public void removeWebsite(Website website)
    {
        getHibernateTemplate().delete(website);
    }
    public void removeWebsite(Long websiteId)
    {
        getHibernateTemplate().delete(getWebsite(websiteId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getWebsitePaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Website w left join w.webStyle left join w.country left join w.area where w.websiteType = 0 and w.webStyle.id = 1 and (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle.name like :strQuery or w.address like :strQuery) "
                    + "and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.id";
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
        } catch (Exception e)
        {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getOutWebsitePaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Website w left join w.webStyle left join w.country left join w.area where w.websiteType = 0 and w.webStyle.id = 2 and (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle.name like :strQuery or w.address like :strQuery) "
                    + "and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.id";
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
        } catch (Exception e)
        {
            return null;
        }
    }
    //查找国内网站总数
    @Override
    public int getWebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from Website w where w.websiteType = 0 and w.webStyle.id = 1 and (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle like :strQuery or w.address like :strQuery) "
                    + "and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    //查找国外网站总数
    @Override
    public int getOutWebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from Website w where w.websiteType = 0 and w.webStyle.id = 2 and (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle like :strQuery or w.address like :strQuery) "
                    + "and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.createTime desc";

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
    public int NewgetWebsiteTotal(String strQuery, String strStraTime, String strEndTime,String webType)
    {
        String webTypeCase = ""; 
        if("0".equals(webType)){
            webTypeCase = "";
        }else if("1".equals(webType)){
            webTypeCase = "(websiteType = 0 OR websiteType = 1) AND ";
        }else if("2".equals(webType)){
            webTypeCase = "websiteType = 2 AND ";
        }
        try
        {
            String hql = "from Website where webStyle.id = 1 and "+webTypeCase+" (name like :strQuery or remark like :strQuery or location like :strQuery "
                    + "or webStyle like :strQuery or address like :strQuery) "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";

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
    public int NewgetOutWebsiteTotal(String strQuery, String strStraTime, String strEndTime,String webType)
    {
        String webTypeCase = ""; 
        if("0".equals(webType)){
            webTypeCase = "";
        }else if("1".equals(webType)){
            webTypeCase = "(websiteType = 0 OR websiteType = 1) AND ";
        }else if("2".equals(webType)){
            webTypeCase = "websiteType = 2 AND ";
        }
        try
        {
            String hql = "from Website where webStyle.id =2 and "+webTypeCase+" (name like :strQuery or remark like :strQuery or location like :strQuery "
                    + "or webStyle like :strQuery or address like :strQuery) "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getGatherWebsitePaging(final int pageIndex,final int pageSize,final String strQuery,final String strStraTime,
            final String strEndTime)
    {
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String hql = "from Website w left join w.webStyle left join w.country left join w.area where (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle.name like :strQuery or w.address like :strQuery) "
                    + "and w.gatherid=0 and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.id";
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
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int getGatherWebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            String hql = "from Website w left join w.webStyle left join w.country left join w.area where (w.name like :strQuery or w.remark like :strQuery or w.location like :strQuery "
                    + "or w.webStyle.name like :strQuery or w.address like :strQuery) "
                    + "and w.gatherid=0 and w.createTime>=:strStraTime and w.createTime<=:strEndTime order by w.createTime desc";

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
    public int getWebsiteTotalByCountryId(Long countryid)
    {
        try
        {
            String hql = "from Website w left join w.country where w.country.id = " + countryid + " order by w.comprehensiveScore desc, w.integrityDegree desc";

          
            return this.getHibernateTemplate().find(hql).size();
        } catch (Exception e)
        {
            return 0;
        }
    }
    
    @Override
    public List<Object[]> getWebsiteListByCountryId(Long countryid)
    {
        try
        {
           
          
            final String hql = "from Website w left join w.country left join w.webStyle left join w.area where w.country.id = " + countryid + " order by w.comprehensiveScore desc, w.integrityDegree desc";
           
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
    //获得世界地图中各个国家网站数量
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getWorldMapWebsiteTotal(String ptl)
    {
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select COUNTRY, COUNTRY_NAME, count(WEBSITE_ID) from website left join COUNTRY on COUNTRY = COUNTRY_ID where country is not null group by COUNTRY, COUNTRY_NAME";
            }
            else
            {
                hql1 = "select COUNTRY, COUNTRY_NAME, count(tab.WEBSITE) from website left join COUNTRY on COUNTRY = COUNTRY_ID right join ("+
                        " select distinct(WEBSITE) from product where first_type in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")"+
                        " ) as tab on WEBSITE = WEBSITE_ID where country is not null group by COUNTRY, COUNTRY_NAME";
            
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
    //获得世界地图中各个国家中的网站名字列表
    //TODO:每个国家显示10个
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getWorldMapWebsiteNameList(String ptl){
        try
        {
            String hql1 ="";
            if(ptl.length() == 0)
            {
                hql1 = "select COUNTRY_NAME, WEBSITE_NAME from WEBSITE a left join COUNTRY on COUNTRY = COUNTRY_ID where COUNTRY is not null and country_name is not null "+ 
                        " and (select count(*) from WEBSITE b where a.COUNTRY = b.COUNTRY and b.COMPREHENSIVE_SCORE > a.COMPREHENSIVE_SCORE) <= 10 order by COUNTRY, a.COMPREHENSIVE_SCORE desc";
            }
            else
            {
                hql1 = "select COUNTRY_NAME, WEBSITE_NAME from WEBSITE a left join COUNTRY on COUNTRY = COUNTRY_ID where country is not null and country_name is not null and WEBSITE_ID in ("+
                        " select distinct (WEBSITE) from PRODUCT where FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")"+
                        " ) and (select count(*) from WEBSITE  b where a.country = b.country and b.COMPREHENSIVE_SCORE > a.COMPREHENSIVE_SCORE and WEBSITE_ID in ("+
                        " select distinct (WEBSITE) from PRODUCT where FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+"))"+
                        " ) <= 10 order by COUNTRY, a.COMPREHENSIVE_SCORE desc";       
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
    public Long getWebsiteTotal()
    {
        try
        {
            String hql = "select count(*) from Website ";
            
            Long res = (Long)this.getHibernateTemplate().find(hql).get(0);
            return res != null ? res : (long)0;
        
        } catch (Exception e)
        {
            return  0L;
        }
    }

    @Override
    public int getWebsiteTotalByProduct(String ptl)
    {
        String sql = "select count(distinct(WEBSITE)) from product where first_type in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")";

        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public int getWebsiteTotalByProductAndCountry(String ptl, Long id)
    {
        String sql = "select count(*) from website right join ("+
                " select distinct(WEBSITE) from product where country="+id+" and (first_type in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")"+
                " )) as tab on WEBSITE = WEBSITE_ID";
        Session session = getSession();
        List ls = session.createSQLQuery(sql).list();
        releaseSession(session);
        return (Integer)ls.get(0);
    }

    @Override
    public List<Object[]> getWebsiteNameLists(Country c, String start, String length, String order, String dir){
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = "select top("+length+") WEBSITE_NAME, LOCATION, WEBSITESTYLE_NAME from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID "
                        +" where country = "+c.getId()+" and WEBSITE_ID not in"
                        +" (select top("+start+")WEBSITE_ID from WEBSITE where country = "+c.getId()+" order by "+order+" "+dir+")  order by "+order+" "+dir;
            }
            else
            {
                hql1 = "select top("+length+") WEBSITE_NAME, LOCATION, WEBSITESTYLE_NAME from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID" 
                        +" where WEBSITE_ID not in"
                        +" (select top("+start+")WEBSITE_ID from WEBSITE order by "+order+" "+dir+")  order by "+order+" "+dir;       
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
    public List<Object[]> getWebsiteNameListsByProduct(String ptl, Country c, String start, String length, String order, String dir){
        try
        {
            String hql1 ="";
            if(c != null)
            {
                hql1 = " select top("+length+") WEBSITE_NAME, LOCATION, WEBSITESTYLE_NAME from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID"
                        +" where WEBSITE_ID in (select distinct(WEBSITE) from PRODUCT where country="+c.getId()+" and (FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+"))) and"
                        +" WEBSITE_ID not in (select top("+start+")WEBSITE_ID from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID"
                        +" where country="+c.getId()+" and WEBSITE_ID in "
                        +" (select distinct(WEBSITE) from PRODUCT where (FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")))"
                        +" order by "+order+" "+dir+") order by "+order+" "+dir;
            }
            else
            {
                hql1 = " select top("+length+") WEBSITE_NAME, LOCATION, WEBSITESTYLE_NAME from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID"
                        +" where WEBSITE_ID in (select distinct(WEBSITE) from PRODUCT where FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+")) and"
                        +" WEBSITE_ID not in (select top("+start+")WEBSITE_ID from WEBSITE left join WEBSITESTYLE on WEBSITE_STYLE = WEBSITESTYLE_ID"
                        +" where WEBSITE_ID in "
                        +" (select distinct(WEBSITE) from PRODUCT where FIRST_TYPE in ("+ptl+") or SECOND_TYPE in ("+ptl+") or THIRD_TYPE in ("+ptl+"))"
                        +" order by "+order+" "+dir+") order by "+order+" "+dir;       
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
    public List<Object[]> NewgetWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime,String webType)
    {
        if("".equals(strQuery)){
            strQuery = "'%'";
        }else{
            strQuery = "'%" + strQuery + "%'";
        }
        String webTypeCase = ""; 
        if("0".equals(webType)){
            webTypeCase = "";
        }else if("1".equals(webType)){
            webTypeCase = "(w.WEBSITE_TYPE = 0 OR w.WEBSITE_TYPE = 1) AND ";
        }else if("2".equals(webType)){
            webTypeCase = "w.WEBSITE_TYPE = 2 AND ";
        }
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String sql = "SELECT w.WEBSITE_ID,w.LOCATION,w.WEBSITE_NAME,w.WEBSITE_CREATETIME, ws.WEBSITESTYLE_NAME,w.WEBSITE_TYPE "
                                +"FROM WEBSITE AS w "
                                +"LEFT JOIN WEBSITESTYLE AS ws ON ws.WEBSITESTYLE_ID = w.WEBSITE_STYLE "
                                +"LEFT JOIN COUNTRY AS c ON c.COUNTRY_ID = w.COUNTRY "
                                +"LEFT JOIN AREA AS a ON a.AREA_ID = w.AREA "
                                +"WHERE "+"w.WEBSITE_STYLE = 1 and "+webTypeCase+" (w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_REMARK like "+strQuery+" or w.LOCATION like "+strQuery+" or w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_ADDRESS like "+strQuery+") "
                                +"and w.WEBSITE_CREATETIME >= '"+strStraTime+"' and w.WEBSITE_CREATETIME<= '"+strEndTime+"'"
                                +" ORDER BY  w.WEBSITE_CREATETIME DESC";
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
            return null;
        }
    }

    @Override
    public List<Object[]> NewgetOutWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime,String webType)
    {
        if("".equals(strQuery)){
            strQuery = "'%'";
        }else{
            strQuery = "'%" + strQuery + "%'";
        }
        String webTypeCase = ""; 
        if("0".equals(webType)){
            webTypeCase = "";
        }else if("1".equals(webType)){
            webTypeCase = "(w.WEBSITE_TYPE = 0 OR w.WEBSITE_TYPE = 1) AND ";
        }else if("2".equals(webType)){
            webTypeCase = "w.WEBSITE_TYPE = 2 AND ";
        }
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String sql = "SELECT w.WEBSITE_ID,w.LOCATION,w.WEBSITE_NAME,w.WEBSITE_CREATETIME, ws.WEBSITESTYLE_NAME,w.WEBSITE_TYPE "
                                +"FROM WEBSITE AS w "
                                +"LEFT JOIN WEBSITESTYLE AS ws ON ws.WEBSITESTYLE_ID = w.WEBSITE_STYLE "
                                +"LEFT JOIN COUNTRY AS c ON c.COUNTRY_ID = w.COUNTRY "
                                +"LEFT JOIN AREA AS a ON a.AREA_ID = w.AREA "
                                +"WHERE "+"w.WEBSITE_STYLE = 2 and "+webTypeCase+" (w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_REMARK like "+strQuery+" or w.LOCATION like "+strQuery+" or w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_ADDRESS like "+strQuery+") "
                                +"and w.WEBSITE_CREATETIME >= '"+strStraTime+"' and w.WEBSITE_CREATETIME<= '"+strEndTime+"'"
                                +" ORDER BY  w.WEBSITE_CREATETIME DESC";
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
            return null;
        }
    }

    @Override
    public List<Website> NewgetWebsiteListByCountryId(Long countryid)
    {
        {
            // TODO Auto-generated method stub
            try
            {
               
              
                final String hql = "select w from Website w left join w.country where w.country.id = " + countryid + " order by w.createTime desc, w.comprehensiveScore desc, w.integrityDegree desc";
               
                List<Website> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                {
                    public Object doInHibernate(Session session) throws HibernateException, SQLException
                    {
                        Query query = session.createQuery(hql);
                     
                        List list = query.list();
                        return list;
                    }
                });
                System.out.println(listTable);

                return listTable;
            } catch (Exception e)
            {
                return null;
            }
        }
    }

    @Override
    public List<Object[]> getNativeWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime, String webType, Long countryId)
    {
        // TODO Auto-generated method stub
        if("".equals(strQuery)){
            strQuery = "'%'";
        }else{
            strQuery = "'%" + strQuery + "%'";
        }
        String webTypeCase = ""; 
        if("0".equals(webType)){
            webTypeCase = "";
        }else if("1".equals(webType)){
            webTypeCase = "(w.WEBSITE_TYPE = 0 OR w.WEBSITE_TYPE = 1) AND ";
        }else if("2".equals(webType)){
            webTypeCase = "w.WEBSITE_TYPE = 2 AND ";
        }
        
        try
        {
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);

            final String sql = "SELECT w.WEBSITE_ID,w.LOCATION,w.WEBSITE_NAME,w.WEBSITE_CREATETIME, ws.WEBSITESTYLE_NAME,w.WEBSITE_TYPE "
                                +"FROM WEBSITE AS w "
                                +"LEFT JOIN WEBSITESTYLE AS ws ON ws.WEBSITESTYLE_ID = w.WEBSITE_STYLE "
                                +"LEFT JOIN COUNTRY AS c ON c.COUNTRY_ID = w.COUNTRY "
                                +"LEFT JOIN AREA AS a ON a.AREA_ID = w.AREA "
                                +"WHERE"+"w.COUNTRY="+countryId+" and "+webTypeCase+" (w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_REMARK like "+strQuery+" or w.LOCATION like "+strQuery+" or w.WEBSITE_NAME like "+strQuery+" or w.WEBSITE_ADDRESS like "+strQuery+") "
                                +"and w.WEBSITE_CREATETIME >= '"+strStraTime+"' and w.WEBSITE_CREATETIME<="+strEndTime+"'"
                                +" ORDER BY w.WEBSITE_CREATETIME DESC";
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
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Website getWebsiteByName(String name)
    {
        try
        {
            final String hql = "from Website w left join w.webStyle left join w.country left join w.area where w.location like '%" + name + "%'";
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List<Object> list = query.list();
                    return list;
                }
            });
            return (Website)listTable.get(0)[0];
        } catch (Exception e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Website> getWebsiteByLocation(String location)
    {
        try
        {
            final String hql = "from Website w where w.location like '%" + location + "%'";
            List<Website> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List<Website> list = query.list();
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
