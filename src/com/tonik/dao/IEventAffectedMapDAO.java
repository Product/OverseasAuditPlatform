package com.tonik.dao;

import java.util.List;

/**
 * @desc 事件纵览DashBoard DAO
 * @author thinvent
 *
 */
public interface IEventAffectedMapDAO
{      
    
    /**
     * @desc: 通过事件id获取受影响商品总数
     * @param eventId 事件id数组
     * @return int
     */
    public int getEventAffectedProductsTotal(Long[] eventIds);

    /**
     * @desc 通过事件id数组获得事件影响清单
     * @param eventIds 事件id数组
     * @return Object
     */
    public List<Object[]> getEventAffectedList(Long[] eventIds,Long[] productTypes);

    /**
     * @desc 通过事件id数组获得事件影响品牌总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long[] eventIds);

    /**
     * @desc 通过事件id数组获得事件影响网站总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedWebsitesTotal(Long[] eventIds);
  
    /**
     * @desc 通过事件id数组获得事件影响商品类别总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedProdcutTypesTotal(Long[] eventIds);

    /**
     * @desc 通过事件id数组获得品牌列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getBrandListByEventsId(Long[] eventIds);

    /**
     * @desc 通过事件id数组获得Map上品牌总数
     * @param eventIds
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapBrandTotal(Long[] eventIds);
 
    /**
     * @desc 通过事件id数组获得网站列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWebsiteListByEventsId(Long[] eventIds);

    /**
     * @desc 通过事件id数组获得Map上网站总数
     * @param eventIds
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapWebsiteTotal(Long[] eventIds);
  
    /**
     * @desc 通过事件id数组和商品类别id数组获得商品列表
     * @param eventIds 事件id数组
     * @param ptl 商品类别id数组
     * @return List<Object[]>
     */
    public List<Object[]> getProductListByEventsId(Long[] eventIds,Long[] ptl);

    /**
     * @desc 通过事件id数组和商品类别id数组获得Map上品牌总数
     * @param eventIds 事件id数组
     * @param ptl 商品类别id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTotal(Long[] eventIds,Long[] ptl);

    /**
     * @desc 通过事件id数组获得商品类别列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTypeInfo(Long[] eventIds);

    //public List<Object[]> getEventInfo(String strQuery, Date beginDate, Date endDate, String ptl, Long eventType);

    public List<Object[]> getEventInfo(String strQuery, String beginDate, String endDate, String ptl, Long eventType);

    public List<Object[]> getProductDefinitionListByEventsId(Long[] eventIds, Long[] ptl);

    public List<Object[]> getWorldMapProductDefinitionTotal(Long[] eventIds, Long[] ptl);

    int getEventAffectedProdcutDefinitionTotal(Long[] eventIds);
}
