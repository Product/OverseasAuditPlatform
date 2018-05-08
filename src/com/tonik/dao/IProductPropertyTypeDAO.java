package com.tonik.dao;

import java.util.List;

import com.tonik.model.ProductPropertyType;

public interface IProductPropertyTypeDAO
{
    public List<ProductPropertyType> getProductPropertyType();
    
    public ProductPropertyType getProductPropertyType(Long productPropertyTypeId);

    public List<Object[]> getChildrenProductPropertyType(Long productPropertyTypeId);
    
    public List<ProductPropertyType> getRootProductPropertyType();
}
