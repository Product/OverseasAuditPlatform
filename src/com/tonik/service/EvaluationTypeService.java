package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEvaluationTypeDAO;
import com.tonik.model.EvaluationType;

/**                 
 * @spring.bean id="EvaluationTypeService"
 * @spring.property name="evaluationTypeDAO" ref="EvaluationTypeDAO"
 */
public class EvaluationTypeService
{
    private IEvaluationTypeDAO evaluationTypeDAO;

    public IEvaluationTypeDAO getEvaluationTypeDAO()
    {
        return evaluationTypeDAO;
    }

    public void setEvaluationTypeDAO(IEvaluationTypeDAO evaluationTypeDAO)
    {
        this.evaluationTypeDAO = evaluationTypeDAO;
    }
    
    public void DelEvaluationType(Long Id)
    {
        evaluationTypeDAO.removeEvaluationType(Id);
    }
    
    public void SaveEvaluationType(EvaluationType evaluationType)
    {
        evaluationTypeDAO.saveEvaluationType(evaluationType);
    }

    public List<EvaluationType> EvaluationTypePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        List<EvaluationType> ls=evaluationTypeDAO.getEvaluationTypePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        
        return ls;
    }

    public String EvaluationTypeTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(evaluationTypeDAO.getEvaluationTypeTotal(strQuery, strStraTime, strEndTime));
    }
    
    public EvaluationType getEvaluationTypeById(Long Id)
    {
        return evaluationTypeDAO.getEvaluationType(Id);
    }
    
    //返回所有EvaluationType对象集
    public List<EvaluationType> GetEvaluationTypes()
    {
        return evaluationTypeDAO.getEvaluationType();
    }
    
    //返回单条EvaluationType字符串格式信息
    public String getEvaluationTypeInfo(EvaluationType evaluationType){
        String res = "";
        res += "{\"Id\":\"" + evaluationType.getId() + "\",\"Name\":\"" + evaluationType.getName()
            + "\",\"Remark\":\"" + evaluationType.getRemark()
            + "\",\"CreatePerson\":\"" + evaluationType.getCreatePersonName()
            + "\",\"CreateTime\":\"" + evaluationType.getFormatCreateTime() + "\"}";
        return res;
    }
}