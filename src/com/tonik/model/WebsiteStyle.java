package com.tonik.model;

import java.util.Date;
import java.util.Set;

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
 * @hibernate.class table="WEBSITESTYLE"
 */
public class WebsiteStyle
{
    private Long id;

    private String name;
    
    private String remark;
    
    private String createPerson;
    
    private Date createTime;
    //新增字段 from Ly
    private Set<Rules> rules;
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="WEBSITESTYLE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.property column="WEBSITESTYLE_NAME" type="string" length="500" not-null="false"
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
     * @hibernate.property column="WEBSITESTYLE_REMARK" type="string" length="2000" not-null="false"
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
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="WEBSITESTYLE_CREATEPERSON" type="string" length="50" not-null="false"
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
     * @hibernate.property column="WEBSITESTYLE_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
  //from Ly
    /**
     * @hibernate.set name="rules" table="WEBSITESTYLERULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="WEBSITESTYLE_ID"
     * @return Returns the rules.
     */
    public Set<Rules> getRules()
    {
        return rules;
    }

    public void setRules(Set<Rules> rules)
    {
        this.rules = rules;
    }
}
