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
 * @author Ly
 * 
 * @hibernate.class table="EPIDEMIC"
 */
public class Epidemic
{
    private Long id;

    private String title;//疫情名称
       
    private String content;//疫情概述

    private Set<ProductType> productTypes;//每种疫情关联的商品类型
    
    private String remark;//备注
    
    private UserInfo createPerson;//创建人
    
    private Date createTime;//创建时间

    /**
     * @hibernate.id column="EPIDEMIC_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EPIDEMIC_TITLE" type="string" length="500" not-null="false"
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
     * @hibernate.property column="EPIDEMIC_CONTENT" type="string" length="2000" not-null="false"
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
     * @hibernate.set name="productTypes" table="PRODUCTTYPEEPIDEMIC" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTTYPE_ID" class="com.tonik.model.ProductType"
     * @hibernate.collection-key column="EPIDEMIC_ID"
     * @return Returns the productTypes.
     */
    public Set<ProductType> getProductTypes()
    {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes)
    {
        this.productTypes = productTypes;
    }

    /**
     * @hibernate.property column="EPIDEMIC_REMARK" type="string" length="2000" not-null="false"
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
     * @hibernate.many-to-one column="EPIDEMIC_USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
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
     * @hibernate.property column="EPIDEMIC_CREATETIME" sql-type="Date" not-null="false" 
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
    
}