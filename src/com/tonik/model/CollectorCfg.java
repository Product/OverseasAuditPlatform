package com.tonik.model;


/**
 * Desc: 采集器配置信息
 * @author fuzhi
 * @hibernate.class table="CollectorCfg"
 */
public class CollectorCfg
{
    private Long id;
    private String url;
    private String blockSign;
    private int pageTotal;
    private String titleSign;
    private String createTimeSign;
    private String nextSign;
    private String siteName;
    private int type;
    private String name;
    private String createTime;
    private int rate;
    private String contentSign;
    /**
     * @hibernate.id column="CollectorCfg_Id" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="CollectorCfg_Url" type="string" not-null="false" length="255"
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
     * @hibernate.property column="CollectorCfg_BlockSign" type="string" not-null="false" length="255"
     * @return
     */
    public String getBlockSign()
    {
        return blockSign;
    }
    public void setBlockSign(String blockSign)
    {
        this.blockSign = blockSign;
    }
    /**
     * @hibernate.property column="CollectorCfg_PageTotal" type="int" not-null="false"
     * @return
     */
    public int getPageTotal()
    {
        return pageTotal;
    }
    public void setPageTotal(int pageTotal)
    {
        this.pageTotal = pageTotal;
    }
    /**
     * @hibernate.property column="CollectorCfg_TitleSign" type="string" not-null="false" length="255"
     * @return
     */
    public String getTitleSign()
    {
        return titleSign;
    }
    public void setTitleSign(String titleSign)
    {
        this.titleSign = titleSign;
    }
    
    /**
     * @hibernate.property column="CollectorCfg_SecondInfoSign" type="string" not-null="false" length="255"
     * @return
     */
    public String getCreateTimeSign()
    {
        return createTimeSign;
    }
    public void setCreateTimeSign(String createTimeSign)
    {
        this.createTimeSign = createTimeSign;
    }
    
    /**
     * @hibernate.property column="CollectorCfg_NextSign" type="string" not-null="false" length="255"
     * @return
     */
    public String getNextSign()
    {
        return nextSign;
    }
    public void setNextSign(String nextSign)
    {
        this.nextSign = nextSign;
    }
    /**
     * @hibernate.property column="CollectorCfg_SiteName" type="string" not-null="false" length="255"
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
     * @hibernate.property column="CollectorCfg_Type" type="int" not-null="false"
     * @return
     */
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * @hibernate.property column="CollectorCfg_Name" type="string" not-null="false" length="100"
     * @return
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
     * @hibernate.property column="CollectorCfg_CreateTime" type="string" not-null="false" length="100"
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
     * @hibernate.property column="CollectorCfg_Rate" type="int" not-null="false"
     * @return
     */
    public int getRate()
    {
        return rate;
    }
    public void setRate(int rate)
    {
        this.rate = rate;
    }
    /**
     * @hibernate.property column="CollectorCfg_ContentSign" type="string" not-null="false" length="255"
     * @return
     */
    public String getContentSign()
    {
        return contentSign;
    }
    public void setContentSign(String contentSign)
    {
        this.contentSign = contentSign;
    }
    
}
