package com.tonik.dao;

import java.util.List;

import com.tonik.model.Rules;

public interface IRulesDAO extends IDAO
{
    public List<Rules> getRules();

    public Rules getRules(Long rulesId);

    public void saveRules(Rules rules);

    public void removeRules(Rules rules);
    
    public void removeRules(Long rulesId);
    
    public List<Rules> getRulesPaging(int pageIndex,int pageSize,String strQuery,String strStraTime, String strEndTime);
    
    public int getRulesTotal(String strQuery, String strStraTime, String strEndTime);
}
