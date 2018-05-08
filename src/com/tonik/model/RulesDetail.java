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
 * @hibernate.class table="RULESDETAIL"
 */
public class RulesDetail
{
    private Long id;
    
    private Long rulesId;//rule页id
    
    private String condition;//查询条件
    
    private String relationship;//查询关系
   
    private String value;//查询值
    
    private String createPerson;//创建人
    
    private Date createTime;//创建时间
    
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="RULESDETAIL_RULESID" type="long" unsaved-value="null"
     * @return Returns the rulesId.
     */
    public Long getRulesid()
    {
        return rulesId;
    }

    /**
     * @param rulesId The rulesId to set.
     */
    public void setRulesid(Long rulesId)
    {
        this.rulesId = rulesId;
    }
    
    /**
     * @hibernate.property column="RULESDETAIL_CONDITION" type="string" length="50" not-null="false"
     * @return Returns the condition.
     */
    public String getCondition()
    {
        return condition;
    }

    /**
     * @param condition The condition to set.
     */
    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    /**
     * @hibernate.property column="RULESDETAIL_RELATIONSHIP" type="string" length="50" not-null="false"
     * @return Returns the relationship.
     */
    public String getRelationship()
    {
        return relationship;
    }

    /**
     * @param relationship The relationship to set.
     */
    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }
    
    /**
     * @hibernate.property column="RULESDETAIL_VALUE" type="string" length="50" not-null="false"
     * @return Returns the value.
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value The value to set.
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="RULESDETAIL_CREATEPERSON" type="string" length="50" not-null="false"
     * @return Returns the createPerson.
     */
    public String getCreatePerson()
    {
        return createPerson;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="RULESDETAIL_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public String getFormatCreateTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    } 
    

}
