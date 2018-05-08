package com.tonik.dao;

import java.io.Serializable;
import java.util.List;

import com.tonik.model.EvaluationIndex;

public interface IEvaluationManagementDAO extends IDAO
{
    void delEvaluationManagements(Integer type, String productType) throws Exception;

    List initEvaluationManagement(Integer type, String productType, String strQuery) throws Exception;
    
    void removeObject(Class clazz, Serializable id);
}
