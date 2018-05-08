package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

import com.tonik.model.ProductTrivialName;

public interface IProductTrivialNameDAO
{

    List<ProductTrivialName> getProductTrivialNamePaging(int pageIndex, int pageSize, String productTrivialName,
            String productScientificName);

    void saveProductTrivialName(ProductTrivialName productTrivialName);

    void removeProductTrivialName(Long id);

    Object getObject(Class clazz, Serializable id);

    Integer getProductTrivialNameTotal(String productTrivialName, String productScientificName);

}
