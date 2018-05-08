package com.tonik.model;

import java.util.Date;
import java.util.Set;

import com.tonik.Constant;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author liuxutong
 * 
 * @hibernate.class table="PRODUCTPROPERTYTYPE"
 */
public class ProductPropertyType
{
    private Long id;
    
    private String name;
    
    private Long ptid;
    
    private String remark;
    
    private UserInfo createPerson; 
    
    private Date createTime;
    
    private String fatherProductTypeName;
    
    private int level;
    
    private Set<ProductType> productTypes;
    
  //新增字段 from Ly
    private Set<Rules> rules;

    /**
     * @hibernate.id column="PROPERTYTYPE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="PROPERTYTYPE_NAME" type="string" length="500" not-null="false"
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
     * @hibernate.property column="PROPERTYTYPE_PTID" type="long" not-null="false"
     * @return Returns the title.
     */
    public Long getPtid()
    {
        return ptid;
    }
    /**
     * @param ptid The ptid to set.
     */
    public void setPtid(Long ptid)
    {
        this.ptid = ptid;
    }
    /**
     * @hibernate.property column="PROPERTYTYPE_REMARK" type="string" length="2000" not-null="false"
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
     * @hibernate.many-to-one column="PROPERTYTYPE_USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
     * @return Returns the createPerson.
     */
    public UserInfo getCreatePerson()
    {
        return createPerson;
    }
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
    }
    /**
     * @hibernate.property column="PROPERTYTYPE_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
  
   
    /**
     * @hibernate.property column="PROPERTYTYPE_LEVEL" type="int" not-null="false"
     * @return Returns the level.
     */  
    public int getLevel()
    {
        return level;
    }
    /**
     * @param level The level to set.
     */
    public void setLevel(int level)
    {
        this.level = level;
    }
    /**
     * @hibernate.set name="ProductTypes" table="PROPERTYTYPES" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTTYPE_ID" class="com.tonik.model.ProductType"
     * @hibernate.collection-key column="PROPERTYTYPE_ID"
     * @return Returns the productTypes.
     */
    public Set<ProductType> getProductTypes()
    {
        return productTypes;
    }
    /**
     * @param productTypes The productTypes to set.
     */
    public void setProductTypes(Set<ProductType> productTypes)
    {
        this.productTypes = productTypes;
    }
    public String getFatherProductTypeName()
    {
        return fatherProductTypeName;
    }

    public void setFatherProductTypeName(String fatherProductTypeName)
    {
        this.fatherProductTypeName = fatherProductTypeName;
    }
    
    public String getCreatePersonName(){
        if(createPerson != null)
            return createPerson.getRealName();
        else
            return null;
    }
    
    
    public String getFormatCreateTime(){
        if(createTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(createTime);
        return date;
        }
    }
    
  //from Ly
    /**
     * @hibernate.set name="rules" table="PRODUCTPROPERTYTYPERULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="PRODUCTPROPERTYTYPE_ID"
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