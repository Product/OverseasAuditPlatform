package com.tonik.model;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author Ly
 * 
 * @hibernate.class table="RULES"
 */
public class Rules//规则与分析
{
    private Long id;//规则序号

    private String name;//规则名称
       
    private String content;//规则内容
    
//    private Long webStyle;
//    private WebsiteStyle websiteStyle;
    private Set<WebsiteStyle> websiteStyles;//适合目标=网站类型
    
    private Set<ProductStyle> productStyles;
    
    private Set<ProductType> productTypes;
    
    private Set<ProductPropertyType> productPropertyTypes;
    
    private Set<Country> countries;//国家
    
    private Set<Area> areas;//对应的省、州名称
    
    private Set<Material> materials;//原料
    
    private String remark;//备注
    
    private UserInfo createPerson;//创建人
    
    private Date createTime;//创建时间
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="RULES_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.property column="RULES_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @hibernate.property column="RULES_CONTENT" type="string" length="50" not-null="false"
     * @return Returns the content.
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content The content to set.
     */
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * @hibernate.set name="websiteStyles" table="WEBSITESTYLERULES" lazy="false"
     * @hibernate.collection-many-to-many column="WEBSITESTYLE_ID" class="com.tonik.model.WebsiteStyle"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the websiteStyles.
     */
    public Set<WebsiteStyle> getWebsiteStyles()
    {
        return websiteStyles;
    }

    public void setWebsiteStyles(Set<WebsiteStyle> websiteStyles)
    {
        this.websiteStyles = websiteStyles;
    }

    /**
     * @hibernate.set name="productTypes" table="PRODUCTTYPERULES" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTTYPE_ID" class="com.tonik.model.ProductType"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the productTypes.
     */
    public Set<ProductType> getProductTypes()
    {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes)
    {
        this.productTypes = productTypes;
    }

    /**
     * @hibernate.set name="productPropertyTypes" table="PRODUCTPROPERTYTYPERULES" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTPROPERTYTYPE_ID" class="com.tonik.model.ProductPropertyType"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the productPropertyTypes.
     */
    public Set<ProductPropertyType> getProductPropertyTypes()
    {
        return productPropertyTypes;
    }

    public void setProductPropertyTypes(Set<ProductPropertyType> productPropertyTypes)
    {
        this.productPropertyTypes = productPropertyTypes;
    }

    /**
     * @hibernate.set name="countries" table="COUNTRYRULES" lazy="false"
     * @hibernate.collection-many-to-many column="COUNTRY_ID" class="com.tonik.model.Country"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the countries.
     */
    public Set<Country> getCountries()
    {
        return countries;
    }

    public void setCountries(Set<Country> countries)
    {
        this.countries = countries;
    }

    /**
     * @hibernate.set name="areas" table="AREARULES" lazy="false"
     * @hibernate.collection-many-to-many column="AREA_ID" class="com.tonik.model.Area"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the areas.
     */
    public Set<Area> getAreas()
    {
        return areas;
    }

    public void setAreas(Set<Area> areas)
    {
        this.areas = areas;
    }

    /**
     * @hibernate.set name="materials" table="MATERIALRULES" lazy="false"
     * @hibernate.collection-many-to-many column="MATERIAL_ID" class="com.tonik.model.Material"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the materials.
     */
    public Set<Material> getMaterials()
    {
        return materials;
    }

    public void setMaterials(Set<Material> materials)
    {
        this.materials = materials;
    }

    /**
     * @hibernate.property column="RULES_REMARK" type="string" length="2000" not-null="false"
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
    
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.many-to-one column="USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
     * @return Returns the createPerson.
     */
    public UserInfo getCreatePerson()
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
     * @hibernate.property column="RULES_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public String getCreatePersonName(){
        if(createPerson == null)
            return null;
        return createPerson.getRealName();
    }
    
    public Long getWebsiteStylesId(){
        if(websiteStyles == null)
            return null;
        return ((Rules) websiteStyles).getId();
    }
    
    public String getWebsiteStylesName(){
        if(websiteStyles == null)
            return null;
        return ((Rules) websiteStyles).getName();
    }
    
    
    public String getFormatCreateTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    }

    /**
     * @hibernate.set name="productStyles" table="PRODUCTSTYLERULES" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTSTYLE_ID" class="com.tonik.model.ProductStyle"
     * @hibernate.collection-key column="RULES_ID"
     * @return Returns the productStyles.
     */
    public Set<ProductStyle> getProductStyles()
    {
        return productStyles;
    }

    public void setProductStyles(Set<ProductStyle> productStyles)
    {
        this.productStyles = productStyles;
    }    
}
