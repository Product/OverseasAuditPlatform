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
 * @hibernate.class table="TESTTARGET"
 */
public class TestTarget
{
    private Long id;
    //1:website
    //2:product
    private Test test;  
    private Website website;
    private int type;
    private Product product;
    
    /**
     * @hibernate.id column="TESTTARGET_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.many-to-one column="TEST" not-null="true" lazy="true" class="com.tonik.model.Test"
     * @return Returns the test.
     */
    public Test getTest()
    {
        return test;
    }
    

    /**
     * @param test The test to set.
     */
    public void setTest(Test test)
    {
        this.test = test;
    }
    
    /**
     * @hibernate.many-to-one column="TESTTARGET_WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
   public Website getWebsite()
    {
        return website;
    }
    
    /**
     * @param website The website to set.
     */
    public void setWebsite(Website website)
    {
        this.website = website;
    }

    /**
     * @hibernate.property column="TESTTARGET_TYPE" type="int" not-null="false" 
     * @return Returns the type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @hibernate.many-to-one column="TESTTARGET_PRODUCT" not-null="false" lazy="false" class="com.tonik.model.Product"
     * @return Returns the product.
     */
    public Product getProduct()
    {
        return product;
    }

    /**
     * @param product The product to set.
     */
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    
    
    
}
