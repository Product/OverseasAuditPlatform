package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

import com.tonik.model.ThirdProductType;

public interface IThirdProductTypeDAO
{
    List<Object[]> listThirdProductTypes(Long parent, Long thirdWebsite) throws Exception;

    void saveObject(Object o);

    void removeObject(Class clazz, Serializable id);
    
    int removeThirdProductTypes(Long thirdWebsite) throws Exception;
    
    Object getObject(Class clazz, Serializable id);
    
    List getObjects(Class clazz);
    
    Object loadObject(Class clazz, Serializable id);
    
    List<Object[]> listRelTypes(final int pageIndex, final int pageSize, final Long thirdWebsite, final String strQuery) throws Exception;

    Integer getlistRelTypesTotal(Long thirdWebsite, String strQuery);
}
