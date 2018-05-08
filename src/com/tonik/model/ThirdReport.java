package com.tonik.model;

/**
 * Desc: 第三方报告
 * @author fuzhi
 * @hibernate.class table="ThirdReport"
 */
public class ThirdReport
{
    private Long id;
    private String title;
    private String url;
    private String createTime;
    private String getherTime;
    private String siteName;
    private String infoType;
    private String brand;
    private String country;
    private Long addressType;
    private String titleTrans;
    private String content;
    private String contentTrans;
    /**
     * @hibernate.id column="ThirdReport_Id" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="ThirdReport_Title" type="string" not-null="false" length="200"
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
     * @hibernate.property column="ThirdReport_Url" type="string" not-null="false" length="100"
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
     * @hibernate.property column="ThirdReport_CreateTime" type="string" not-null="false"
     * @return
     */
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    /**
     * @hibernate.property column="ThirdReport_GetherTime" type="string" not-null="false"
     * @return
     */
    public String getGether()
    {
        return getherTime;
    }
    public void setGether(String getherTime)
    {
        this.getherTime = getherTime;
    }
    /**
     * @hibernate.property column="ThirdReport_SiteName" type="string" not-null="false" length="50"
     * @return
     */
    public String getSiteName()
    {
        return siteName;
    }
    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }
    /**
     * @hibernate.property column="ThirdReport_InfoType" type="string" not-null="false" length="50"
     * @return
     */
    public String getInfoType()
    {
        return infoType;
    }
    public void setInfoType(String infoType)
    {
        this.infoType = infoType;
    }
    /**
     * @hibernate.property column="ThirdReport_Brand" type="string" length="150" not-null="false"
     * @return
     */
    public String getBrand()
    {
        return brand;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    /**
     * @hibernate.property column="ThirdReport_Country" type="string" length="150" not-null="false"
     * @return
     */
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    /**
     * @hibernate.property column="ThirdReport_AddressType" type="long" not-null="false"
     * @return
     */
    public Long getAddressType()
    {
        return addressType;
    }
    public void setAddressType(Long addressType)
    {
        this.addressType = addressType;
    }
    /**
     * @hibernate.property column="ThirdReport_TitleTrans" type="string" length="250" not-null="false"
     * @return
     */
    public String getTitleTrans()
    {
        return titleTrans;
    }
    public void setTitleTrans(String titleTrans)
    {
        this.titleTrans = titleTrans;
    }
    /**
     * @hibernate.property column="ThirdReport_Content" type="string" length="250" not-null="false"
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
     * @hibernate.property column="ThirdReport_ConTrans" type="string" length="250" not-null="false"
     * @return
     */
    public String getContentTrans()
    {
        return contentTrans;
    }
    public void setContentTrans(String contentTrans)
    {
        this.contentTrans = contentTrans;
    }
    
}
