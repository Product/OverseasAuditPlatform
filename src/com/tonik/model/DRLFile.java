package com.tonik.model;

import java.util.Date;

/**
 * <p>
 * Title: Thinvent Integration
 * </p>
 * <p>
 * Description: This is a model object for example.
 * </p>
 * @since Jun 26, 2016
 * @version 1.0
 * @author yekai
 * @hibernate.class table="DRLFILE"
 */
public class DRLFile
{    
    private Long id;
    
    private String filename;//文件名称
    
    private byte[] content;//文件内容
        
    private Date updateTime;//更新时间
    
    private Date createTime;//创建时间

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
     * @hibernate.property column="FILENAME" type="string" length="100" not-null="false"
     * @return Returns the filename.
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @hibernate.property column="CONTENT" type="com.thinvent.rules.dao.broker.hibernate.type.BinaryBlobType" not-null="false" lazy="true"
     * @return Returns the content.
     */
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @hibernate.property column="UPDATETIME" sql-type="Date" not-null="false"
     * @return Returns the updateTime.
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @hibernate.property column="CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the createTime.
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
