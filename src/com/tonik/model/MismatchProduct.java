package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Apr 15, 2016
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="MISMATCH_PRODUCT"
 */
public class MismatchProduct//不符合标准的商品
{
    private Long id;
    private Long productId;//商品id
    private Long standardId;//标准id
    private String mismatchContent;//不匹配的配料信息 MISMATCH_CONTENT
    private Date createTime;//创建时间
    private Integer mismatchNum;//不匹配的配料数量
    
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.property column="PRODUCT_ID" type="long" not-null="false"
     * @return Returns the productId.
     */
    public Long getProductId()
    {
        return productId;
    }
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }
    
    /**
     * @hibernate.property column="STANDARD_ID" type="long" not-null="false"
     * @return Returns the standardId.
     */
    public Long getStandardId()
    {
        return standardId;
    }
    public void setStandardId(Long standardId)
    {
        this.standardId = standardId;
    }
    
    /**
     * @hibernate.property column="MISMATCH_CONTENT" type="string" length="2000" not-null="false"
     * @return Returns the mismatchContent.
     */
    public String getMismatchContent()
    {
        return mismatchContent;
    }
    public void setMismatchContent(String mismatchContent)
    {
        this.mismatchContent = mismatchContent;
    }
    
    /**
     * @hibernate.property column="CREATE_TIME" sql-type="Date" not-null="false"
     * @return Returns the createTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="MISMATCH_NUM" type="integer" not-null="false"
     * @return Returns the productId.
     */
    public Integer getMismatchNum()
    {
        return mismatchNum;
    }
    public void setMismatchNum(Integer mismatchNum)
    {
        this.mismatchNum = mismatchNum;
    }
    
    
}
