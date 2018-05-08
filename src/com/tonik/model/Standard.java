package com.tonik.model;

import java.util.Date;

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
 * @hibernate.class table="STANDARD"
 */
public class Standard// 匹配标准
{
    private Long id;
    private String name;// 标准名称
    private String agency;// 出台机构
    private String remark;// 备注
    private Integer system;// 系统
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间


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
     * @hibernate.property column="NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="AGENCY" type="string" length="50" not-null="false"
     * @return Returns the agency.
     */
    public String getAgency()
    {
        return agency;
    }

    public void setAgency(String agency)
    {
        this.agency = agency;
    }

    /**
     * @hibernate.property column="REMARK" type="string" length="500" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * @hibernate.property column="SYSTEM" type="int" not-null="false"
     * @return Returns the system.
     */
    public Integer getSystem()
    {
        return system;
    }

    public void setSystem(Integer system)
    {
        this.system = system;
    }

    /**
     * @hibernate.property column="CREATE_TIME" sql-type="Date" not-null="false"
     * @return Returns the createTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @hibernate.property column="UPDATE_TIME" sql-type="Date" not-null="false"
     * @return Returns the updateTime.
     */
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

}
