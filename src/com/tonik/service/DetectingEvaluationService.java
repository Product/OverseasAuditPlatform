package com.tonik.service;

import java.util.List;

import com.tonik.dao.IDetectingEvaluationDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.DetectingEvaluation;
import com.tonik.model.Website;
//检测评价模块 Service层
/**
 * @spring.bean id="DetectingEvaluationService"
 * @spring.property name="detectingEvaluationDAO" ref="DetectingEvaluationDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 */

public class DetectingEvaluationService
{
    private IDetectingEvaluationDAO DetectingEvaluationDAO;
    private IWebsiteDAO WebsiteDAO;

    public void setDetectingEvaluationDAO(IDetectingEvaluationDAO DetectingEvaluationDAO)
    {
        this.DetectingEvaluationDAO = DetectingEvaluationDAO;
    }

    public IWebsiteDAO getWebsiteDAO()
    {
        return WebsiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        WebsiteDAO = websiteDAO;
    }

    //获取符合条件的所有检测评价
    public List<DetectingEvaluation> DetectingEvaluationPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        List<DetectingEvaluation> ls = DetectingEvaluationDAO.getDetectingEvaluationPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    //获取符合条件的检测评价总数
    public String DetectingEvaluationTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(DetectingEvaluationDAO.getDetectingEvaluationTotal(strQuery, strStraTime, strEndTime));
    }

    //通过ID查找检测评价
    public DetectingEvaluation GetDetectingEvaluationById(long parseLong)
    {
        DetectingEvaluation de =DetectingEvaluationDAO.getDetectingEvaluation(parseLong);
        Website ws=WebsiteDAO.getWebsite(de.getWebsite().getId());
        de.setWebsite(ws);
        return de;
    }

    //通过ID删除检测评价
    public void DelDetectingEvaluation(long parseLong)
    {
        DetectingEvaluationDAO.removeDetectingEvaluation(this.GetDetectingEvaluationById(parseLong));
    }

    //保存检测评价
    public void SaveDetecting(DetectingEvaluation detectingEvaluation,String id)
    {
        Website ws = WebsiteDAO.getWebsite(Long.parseLong(id));
        detectingEvaluation.setWebsite(ws);

        DetectingEvaluationDAO.saveDetectingEvaluation(detectingEvaluation);
    }
}
