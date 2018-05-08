package com.tonik.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @hibernate.class table="REL_PRODUCTTYPE_STYLE"
 */
public class RelProductTypeStyle implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Long productTypeId;
        
    private Long productStyleId;
    
    private Date relateTime;
    
    private Integer status;

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public RelProductTypeStyle setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="PRODUCTTYPE_ID" type="long" not-null="true"
     * @return Returns the productTypeId.
     */
    public Long getProductTypeId()
    {
        return productTypeId;
    }

    public RelProductTypeStyle setProductTypeId(Long productTypeId)
    {
        this.productTypeId = productTypeId;
        return this;
    }

    /**
     * @hibernate.property column="PRODUCTSTYLE_ID" type="long" not-null="true"
     * @return Returns the productStyleId.
     */
    public Long getProductStyleId()
    {
        return productStyleId;
    }

    public RelProductTypeStyle setProductStyleId(Long productStyleId)
    {
        this.productStyleId = productStyleId;
        return this;
    }

    /**
     * @hibernate.property column="RELATE_TIME" sql-type="Date" not-null="true" 
     * @return Returns the relateTime.
     */
    public Date getRelateTime()
    {
        return relateTime;
    }

    public RelProductTypeStyle setRelateTime(Date relateTime)
    {
        this.relateTime = relateTime;
        return this;
    }

    /**
     * @hibernate.property column="IS_EANABLE" type="int" not-null="false" 
     * @return Returns the status.
     */
    public Integer getStatus()
    {
        return status;
    }

    public RelProductTypeStyle setStatus(Integer status)
    {
        this.status = status;
        return this;
    }
}

