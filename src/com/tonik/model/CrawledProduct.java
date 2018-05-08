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
 * @hibernate.class table="CRAWLED_PRODUCT"
 */
public class CrawledProduct//爬虫抓取的商品
{
    private Long id;
    private String url;//网址
    private Website website;//对应网站
    private Date createTime;//创建时间
    
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
     * @hibernate.property column="URL" type="long" length="50" not-null="false"
     * @return Returns the url.
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
     * @hibernate.many-to-one column="WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }
    public void setWebsite(Website website)
    {
        this.website = website;
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
    
    
}
