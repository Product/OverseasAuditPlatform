package com.tonik.model;

/**
 * @hibernate.class table="EvaluationTree" lazy="true"
 */
public class EvaluationTree
{
    private Long id;
    private String name;


    /**
     * @hibernate.id column="Id" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public EvaluationTree setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="Name" type="string" length="50" not-null="false"
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
}
