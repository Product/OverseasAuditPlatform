package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响的品牌
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTEFFECTBRAND"
 */
public class EventAffectedBrand
{
    private Long id;//品牌事件id
    
    private Long eventId;//对应的事件id
    
    private Long brandId;//对应的品牌id
    
    private String brandName;//对应的品牌名称

    /**
     * @hibernate.id column="EVENTEFFECTBRAND_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENT_ID" type="long" unsaved-value="null"
     * @return Returns the eventId.
     */
    public Long getEventId()
    {
        return eventId;
    }

    /**
     * @param eventId The eventId to set.
     */
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    /**
     * @hibernate.property column="EVENT_BRAND_ID" type="long" not-null="false"
     * @return Returns the eventId.
     */
    public Long getBrandId()
    {
        return brandId;
    }

    /**
     * @param brandId The brandId to set.
     */
    public void setBrandId(Long brandId)
    {
        this.brandId = brandId;
    }

    /**
     * @hibernate.property column="EVENT_BRAND_NAME" type="string" length="50" not-null="false"
     * @return Returns the brandName.
     */
    public String getBrandName()
    {
        return brandName;
    }


    /**
     * @param brandName The brandName to set.
     */
    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }
    
}
