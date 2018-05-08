package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @hibernate.class table="DETECTINGSUGGEST"
 */
public class DetectingSuggest
{
    private Long id;
    
    private Product product;
    
    private Event event;    
    
    private String sourceType;
    
    private String sourceContent;
    
    private Date createTime;

    /**
     * @hibernate.id column="DETECTINGSUGGEST_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @hibernate.many-to-one column="PRODUCT" not-null="false" lazy="false" class="com.tonik.model.Product"
     * @return Returns the product.
     */
    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
  

    /**
     * @hibernate.property column="SOURCETYPE" type="string" not-null="false"
     * @return Returns the sourceType.
     */
    public String getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }

    /**
     * @hibernate.property column="SOURCECONTENT" type="string" not-null="false"
     * @return Returns the sourceContent.
     */
    public String getSourceContent()
    {
        return sourceContent;
    }

    public void setSourceContent(String sourceContent)
    {
        this.sourceContent = sourceContent;
    }

    /**
     * @hibernate.property column="CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the createTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @hibernate.many-to-one column="EVENT" not-null="false" lazy="false" class="com.tonik.model.Event"
     * @return Returns the event.
     */
    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
}
