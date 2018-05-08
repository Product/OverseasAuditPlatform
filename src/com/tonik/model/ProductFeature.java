package com.tonik.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @hibernate.class table="PRODUCT_FEATURE"
 */
public class ProductFeature implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String productType = "";
    
    private Integer majorFeature;
    
    private String optionalFeatures;
    
    private Date updateTime;

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public ProductFeature setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="PRODUCT_TYPE" type="string" length="50" not-null="true" unique="true"
     * @return Returns the productType.
     */
    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    /**
     * @hibernate.property column="MAJOR_FEATURE" type="int" not-null="false"
     * @return Returns the majorFeature.
     */
    public Integer getMajorFeature()
    {
        return majorFeature;
    }

    public void setMajorFeature(Integer majorFeature)
    {
        this.majorFeature = majorFeature;
    }

    /**
     * @hibernate.property column="OPTIONAL_FEATURE" type="string" length="50" not-null="false"
     * @return Returns the optionalFeatures.
     */
    public String getOptionalFeatures()
    {
        return optionalFeatures;
    }

    public void setOptionalFeatures(String optionalFeatures)
    {
        this.optionalFeatures = optionalFeatures;
    }

    /**
     * @hibernate.property column="UPDATE_TIME" sql-type="Date" not-null="false" 
     * @return Returns the updateTime.
     */
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}
