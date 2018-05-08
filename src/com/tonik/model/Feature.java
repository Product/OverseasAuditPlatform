package com.tonik.model;

import java.io.Serializable;

/**
 * @hibernate.class table="FEATURE"
 */
public class Feature implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;


    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    public Feature setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="NAME" type="string" length="20" not-null="true"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    public Feature setName(String name)
    {
        this.name = name;
        return this;
    }
}
