package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description:  地区事件
 * </p>
 * @since OCT 30, 2015
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTEFFECTAREA"
 */
public class EventAffectedArea
{
    private Long id;//地区事件id
    
    private Long eventId;//对应的事件id
    
    private Long regionId;//对应的大区id
    
    private String regionName;//对应的大区名称
    
    private Long countryId;//对应的国家id
    
    private String countryName;//对应的国家名称
    

    private Long areaId;//对应的省、州id
    
    private String areaName;//对应的省、州名称

    /**
     * @hibernate.id column="EVENTEFFECTAREA_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENT_REGION_ID" type="long" not-null="false"
     * @return Returns the regionId.
     */
    public Long getRegionId()
    {
        return regionId;
    }

    public void setRegionId(Long regionId)
    {
        this.regionId = regionId;
    }

    /**
     * @hibernate.property column="EVENT_REGION_NAME" type="string" length="50" not-null="false"
     * @return Returns the regionName.
     */
    public String getRegionName()
    {
        return regionName;
    }
    
    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }
    
    /**
     * @hibernate.property column="EVENT_COUNTRY_ID" type="long" not-null="false"
     * @return Returns the countryId.
     */
    public Long getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Long countryId)
    {
        this.countryId = countryId;
    }

    /**
     * @hibernate.property column="EVENT_COUNTRY_NAME" type="string" length="50" not-null="false"
     * @return Returns the countryName.
     */
    public String getCountryName()
    {
        return countryName;
    }
    
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }
    

    /**
     * @hibernate.property column="EVENT_AREA_ID" type="long" unsaved-value="null" not-null="false"
     * @return Returns the areaId.
     */
    public Long getAreaId()
    {
        return areaId;
    }

    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }  
 
    /**
     * @hibernate.property column="EVENT_AREA_NAME" type="string" length="50" not-null="false"
     * @return Returns the areaName.
     */
    public String getAreaName()
    {
        return areaName;
    }
    
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }
}
