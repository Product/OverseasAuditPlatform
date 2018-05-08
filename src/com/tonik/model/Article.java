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
 * @hibernate.class table="ARTICLE"
 */
public class Article
{
    private Long id;
    private String title;
    private String content;
    private String createTime;
    private Date gatherTime;
    private String location;
    private Boolean validity;
    private String remark;
    private UserInfo createPerson;
    private Long ngtid;
    /**
     * @hibernate.id column="ARTICLE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="ARTICLE_TITLE" type="string" length="200" not-null="false"
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * @hibernate.property column="ARTICLE_CONTENT" type="text" not-null="false"
     * @return Returns the content.
     */
    public String getContent()
    {
        return content;
    }
    /**
     * @param content The content to set.
     */
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * @hibernate.property column="ARTICLE_CREATETIME" sql-type="string" length="50" not-null="false"
     * @return Returns the createTime.
     */
    public String getCreateTime()
    {
        return createTime;
    }
    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    /**
     * @hibernate.property column="ARTICLE_GATHERTIME" sql-type="Date" not-null="false"
     * @return Returns the gatherTime.
     */
    public Date getGatherTime()
    {
        return gatherTime;
    }
    /**
     * @param gatherTime The gatherTime to set.
     */
    public void setGatherTime(Date gatherTime)
    {
        this.gatherTime = gatherTime;
    }
    /**
     * @hibernate.property column="ARTICLE_LOCATION" type="string" length="400" not-null="false"
     * @return Returns the location.
     */
    public String getLocation()
    {
        return location;
    }
    /**
     * @param location The location to set.
     */
    public void setLocation(String location)
    {
        this.location = location;
    }
    /**
     * @hibernate.property column="ARTICLE_VALIDITY" type="boolean" not-null="false"
     * @return Returns the validity.
     */
    public Boolean getValidity()
    {
        return validity;
    }
    /**
     * @param validity The validity to set.
     */
    public void setValidity(Boolean validity)
    {
        this.validity = validity;
    }
    /**
     * @hibernate.property column="ARTICLE_REMARK" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="ARTICLE_USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
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
     * @hibernate.property column="ARTICLE_NEWSGATHERTASKID" type="long" unsaved-value="null" not-null="false"
     * @return Returns the ngtid.
     */
    public Long getNgtid()
    {
        return ngtid;
    }
    /**
     * @param ngtid The ngtid to set.
     */
    public void setNgtid(Long ngtid)
    {
        this.ngtid = ngtid;
    }
    
    public String getCreatePersonName(){
        if(createPerson != null)
            return createPerson.getRealName();
        else
            return null;
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Article other = (Article) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
