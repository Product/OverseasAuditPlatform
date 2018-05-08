package com.tonik.model;

import java.util.Date;

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
 * @hibernate.class table="DATAGATHER"
 */
public class DataGather
{
    private Long id;

    private Website website;
    
    private Long tnum;//采集任务数量
    
    private Long pnum;//商品数量
    
    //0：空闲
    //1：正在执行
    //2：异常
    private int state; //任务状态
    
    private String remark;
    
    private UserInfo createPerson;
    
    private Date createTime;
   
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="GATHER_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    
    /**
     * @hibernate.many-to-one column="GATHER_WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }

    /**
     * @param website The website to set.
     */
    public void setWebsite(Website website)
    {
        this.website = website;
    }

    /**
     *@hibernate.property column="GATHER_TARGETNUM" type="long" not-null="false" 
     * @return Returns the tnum.
     */
    public Long getTnum()
    {
        return tnum;
    }

    /**
     * @param tnum The tnum to set.
     */
    public void setTnum(Long tnum)
    {
        this.tnum = tnum;
    }
    /**
     * @hibernate.property column="GATHER_PRODUCTNUM" type="long" not-null="false" 
     * @return Returns the pnum.
     */
    public Long getPnum()
    {
        return pnum;
    }

    /**
     * @param pnum The pnum to set.
     */
    public void setPnum(Long pnum)
    {
        this.pnum = pnum;
    }

    /**
     * @hibernate.property column="GATHER_STATE" type="int" not-null="false"
     * @return Returns the state.
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state The state to set.
     */
    public void setState(int state)
    {
        this.state = state;
    }
   
    /**
     * @hibernate.property column="GATHER_REMARK" type="string" length="2000" not-null="false"
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.many-to-one column="GATHER_CREATEPERSON" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
     * @return Returns the website.
     */
    public UserInfo getCreatePerson()
    {
        return createPerson;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="GATHER_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    public String getStrState()
    {
        String strState = "";
        switch(state){
            case 0:
                strState = "空闲";break;
            case 1:
                strState = "执行中";break;
            case 2:
                strState = "异常";break;
        }
        return strState;
    }

    public String getStrPnum()
    {
        if(state == 0 || state == 2)
            return pnum.toString();
        else
            return "--";
    }

    public String getStrTnum()
    {
        if(state == 0 || state == 2)
            return tnum.toString();
        else
            return "--";
    }   
    
}

