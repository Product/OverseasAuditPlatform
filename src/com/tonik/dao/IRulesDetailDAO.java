package com.tonik.dao;

import java.util.List;

import com.tonik.model.RulesDetail;

public interface IRulesDetailDAO extends IDAO
{
    public List<RulesDetail> getRulesDetail();

    public List<RulesDetail> GetRulesDetailById(Long rulesId);

    public void saveRulesDetail(RulesDetail rulesDetail);

    public void removeRulesDetail(RulesDetail rulesDetail);
    
    public void removeRulesDetail(Long rulesDetailId);
    
    public List<RulesDetail> getRulesDetailPaging(int pageIndex,int pageSize,String strQuery,String strStraTime, String strEndTime);
    
    public int getRulesDetailTotal(String strQuery, String strStraTime, String strEndTime);

    public RulesDetail getRulesDetail(Long rulesDetailId);
}
