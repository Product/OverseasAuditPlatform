package com.tonik.dao;

import java.util.List;

import com.tonik.model.Country;
import com.tonik.model.ProductDefinition;

public interface IProductDefinitionDAO
{
    public int getProductDefinitionTotal(String strQuery,String strStraTime,String strEndTime);
    
    public List<ProductDefinition> getProductDefinitionPaging(int pageIndex,int pageSize,String strQuery,String strStraTime,String strEndTime);
    
    public void SaveProductDefinition(ProductDefinition productdefinition);
    
    public ProductDefinition getProductDefinitionById(Long ProductDefinitionId);
    
    public void RemoveProductDefinition(Long ProductDefinitionId);
    
    public int ProductDefinitionByBrandTotal(Long id);
    
    public List<ProductDefinition> getProductDefinitionByBrand(Long brandId);
    
    public List<ProductDefinition> getProductDefinition();
    
    public List<Object[]> getProductDefinitionFeaturesPaging(final int pageIndex,final int pageSize);
    
    public int getProductDefTotal();

    public List<Object[]> getWorldMapProductDefinitionTotal(String ptl);

    public List<Object[]> getWorldMapProductDefinitionNameList(String ptl);

    public Integer ProductDefinitionTotalByCountry(Long id);

    public Integer getProductTypeTotal(Long id);

    public Integer getProductDefinitTotal();

    public Integer getProductDefinitionTotalByCountryId(Long id);

    public Integer getProductDefinitionTotalByType(String ptl);

    public Integer getProductDefinitionTotalByTypeAndCountry(String ptl, Long id);

    public List<Object[]> getProductDefinitionLists(Country c, String start, String len, String strOrder, String dir);

    public List<Object[]> getProductDefinitionListsByFeature(String ptl, Country c, String start, String len,
            String strOrder, String dir);

    public ProductDefinition getProductDefinitionByFeature(String first, String second);
    
}
