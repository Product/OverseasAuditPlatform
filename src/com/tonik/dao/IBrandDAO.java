package com.tonik.dao;

import java.util.List;

import com.tonik.model.Brand;
import com.tonik.model.Country;

public interface IBrandDAO
{
    public List<Brand> getBrandList();
    
    public int getBrandTotal(String strQuery,String strStraTime,String strEndTime);
    
    public List<Brand> getBrandPaging(int pageIndex,int pageSize,String strQuery,String strStraTime,String strEndTime);
    
    public void SaveBrand(Brand brand);
    
    public Brand getBrandById(Long brandId);
    
    public void RemoveBrand(Long brandId);
    
    public List<Object[]> getBrandListByCountryId(Long countryid);
    
    public int getBrandTotalByCountryId(Long countryid);
    
    public Long getBrandTotal();
    
    //public List<Object[]> getWorldMapBrandTotal(Long[] ptl);
    
    //public List<Object[]> getWorldMapBrandNameList();

    //public List<Object[]> getWorldMapBrandNameListByCountry(Long[] ptl);

    public List<Object[]> getWorldMapBrandNameList(String ptl);

    public List<Object[]> getWorldMapBrandTotal(String ptl);

    /**
     * 通过产品类型获取相关的品牌总数
     * @param ptl
     * @return
     */
    public Integer getBrandTotalByProduct(String ptl);

    /**
     * 通过产品类型和国家id获取相关的品牌总数
     * @param ptl
     * @param id
     * @return
     */
    public Integer getBrandTotalByProductAndCountry(String ptl, Long id);

    public List<Object[]> getBrandLists(Country c, String start, String len, String strOrder, String dir);

    public List<Object[]> getBrandListsByProduct(String ptl, Country c, String start, String len, String strOrder,
            String dir);

}
