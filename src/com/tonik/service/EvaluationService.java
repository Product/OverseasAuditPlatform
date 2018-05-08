package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IEvaluationDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.Evaluation;
import com.tonik.model.Website;

/**
 * @spring.bean id="EvaluationService"
 * @spring.property name="evaluationDAO" ref="EvaluationDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class EvaluationService  
{
    private IEvaluationDAO EvaluationDAO;
    private IUserDAO UserDAO;
    private IWebsiteDAO WebsiteDAO;
    public void setEvaluationDAO(IEvaluationDAO EvaluationDAO)
    {
        this.EvaluationDAO = EvaluationDAO;
    }
    
    public IUserDAO getUserDAO()
    {
        return UserDAO;
    }

    public void setUserDAO(IUserDAO userDAO)
    {
        UserDAO = userDAO;
    }



    public IWebsiteDAO getWebsiteDAO()
    {
        return WebsiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        WebsiteDAO = websiteDAO;
    }

    public IEvaluationDAO getEvaluationDAO()
    {
        return EvaluationDAO;
    }



    public List<Evaluation> EvaluationPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls= EvaluationDAO.getEvaluationPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Evaluation> es = new ArrayList<Evaluation>();
        for(Object[] obj:ls){
            Evaluation e = (Evaluation)obj[0];
            Website w = WebsiteDAO.getWebsite(e.getWebsite().getId());
            e.setWebsite(w);
            es.add(e);
        }
        return es; 
    }
    
    public String EvaluationTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(EvaluationDAO.getEvaluationTotal(strQuery, strStraTime, strEndTime));
    }

    public void SaveEvaluation(Evaluation e, String websiteId)
    {   
        Website web = WebsiteDAO.getWebsite(Long.parseLong(websiteId));
        
        e.setWebsite(web);
        EvaluationDAO.saveEvaluation(e);
    }

    public void DelEvaluation(Long id)
    {
        EvaluationDAO.removeObject(Evaluation.class, id);
    }

    public Evaluation GetEvaluationById(Long id)
    {
        Evaluation e = EvaluationDAO.getEvaluation(id);
        Website w = WebsiteDAO.getWebsite(e.getWebsite().getId());
        e.setWebsite(w);
        return e;
    }

    public void SaveEvaluation(Evaluation e)
    {
        EvaluationDAO.saveEvaluation(e); 
    }
    
    public String getEvaluationInfo(Evaluation e){
        String res = "";
        
        if(e.getWebsite() != null)
            res += "{\"Id\":\"" + e.getId() + "\",\"Name\":\"" + e.getWebsite().getName()
                + "\",\"Location\":\"" + e.getWebsite().getLocation()
                + "\",\"Style\":\"" + e.getWebsite().getWebStyleName()
                + "\",\"Address\":\"" + e.getWebsite().getAddress()
                + "\",\"Wid\":\"" + e.getWebsite().getId();
        
        res += "\",\"Score\":\"" + e.getScore() + "\",\"Status\":\"" + e.getEvaluationStatus()
                + "\",\"Remark\":\"" + e.getRemark() +"\",\"CreatePerson\":\"" + e.getCreatePersonName()
                + "\",\"CreateTime\":\"" + e.getFormatCreateTime() + "\"}";
        return res;
    }

}
