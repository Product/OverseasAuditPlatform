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
 * @author zby
 * 
 * @hibernate.class table="TESTSTYLE"
 */
public class TestStyle
{
    private Long id;
    private String name;
    private String remark;
    private int type;
    private UserInfo createPerson;
    private Date createTime;
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="TESTSTYLE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    
    
    /**
     * @hibernate.property column="TESTSTYLE_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="TESTSTYLE_REMARK" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="USERINFO_ID" not-null="false" cascade="none" lazy="false" class="com.tonik.model.UserInfo"
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
     * @hibernate.property column="TESTSTYLE_CREATETIME" sql-type="Date" not-null="false"
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
     * @hibernate.property column="TESTSTYLE_TYPE" type="int" not-null="false"
     * @return Returns the type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(int type)
    {
        this.type = type;
    } 
    
    public String getCreatePersonName(){
        if(createPerson == null)
            return null;
        else 
            return createPerson.getRealName();
    }
    
    public String getTypeName(){
        String res = "";
        if(type == 1)
            res = "网站";
        else if(type == 2)
            res = "商品";
        else
            res = "网站、商品";
        return res;
    }
    
    public String getFormatCreateTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    }
}
