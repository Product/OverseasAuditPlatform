package com.tonik.service;

import java.util.List;

import com.tonik.dao.IMaterialTypeDAO;
import com.tonik.model.MaterialType;

/**
 * @spring.bean id="MaterialTypeService"
 * @spring.property name="materialTypeDAO" ref="MaterialTypeDAO"
 */

public class MaterialTypeService
{
    private IMaterialTypeDAO MaterialTypeDAO;

    public void setMaterialTypeDAO(IMaterialTypeDAO materialTypeDAO)
    {
        MaterialTypeDAO = materialTypeDAO;
    }
    
    public String MaterialTypeTotal(String strQuery)
    {
        return Integer.toString(MaterialTypeDAO.getMaterialTypeTotal(strQuery));
    }
    
    public List<MaterialType> MaterialTypePaging(int pageIndex, int pageSize, String strQuery)
    {
        return MaterialTypeDAO.getMaterialTypePaging(pageIndex, pageSize, strQuery);
    }
    
    public MaterialType getMaterialTypeById(Long id)
    {
        return MaterialTypeDAO.getMaterialTypeById(id);
    }
    
    public void SaveMaterialType(MaterialType materialtype)
    {
        MaterialTypeDAO.saveMaterialType(materialtype);
    }
    
    public void RemoveMaterialType(Long id)
    {
        MaterialTypeDAO.removeMaterialTpe(id);
    }
    
    public List<MaterialType> getMaterialTypeList()
    {
        return MaterialTypeDAO.getMaterialType();
    }
}
