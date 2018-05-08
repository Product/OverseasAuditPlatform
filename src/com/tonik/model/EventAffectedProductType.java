package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响的商品类型
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTEFFECTPRODUCTTYPE"
 */
public class EventAffectedProductType
{
    private Long id;//商品类型事件id
    
    private Long eventId;//对应事件id
    
    private Long productFirstTypeId;//对应商品大类id
    
    private String productFirstTypeName;//对应商品大类名称
  
    private Long productSecondTypeId;//对应商品中类id
    
    private String productSecondTypeName;//对应商品中类名称   
 
    private Long productThirdTypeId;//对应商品小类id
    
    private String productThirdTypeName;//对应商品小类名称

    /**
     * @hibernate.id column="EVENTEFFECTPRODUCTTYPE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENT_ID" type="long" not-null="false"
     * @return Returns the eventId.
     */
    public Long getEventId()
    {
        return eventId;
    }

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTFIRSTTYPE_ID" type="long" not-null="false"
     * @return Returns the productFirstTypeId.
     */
    public Long getProductFirstTypeId()
    {
        return productFirstTypeId;
    }

    public void setProductFirstTypeId(Long productFirstTypeId)
    {
        this.productFirstTypeId = productFirstTypeId;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTFIRSTTYPE_NAME" type="string" length="50" not-null="false"
     * @return Returns the remark.
     */
    public String getProductFirstTypeName()
    {
        return productFirstTypeName;
    }

    public void setProductFirstTypeName(String productFirstTypeName)
    {
        this.productFirstTypeName = productFirstTypeName;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTSECONDTYPE_ID" type="long" not-null="false"
     * @return Returns the productSecondTypeId.
     */
    public Long getProductSecondTypeId()
    {
        return productSecondTypeId;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTSECONDTYPE_NAME" type="string" length="50" not-null="false"
     * @return Returns the remark.
     */
    public String getProductSecondTypeName()
    {
        return productSecondTypeName;
    }

    public void setProductSecondTypeName(String productSecondTypeName)
    {
        this.productSecondTypeName = productSecondTypeName;
    }

    public void setProductSecondTypeId(Long productSecondTypeId)
    {
        this.productSecondTypeId = productSecondTypeId;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTTHIRDTYPE_ID" type="long" not-null="false"
     * @return Returns the productThirdTypeId.
     */
    public Long getProductThirdTypeId()
    {
        return productThirdTypeId;
    }

    public void setProductThirdTypeId(Long productThirdTypeId)
    {
        this.productThirdTypeId = productThirdTypeId;
    }

    /**
     * @hibernate.property column="EVENT_PRODUCTTHIRDTYPE_NAME" type="string" length="50" not-null="false"
     * @return Returns the remark.
     */
    public String getProductThirdTypeName()
    {
        return productThirdTypeName;
    }

    public void setProductThirdTypeName(String productThirdTypeName)
    {
        this.productThirdTypeName = productThirdTypeName;
    }
}
