package com.tonik.model;

/**
 * @hibernate.class table="EVALUATION_MANAGEMENT"
 */
public class EvaluationManagement
{
    private Long id;
    
    private EvaluationIndex evaluationIndex;
    
    private Long evaluationObjectId;
    
    private Integer evaluationType;
    
    private Float evaluationIndexWeight;

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.many-to-one column="EVALUATIONINDEX_ID" not-null="true" lazy="false" class="com.tonik.model.EvaluationIndex"
     * @return Returns the evaluationIndex.
     */
    public EvaluationIndex getEvaluationIndex()
    {
        return evaluationIndex;
    }

    public void setEvaluationIndex(EvaluationIndex evaluationIndex)
    {
        this.evaluationIndex = evaluationIndex;
    }

    /**
     * @hibernate.property column="EVALUATIONOBJECT_ID" type="long" not-null="false"
     * @return Returns the evaluationObjectId.
     */
    public Long getEvaluationObjectId()
    {
        return evaluationObjectId;
    }

    public void setEvaluationObjectId(Long evaluationObjectId)
    {
        this.evaluationObjectId = evaluationObjectId;
    }

    /**
     * @hibernate.property column="EVALUATION_TYPE" type="int" not-null="true"
     * @return Returns the evaluationType.
     */
    public Integer getEvaluationType()
    {
        return evaluationType;
    }

    public void setEvaluationType(Integer evaluationType)
    {
        this.evaluationType = evaluationType;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_WEIGHT" type="float" not-null="true"
     * @return Returns the evaluationIndexWeight.
     */
    public Float getEvaluationIndexWeight()
    {
        return evaluationIndexWeight;
    }

    public void setEvaluationIndexWeight(Float evaluationIndexWeight)
    {
        this.evaluationIndexWeight = evaluationIndexWeight;
    }    
}
