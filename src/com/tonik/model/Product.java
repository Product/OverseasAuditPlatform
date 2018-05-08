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
 * @hibernate.class table="PRODUCT"
 */
public class Product//商品
{
    private Long id;
    
    private Long tid;//所属任务Id
    
    private Long pdid = 0L;//所属产品id
    
    private String name ="";//商品名
    
    private String brand ="";//品牌
    
    private String price ="";//价格
    
    private String freight ="";//运费
    
    private String  customsDuty ="";//关税
    
    private Date purchaseTime;//采购日期
    
    private Date onlineTime;//上架日期
    
    private ProductStyle productType;//商品类型
    
    private ProductType firstlevelType;//所属一级商品类别
    
    private ProductType secondlevelType;//所属二级商品类别
    
    private ProductType thirdlevelType;//所属三级商品类别
    
    private String location ="";//网址
    
    private String picture ="";//图片
    
    private String producingArea ="";//产地
    
    private String suitableAge ="";//适用年龄
    
    private String standard ="";//规格
    
    private String expirationDate ="";//保质期
    
    private String remark ="";

    private String createPerson ="";
    
    private Date createTime;
    
    private Website website;
    
    private String websiteName;
    
    private int sales = 0;//销量
    
    private Country country;
    
    private Area area;
    
    private double productDefineGrade = 0;
    
    private double productWebisteGrade = 0;
    
    private String powerValue;  
   
    /**
     * @hibernate.property column="POWER_VALUE" type="string" length="20" not-null="false"
     * @return Returns the websiteName.
     */
    public String getPowerValue()
    {
        return powerValue;
    }

    public void setPowerValue(String powerValue)
    {
        this.powerValue = powerValue;
    }

    /**
     * @param website The website to set.
     */
    public void setWebsite(Website website)
    {
        this.website = website;
    }
    
    /**
     * @hibernate.many-to-one column="WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }
  
  
 
   
    /**
     * @hibernate.property column="WEBSITE_NAME" type="string" length="200" not-null="false"
     * @return Returns the websiteName.
     */
    public String getWebsiteName()
    {
        return websiteName;
    }
    /**
     * @param websiteName The websiteName to set.
     */
    public void setWebsiteName(String websiteName)
    {
        this.websiteName = websiteName;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="PRODUCT_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
   

    /**
     * @hibernate.property column="TASK_ID" type="long"  unsaved-value="null" not-null="false" 
     * @return Returns the tid.
     */
    public Long getTid()
    {
        return tid;
    }
    /**
     * @param tid The tid to set.
     */
    public void setTid(Long tid)
    {
        this.tid = tid;
    }
    
    /**
     * @hibernate.property column="PRODEFINITION_ID" type="long"  unsaved-value="null" not-null="false" 
     * @return Returns the pdid.
     */
    public Long getPdid()
    {
        return pdid;
    }
    /**
     * @param pdid The pdid to set.
     */
    public void setPdid(Long pdid)
    {
        this.pdid = pdid;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.property column="PRODUCT_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    

    /**
     * @hibernate.property column="PRODUCT_BRAND" type="string" length="50" not-null="false"
     * @return Returns the brand.
     */
    public String getBrand()
    {
        return brand;
    }

    /**
     * @param brand The brand to set.
     */
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    /**
     * @hibernate.property column="PRODUCT_PRICE" type="string" length="500" not-null="false"
     * @return Returns the price.
     */
    public String getPrice()
    {
        return price;
    }

    /**
     * @param price The price to set.
     */
    public void setPrice(String price)
    {
        this.price = price;
    }

    /**
     * @hibernate.property column="PRODUCT_FREIGHT" type="string" length="50" not-null="false"
     * @return Returns the freight.
     */
    public String getFreight()
    {
        return freight;
    }

    /**
     * @param freight The freight to set.
     */
    public void setFreight(String freight)
    {
        this.freight = freight;
    }

    /**
     * @hibernate.property column="PRODUCT_CUSTOMS_DUTY" type="string" length="50" not-null="false"
     * @return Returns the customsDuty.
     */
    public String getCustomsDuty()
    {
        return customsDuty;
    }

    /**
     * @param customsDuty The customsDuty to set.
     */
    public void setCustomsDuty(String customsDuty)
    {
        this.customsDuty = customsDuty;
    }

    /**
     * @hibernate.property column="PRODUCT_LOCATION" type="string" length="500" not-null="true"
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
     * @hibernate.many-to-one column="PRODUCT_STYLE" not-null="false" lazy="false" class="com.tonik.model.ProductStyle"
     * @return Returns the productType.
     */
    public ProductStyle getProductType()
    {
        return productType;
    
    }
    
  

    public String getProductTypeName()
    {
        return productType.getName();
    }
  
    /**
     * @param productType The productType to set.
     */

    public void setProductType(ProductStyle productType)
    {
        this.productType = productType;
    }
    
    /**
     * @hibernate.many-to-one column="FIRST_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the firstlevelType.
     */
    public ProductType getFirstlevelType()
    {
        return firstlevelType;
    }
    /**
     * @param firstlevelType The firstlevelType to set.
     */
    public void setFirstlevelType(ProductType firstlevelType)
    {
        this.firstlevelType = firstlevelType;
    }
    /**
     * @hibernate.many-to-one column="SECOND_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the secondlevelType.
     */
    public ProductType getSecondlevelType()
    {
        return secondlevelType;
    }
    /**
     * @param secondlevelType The secondlevelType to set.
     */
    public void setSecondlevelType(ProductType secondlevelType)
    {
        this.secondlevelType = secondlevelType;
    }
    /**
     * @hibernate.many-to-one column="THIRD_TYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the thirdlevelType.
     */
    public ProductType getThirdlevelType()
    {
        return thirdlevelType;
    }
    /**
     * @param thirdlevelType The thirdlevelType to set.
     */
    public void setThirdlevelType(ProductType thirdlevelType)
    {
        this.thirdlevelType = thirdlevelType;
    }

    /**
     * @hibernate.property column="PRODUCT_PICTURE" type="string" length="50" not-null="false"
     * @return Returns the picture.
     */
    public String getPicture()
    {
        return picture;
    }

    /**
     * @param picture The picture to set.
     */
    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    /**
     * @hibernate.property column="PRODUCT_PRODUCINGAREA" type="string" length="50" not-null="false"
     * @return Returns the producingArea.
     */
    public String getProducingArea()
    {
        return producingArea;
    }

    /**
     * @param producingArea The producingArea to set.
     */
    public void setProducingArea(String producingArea)
    {
        this.producingArea = producingArea;
    }

    /**
     * @hibernate.property column="PRODUCT_SUITABLEAGE" type="string" length="50" not-null="false"
     * @return Returns the suitableAge.
     */
    public String getSuitableAge()
    {
        return suitableAge;
    }

    /**
     * @param suitableAge The suitableAge to set.
     */
    public void setSuitableAge(String suitableAge)
    {
        this.suitableAge = suitableAge;
    }

    /**
     * @hibernate.property column="PRODUCT_STANDARD" type="string" length="50" not-null="false"
     * @return Returns the standard.
     */
    public String getStandard()
    {
        return standard;
    }

    /**
     * @param standard The standard to set.
     */
    public void setStandard(String standard)
    {
        this.standard = standard;
    }

    /**
     * @hibernate.property column="PRODUCT_EXPIRATIONDATE" type="string" length="50" not-null="false"
     * @return Returns the expirationDate.
     */
    public String getExpirationDate()
    {
        return expirationDate;
    }

    /**
     * @param expirationDate The expirationDate to set.
     */
    public void setExpirationDate(String expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    /**
     * @hibernate.property column="PRODUCT_REMARK" type="string" length="5000" not-null="false"
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
     * @hibernate.property column="PRODUCT_PURCHASETIME" sql-type="Date" not-null="false"
     * @return Returns the purchaseTime.
     */
    public Date getPurchaseTime()
    {
        return purchaseTime;
    }

    /**
     * @param purchaseTime The purchaseTime to set.
     */
    public void setPurchaseTime(Date purchaseTime)
    {
        this.purchaseTime = purchaseTime;
    }
    
    /**
     * @hibernate.property column="PRODUCT_ONLINETIME" sql-type="Date" not-null="false"
     * @return Returns the onlineTime.
     */
    public Date getOnlineTime()
    {
        return onlineTime;
    }

    /**
     * @param onlineTime The onlineTime to set.
     */
    public void setOnlineTime(Date onlineTime)
    {
        this.onlineTime = onlineTime;
    }


   
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="CREATEPERSON" type="string" length="50" not-null="false"
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
     * @hibernate.property column="CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    /**
     * @hibernate.property column="SALES" type="integer" not-null="false"
     * @return Returns the sales.
     */
  
    public int getSales()
    {
        return sales;
    }
    /**
     * @param sales The sales to set.
     */
    public void setSales(int sales)
    {
        this.sales = sales;
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
     * @hibernate.property column="PRODUCTDEFINEGRADE" type="double" not-null="false"
     * @return Returns the productDefineGrade.
     */
    public double getProductDefineGrade()
    {
        return productDefineGrade;
    }
    /**
     * @param productDefineGrade The productDefineGrade to set.
     */
    public void setProductDefineGrade(double productDefineGrade)
    {
        this.productDefineGrade = productDefineGrade;
    }

    /**
     * @hibernate.property column="WEBSITEGRADE" type="double" not-null="false"
     * @return Returns the productWebisteGrade.
     */
    public double getProductWebisteGrade()
    {
        return productWebisteGrade;
    }
    /**
     * @param productWebisteGrade The productWebisteGrade to set.
     */
    public void setProductWebisteGrade(double productWebisteGrade)
    {
        this.productWebisteGrade = productWebisteGrade;
    }

    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }
        if (o == this)
        {
           return true;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
        
        Product e = (Product) o;
        return (this.getLocation() == e.getLocation());

    }  
}
