package com.tonik.model;

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
 * @hibernate.class table="PROCATEGORY"
 */

public class ProCategory
{
    private Long id;
    
    private String key;//属性关键字
    
    private String value;//属性值
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="CATEGORY_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    /**
     * @param key The key to set.
     */
    
    public void setKey(String key)
    {
        this.key = key;
    }
    /**
     * @hibernate.property column="CATEGORY_KEY" type="string" unsaved-value="null" generator-class="identity"
     * @return Returns the key.
     */
    
    public String getKey()
    {
        return key;
    }
    /**
     * @param value The value to set.
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * @hibernate.property column="CATEGORY_VALUE" type="string" unsaved-value="null" generator-class="identity"
     * @return Returns the value.
     */
    public String getValue()
    {
        return value;
    }

   
}
