package com.tonik.service;

import java.util.List;

import com.tonik.dao.IMaterialDAO;
import com.tonik.model.Material;

/**
 * @spring.bean id="MaterialsService"
 * @spring.property name="materialDAO" ref="MaterialDAO"
 */
public class MaterialsService
{
    private IMaterialDAO MaterialDAO;

    public void setMaterialDAO(IMaterialDAO materialDAO)
    {
        this.MaterialDAO = materialDAO;
    }
    
    public String MaterialsTotal(String strQuery)
    {
        return Integer.toString(MaterialDAO.getMateialTotal(strQuery));
    }
   
    public List<Material> MaterialPaging(int pageIndex,int pageSize,String strQuery)
    {
        return MaterialDAO.getMaterialPaging(pageIndex, pageSize, strQuery);
    }
    
    public void SaveMaterial(Material material) 
    {
        MaterialDAO.SaveMaterial(material);
    }
    
    public void RemoveMaterial(Long id)
    {
        MaterialDAO.RemoveMaterial(id);
    }
    
    public Material getMaterialById(Long id)
    {
        return MaterialDAO.getMaterialById(id);
    }
    
    public int getMaterialsById(Long id)
    {
        return MaterialDAO.getMaterialsById(id);
    }
    
    public List<Material> getMaterial()
    {
        return MaterialDAO.getMaterial();
    }

    /**
     * @desc 通过原料类别id获得对应的所有原料
     * @param typeId 原料类别id
     * @return 原料列表
     */
    public List<Material> getMaterialListByTypeId(Long typeId)
    {
        return MaterialDAO.getMaterialByTypeId(typeId);
    }
}
