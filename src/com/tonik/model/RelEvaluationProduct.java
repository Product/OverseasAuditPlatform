package com.tonik.model;

import java.io.Serializable;

/**
 * @hibernate.class table="REL_EVALUATION_PRODUCT"
 */
public class RelEvaluationProduct implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Long productId;
        
    private Long evaluationManagementId;
    
    private Double grade;
    
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public RelEvaluationProduct setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="PRODUCT_ID" type="long" not-null="true"
     * @return Returns the productId.
     */
    public Long getProductId()
    {
        return productId;
    }

    public RelEvaluationProduct setProductId(Long productId)
    {
        this.productId = productId;
        return this;
    }

    /**
     * @hibernate.property column="EVALUATION_MANAGEMENT_ID" type="long" not-null="true"
     * @return Returns the evaluationManagementId.
     */
    public Long getEvaluationManagementId()
    {
        return evaluationManagementId;
    }

    public RelEvaluationProduct setEvaluationManagementId(Long evaluationManagementId)
    {
        this.evaluationManagementId = evaluationManagementId;
        return this;
    }

    /**
     * @hibernate.property column="GRADE" type="double" not-null="true" 
     * @return Returns the grade.
     */
    public Double getGrade()
    {
        return grade;
    }

    public RelEvaluationProduct setGrade(Double grade)
    {
        this.grade = grade;
        return this;
    }
}

