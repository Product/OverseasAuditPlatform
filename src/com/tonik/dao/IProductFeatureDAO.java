package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

import com.tonik.model.ProductFeature;

public interface IProductFeatureDAO
{
    public List<ProductFeature> getProductFeaturePaging(final int pageIndex, final int pageSize);

    Integer getProductFeatureTotal();

    void saveProductFeature(ProductFeature productFeature);

    void removeProductFeature(Long id);
    
    Object getObject(Class clazz, Serializable id);
}
