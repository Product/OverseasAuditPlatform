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
 * @author bchen
 * 
 * @hibernate.class table="WEBSITE"
 */
public class Website
{
    private Long id;
    
    private Long gatherid;//所属的数据采集总任务Id
    
    private String name;//网站名
    
    private String location;//网址
    
    private WebsiteStyle webStyle;//网站类别
    
    private String address="";//地址
    
    private String remark="";//备注
    
    private String createPerson;
    
    private Date createTime;
    
    private Country country;
    
    private Area area;
    
    private boolean regulatory;//网站是否受监管
    
    private int integrityDegree;//网站诚信度
    
    private double comprehensiveScore;//网站综合评分
    
    private int websiteType;//网站是否是电商网站
    
    
    /**
     * @param id The id to set.
     */

 
    public void setId(Long id)

    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="WEBSITE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
   
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.property column="WEBSITE_NAME" type="string" length="500" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @hibernate.property column="LOCATION" type="string" length="500" not-null="false"
     * @return Returns the Location.
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * @param Location The Location to set.
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

   
    /**
     * @hibernate.many-to-one column="WEBSITE_STYLE" not-null="false" lazy="false" class="com.tonik.model.WebsiteStyle"
     * @return Returns the webStyle.
     */
    public  WebsiteStyle getWebStyle()
    {
        return webStyle;
    }

    /**
     * @param webStyle The webStyle to set.
     */
    public void setWebStyle(WebsiteStyle webStyle)
    {
        this.webStyle = webStyle;
    }

    /**
     * @hibernate.property column="WEBSITE_ADDRESS" type="string" length="2000" not-null="false"
     * @return Returns the address.
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address The address to set.
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @hibernate.property column="WEBSITE_REMARK" type="string" length="5000" not-null="false"
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
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="WEBSITE_CREATEPERSON" type="string" length="50" not-null="false"
     * @return Returns the createPerson.
     */
    public String getCreatePerson()
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
     * @hibernate.property column="WEBSITE_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public String getWebStyleName()
    {
        if(webStyle == null)
            return null;
        else
            return webStyle.getName();
    }
    /**
     * @hibernate.property column="GATHER_ID" type="long" not-null="false"
     * @return Returns the gatherid.
     */
    public Long getGatherid()
    {
        return gatherid;
    }
    /**
     * @param gatherid The gatherid to set.
     */
    public void setGatherid(Long gatherid)
    {
        this.gatherid = gatherid;
    }
   
 
    
    /**
     * @hibernate.many-to-one column="COUNTRY" not-null="false" lazy="false" class="com.tonik.model.Country"
     * @return Returns the area.
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
     * @hibernate.property column="REGULATORY" type="boolean" not-null="false"
     * @return Returns the regulatory.
     */
    public boolean isRegulatory()
    {
        return regulatory;
    }
    /**
     * @param regulatory The regulatory to set.
     */
    public void setRegulatory(boolean regulatory)
    {
        this.regulatory = regulatory;
    }
    /**
     * @hibernate.property column="INTEGRITY_DEGREE" type="integer" not-null="false"
     * @return Returns the integrityDegree.
     */
    public int getIntegrityDegree()
    {
        return integrityDegree;
    }
    /**
     * @param integrityDegree The integrityDegree to set.
     */
    public void setIntegrityDegree(int integrityDegree)
    {
        this.integrityDegree = integrityDegree;
    }
    /**
     * @hibernate.property column="COMPREHENSIVE_SCORE" type="double" not-null="false"
     * @return Returns the comprehensiveScore.
     */
    public double getComprehensiveScore()
    {
        return comprehensiveScore;
    }
    /**
     * @param comprehensiveScore The comprehensiveScore to set.
     */
    public void setComprehensiveScore(double comprehensiveScore)
    {
        this.comprehensiveScore = comprehensiveScore;
    }
    /**
     * @hibernate.property column="WEBSITE_TYPE" type="integer" not-null="false"
     * @return Returns the websiteType.
     */
    public int getWebsiteType()
    {
        return websiteType;
    }

    public void setWebsiteType(int websiteType)
    {
        this.websiteType = websiteType;
    }
}
