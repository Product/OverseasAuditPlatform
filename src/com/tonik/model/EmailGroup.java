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
 * @hibernate.class table="EMAILGROUP"
 */
public class EmailGroup
{
    private Long id;
        
    private String name;//群组名称
    
    private Set<Email> emails;//邮件地址集
    
    private Date createTime;
    
    private UserInfo createPerson;
    
    /**
     * @hibernate.id column="EMAILGROUP_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EMAILGROUP_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.set name="emails" table="EMAILGROUPEMAIL" lazy="false" 
     * @hibernate.collection-many-to-many column="EMAIL_ID" class="com.tonik.model.Email"
     * @hibernate.collection-key column="EMAILGROUP_ID"
     * @return Returns the emails.
     */
    public Set<Email> getEmails()
    {
        return emails;
    }

    public void setEmails(Set<Email> emails)
    {
        this.emails = emails;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property name="EMAILGROUP_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * @hibernate.many-to-one column="USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
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
    
    //返回createPerson.RealName
    public String getCreatePersonName(){
        if(createPerson == null)
            return null;
        else
            return createPerson.getRealName();
    }
    
    public String getFormatCreateTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    }
}