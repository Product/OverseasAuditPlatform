package com.tonik.model;

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
 * @author nimengfei
 * 
 * @hibernate.class table="MATERIAL"
 */
public class Material
{
    private Long id;
    
    private String name;
    
    private MaterialType materialtype;
    
    private Set<ProductDefinition> productDefinitions;
    
    private String remark;
    
  //新增字段 from Ly
    private Set<Rules> rules;

    /**
     * @hibernate.id column="MATERIAL_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="MATERIAL_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="MATERIALTYPE" not-null="false" lazy="false" class="com.tonik.model.MaterialType"
     * @return Returns the materialtype.
     */
    public MaterialType getMaterialtype()
    {
        return materialtype;
    }

    /**
     * @param materialtype The materialtype to set.
     */
    public void setMaterialtype(MaterialType materialtype)
    {
        this.materialtype = materialtype;
    }

    /**
     * @hibernate.set name="productDefinitions" table="PRODUCTDEFMATERIALS" lazy="false"
     * @hibernate.collection-many-to-many column="PRODUCTDEFINITION_ID" class="com.tonik.model.ProductDefinition"
     * @hibernate.collection-key column="MATERIAL_ID"
     * @return Returns the productDefinitions.
     */
    public Set<ProductDefinition> getProductDefinitions()
    {
        return productDefinitions;
    }

    public void setProductDefinitions(Set<ProductDefinition> productDefinitions)
    {
        this.productDefinitions = productDefinitions;
    }

    /**
     * @hibernate.property column="MATERIAL_REMARK" type="string" length="50" not-null="false"
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
    
  //from Ly
    /**
     * @hibernate.set name="rules" table="MATERIALRULES" lazy="false" 
     * @hibernate.collection-many-to-many column="RULES_ID" class="com.tonik.model.Rules"
     * @hibernate.collection-key column="MATERIAL_ID"
     * @return Returns the rules.
     */
    public Set<Rules> getRules()
    {
        return rules;
    }

    public void setRules(Set<Rules> rules)
    {
        this.rules = rules;
    }
}
