package com.tonik.model;

import java.util.Date;

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
 * @author nimengfei
 * 
 * @hibernate.class table="BRAND"
 */
public class Brand
{
    private Long id;
    
    private String name_en;
    
    private String name_cn;
    
    private String name_other;
    
    private Area area;
    
    private Country country;
    
    private Integer popularity;//知名度
    
    private float marketShare;//市场占有率
    
    private Date createTime;

    /**
     * @hibernate.id column="BRAND_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="BRAND_NAMEEN" type="string" length="50" not-null="false"
     * @return Returns the name_en.
     */
    public String getName_en()
    {
        return name_en;
    }

    /**
     * @param name_en The name_en to set.
     */
    public void setName_en(String name_en)
    {
        this.name_en = name_en;
    }

    /**
     * @hibernate.property column="BRAND_NAMECN" type="string" length="50" not-null="false"
     * @return Returns the name_cn.
     */
    public String getName_cn()
    {
        return name_cn;
    }

    /**
     * @param name_cn The name_cn to set.
     */
    public void setName_cn(String name_cn)
    {
        this.name_cn = name_cn;
    }

    /**
     * @hibernate.property column="BRAND_NAMEOTHER" type="string" length="50" not-null="false"
     * @return Returns the name_other.
     */
    public String getName_other()
    {
        return name_other;
    }

    /**
     * @param name_other The name_other to set.
     */
    public void setName_other(String name_other)
    {
        this.name_other = name_other;
    }

    /**
     * @hibernate.many-to-one column="AREA" not-null="false" lazy="false" class="com.tonik.model.Area"
     * @return Returns the area.
     */
    public Area getArea()
    {
        return area;
    }

    /**
     * @param area The area to set.
     */
    public void setArea(Area area)
    {
        this.area = area;
    }
    
    /**
     * @hibernate.many-to-one column="COUNTRY" not-null="false" lazy="false" class="com.tonik.model.Country"
     * @return Returns the country.
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * @param country The country to set.
     */
    public void setCountry(Country country)
    {
        this.country = country;
    }
    /**
     * @hibernate.property column="POPULARITY" type="integer" not-null="false"
     * @return Returns the popularity.
     */
    public Integer getPopularity()
    {
        return popularity;
    }
    /**
     * @param popularity The popularity to set.
     */
    public void setPopularity(Integer popularity)
    {
        this.popularity = popularity;
    }
    /**
     * @hibernate.property column="MARKET_SHARE" type="float" not-null="false"
     * @return Returns the marketShare.
     */
    public float getMarketShare()
    {
        return marketShare;
    }
    /**
     * @param marketShare The marketShare to set.
     */
    public void setMarketShare(float marketShare)
    {
        this.marketShare = marketShare;
    }

    /**
     * @hibernate.property column="BRAND_CREATETIME" sql-type="Date" not-null="false" 
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
    
    public String getFormatCreateTime(){
        if(createTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(createTime);
        return date;
        }
    }
    
    public String getName(){
        return "".equals(name_cn) ? name_en : name_cn;
    }
    
}
