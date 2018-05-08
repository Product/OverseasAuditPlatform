package com.tonik.service;

import java.util.List;

import com.tonik.dao.IDetectingSuggestDAO;
import com.tonik.model.DetectingSuggest;

/**
 * @spring.bean id="DetectingSuggestService"
 * @spring.property name="detectingSuggestDAO" ref="DetectingSuggestDAO"
 */
public class DetectingSuggestService
{
    private IDetectingSuggestDAO DetectingSuggestDAO;

    public IDetectingSuggestDAO getDetectingSuggestDAO()
    {
        return DetectingSuggestDAO;
    }

    public void setDetectingSuggestDAO(IDetectingSuggestDAO detectingSuggestDAO)
    {
        DetectingSuggestDAO = detectingSuggestDAO;
    }

    public List getDetectingSuggestList(String strSql, int reRowCount)
    {
        return DetectingSuggestDAO.getDetectingSuggest(strSql, reRowCount);
    }

    public String getDashboardJson(String strSql, int reRowCount, String strKeyWord)
    {
        return DetectingSuggestDAO.getDashboardJson(strSql, reRowCount, strKeyWord);
    }
    
    public void saveDetectingSuggest(DetectingSuggest detectingSuggest)
    {
       DetectingSuggestDAO.saveDetectingSuggest(detectingSuggest); 
    }
    
    public void delFromDetectingSuggest(Long eventId)
    {
        DetectingSuggestDAO.delFromDetectingSuggest(eventId);
    }
    
    public String getDashEvaluationJson(String strSql, int reRowCount){
        return DetectingSuggestDAO.getEvaluationSuggest(strSql, reRowCount);
    }
    
    public List getEvaluationList(String strSql, int reRowCount){
        return DetectingSuggestDAO.getEvaluationRecord(strSql, reRowCount);
    }
    
    String val(Object obj){
        if(obj != null){
            return obj.toString();
        }else{
            return "";
        }
    }
    
    public String getDetectingSuggestJsonInfo(Object[] obj, int type){
        String res = "";
        if(type == 2){
            String strType = "诚信评价";
            res += "[\"" + val(obj[0]) + "\",\"" + val(obj[1])
                    + "\",\"" + val(obj[2]) + "\",\"" + strType
                    + "\",\"" + val(obj[3]) + "\",\"" + val(obj[4]) + "\"]";
        }else{
            String strType = "舆情关注";
            res += "[\"" + val(obj[0]) + "\",\"" + val(obj[1])
                    + "\",\"" + val(obj[2]) + "\",\"" + strType
                    + "\",\"" + val(obj[4]) + "\",\"" + val(obj[5])
                    + "\",\"" + val(obj[6]) + "\"]";
        }
        return res;
    }
}
