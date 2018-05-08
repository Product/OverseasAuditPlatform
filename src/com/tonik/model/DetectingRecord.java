package com.tonik.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description:检测记录模块 model层
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @hibernate.class table="DETECTINGRECORD"
 */
public class DetectingRecord  implements Serializable//本平台自身的检测记录
{
    /**
     * 
     */
    private static final long serialVersionUID = 2663561905771229098L;

    private Long id;
    
    private Website website;//网站对象
    
    private String product;//检测商品
    
    private String organization;//抽查机构
    
    private int sample;//样品数
    
    private int qualified;//合格数
    
    private Date detectingTime;//检测时间
    
    private String remark;//备注
    
    private Long createPersonId;//创建人ID
    
    private String createPersonName;//创建人真实姓名
    
    private Date createTime;//创建时间
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @hibernate.id column="DETECTINGRECORD_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @hibernate.many-to-one column="DETECTINGRECORD_WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }
    
    /**
     * @param website The website to set.
     */
    public void setWebsite(Website website)
    {
        this.website = website;
    }
  
    
    /**
     * 
     * @param detectingTime The detectingTime to set.
     */
    public void setDetectingTime(Date detectingTime)
    {
        this.detectingTime = detectingTime;
    }
    
    /**
     * @hibernate.property column="DETECTINGRECORD_TIME" sql-type="Date" not-null="false"
     * @return Returns the detectingTime.
     */
    public Date getDetectingTime()
    {
        return detectingTime;
    }

    /**
     * @hibernate.property column="DETECTINGRECORD_SAMPLE" type="int" not-null="false"
     * @return Returns the sample.
     */
    public int getSample()
    {
        return sample;
    }

    /**
     * @param sample The sample to set.
     */
    public void setSample(int sample)
    {
        this.sample = sample;
    }

    /**
     * @hibernate.property column="DETECTINGRECORD_QUALIFIED" type="int" not-null="false"
     * @return Returns the qualified.
     */
    public int getQualified()
    {
        return qualified;
    }

    /**
     * @param qualified The qualified to set.
     */
    public void setQualified(int qualified)
    {
        this.qualified = qualified;
    }

    /**
     * @hibernate.property column="DETECTINGRECORD_REMARK" type="string" length="2000" not-null="false"
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
     * @hibernate.property column="DETECTINGRECORD_PRODUCT" type="string" length="50" not-null="false"
     * @return Returns the product.
     */
    public String getProduct()
    {
        return product;
    }

    /**
     * 
     * @param Product The Product to set.
     */
    public void setProduct(String product)
    {
        this.product = product;
    }

    /**
     * @hibernate.property column="DETECTINGRECORD_ORGANIZATION" type="string" length="50" not-null="false"
     * @return Returns the organization.
     */
    public String getOrganization()
    {
        return organization;
    }

    /**
     * @param Organization The Organization to set.
     */
    public void setOrganization(String organization)
    {
        this.organization = organization;
    }
    

    /**
     * @hibernate.property column="DETECTINGRECORD_CREATEPERSONID" type="long" unsaved-value="null"
     * @return Returns the createPersonId.
     */
    public Long getCreatePersonId()
    {
        return createPersonId;
    }

    /**
     * @param CreatePersonid The CreatePersonid to set.
     */
    public void setCreatePersonId(Long createPersonId)
    {
        this.createPersonId = createPersonId;
    }

    /**
     * @hibernate.property column="DETECTINGRECORD_CREATEPERSONNAME" type="string" length="50" not-null="false"
     * @return Returns the createPersonName.
     */
    public String getCreatePersonName()
    {
        return createPersonName;
    }

    /**
     * @param CreatePersonName The CreatePersonName to set.
     */  
    public void setCreatePersonName(String createPersonName)
    {
        this.createPersonName = createPersonName;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="DETECTINGRECORD_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the createTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

}
