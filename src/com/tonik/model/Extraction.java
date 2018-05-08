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
 * @author nimengfei
 * 
 * @hibernate.class table="Extraction"
 */

public class Extraction
{
    private Long id;
    
    private Product product;
    
    private Rules rules;
    
    private Date ExtractionTime;

    /**
     * @hibernate.id column="EXTRACTION_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.many-to-one column="PRODUCT" not-null="false" lazy="false" class="com.tonik.model.Product"
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

    /**
     * @hibernate.many-to-one column="RULES" not-null="false" lazy="false" class="com.tonik.model.Rules"
     * @return Returns the rules. 
     */
    public Rules getRules()
    {
        return rules;
    }

    /**
     * @param rules The rules to set.
     */
    public void setRules(Rules rules)
    {
        this.rules = rules;
    }

    /**
     * @hibernate.property column="EXTRACTION_EXTRACTIONTIME sql-type="Date" not-null="false"
     * @return Returns the ExtractionTime.
     */
    public Date getExtractionTime()
    {
        return ExtractionTime;
    }

    /**
     * @param extractionTime The extractionTime to set.
     */
    public void setExtractionTime(Date extractionTime)
    {
        ExtractionTime = extractionTime;
    }
    
    
}
