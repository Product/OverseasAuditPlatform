package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 检测事件模块 model层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @hibernate.class table="DETECTINGEVENT"
 */
public class DetectingEvent
{
    private Long id;
      
    private Website website;//网站对象
    
    private String eventType;//事件类型
    
    private int productNum;//抽检样品数量
    
    private Date detectingDate;//检测时间
    
    private String remark;//备注

    private Long createPersonId;//创建人ID
    
    private String createPersonName;//创建人真实姓名
    
    private Date createTime;//创建时间
    

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="DETECTINGEVENT_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.many-to-one column="DETECTINGEVENT_WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }

    /**
     * @param website The website to set.
     */
    public void setWebsite(Website website)
    {
        this.website = website;
    }

    /**
     * @hibernate.property column="DETECTINGEVENT_TYPE" type="string" length="50" not-null="true"
     * @return Returns the eventType.
     */
    public String getEventType()
    {
        return eventType;
    }

    /**
     * @param eventType The eventType to set.
     */
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }
 
    /**
     * @hibernate.property column="DETECTINGEVENT_PRODUCTNUM" type="int" not-null="false"
     * @return Returns the productNum.
     */
    public int getProductNum()
    {
        return productNum;
    }

    /**
     * @param productNum The productNum to set.
     */
    public void setProductNum(int productNum)
    {
        this.productNum = productNum;
    }

    /**
     * @hibernate.property column="DETECTINGEVENT_REMARK" type="string" length="2000" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    /**
     * @hibernate.property column="DETECTINGEVENT_DATE" sql-type="Date" not-null="true"
     * @return Returns the detectingEndTime.
     */
    public Date getDetectingDate()
    {
        return detectingDate;
    }

    /**
     * 
     * @param detectingDate The detectingDate to set.
     */
    public void setDetectingDate(Date detectingDate)
    {
        this.detectingDate = detectingDate;
    }

    /**
     * @hibernate.property column="DETECTINGEVENT_CREATEPERSONID" type="long" unsaved-value="null"
     * @return Returns the createPersonId.
     */
    public Long getCreatePersonId()
    {
        return createPersonId;
    }

    /**
     * 
     * @param CreatePersonId The CreatePersonId to set.
     */
    public void setCreatePersonId(Long createPersonId)
    {
        this.createPersonId = createPersonId;
    }

    /**
     * @hibernate.property column="DETECTINGEVENT_CREATEPERSONNAME" type="string" length="50" not-null="false"
     * @return Returns the createPersonName.
     */
    public String getCreatePersonName()
    {
        return createPersonName;
    }

    /**
     * 
     * @param CreatePersonName The CreatePersonName to set.
     */
    public void setCreatePersonName(String createPersonName)
    {
        this.createPersonName = createPersonName;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="DETECTINGEVENT_CREATETIME" sql-type="Date" not-null="true"
     * @return Returns the createTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
}
