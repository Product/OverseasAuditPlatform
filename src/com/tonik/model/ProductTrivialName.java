package com.tonik.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @hibernate.class table="PRODUCT_TRIVIALNAME"
 */
public class ProductTrivialName implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String productTrivialName;
        
    private String productScientificName;
    
    private Date createTime;

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public ProductTrivialName setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="PRODUCT_TRIVIALNAME" type="string" length="50" not-null="false"
     * @return Returns the productTrivialName.
     */
    public String getProductTrivialName()
    {
        return productTrivialName;
    }

    public void setProductTrivialName(String productTrivialName)
    {
        this.productTrivialName = productTrivialName;
    }

    /**
     * @hibernate.property column="PRODUCT_SCIENTIFICNAME" type="string" length="50" not-null="false"
     * @return Returns the productScientificName.
     */
    public String getProductScientificName()
    {
        return productScientificName;
    }

    public void setProductScientificName(String productScientificName)
    {
        this.productScientificName = productScientificName;
    }

    /**
     * @hibernate.property column="CREATE_TIME" sql-type="Date" not-null="false" 
     * @return Returns the updateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}

