package com.tonik.dao;

import java.util.List;

import com.tonik.model.RelProductMaterial;

public interface IRelProductMaterialDAO extends IDAO
{

    List<Object[]> getProductMaterialsByProductId(Long productId);

    void saveRelProductMaterial(RelProductMaterial relProductmaterial);

    RelProductMaterial getProductMaterial(Long id);

    void delProductMaterial(Long id);

}
