package com.tonik.dao;

import java.util.List;

import com.tonik.model.Country;
import com.tonik.model.ProductType;

public interface IProductTypeDAO extends IDAO
{
    public List<ProductType> getProductType();

    public ProductType getProductType(Long productTypeId);

    public void saveProductType(ProductType productType);

    public void removeProductType(ProductType productType);
    
    public List<Object[]> getProductTypePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime, int level);

    public List<Object[]> getChildrenProductType(Long productTypeId);

    public List<ProductType> getRootProductType();

    public int getProductTypeTotal(String strQuery, String strStraTime, String strEndTime, int level);

    public Integer getProductTypeMaxTotal();
    
    public int getProductTypeTotalByCountryId(Long countryid,int level);
    
    public List<Object[]> getProductTypeListByCountryId(Long countryid,int level);
    
    public Long getProductTypeTotal();
    
    public List<Object[]> getWorldMapProductTypeInfo(String ptl);

    public List<Object[]> getWorldMapProductTypeTotal(String ptl);

    public Integer getProductTypeByCountry(Long id);

    public Integer getProductTypeByProduct(String ptl);

    public Integer getProductTypeByProductAndCountry(String ptl, Long id);

    public List<Object[]> getProductTypeLists(Country c, String start, String len, String strOrder, String dir);

    public List<Object[]> getProductTypeListsByProduct(String ptl, Country c, String start, String len,
            String strOrder, String dir);

    List<Object[]> getChildProductType(Long productTypeId);

    List<Object[]> getChildProductTypeWithPDNum(Long productTypeId);
}
