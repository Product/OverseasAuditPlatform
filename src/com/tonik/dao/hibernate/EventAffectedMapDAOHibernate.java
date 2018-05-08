package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedMapDAO;
import com.tonik.model.Product;
/**
 * <p>
 * Title: Tonik Candidates
 * </p>
 * <p>
 * Description: 事件纵览DashBoard  DAO层接口的实现
 * </p>
 * @since Oct 30, 2015
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedMapDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedMapDAOHibernate extends BaseDaoHibernate implements IEventAffectedMapDAO
{

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedMapDAO#getEventAffectedProductsTotal(java.lang.Long)
     * @Override
     * @desc: 获取事件影响的商品总数
     * @param eventId 事件id
     * @return int
     */
    public int getEventAffectedProductsTotal(Long[] eventIds){
       
        try{
            String hql="select distinct p from DetectingSuggest d left join d.product p left join d.event e where e.id in (:eventIds)";
            
            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public List<Product> doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                        query.setParameterList("eventIds", eventIds);
                    List<Product> list = query.list();
                    return list;
                }
            });           
            return listTable.size();
        }catch(Exception e)
        {
        return 0;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedMapDAO#getEventAffectedList(java.lang.Long[])
     * @Override
     * @desc 通过事件id数组和商品类别获得事件影响列表
     * @param eventIds 事件id数组
     * @param productTypeList 商品类别id数组
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List getEventAffectedList(Long[] eventIds,Long[] productTypeList)
    {
        try{
            String hql="select p.remark,w.name,p.location, e.name "
                    + "from DetectingSuggest d left join d.product p left join d.event e left join p.website w where "
                    + " e.id in (:eventIds)";
            if(productTypeList.length>0)
                hql+=" and (p.firstlevelType in  (:ptl) or p.secondlevelType in (:ptl) or p.thirdlevelType in (:ptl))";
                    
            final String finalHql=hql;
            
            List<Product> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(finalHql);
                    query.setParameterList("eventIds", eventIds);
                    if(productTypeList.length>0)
                        query.setParameterList("ptl", productTypeList);
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

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedMapDAO#getEventAffectedBrandsTotal(java.lang.Long[])
     * @Override
     * @desc 通过事件id数组获得事件影响品牌总数
     */
    public int getEventAffectedBrandsTotal(Long[] eventIds)
    {
        try
        {
            final String sql = "SELECT count(distinct PRODUCT_BRAND)  FROM PRODUCT p "
                    + "where p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId))";
            Session session = getSession();
            Query query = session.createSQLQuery(sql);
            query.setParameterList("eventId", eventIds);
            releaseSession(session);
            return (int) query.list().get(0);
        } catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedMapDAO#getEventAffectedWebsitesTotal(java.lang.Long[])
     * @Override
     * @desc 通过事件id数组获得事件影响网站总数
     */
    public int getEventAffectedWebsitesTotal(Long[] eventIds)
    {
        String sql = "select count(distinct p.WEBSITE_NAME) from PRODUCT p "
                + "where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventIds))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventIds", eventIds);
        releaseSession(session);
        return (int) query.list().get(0);
    }

    /**
     * (non-Javadoc)
     * @see com.tonik.dao.IEventAffectedMapDAO#getEventAffectedProdcutTypesTotal(java.lang.Long[])
     * @Override
     * @desc 通过事件id数组获得事件影响商品类别总数
     */
    public int getEventAffectedProdcutTypesTotal(Long[] eventIds)
    {
        String sql = "select count(distinct p.FIRST_TYPE) from PRODUCT p "
                + "where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventIds))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventIds", eventIds);
        releaseSession(session);
        return (int) query.list().get(0);
    }

    @Override
    public int getEventAffectedProdcutDefinitionTotal(Long[] eventIds)
    {
        String sql = "select count(distinct PRODEFINITION_ID) from PRODUCT where PRODUCT_ID in"
                +" (select PRODUCT from DETECTINGSUGGEST where DETECTINGSUGGEST.EVENT in (:eventIds))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        releaseSession(session);
        query.setParameterList("eventIds", eventIds);
        releaseSession(session);
        return (int) query.list().get(0);
    }
    
    /**
     * @desc: 通过事件id数组获得品牌列表
     * @param eventIds 事件id数组
     * @Override
     * return List
     */
    public List<Object[]> getBrandListByEventsId(Long[] eventIds)
    {
        String sql = "SELECT distinct c.COUNTRY_NAME,PRODUCT_BRAND  FROM PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                   + "where p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id数组获得Map上品牌分布数
     * @param eventIds 事件id数组
     * @Override
     * return List<Object[]>
     */
    public List<Object[]> getWorldMapBrandTotal(Long[] eventIds)
    {
        String sql = "SELECT c.COUNTRY_NAME,count(distinct PRODUCT_BRAND) as brand_counts FROM PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                   + " where p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) "
                   + " group by c.COUNTRY_NAME";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id数组获得网站列表
     * @param eventIds 事件id数组
     * @Override
     * return List<Object[]
     */
    public List<Object[]> getWebsiteListByEventsId(Long[] eventIds)
    {
        String sql =" select distinct c.COUNTRY_NAME, p.WEBSITE_NAME from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                   +" where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id列表获得Map上网站分布总数
     * @param eventIds 事件id数组
     * @Override
     * return List<Object[]>
     */
    public List<Object[]> getWorldMapWebsiteTotal(Long[] eventIds)
    {
        String sql = " select c.COUNTRY_NAME, count(distinct p.WEBSITE_NAME) from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                   + " where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) "
                   + " group by c.COUNTRY_NAME ";
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id数组和商品类别数组获得商品列表
     * @param eventIds 事件id数组
     * @param ptl 商品类别数组
     * @Override
     * return List<Object[]>
     */
    public List<Object[]> getProductListByEventsId(Long[] eventIds,Long[] ptl)
    {
        String sql ="";
        if(ptl.length>0){
            sql =" select distinct c.COUNTRY_NAME, p.PRODUCT_NAME from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                   +" where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) "
                   +" and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl))";
        }else{
            sql =" select distinct c.COUNTRY_NAME, p.PRODUCT_NAME from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                    +" where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) ";
        }
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        if(ptl.length>0){
            query.setParameterList("ptl", ptl);
        }
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id数组和商品类别id数组获得Map上商品分布总数
     * @param eventIds 事件id数组
     * @param ptl 商品类别数组
     * @Override
     * return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTotal(Long[] eventIds,Long[] ptl)
    {
        String sql ="";
        if(ptl.length>0){
            sql = "select c.COUNTRY_NAME, count(distinct p.PRODUCT_NAME) from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                + " where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) "
                + " and (p.FIRST_TYPE in (:ptl) or p.SECOND_TYPE in (:ptl) or p.THIRD_TYPE in (:ptl)) "
                + " group by c.COUNTRY_NAME ";
        }else{
            sql = "select c.COUNTRY_NAME, count(distinct p.PRODUCT_NAME) from PRODUCT p left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                    + " where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId)) "
                    + " group by c.COUNTRY_NAME ";
        }
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        if(ptl.length>0){
            query.setParameterList("ptl", ptl);
        }
        List list = query.list();
        releaseSession(session);
        return list;
    }

    /**
     * @desc: 通过事件id数组获得商品类别列表
     * @param eventIds 事件id数组
     * @Override
     * return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTypeInfo(Long[] eventIds)
    {
        final String sql = "select distinct c.COUNTRY_ID,c.COUNTRY_NAME, p.FIRST_TYPE,pt.PRODUCTTYPE_NAME,p.sales from PRODUCT p left join PRODUCTTYPE pt on p.FIRST_TYPE = pt.PRODUCTTYPE_ID left join COUNTRY c on p.COUNTRY = c.COUNTRY_ID "
                + "where  p.PRODUCT_ID in (select PRODUCT from DETECTINGSUGGEST where EVENT in (:eventId))";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        query.setParameterList("eventId", eventIds);
        List list = query.list();
        releaseSession(session);
        return list;
    }
    
    @Override
    public List<Object[]> getProductDefinitionListByEventsId(Long[] eventIds,Long[] ptl)
    {
        String sql ="", pdtl = "", etl = "";
        if(ptl.length>0){
            pdtl = " (PRODUCTTYPE in (:ptl) or PRODUCTTYPE_PTID in (:ptl)) and ";
        }
        if(eventIds.length > 0){
            etl = " where EVENT in (:eventId) ";
        }
        sql = "select COUNTRY_NAME, isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN) from PRODUCTDEFINITION left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                +" where "+pdtl+" PRODUCTDEFINITION_ID in (select distinct PRODEFINITION_ID from PRODUCT where PRODUCT_ID in (select distinct PRODUCT from DETECTINGSUGGEST "+etl+"))  order by COUNTRY_ID";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        if(eventIds.length > 0){
            query.setParameterList("eventId", eventIds);
        }
        if(ptl.length>0){
            query.setParameterList("ptl", ptl);
        }
        List list = query.list();
        releaseSession(session);
        return list;
    }
    
    @Override
    public List<Object[]> getWorldMapProductDefinitionTotal(Long[] eventIds,Long[] ptl)
    {
        String sql ="", pdtl = "", etl = "";
        if(ptl.length>0){
            pdtl = " (PRODUCTTYPE in (:ptl) or PRODUCTTYPE_PTID in (:ptl)) and ";
        }
        if(eventIds.length > 0){
            etl = " where EVENT in (:eventId) ";
        }
        sql = "select COUNTRY_NAME, count(PRODUCTDEFINITION_ID) from PRODUCTDEFINITION left join COUNTRY on COUNTRY = COUNTRY_ID left join PRODUCTTYPE on PRODUCTTYPE = PRODUCTTYPE_ID"
                +" where "+pdtl+" PRODUCTDEFINITION_ID in (select distinct PRODEFINITION_ID from PRODUCT where PRODUCT_ID in (select distinct PRODUCT from DETECTINGSUGGEST "+etl+"))  group by COUNTRY_NAME";

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        if(eventIds.length > 0){
            query.setParameterList("eventId", eventIds);
        }
        if(ptl.length>0){
            query.setParameterList("ptl", ptl);
        }
        List list = query.list();
        releaseSession(session);
        return list;
    }
    
    public List<Object[]> getEventInfo(String strQuery, String beginDate, String endDate, String ptl, Long eventType)
    {
        String tql = "";
        if(eventType == null && ptl == ""){
            tql = "select distinct EVENT.EVENT_ID, EVENT_NAME, MATERIAL_NAME, isnull(EVENT_COUNTRY_NAME+isnull(event_area_name, ''), '') as area, EVENT_BRAND_NAME, isnull(EVENT_PRODUCTTHIRDTYPE_NAME, ISNULL(EVENT_PRODUCTSECONDTYPE_NAME, EVENT_PRODUCTFIRSTTYPE_NAME)) as type, EVENT_WEBSITE_NAME," 
                    +" EVENT_BEGINDATE from EVENT left join EVENTAFFECTEDMATERIAL on EVENT.EVENT_ID = EVENTAFFECTEDMATERIAL.EVENT_ID left join EVENTEFFECTAREA on EVENTEFFECTAREA.EVENT_ID = EVENT.EVENT_ID"
                    +" left join EVENTEFFECTBRAND on EVENT.EVENT_ID = EVENTEFFECTBRAND.EVENT_ID left join EVENTEFFECTPRODUCTTYPE on EVENTEFFECTPRODUCTTYPE.EVENT_ID = EVENT.EVENT_ID left join EVENTEFFECTWEBSITE on EVENT.EVENT_ID = EVENTEFFECTWEBSITE.EVENT_ID"
                    +" where (Event.EVENT_NAME like '%"+strQuery+"%' or EVENT_REMARK like '%"+strQuery+"%') and EVENT_BEGINDATE >= '"+beginDate+"' and EVENT_BEGINDATE <= '"+endDate+"'"
                    +" order by EVENT_BEGINDATE";
        }else if(ptl != "" && eventType == null){
            tql = "select distinct EVENT.EVENT_ID, EVENT_NAME, MATERIAL_NAME, isnull(EVENT_COUNTRY_NAME+isnull(event_area_name, ''), '') as area, EVENT_BRAND_NAME, isnull(EVENT_PRODUCTFIRSTTYPE_NAME+isnull(EVENT_PRODUCTTHIRDTYPE_NAME, ISNULL(EVENT_PRODUCTSECONDTYPE_NAME, EVENT_PRODUCTFIRSTTYPE_NAME)) as type, EVENT_WEBSITE_NAME," 
                    +" EVENT_BEGINDATE from EVENT left join EVENTAFFECTEDMATERIAL on EVENT.EVENT_ID = EVENTAFFECTEDMATERIAL.EVENT_ID left join EVENTEFFECTAREA on EVENTEFFECTAREA.EVENT_ID = EVENT.EVENT_ID"
                    +" left join EVENTEFFECTBRAND on EVENT.EVENT_ID = EVENTEFFECTBRAND.EVENT_ID left join EVENTEFFECTPRODUCTTYPE on EVENTEFFECTPRODUCTTYPE.EVENT_ID = EVENT.EVENT_ID left join EVENTEFFECTWEBSITE on EVENT.EVENT_ID = EVENTEFFECTWEBSITE.EVENT_ID"
                    +" where (Event.EVENT_NAME like '%"+strQuery+"%' or EVENT_REMARK like '%"+strQuery+"%') and EVENT_BEGINDATE >= "+beginDate+" and EVENT_BEGINDATE <= "+endDate+" and (EVENT_PRODUCTFIRSTTYPE_ID in ("+ptl+") or EVENT_PRODUCTSECONDTYPE_ID in ("+ptl+") or EVENT_PRODUCTTHIRDTYPE_ID in ("+ptl+"))"
                    +" order by EVENT_BEGINDATE";
        }else if(ptl == "" && eventType != null){
            tql = "select distinct EVENT.EVENT_ID, EVENT_NAME, MATERIAL_NAME, isnull(EVENT_COUNTRY_NAME+isnull(event_area_name, ''), '') as area, EVENT_BRAND_NAME, isnull(EVENT_PRODUCTTHIRDTYPE_NAME, ISNULL(EVENT_PRODUCTSECONDTYPE_NAME, EVENT_PRODUCTFIRSTTYPE_NAME)) as type, EVENT_WEBSITE_NAME," 
                    +" EVENT_BEGINDATE from EVENT left join EVENTAFFECTEDMATERIAL on EVENT.EVENT_ID = EVENTAFFECTEDMATERIAL.EVENT_ID left join EVENTEFFECTAREA on EVENTEFFECTAREA.EVENT_ID = EVENT.EVENT_ID"
                    +" left join EVENTEFFECTBRAND on EVENT.EVENT_ID = EVENTEFFECTBRAND.EVENT_ID left join EVENTEFFECTPRODUCTTYPE on EVENTEFFECTPRODUCTTYPE.EVENT_ID = EVENT.EVENT_ID left join EVENTEFFECTWEBSITE on EVENT.EVENT_ID = EVENTEFFECTWEBSITE.EVENT_ID"
                    +" where EVENT.EVENT_TYPEID= "+eventType+" and (Event.EVENT_NAME like '%"+strQuery+"%' or EVENT_REMARK like '%"+strQuery+"%') and EVENT_BEGINDATE >= "+beginDate+" and EVENT_BEGINDATE <= "+endDate
                    +" order by EVENT_BEGINDATE";
        }else{
            tql = "select distinct EVENT.EVENT_ID, EVENT_NAME, MATERIAL_NAME, isnull(EVENT_COUNTRY_NAME+isnull(event_area_name, ''), '') as area, EVENT_BRAND_NAME, isnull(EVENT_PRODUCTTHIRDTYPE_NAME, ISNULL(EVENT_PRODUCTSECONDTYPE_NAME, EVENT_PRODUCTFIRSTTYPE_NAME)) as type, EVENT_WEBSITE_NAME," 
                    +" EVENT_BEGINDATE from EVENT left join EVENTAFFECTEDMATERIAL on EVENT.EVENT_ID = EVENTAFFECTEDMATERIAL.EVENT_ID left join EVENTEFFECTAREA on EVENTEFFECTAREA.EVENT_ID = EVENT.EVENT_ID"
                    +" left join EVENTEFFECTBRAND on EVENT.EVENT_ID = EVENTEFFECTBRAND.EVENT_ID left join EVENTEFFECTPRODUCTTYPE on EVENTEFFECTPRODUCTTYPE.EVENT_ID = EVENT.EVENT_ID left join EVENTEFFECTWEBSITE on EVENT.EVENT_ID = EVENTEFFECTWEBSITE.EVENT_ID"
                    +" where EVENT.EVENT_TYPEID= "+eventType+" and (Event.EVENT_NAME like '%"+strQuery+"%' or EVENT_REMARK like '%"+strQuery+"%') and EVENT_BEGINDATE >= "+beginDate+" and EVENT_BEGINDATE <= "+endDate+" and (EVENT_PRODUCTFIRSTTYPE_ID in ("+ptl+") or EVENT_PRODUCTSECONDTYPE_ID in ("+ptl+") or EVENT_PRODUCTTHIRDTYPE_ID in ("+ptl+"))"
                    +" order by EVENT_BEGINDATE";
        }
        final String sql = tql;

        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        List list = query.list();
        releaseSession(session);
        return list;
    }
}
