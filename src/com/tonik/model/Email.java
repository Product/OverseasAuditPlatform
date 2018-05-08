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
 * @author yekai
 * 
 * @hibernate.class table="EMAIL"
 */
public class Email
{
    private Long id;
    
    private String name;//邮件名称
        
    private String emailAddress;//邮件地址
    
    private String company;//所属单位
    
    private String subscription;//订阅状态
    
    private Set<EmailGroup> emailGroups;//所属群组
    
    private String remark;//注释
    
    private UserInfo createPerson;
    
    private Date createTime;
    
    /**
     * @hibernate.id column="EMAIL_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EMAIL_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="EMAILADDRESS" type="string" length="50" not-null="false"
     * @return Returns the emailAddress.
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * @param emailAddress The emailAddress to set.
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * @hibernate.property column="COMPANY" type="string" length="50" not-null="false"
     * @return Returns the company.
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company The company to set.
     */
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    /**
     * @hibernate.property column="SUBSCRIPTION" type="string" length="50" not-null="false"
     * @return Returns the subscription.
     */
    public String getSubscription()
    {
        return subscription;
    }

    /**
     * @param subscription The subscription to set.
     */
    public void setSubscription(String subscription)
    {
        this.subscription = subscription;
    }
    
    /**
     * @hibernate.set name="emailGroups" table="EMAILGROUPEMAIL" lazy="false"
     * @hibernate.collection-many-to-many column="EMAILGROUP_ID" class="com.tonik.model.EmailGroup"
     * @hibernate.collection-key column="EMAIL_ID"
     * @return Returns the emailGroups.
     */
    public Set<EmailGroup> getEmailGroups()
    {
        return emailGroups;
    }

    public void setEmailGroups(Set<EmailGroup> emailGroups)
    {
        this.emailGroups = emailGroups;
    }

    /**
     * @hibernate.property column="REMARK" type="string" length="5000" not-null="false"
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
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
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
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="EMAIL_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
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
