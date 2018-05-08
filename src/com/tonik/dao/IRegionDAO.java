package com.tonik.dao;

import java.util.List;

import com.tonik.model.Region;

public interface IRegionDAO
{
    public List<Region> getRegion();
    
    public Region getRegion(Long id);
    
    public void savaRegion(Region region);
    
    public int getRegionTotal(String strQuery);
    
    public List<Region> getRegionPaging(int pageIndex,int pageSize,String strQuery);
}
