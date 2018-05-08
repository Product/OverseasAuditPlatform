package com.tonik.dao;

import java.util.List;

import com.tonik.model.ProductStyle;

public interface IProductStyleDAO extends IDAO
{
    public List<ProductStyle> getProductStyle();

    public ProductStyle getProductStyle(Long productStyleId);

    public void saveProductStyle(ProductStyle productStyle);

    public void removeProductStyle(ProductStyle productStyle);
    
    public List<ProductStyle> getProductStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getProductStyleTotal(String strQuery, String strStraTime, String strEndTime);
    
    public void removeProductStyle(Long productStyleId);

    ProductStyle getProductStyleByName(String productStyleName);
    
    public List<Long> getStyleIdsByTypeIds(List<Long> typeIds);
    
    public List getObjects(Class clazz);   
}
