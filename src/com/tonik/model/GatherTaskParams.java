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
 * @hibernate.class table="GATHERTASKPARAMS"
 */
public class GatherTaskParams
{
    private Long id;
    
    private String key;
    
    private String val;

    private Long gtid;
    /**
     * @hibernate.id column="TASKPARAMS_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="TASKPARAMS_KEY" type="string" length="500" not-null="false"
     * @return Returns the name.
     */
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @hibernate.property column="TASKPARAMS_VAL" type="string" length="500" not-null="false"
     * @return Returns the name.
     */
    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
    }

    /**
     * @hibernate.property column="TASKPARAMS_GATHERTASKID" type="long" not-null="false"
     * @return Returns the gtid.
     */
    public Long getGtid()
    {
        return gtid;
    }

    public void setGtid(Long gtid)
    {
        this.gtid = gtid;
    }
    
}
