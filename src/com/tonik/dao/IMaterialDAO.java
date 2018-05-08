package com.tonik.dao;

import java.util.List;

import com.tonik.model.Material;

public interface IMaterialDAO
{
    public List<Material> getMaterial();
    
    public int getMateialTotal(String strQuery);
   
    public int getMaterialsById(Long id);
    
    public List<Material> getMaterialPaging(int pageIndex,int pageSize,String strQuery);
    
    public void SaveMaterial(Material material);
    
    public Material getMaterialById(Long materialId);
    
    public void RemoveMaterial(Long materialId);
    
    public List<Object[]> getMaterialsByMaterialTypeId(Long materialTypeId);

    /**
     * @desc 通过配方原料类别id获得原料列表
     * @param typeId 配方原料类别id
     * @return List<Material> 配方原料列表
     */
    public List<Material> getMaterialByTypeId(Long typeId);
}
