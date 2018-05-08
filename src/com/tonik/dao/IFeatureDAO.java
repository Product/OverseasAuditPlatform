package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

public interface IFeatureDAO
{
    public List getObjects(Class clazz);

    public Object getObject(Class clazz, Serializable id);

    public void saveObject(Object o);

    public void removeObject(Class clazz, Serializable id);

}
