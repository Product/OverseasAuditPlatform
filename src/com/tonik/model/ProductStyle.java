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
 * @hibernate.class table="PRODUCTSTYLE"
 */
public class ProductStyle
{
    private Long id;
    
    private String name;
    
    private String remark;
    
    private String createPerson;
    
    private Date createTime;
  //from Ly
    private Set<Rules> rules;
  //from ly
    private Set<Epidemic> epidemics;
    

    /**
     * @hibernate.id column="PRODUCTSTYLE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @hibernate.property column="PRODUCTSTYLE_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    /**
     * @hibernate.property column="PRODUCTSTYLE_REMARK" type="string" length="5000" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="PRODUCTSTYLE_CREATEPERSON" type="string" length="50" not-null="false"
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
     * @hibernate.property column="PRODUCTSTYLE_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    //from Ly
    /**
     * @hibernate.set name="rules" table="PRODUCTSTYLERULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="PRODUCTSTYLE_ID"
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
    
    //from Ly
    /**
     * @hibernate.set name="epidemics" table="PRODUCTSTYLEEPIDEMIC" lazy="false" 
     * @hibernate.collection-many-to-many column="EPIDEMIC_ID" class="com.tonik.model.Epidemic"
     * @hibernate.collection-key column="PRODUCTSTYLE_ID"
     * @return Returns the epidemics.
     */

    public Set<Epidemic> getEpidemics()
    {
        return epidemics;
    }
    public void setEpidemics(Set<Epidemic> epidemics)
    {
        this.epidemics = epidemics;
    }
}
