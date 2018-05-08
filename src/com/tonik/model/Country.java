package com.tonik.model;

import java.io.Serializable;
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
 * @hibernate.class table="COUNTRY"
 */
public class Country implements Serializable
{
    private Long id;
    
    private String countryCode;
    
    private String name;
    
    private Set<Region> regions;
    
  //新增字段 from Ly
    private Set<Rules> rules;

    /**
     * @hibernate.id column="COUNTRY_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="COUNTRY_COUNTRYCODE" type="string" length="50" not-null="false"
     * @return Returns the countryCode.
     */
    public String getCountryCode()
    {
        return countryCode;
    }

    /**
     * @param countryCode The countryCode to set.
     */
    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    /**
     * @hibernate.property column="COUNTRY_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.set name="regions" table="COUNTRYREGION" lazy="false"
     * @hibernate.collection-many-to-many column="REGION_ID" class="com.tonik.model.Region"
     * @hibernate.collection-key column="COUNTRY_ID"
     * @return Returns the regions.
     */
    public Set<Region> getRegions()
    {
        return regions;
    }

    /**
     * @param region The region to set.
     */
    public void setRegions(Set<Region> regions)
    {
        this.regions = regions;
    }
    
  //from Ly
    /**
     * @hibernate.set name="rules" table="COUNTRYRULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="COUNTRY_ID"
     * @return Returns the rules.
     */
    public Set<Rules> getRules()
    {
        return rules;
    }

    public void setRules(Set<Rules> rules)
    {
        this.rules = rules;
    }
}
