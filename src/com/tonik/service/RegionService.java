package com.tonik.service;

import java.util.List;

import com.tonik.dao.IRegionDAO;
import com.tonik.model.Region;

/**
 * @spring.bean id="RegionService"
 * @spring.property name="regionDAO" ref="RegionDAO"
 */
public class RegionService
{
    private IRegionDAO RegionDAO;

    public void setRegionDAO(IRegionDAO regionDAO)
    {
        this.RegionDAO = regionDAO;
    }
    
    public List<Region> getRegionList()
    {
        return RegionDAO.getRegion();
    }
    
    public void saveRegion(Region region)
    {
        RegionDAO.savaRegion(region);
    }
    
    public Region getRegionById(Long id)
    {
       return RegionDAO.getRegion(id);
    }
    
    public String RegionTotal(String strQuery)
    {
        return Integer.toString(RegionDAO.getRegionTotal(strQuery));
    }
    
    public List<Region> RegionPaging(int pageIndex,int pageSize,String strQuery)
    {
        return RegionDAO.getRegionPaging(pageIndex, pageSize, strQuery);
    }
}
