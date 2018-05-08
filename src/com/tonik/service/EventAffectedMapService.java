package com.tonik.service;

import java.util.Date;
import java.util.List;

import com.tonik.Constant;
import com.tonik.dao.IEventAffectedMapDAO;
import com.tonik.dao.IEventDAO;
import com.tonik.model.Event;

/**
 * @desc 事件影响DashBoard Service
 * @spring.bean id="eventAffectedMapService"
 * @spring.property name="eventDAO" ref="EventDAO"
 * @spring.property name="eventAffectedMapDAO" ref="EventAffectedMapDAO"
 */
public class EventAffectedMapService
{
    private IEventDAO EventDAO;
    private IEventAffectedMapDAO EventAffectedMapDAO;
  
    public IEventDAO getEventDAO()
    {
        return EventDAO;
    }

    public void setEventDAO(IEventDAO eventDAO)
    {
        EventDAO = eventDAO;
    }

    public IEventAffectedMapDAO getEventAffectedMapDAO()
    {
        return EventAffectedMapDAO;
    }

    public void setEventAffectedMapDAO(IEventAffectedMapDAO eventAffectedMapDAO)
    {
        EventAffectedMapDAO = eventAffectedMapDAO;
    }
 
    /**
     * @desc:获取符合条件的所有事件
     * @param strQuery
     * @param strType
     * @param strStartTime
     * @param strEndTime
     * @return List<Event>
     */
    public List<Event> getEvent(String strQuery,Long strType,String strStartTime,String strEndTime){
         return EventDAO.getEvents(strQuery,strType, strStartTime, strEndTime);
    }

    /**
     * @desc 通过事件id数组获得事件影响清单
     * @param eventIds 事件id数组
     * @return Object
     */
    public List<Object[]> getEventAffectedList(Long[] eventIds,Long[] productTypes)
    {
        return EventAffectedMapDAO.getEventAffectedList(eventIds,productTypes);
    }

    /**
     * @desc 通过事件id数组获得影响的品牌总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedBrandsTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getEventAffectedBrandsTotal(eventIds);
    }

    /**
     * @desc 通过事件id数组获得影响的网站总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedWebsitesTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getEventAffectedWebsitesTotal(eventIds);
    }

    /**
     * @desc 通过事件id数组获得影响的商品类别总数
     * @param eventIds 事件id数组
     * @return int
     */
    public int getEventAffectedProductTypesTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getEventAffectedProdcutTypesTotal(eventIds);
    }
    
    /**
     * @desc 通过事件id数组获得影响的商品总数
     * @param eventIds
     * @return int
     */
    public int getEventAffectedProductsTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getEventAffectedProductsTotal(eventIds);
    }

    /**
     * @desc 获得疫情事件列表
     * @param strQuery 关键字
     * @param epidemicTypeId 疫情类别
     * @param strStartTime 开始时间
     * @param strEndTime 结束时间
     * @return List<Event>
     */
    public List<Event> getEpidemicEvent(String strQuery, Long epidemicTypeId, String strStartTime, String strEndTime)
    {
        return EventDAO.getEpidemicEvent(strQuery,epidemicTypeId, strStartTime, strEndTime);
    }

    /**
     * @desc 通过事件id数组获得事件影响品牌列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getBrandListByEventsId(Long[] eventIds)
    {
        return EventAffectedMapDAO.getBrandListByEventsId(eventIds);
    }

    /**
     * @desc 通过事件id数组获得事件影响品牌总数
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapBrandTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getWorldMapBrandTotal(eventIds);
    }

    /**
     * @desc 通过事件id数组获得事件影响网站列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWebsiteListByEventsId(Long[] eventIds)
    {
        return EventAffectedMapDAO.getWebsiteListByEventsId(eventIds);
    }

    /**
     * @desc 通过事件id数组获得事件影响网站总数
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapWebsiteTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getWorldMapWebsiteTotal(eventIds);
    }

    /**
     * @desc 通过事件id数组和商品类别id数组获得事件影响商品列表
     * @param eventIds 事件id数组
     * @param ptl 商品类别id数组
     * @return List<Object[]>
     */
    public List<Object[]> getProductListByEventsId(Long[] eventIds, Long[] ptl)
    {
        return EventAffectedMapDAO.getProductListByEventsId(eventIds, ptl);
    }

    /**
     * @desc 通过事件id数组和商品类别id数组获得事件影响商品总数
     * @param eventIds 事件id数组
     * @param ptl 商品类别id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTotal(Long[] eventIds, Long[] ptl)
    {
        return EventAffectedMapDAO.getWorldMapProductTotal(eventIds, ptl);
    }

    /**
     * @desc 通过事件id数组获得事件影响商品类型列表
     * @param eventIds 事件id数组
     * @return List<Object[]>
     */
    public List<Object[]> getWorldMapProductTypeInfo(Long[] eventIds)
    {
        return EventAffectedMapDAO.getWorldMapProductTypeInfo(eventIds);
    }

    
    public String getEventAffectedMapJsonInfo(Object[] ds)
    {
        String res = "[\""+Constant.val(ds[0])+"\",\"" + Constant.val(ds[1])+ "\",\"" + Constant.val(ds[2])+ "\",\"" + Constant.val(ds[3])+"\"]";
        return res;
    }
    
    public List<Object[]> getEventsInfo(String strQuery, Long eventType, Date beginDate, Date endDate, String ptl){
        String strBeginDate = Constant.getFormatTime(beginDate);
        String strEndDate = Constant.getFormatTime(endDate);
        return EventAffectedMapDAO.getEventInfo(strQuery, strBeginDate, strEndDate, ptl, eventType);
    }

    public String getEventJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1]) + "\",\"" + Constant.val(obj[2]) 
                + "\",\"" + Constant.val(obj[3]) + "\",\"" + Constant.val(obj[4])+ "\",\"" + Constant.val(obj[5])
                + "\",\"" + Constant.val(obj[6]) + "\",\"" + "\"]";
        return res;
    }

    public List<Object[]> getWorldMapProductDefinitionTotal(Long[] eventIds, Long[] ptl)
    {
        return EventAffectedMapDAO.getWorldMapProductDefinitionTotal(eventIds, ptl);
    }

    public List<Object[]> getProductDefinitionListByEventsId(Long[] eventIds, Long[] ptl)
    {
        return EventAffectedMapDAO.getProductDefinitionListByEventsId(eventIds, ptl);
    }

    public int getEventAffectedProductDefinitionTotal(Long[] eventIds)
    {
        return EventAffectedMapDAO.getEventAffectedProdcutDefinitionTotal(eventIds);
    }
}
