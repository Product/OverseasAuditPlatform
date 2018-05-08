package com.tonik.model;

import java.util.Date;

import com.tonik.Constant;

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
 * @hibernate.class table="POINTOFTIME"
 */
public class PointOfTime
{
    private Long id;
    
    private Date extractTime;

    /**
   * @hibernate.id column="POINTOFTIME_ID" type="long" unsaved-value="null" generator-class="identity"
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
   * @hibernate.property column="POINTOFTIME_EXTRACTTIME" sql-type="Date" not-null="false" 
   * @return Returns the ExtractTime.
   */
    public Date getExtractTime()
    {
        return extractTime;
    }

    /**
     * @param extractTime The extractTime to set.
     */
    public void setExtractTime(Date extractTime)
    {
        this.extractTime = extractTime;
    }
    
    public String getExtractTimeFormat()
    {
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(extractTime);
        return date;
    }
}
