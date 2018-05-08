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
 * @author zby
 * 
 * @hibernate.class table="KEYWORD"
 */
public class KeyWord
{
    private Long id;
    private String name;
    private int category;
	// 关键字来源表中的id
    private Long categoryId;
    
    /**
     * @hibernate.id column="KEYWORD_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="KEYWORD_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="KEYWORD_CATAGORY" type="int" not-null="false"
     * @return Returns the category.
     */
    public int getCategory()
    {
        return category;
    }
    /**
     * @param category The category to set.
     */
    public void setCategory(int category)
    {
        this.category = category;
    }
    
    /**
     * @hibernate.property column="KEYWORD_CATAGORY_ID" type="long" not-null="false"
     * @return Returns the categoryid.
     */
    public Long getCategoryId()
    {
        return categoryId;
    }
    /**
     * @param categoryId The categoryId to set.
     */
    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }
    
    public String getCategoryStr(){
        String res = "";
        switch(category){
            case 1:res= "商品类别";break;
            case 2:res= "品牌";break;
            case 3:res = "国家地区";break;
            case 4:res="原料";break;
            case 5:res = "局部地区";break;
        }
        return res;
    }
    
}
