package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * </p>
 * @since May 20, 2016
 * @version 1.0
 * @author panweijun
 * 
 * @hibernate.class table="SPRINGQTZ"
 */
public class SpringQTZ
{
    private Long id;
    private String name;
    private int status;
    private Date runtime;

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
     * @hibernate.property column="NAME" type="string" length="10" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @hibernate.property column="STATUS" type="int" not-null="false"
     * @return Returns the affectArea.
     */
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    /**
     * @hibernate.property column="RUN_TIME" sql-type="Date" not-null="false"
     * @return Returns the purchaseTime.
     */
    public Date getRuntime()
    {
        return runtime;
    }
    
    public void setRuntime(Date runtime)
    {
        this.runtime = runtime;
    }    
    
}
