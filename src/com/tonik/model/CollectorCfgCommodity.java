package com.tonik.model;

/**
 * 
 * @author fuzhi
 * @hibernate.class table="CollectorCfgCommodity"
 */
public class CollectorCfgCommodity
{
    //主键id
    private Long id;
    //采集名称
    private String name; 
    //创建时间
    private String createTime;
    //采集频率
    private int rate;
    //采集地址url
    private String url;
    //采集列表
    private String contentSign;
    //采集内容标志
    private String titleSign;
    //采集价格标志
    private String priceSign;
    //采集销售量标志
    private String saleMountSign;
    //下一页标志
    private String nextSign;
    //采集页数
    private int pageTotal;
    
    
    //采集出处网站（淘宝、京东）
    private Website website;
    //产品类别（食品，电子产品，服饰）
    private ProductStyle productStyle;
    //所属一级商品类别
    private ProductType firstlevelType;
    //所属二级商品类别
    private ProductType secondlevelType;
    //所属三级商品类别
    private ProductType thirdlevelType;


    //采集品牌标志
    private String brandSign;
    //采集产地标志
    private String producingAreaSign;
    //采集适用年龄
    private String suitableAgeSign;
    //采集规格标志
    private String standardSign;
    //采集保质期标志
    private String expirationDateSign;
    //采集国家标志
    private String countrySign;
    //采集商品类型
    private String productTypeSign;
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="name" type="string" length="255" not-null="false"
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
     * @hibernate.property column="createTime" type="string" length="255" not-null="false"
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
     * @hibernate.property column="rate" type="int" not-null="false"
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
     * @hibernate.property column="url" type="string" length="255" not-null="false"
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
     * @hibernate.property column="contentSign" type="string" length="255" not-null="false"
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
    /**
     * @hibernate.property column="titleSign" type="string" length="255" not-null="false"
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
     * @hibernate.property column="priceSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getPriceSign()
    {
        return priceSign;
    }
    public void setPriceSign(String priceSign)
    {
        this.priceSign = priceSign;
    }
    /**
     * @hibernate.property column="nextSign" type="string" length="255" not-null="false"
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
     * @hibernate.property column="pageTotal" type="int" not-null="false"
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
     * @hibernate.many-to-one column="webSiteId" not-null="false" class="com.tonik.model.Website"
     * @return
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
     * @hibernate.many-to-one column="productStyleId" not-null="false" class="com.tonik.model.ProductStyle"
     * @return
     */
    public ProductStyle getProductStyle()
    {
        return productStyle;
    }
    public void setProductStyle(ProductStyle productStyle)
    {
        this.productStyle = productStyle;
    }
    /**
     * @hibernate.many-to-one column="firstlevelType" not-null="false" class="com.tonik.model.ProductType"
     * @return
     */
    public ProductType getFirstlevelType()
    {
        return firstlevelType;
    }
    public void setFirstlevelType(ProductType firstlevelType)
    {
        this.firstlevelType = firstlevelType;
    }
    /**
     * @hibernate.many-to-one column="secondlevelType" not-null="false" class="com.tonik.model.ProductType"
     * @return
     */
    public ProductType getSecondlevelType()
    {
        return secondlevelType;
    }
    public void setSecondlevelType(ProductType secondlevelType)
    {
        this.secondlevelType = secondlevelType;
    }
    /**
     * @hibernate.many-to-one column="thirdlevelType" not-null="false" class="com.tonik.model.ProductType"
     * @return
     */
    public ProductType getThirdlevelType()
    {
        return thirdlevelType;
    }
    public void setThirdlevelType(ProductType thirdlevelType)
    {
        this.thirdlevelType = thirdlevelType;
    }
    /**
     * @hibernate.property column="saleMountSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getSaleMountSign()
    {
        return saleMountSign;
    }
    public void setSaleMountSign(String saleMountSign)
    {
        this.saleMountSign = saleMountSign;
    }
    /**
     * @hibernate.property column="brandSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getBrandSign()
    {
        return brandSign;
    }
    public void setBrandSign(String brandSign)
    {
        this.brandSign = brandSign;
    }
    /**
     * @hibernate.property column="producingAreaSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getProducingAreaSign()
    {
        return producingAreaSign;
    }
    public void setProducingAreaSign(String producingAreaSign)
    {
        this.producingAreaSign = producingAreaSign;
    }
    /**
     * @hibernate.property column="suitableAgeSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getSuitableAgeSign()
    {
        return suitableAgeSign;
    }
    public void setSuitableAgeSign(String suitableAgeSign)
    {
        this.suitableAgeSign = suitableAgeSign;
    }
    /**
     * @hibernate.property column="standardSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getStandardSign()
    {
        return standardSign;
    }
    public void setStandardSign(String standardSign)
    {
        this.standardSign = standardSign;
    }
    /**
     * @hibernate.property column="expirationDateSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getExpirationDateSign()
    {
        return expirationDateSign;
    }
    public void setExpirationDateSign(String expirationDateSign)
    {
        this.expirationDateSign = expirationDateSign;
    }
    /**
     * @hibernate.property column="countrySign" type="string" length="255" not-null="false"
     * @return
     */
    public String getCountrySign()
    {
        return countrySign;
    }
    public void setCountrySign(String countrySign)
    {
        this.countrySign = countrySign;
    }
    /**
     * @hibernate.property column="productTypeSign" type="string" length="255" not-null="false"
     * @return
     */
    public String getProductTypeSign()
    {
        return productTypeSign;
    }
    public void setProductTypeSign(String productTypeSign)
    {
        this.productTypeSign = productTypeSign;
    }
}
