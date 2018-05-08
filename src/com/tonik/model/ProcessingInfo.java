package com.tonik.model;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Apr 15, 2016
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="PROCESSING_INFO"
 */
public class ProcessingInfo//爬虫任务信息
{
    private Long id;
    private Long type;//任务类型
    private Long typeStamp;//时间戳
    
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
     * @hibernate.property column="URL" type="String" length="500" not-null="false"
     * @return Returns the type.
     */
    public Long getType()
    {
        return type;
    }
    public void setType(Long type)
    {
        this.type = type;
    }
    
    /**
     * @hibernate.property column="URL" type="long" length="50" not-null="false"
     * @return Returns the typeStamp.
     */
    public Long getTypeStamp()
    {
        return typeStamp;
    }
    public void setTypeStamp(Long typeStamp)
    {
        this.typeStamp = typeStamp;
    }
    
    
}
