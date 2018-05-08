package com.tonik.model;

import java.util.Date;

/**
 * @desc: 专家知识库
 * @author fuzhi
 * @hibernate.class table="ExpertRepository"
 */
public class ExpertRepository
{
    private Long id;
    private String url;
    private String title;
    private Date createtime;
    private String author;
    private String content;
    private ProductType productType;
    /**
     * @hibernate.id column="ExpertRepository_Id" type="long" unsaved-value="null" generator-class="identity"
     * @return
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
     * @hibernate.property column="ExpertRepository_Url" type="string" length="250" not-null="false"
     * @return
     */
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    /**
     * @hibernate.property column="ExpertRepository_Title" type="string" length="150" not-null="false"
     * @return
     */
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * @hibernate.property column="ExpertRepository_Createtime" sql-type="Date" not-null="false"
     * @return
     */
    public Date getCreatetime()
    {
        return createtime;
    }
    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }
    /**
     * @hibernate.property column="ExpertRepository_Author" type="string" length="150" not-null="false"
     * @return
     */
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    /**
     * @hibernate.property column="ExpertRepository_Content" type="text" not-null="false"
     * @return
     */
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    /**
     * @hibernate.many-to-one column="ProductType_Id" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return
     */
    public ProductType getProductType()
    {
        return productType;
    }
    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }
    
}
