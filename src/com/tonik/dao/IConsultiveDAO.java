package com.tonik.dao;

import java.util.List;

import com.tonik.model.Consultive;

public interface IConsultiveDAO extends IDAO
{
    public List<Consultive> getConsultive();

    public Consultive getConsultive(Long consultiveId);

    public void saveConsultive(Consultive consultive);

    public void removeConsultive(Consultive consultive);

    public List<Consultive> getConsultivePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime);

    public int getConsultiveTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<Consultive> getComplainConsultivePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime);

    public int MyConsultiveTotal(Long consultiveId);
    
    public List<Consultive> getMyConsultivePaging(int pageIndex, int pageSize,Long consultiveId);
    
    public int getComplainConsultiveTotal(String strQuery, String strStraTime, String strEndTime);
    
    public List<Consultive> getDetailAnswer(Long consultiveId);
}
