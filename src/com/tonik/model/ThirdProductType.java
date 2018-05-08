package com.tonik.model;

import java.io.Serializable;

/**
 * @hibernate.class table="ThirdProductType" lazy="true"
 */
public class ThirdProductType implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String name;
    
    private ThirdProductType parent;
    
    private String remark;
    
    private ProductType productType;
    
    private ThirdWebsite thirdWebsite;

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public ThirdProductType setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="Name" type="string" length="200" not-null="true"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    public ThirdProductType setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * @hibernate.many-to-one column="Parent" not-null="false" class="com.tonik.model.ThirdProductType"
     * @return Returns the parent.
     */
    public ThirdProductType getParent()
    {
        return parent;
    }

    public void setParent(ThirdProductType parent)
    {
        this.parent = parent;
    }

    /**
     * @hibernate.property column="Remark" type="string" length="200" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * @hibernate.many-to-one column="ProductType" not-null="false" class="com.tonik.model.ProductType"
     * @return Returns the productType.
     */
    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    /**
     * @hibernate.many-to-one column="ThirdWebsite" not-null="false" class="com.tonik.model.ThirdWebsite"
     * @return Returns the thirdWebsite.
     */
    public ThirdWebsite getThirdWebsite()
    {
        return thirdWebsite;
    }

    public void setThirdWebsite(ThirdWebsite thirdWebsite)
    {
        this.thirdWebsite = thirdWebsite;
    }

}
