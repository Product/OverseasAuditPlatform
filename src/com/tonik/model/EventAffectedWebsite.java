package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响的网站
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTEFFECTWEBSITE"
 */
public class EventAffectedWebsite
{
    private Long id;//网站事件id
    
    private Long eventId;//对应的事件id
    
    private Long websiteId;//对应的网站id
    
    private String websiteName;//对应的网站名称

    /**
     * @hibernate.id column="EVENTEFFECTWEBSITE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENT_WEBSITE_ID" type="long" not-null="false"
     * @return Returns the websiteId.
     */
    public Long getWebsiteId()
    {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId)
    {
        this.websiteId = websiteId;
    }

    /**
     * @hibernate.property column="EVENT_WEBSITE_NAME" type="string" length="50" not-null="false"
     * @return Returns the websiteName.
     */
    public String getWebsiteName()
    {
        return websiteName;
    }

    public void setWebsiteName(String websiteName)
    {
        this.websiteName = websiteName;
    }
}
