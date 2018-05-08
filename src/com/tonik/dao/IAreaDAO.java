package com.tonik.dao;

import java.util.List;

import com.tonik.model.Area;

public interface IAreaDAO
{
    public List<Area> getAreas();
    
    public void saveArea(Area area);
    
    public void removeArea(Long id);
    
    public Area getAreaById(Long id);
    
    public int getAreasTotal(String strQuery,Long countryId);
    
    public List<Area> getAreasPaging(int pageIndex,int pageSize,String strQuery,Long countryId);

    public Area getAreaByCountryId(Long id);
}
