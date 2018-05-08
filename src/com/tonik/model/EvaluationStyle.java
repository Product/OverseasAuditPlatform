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
 * @hibernate.class table="EVALUATIONSTYLE" lazy="true"
 */
public class EvaluationStyle
{
    private Long id;
    private String name;
    private EvaluationStyle parent;
    private EvaluationTree tree;


    /**
     * @hibernate.id column="EVALUATIONSTYLE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public EvaluationStyle setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.property column="EVALUATIONSTYLE_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="Parent" not-null="false" class="com.tonik.model.EvaluationStyle"
     * @return Returns the parent.
     */
    public EvaluationStyle getParent()
    {
        return parent;
    }

    public void setParent(EvaluationStyle parent)
    {
        this.parent = parent;
    }

    /**
     * @hibernate.many-to-one column="Tree" not-null="false" class="com.tonik.model.EvaluationTree"
     * @return Returns the tree.
     */
    public EvaluationTree getTree()
    {
        return tree;
    }

    public void setTree(EvaluationTree tree)
    {
        this.tree = tree;
    }

}
