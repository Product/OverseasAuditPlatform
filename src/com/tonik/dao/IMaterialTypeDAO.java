package com.tonik.dao;

import java.util.List;

import com.tonik.model.MaterialType;

public interface IMaterialTypeDAO
{
    public List<MaterialType> getMaterialType();
    
    public int getMaterialTypeTotal(String strQuery);
    
    public List<MaterialType> getMaterialTypePaging(int pageIndex,int pageSize,String strQuery);
    
    public MaterialType getMaterialTypeById(Long id);
    
    public void saveMaterialType(MaterialType materialtype);
    
    public void removeMaterialTpe(Long id);
}
