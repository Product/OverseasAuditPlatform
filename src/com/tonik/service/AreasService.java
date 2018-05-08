package com.tonik.service;

import java.util.List;

import com.tonik.dao.IAreaDAO;
import com.tonik.model.Area;

/**
 * @spring.bean id="AreasService"
 * @spring.property name="areaDAO" ref="AreaDAO"
 */
public class AreasService
{
    private IAreaDAO AreaDAO;

    public void setAreaDAO(IAreaDAO areaDAO)
    {
        AreaDAO = areaDAO;
    }
    
    public void SaveArea(Area area)
    {
        AreaDAO.saveArea(area);
    }
    
    public void removeArea(Long id)
    {
        AreaDAO.removeArea(id);
    }
    
    public Area getAreaById(Long id)
    {
        return AreaDAO.getAreaById(id);
    }
    
    public String AreasTotal(String strQuery,Long countryId)
    {
        return Integer.toString(AreaDAO.getAreasTotal(strQuery,countryId));
    }
    
    public List<Area> AreasPaging(int pageIndex,int pageSize,String strQuery,Long countryId)
    {
        return AreaDAO.getAreasPaging(pageIndex, pageSize, strQuery,countryId);
    }
    
    public List<Area> getArea()
    {
        return AreaDAO.getAreas();
    }
}
