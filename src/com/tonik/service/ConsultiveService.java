package com.tonik.service;

import java.util.List;

import com.tonik.dao.IConsultiveDAO;
import com.tonik.model.Consultive;

/**
 * @spring.bean id="ConsultiveService"
 * @spring.property name="consultiveDAO" ref="ConsultiveDAO"
 */
public class ConsultiveService
{
    private IConsultiveDAO ConsultiveDAO;


    public void setConsultiveDAO(IConsultiveDAO ConsultiveDAO)
    {
        this.ConsultiveDAO = ConsultiveDAO;
    }

    public List<Consultive> ConsultivePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return ConsultiveDAO.getConsultivePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String ConsultiveTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(ConsultiveDAO.getConsultiveTotal(strQuery, strStraTime, strEndTime));
    }
    public String MyConsultiveTotal(Long consultiveId)
    {
        return Integer.toString(ConsultiveDAO.MyConsultiveTotal(consultiveId));
    }
    
    public List<Consultive> MyConsultivePaging(int pageIndex, int pageSize,Long consultiveId)
    {
        return ConsultiveDAO.getMyConsultivePaging(pageIndex, pageSize, consultiveId);
    }
    public List<Consultive> ComplainConsultivePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return ConsultiveDAO.getComplainConsultivePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String ComplainConsultiveTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(ConsultiveDAO.getComplainConsultiveTotal(strQuery, strStraTime, strEndTime));
    }
    
    public void SaveConsultive(Consultive cl)
    {
        ConsultiveDAO.saveConsultive(cl);
    }
    public Consultive getConsultiveById(Long id)
    {
        Consultive ce=ConsultiveDAO.getConsultive(id);
        return ce;
    }
//    public List<Consultive> ConsultivePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
//            String strEndTime)
//    {
//        Consultive ct=new Consultive();
//        
//        ct.setId(1l);
//        ct.setTitle("Hello");
//        ct.setContent("How to pay the goods's money?");
//        ct.setRootNod(1l);
//        ct.setParentNod(0l);
//        List<Consultive> ls=new ArrayList<Consultive>();
//        ls.add(ct);
//        return ls;
//    }
//
//    public String ConsultiveTotal(String strQuery, String strStraTime, String strEndTime)
//    {
//        return "1";
//    }
    
    public List<Consultive> FindAnswerDetailById(Long ID)
    {
        List<Consultive> ls=ConsultiveDAO.getDetailAnswer(ID);
        return ls;
    }
//    public  AnswerTotal(Long ID)
//    {
//        ConsultiveDAO.getDetailAnswer(ID);
//    }
}