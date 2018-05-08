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
 * @hibernate.class table="AREA"
 */
public class Area
{
    private Long id;
    
    private String areaCode;
    
    private String name;
    
    private Country country;
    
  //新增字段 from Ly
    private Set<Rules> rules;

    /**
     * @hibernate.id column="AREA_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="AREA_AREACODE" type="string" length="50" not-null="false"
     * @return Returns the areaCode.
     */
    public String getAreaCode()
    {
        return areaCode;
    }

    /**
     * @param areaCode The areaCode to set.
     */
    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    /**
     * @hibernate.property column="AREA_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="COUNTRY" not-null="false" lazy="false" class="com.tonik.model.Country"
     * @return Returns the country.
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * @param country The country to set.
     */
    public void setCountry(Country country)
    {
        this.country = country;
    }
    
  //from Ly
    /**
     * @hibernate.set name="rules" table="AREARULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="AREA_ID"
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
