package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description:  事件类别
 * </p>
 * @since Nov 02, 2015
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTTYPE"
 */
public class EventType
{
    private Long id;  //事件类别id
    
    private String name;//事件类别名称
    
    private int affectArea;//事件是否影响地区，0代表不影响，1代表影响，下同

    private int affectWebsite;//事件是否影响网站
    
    private int affectBrand;//事件是否影响品牌
    
    private int affectProductType;//事件是否影响商品类别

    private int affectMaterial; 
    /**
     * @hibernate.id column="EVENTTYPEID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="EVENTTYPENAME" type="string" length="50" not-null="false"
     * @return Returns the name.
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
     * @hibernate.property column="TYPEAFFECT_AREA" type="int" not-null="false"
     * @return Returns the affectArea.
     */
    public int getAffectArea()
    {
        return affectArea;
    }

    public void setAffectArea(int affectArea)
    {
        this.affectArea = affectArea;
    }

    /**
     * @hibernate.property column="TYPEAFFECT_WEBSITE" type="int" not-null="false"
     * @return Returns the affectWebsite.
     */
    public int getAffectWebsite()
    {
        return affectWebsite;
    }

    public void setAffectWebsite(int affectWebsite)
    {
        this.affectWebsite = affectWebsite;
    }

    /**
     * @hibernate.property column="TYPEAFFECT_BRAND" type="int" not-null="false"
     * @return Returns the affectBrand.
     */
    public int getAffectBrand()
    {
        return affectBrand;
    }

    public void setAffectBrand(int affectBrand)
    {
        this.affectBrand = affectBrand;
    }

    /**
     * @hibernate.property column="TYPEAFFECT_PRODUCTTYPE" type="int" not-null="false"
     * @return Returns the affectProductType.
     */
    public int getAffectProductType()
    {
        return affectProductType;
    }

    public void setAffectProductType(int affectProductType)
    {
        this.affectProductType = affectProductType;
    }

    /**
     * @hibernate.property column="TYPEAFFECT_MATERIAL" type="int" not-null="false"
     * @return Returns the affectProductType.
     */
    public int getAffectMaterial()
    {
        return affectMaterial;
    }

    public void setAffectMaterial(int affectMaterial)
    {
        this.affectMaterial = affectMaterial;
    }
    
    
}
