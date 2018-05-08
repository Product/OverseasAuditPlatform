package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件概览
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENT"
 */
public class Event
{
    private Long id; //事件ID 
    
    private String name;//事件名称
    
    private Long typeId;//事件类型id
    
    private String typeName;//事件类型名称
    
    private Date beginDate;//事件开始时间，必填
    
    private Date endDate;//事件结束事件,可以不填
    
    private String remark;//备注
    
    private Epidemic epidemic;  //疾病疫情时

    /**
     * @hibernate.id column="EVENT_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENT_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */   
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.property column="EVENT_TYPEID" type="long" not-null="false"
     * @return Returns the typeId.
     */    
    public Long getTypeId()
    {
        return typeId;
    }

    /**
     * @param typeId the typeId to set.
     */
    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    /**
     * @hibernate.property column="EVENT_TYPENAME" type="string" length="50" not-null="false"
     * @return Returns the typeName.
     */ 
    public String getTypeName()
    {
        return typeName;
    }

    /**
     * @param typeName The typeName to set.
     */ 
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    /**
     * @hibernate.property column="EVENT_BEGINDATE" sql-type="Date" not-null="false"
     * @return Returns the beginDate.
     */
    public Date getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate The beginDate to set.
     */ 
    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @hibernate.property column="EVENT_ENDDATE" sql-type="Date" not-null="false"
     * @return Returns the endDate.
     */  
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate The endDate to set.
     */   
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @hibernate.property column="EVENT_REMARK" type="string" length="2000" not-null="false"
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
     * @hibernate.many-to-one column="EPIDEMIC" not-null="false" lazy="false" class="com.tonik.model.Epidemic"
     * @return Returns the epidemic.
     */
    public Epidemic getEpidemic()
    {
        return epidemic;
    }

    /**
     * @param epidemic The epidemic to set.
     */
    public void setEpidemic(Epidemic epidemic)
    {
        this.epidemic = epidemic;
    }
    
    
}
