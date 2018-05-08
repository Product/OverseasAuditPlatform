package com.tonik.dao;

import java.util.List;

import com.tonik.model.Country;

public interface ICountryDAO
{
    public List<Country> getCountry();
    
    public int getCountryTotal(String strQuery,String strRegionId);
    
    public List<Country> getCountryPaging(int pageIndex,int pageSize,String strQuery,String strRegionId);
    
    public Country getCountryById(Long countryId);
    
    public void saveCountry(Country country);
    
    public void removeCountry(Long id);

    public Country getCountryByName(String country);
}
