package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

public interface IDAO
{
    public List getObjects(Class clazz);

    public Object getObject(Class clazz, Serializable id);

    public void saveObject(Object o);

    public void removeObject(Class clazz, Serializable id);
    
    Object loadObject(Class clazz, Serializable id);
    
}// EOF
