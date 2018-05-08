package com.tonik.service;

import java.util.List;

import com.tonik.dao.IRulesDetailDAO;
import com.tonik.model.RulesDetail;

/**                 
 * @spring.bean id="RulesDetailService"
 * @spring.property name="rulesDetailDAO" ref="RulesDetailDAO"
 */
public class RulesDetailService
{
    private IRulesDetailDAO rulesDetailDAO;
    private Long Id;

    public IRulesDetailDAO getRulesDetailDAO()
    {
        return rulesDetailDAO;
    }

    public void setRulesDetailDAO(IRulesDetailDAO rulesDetailDAO)
    {
        this.rulesDetailDAO = rulesDetailDAO;
    }
    
    public void DelRulesDetail(Long Id)
    {
        rulesDetailDAO.removeRulesDetail(Id);
    }
    
    public void SaveRulesDetail(RulesDetail rulesDetail)
    {
        rulesDetailDAO.saveRulesDetail(rulesDetail);
    }
    
    public List<RulesDetail> GetRulesDetailById(Long Id)
    {
        return (List<RulesDetail>) rulesDetailDAO.GetRulesDetailById(Id);
    }

    public List<RulesDetail> RulesDetailPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,String strEndTime)
    {
        List<RulesDetail> ls=rulesDetailDAO.getRulesDetailPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    public String RulesDetailTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(rulesDetailDAO.getRulesDetailTotal(strQuery, strStraTime, strEndTime));
    }
    
    
    //返回单条规则子表信息
    public String getRulesDetailInfo(RulesDetail rds){
        String rrds="";
        rrds="{\"Id\":\"" + rds.getId() + "\",\"RulesId\":\"" + rds.getRulesid() + "\"," + "\"Condition\":\"" 
              + rds.getCondition() + "\",\"Relationship\":\"" + rds.getRelationship() + "\",\"Value\":\"" 
              + rds.getValue() + "\"," + "\"CreatePerson\":\"" + rds.getCreatePerson() + "\",\"CreateTime\":\""
              + rds.getCreateTime() + "\"}";
        return rrds;
    }

}
