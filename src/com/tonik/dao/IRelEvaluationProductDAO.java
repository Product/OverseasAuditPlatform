package com.tonik.dao;

import java.util.List;

import com.tonik.model.RelEvaluationProduct;

public interface IRelEvaluationProductDAO
{

    List<Object[]> getRelEvaluationProductPaging(int pageIndex, int pageSize, Integer evaluationType, Long productType,
            String strQuery);

    Integer getRelEvaluationProductTotal(Integer evaluationType, Long productType, String strQuery);

    void saveRelEvaluationProduct(RelEvaluationProduct relEvaluationProduct);

    void removeRelEvaluationProduct(Long id);

    void delRelEvaluationProducts(Integer type, Long productTypeId, Long productId) throws Exception;

    List<Object[]> getRelEvaluationProductDetail(Integer evaluationType, Long productType, Long productId);

}
