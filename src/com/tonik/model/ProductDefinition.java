package com.tonik.model;

import java.util.Date;
import java.util.Set;

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
 * @hibernate.class table="PRODUCTDEFINITION"
 */
public class ProductDefinition
{
    private Long id;
    
    private String name_en;
    
    private String name_cn;
    
    private String name_other;
    
    private Brand brand;
    
    private ProductType producttype;
    
    private Set<Material> materials;
    
    private Date createTime;
    
    private Integer sales;//销量
    
    private Double grade;
    
    private String feature_one;
    
    private String feature_two;
    
    private String feature_three;
    
    private String feature_four;
    
    private String feature_five;
    
    private String feature_six;
    
    private Area area;
    
    private Country country;
    
    private String Manufactor;
    
    private String picture;

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
     * @hibernate.id column="PRODUCTDEFINITION_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="PRODUCTDEFINITION_NAMEEN" type="string" length="500" not-null="false"
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
     * @hibernate.property column="PRODUCTDEFINITION_NAMECN" type="string" length="500" not-null="false"
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
     * @hibernate.property column="PRODUCTDEFINITION_NAMEOTHER" type="string" length="500" not-null="false"
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
     * @hibernate.many-to-one column="BRAND" not-null="false" lazy="false" class="com.tonik.model.Brand"
     * @return Returns the brand.
     */
    public Brand getBrand()
    {
        return brand;
    }

    /**
     * @param brand The brand to set.
     */
    public void setBrand(Brand brand)
    {
        this.brand = brand;
    }
    
    /**
     * @hibernate.property column="PRODUCTDEFINITION_CREATETIME" sql-type="Date" not-null="false" 
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
    
    /**
     * @hibernate.many-to-one column="PRODUCTTYPE" not-null="false" lazy="false" class="com.tonik.model.ProductType"
     * @return Returns the ProductType.
     */
    public ProductType getProducttype()
    {
        return producttype;
    }

    /**
     * @param producttype The producttype to set.
     */
    public void setProducttype(ProductType producttype)
    {
        this.producttype = producttype;
    }
    
    /**
     * @hibernate.set name="materials" table="PRODUCTDEFMATERIALS" lazy="false"
     * @hibernate.collection-many-to-many column="MATERIAL_ID" class="com.tonik.model.Material"
     * @hibernate.collection-key column="PRODUCTDEFINITION_ID"
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
     * @hibernate.property column="SALES" type="integer" not-null="false"
     * @return Returns the sales.
     */
    public Integer getSales()
    {
        return sales;
    }
    /**
     * @param sales The sales to set.
     */
    public void setSales(Integer sales)
    {
        this.sales = sales;
    }

    /**
     * @hibernate.property column="GRADE" type="double" not-null="false"
     * @return Returns the grade.
     */
    public Double getGrade()
    {
        return grade;
    }
    /**
     * @param grade The grade to set.
     */
    public void setGrade(Double grade)
    {
        this.grade = grade;
    }
    
    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATUREONE" type="string" length="200" not-null="false"
     * @return Returns the feature_one.
     */
    public String getFeature_one()
    {
        return feature_one;
    }

    /**
     * @param feature_one The feature_one to set.
     */
    public void setFeature_one(String feature_one)
    {
        this.feature_one = feature_one;
    }

    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATURETWO" type="string" length="200" not-null="false"
     * @return Returns the feature_two.
     */
    public String getFeature_two()
    {
        return feature_two;
    }

    /**
     * @param feature_two The feature_two to set.
     */
    public void setFeature_two(String feature_two)
    {
        this.feature_two = feature_two;
    }

    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATURETHREE" type="string" length="200" not-null="false"
     * @return Returns the feature_three.
     */
    public String getFeature_three()
    {
        return feature_three;
    }

    /**
     * @param feature_three The feature_three to set.
     */
    public void setFeature_three(String feature_three)
    {
        this.feature_three = feature_three;
    }
    
    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATUREFOUR" type="string" length="200" not-null="false"
     * @return Returns the feature_four.
     */
    public String getFeature_four()
    {
        return feature_four;
    }

    /**
     * @param feature_four The feature_four to set.
     */
    public void setFeature_four(String feature_four)
    {
        this.feature_four = feature_four;
    }

    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATUREFIVE" type="string" length="200" not-null="false"
     * @return Returns the feature_five.
     */
    public String getFeature_five()
    {
        return feature_five;
    }

    /**
     * @param feature_five The feature_five to set.
     */
    public void setFeature_five(String feature_five)
    {
        this.feature_five = feature_five;
    }

    /**
     * @hibernate.property column="PRODUCTDEFINITION_FEATURESIX" type="string" length="200" not-null="false"
     * @return Returns the feature_six.
     */
    public String getFeature_six()
    {
        return feature_six;
    }

    /**
     * @param feature_six The feature_six to set.
     */
    public void setFeature_six(String feature_six)
    {
        this.feature_six = feature_six;
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
     * @hibernate.property column="PRODUCTDEFINITION_MANUFACTOR" type="string" length="20" not-null="false"
     * @return Returns the manufactor.
     */
    public String getManufactor()
    {
        return Manufactor;
    }

    /**
     * @param manufactor The manufactor to set.
     */
    public void setManufactor(String manufactor)
    {
        Manufactor = manufactor;
    }

    /**
     * @hibernate.property column="PRODUCTDEFINITION_PICTURE" type="string" length="500" not-null="false"
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

    public String getFormatCreateTime(){
        if(createTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(createTime);
        return date;
        }
    }
    
}
