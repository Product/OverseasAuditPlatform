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
 * @hibernate.class table="STATE"
 */
public class State
{

    private Long id;

    private String stateName;

    private String stateCode;


    /**
     * @hibernate.id column="STATE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    protected void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="STATE_CODE" type="string" length="50" not-null="false"
     * @return Returns the stateCode.
     */
    public String getStateCode()
    {
        return stateCode;
    }

    /**
     * @param stateCode The stateCode to set.
     */
    public void setStateCode(String stateCode)
    {
        this.stateCode = stateCode;
    }

    /**
     * @hibernate.property column="STATE_NAME" type="string" length="50" not-null="false"
     * @return Returns the stateName.
     */
    public String getStateName()
    {
        return stateName;
    }

    /**
     * @param stateName The stateName to set.
     */
    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }
}// EOF
