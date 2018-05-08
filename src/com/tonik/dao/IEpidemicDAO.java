package com.tonik.dao;

import java.util.List;

import com.tonik.model.Epidemic;

public interface IEpidemicDAO extends IDAO
{
    public List<Object[]> getEpidemic();
    
    public Epidemic getEpidemic(Long EpidemicId);

    public void saveEpidemic(Epidemic Epidemic);

    public void removeEpidemic(Epidemic Epidemic);
    
    public void removeEpidemic(Long EpidemicId);
    
    public List<Epidemic> getEpidemicPaging(int pageIndex,int pageSize,String strQuery,String strStraTime, String strEndTime);

    public int getEpidemicTotal(String strQuery, String strStraTime, String strEndTime);
}
