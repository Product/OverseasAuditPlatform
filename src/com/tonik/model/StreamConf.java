package com.tonik.model;
/**
 * @hibernate.class table="streamConf"
 * @author fuzhi
 *
 */
public class StreamConf
{
    private Long id;
    private String property;
    private String value;
    private String detail;
    /**
     * @hibernate.id column="stream_id" type="long" unsaved-value="null" generator-class="identity"
     * @return
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
     * @hibernate.property column="property" type="string" length="255" not-null="false"
     * @return
     */
    public String getProperty()
    {
        return property;
    }
    public void setProperty(String property)
    {
        this.property = property;
    }
    /**
     * @hibernate.property column="value" type="string" length="255" not-null="false"
     * @return
     */
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * @hibernate.property column="detail" type="string" length="255" not-null="false"
     * @return
     */
    public String getDetail()
    {
        return detail;
    }
    public void setDetail(String detail)
    {
        this.detail = detail;
    }
    
}
