package com.tonik.dao;

import java.util.List;

import com.tonik.model.Country;
import com.tonik.model.Website;

public interface IWebsiteDAO
{
    public List<Website> getWebsite();

    public Website getWebsite(Long WebsiteId);

    public void saveWebsite(Website Website);

    public void removeWebsite(Website Website);
    public void removeWebsite(Long Id);
    

    public List<Object[]> getWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public List<Object[]> getOutWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getWebsiteTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<Object[]> getGatherWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getGatherWebsiteTotal(String strQuery, String strStraTime, String strEndTime);
    
    public int getWebsiteTotalByCountryId(Long countryid);
    
    public List<Object[]> getWebsiteListByCountryId(Long countryid);
    
    public List<Website> NewgetWebsiteListByCountryId(Long countryid);
    
    public Long getWebsiteTotal();
    
    //public List<Object[]> getWorldMapWebsiteTotal(Long[] ptl);
    
    //public List<Object[]> getWorldMapWebsiteNameList(Long[] ptl);

    //public List<Object[]> getWorldMapWebsiteNameListByCountry(Long[] ptl, Long id);

    public List<Object[]> getWorldMapWebsiteNameList(String ptl);

    public List<Object[]> getWorldMapWebsiteTotal(String ptl);

    public int getWebsiteTotalByProduct(String ptl);

    public int getWebsiteTotalByProductAndCountry(String ptl, Long id);

    public List<Object[]> getWebsiteNameLists(Country c, String start, String length, String order, String dir);

    public List<Object[]> getWebsiteNameListsByProduct(String ptl, Country c, String start, String length, String order, String dir);
    
    public List<Object[]> NewgetWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime,String webType);
    
    public List<Object[]> getNativeWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime,String webType,Long countryId);

    int NewgetWebsiteTotal(String strQuery, String strStraTime, String strEndTime,String webType);

    public Website getWebsiteByName(String name);

    public int NewgetOutWebsiteTotal(String strQuery, String strStraTime, String strEndTime, String webType);

    public List<Object[]> NewgetOutWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime, String webType);

    public int getOutWebsiteTotal(String strQuery, String strStraTime, String strEndTime);

    public List<Website> getWebsiteByLocation(String name);


}
