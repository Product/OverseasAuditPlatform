package com.tonik.service;

import java.util.List;

import com.tonik.dao.ICountryDAO;
import com.tonik.dao.IRegionDAO;
import com.tonik.model.Country;
import com.tonik.model.Region;

/**
 * @spring.bean id="CountryService"
 * @spring.property name="countryDAO" ref="CountryDAO"
 * * @spring.property name="regionDAO" ref="RegionDAO"
 */
public class CountryService
{
    private ICountryDAO CountryDAO;
    private IRegionDAO RegionDAO;
    
    
    public void setCountryDAO(ICountryDAO countryDAO)
    {
        this.CountryDAO = countryDAO;
    }
    
    public void setRegionDAO(IRegionDAO regionDAO)
    {
        this.RegionDAO = regionDAO;
    }

    public void SaveCountry(Country country)
    {
        CountryDAO.saveCountry(country);
    }
    
    public void DelCountry(Long id)
    {
        CountryDAO.removeCountry(id);
    }
    
    public List<Country> getCountryList()
    {
        return CountryDAO.getCountry();
    }
    
    public String CountryTotal(String strQuery,String strRegionId)
    {
        return Integer.toString(CountryDAO.getCountryTotal(strQuery,strRegionId));
    }
    
    public List<Country> CountryPaging(int pageIndex, int pageSize, String strQuery,String strRegionId)
    {
        return CountryDAO.getCountryPaging(pageIndex, pageSize, strQuery,strRegionId);
    }
    
    public Country getCountryById(Long countryId)
    {
        return CountryDAO.getCountryById(countryId);
    }
    
    public Region getRegionById(Long id)
    {
        return RegionDAO.getRegion(id);
    }

    public Country getCountryByName(String country)
    {
        Country c = CountryDAO.getCountryByName(country);
        return c;
    }
}
