package com.tonik.model;

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
 * @Entity
 * @hibernate.class table="REL_PRODUCT_MATERIAL"
 */
public class RelProductMaterial//商品配料对应
{
    private Long Id;//id
    private Long productId;//商品
    private Long materialId;//配料
    private float content;//配料含量
    private String unit;//配料单位
    
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return Id;
    }
    public void setId(Long id)
    {
        Id = id;
    }

    
    /**
     * @hibernate.property column="PRODUCT_ID" type="long" not-null="false"
     * @return Returns the productId.
     */
    public Long getProductId()
    {
        return productId;
    }
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }
    
    /**
     * @hibernate.property column="MATERIAL_ID" type="long" not-null="false"
     * @return Returns the materialId.
     */
    public Long getMaterialId()
    {
        return materialId;
    }
    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }
    
    /**
     * @hibernate.property column="MATERIAL_CONTENT" type="float" length="5000" not-null="false"
     * @return Returns the content.
     */
    public float getContent()
    {
        return content;
    }
    public void setContent(float content)
    {
        this.content = content;
    }
    
    /**
     * @hibernate.property column="UNIT" type="string" length="500" not-null="false"
     * @return Returns the unit.
     */
    public String getUnit()
    {
        return unit;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    } 
}