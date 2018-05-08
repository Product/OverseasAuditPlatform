package com.tonik.service;

import java.util.List;

import com.tonik.dao.IRelProductMaterialDAO;
import com.tonik.model.RelProductMaterial;

/**
 * @desc  Service
 * @spring.bean id="relProductMaterialService"
 * @spring.property name="relProductMaterialDAO" ref="RelProductMaterialDAO"
 */
public class RelProductMaterialService
{
    private IRelProductMaterialDAO relProductMaterialDAO;
    

    public IRelProductMaterialDAO getRelProductMaterialDAO()
    {
        return relProductMaterialDAO;
    }



    public void setRelProductMaterialDAO(IRelProductMaterialDAO relProductMaterialDAO)
    {
        this.relProductMaterialDAO = relProductMaterialDAO;
    }



    public List<Object[]> getProductMaterialsByProductId(Long productId)
    {
        return relProductMaterialDAO.getProductMaterialsByProductId(productId);
    }



    public void saveRelProductMaterial(RelProductMaterial relProductmaterial)
    {
        relProductMaterialDAO.saveRelProductMaterial(relProductmaterial);
    }



    public RelProductMaterial getProductMaterial(Long id)
    {
        return  relProductMaterialDAO.getProductMaterial(id);
    }



    public void delProductMaterial(Long id)
    {
        relProductMaterialDAO.delProductMaterial(id);
    }

}
