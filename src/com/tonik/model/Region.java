package com.tonik.model;

import java.util.Set;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * 
 * @hibernate.class table="REGION"
 */
public class Region
{
    private Long id; //区域ID
    
    private String regionCode;
    
    private String name; //区域名称

    private Set<Country> countries;
    /**
     * @hibernate.id column="REGION_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
  
    
    /**
     * @hibernate.property column="REGION_REGIONCODE" type="string" length="50" not-null="false"
     * @return Returns the regionCode.
     */
    public String getRegionCode()
    {
        return regionCode;
    }

    /**
     * @param regionCode The regionCode to set.
     */
    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    /**
     * @hibernate.property column="REGION_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.set name="countrys" table="COUNTRYREGION" lazy="false"
     * @hibernate.collection-many-to-many column="Country_ID" class="com.tonik.model.Country"
     * @hibernate.collection-key column="REGION_ID"
     * @return Returns the countries.
     */
    public Set<Country> getCountries()
    {
        return countries;
    }

    /**
     * @param countries The countries to set.
     */
    public void setCountries(Set<Country> countries)
    {
        this.countries = countries;
    }
    
    
}
