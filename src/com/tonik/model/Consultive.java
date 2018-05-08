package com.tonik.model;

import java.util.Date;

import com.tonik.Constant;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 咨询建议
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @hibernate.class table="CONSULTIVE"
 */
public class Consultive
{
    private Long id;

    private int type;//类型  咨询或建议
    
    private String title;//标题
    
    private String content;//内容
    
    private Date returnTime;//回复时间
    
    private String returnContent;//回复内容
    
    private Long consultant;//咨询人
    
    private Date consultiveTime;//咨询时间
    
    private String createPerson;
    
    private Date createTime;
    
    private Long parentNod;//父节点
    
    private Long RootNod;//根节点
    

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="CONSULTIVE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.property column="CONSULTIVE_TYPE" type="int" not-null="false"
     * @return Returns the type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @hibernate.property column="CONSULTIVE_TITLE" type="string" length="50" not-null="false"
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param type The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @hibernate.property column="CONSULTIVE_CONTENT" type="string" length="50" not-null="false"
     * @return Returns the content.
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content The content to set.
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * @hibernate.property column="CONSULTIVE_RETURNTIME" sql-type="Date" not-null="false"
     * @return Returns the returnTime.
     */
    public Date getReturnTime()
    {
        return returnTime;
    }

    /**
     * @param returnTime The returnTime to set.
     */
    public void setReturnTime(Date returnTime)
    {
        this.returnTime = returnTime;
    }

    /**
     * @hibernate.property column="CONSULTIVE_RETURNCONTENT" type="string" length="50" not-null="false"
     * @return Returns the returnContent.
     */
    public String getReturnContent()
    {
        return returnContent;
    }

    /**
     * @param returnContent The returnContent to set.
     */
    public void setReturnContent(String returnContent)
    {
        this.returnContent = returnContent;
    }

    /**
     * @hibernate.property column="CONSULTIVE_CONSULTANT" type="long" not-null="false"
     * @return Returns the consultant.
     */
    public Long getConsultant()
    {
        return consultant;
    }

    /**
     * @param consultant The consultant to set.
     */
    public void setConsultant(Long consultant)
    {
        this.consultant = consultant;
    }

    /**
     * @hibernate.property column="CONSULTIVE_CONSULTIVETIME" sql-type="Date" not-null="false"
     * @return Returns the consultiveTime.
     */
    public Date getConsultiveTime()
    {
        return consultiveTime;
    }

    /**
     * @param consultiveTime The consultiveTime to set.
     */
    public void setConsultiveTime(Date consultiveTime)
    {
        this.consultiveTime = consultiveTime;
    }
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="CONSULTIVE_CREATEPERSON" type="string" length="50" not-null="false"
     * @return Returns the createPerson.
     */
    public String getCreatePerson()
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
     * @hibernate.property column="CONSULTIVE_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * @hibernate.property column="CONSULTIVE_PARENTNOD" type="long" not-null="false"
     * @return Returns the ParentNod.
     */
    public Long getParentNod()
    {
        return parentNod;
    }
    /**
     * @param parentNod The parentNod to set.
     */
    public void setParentNod(Long parentNod)
    {
        this.parentNod = parentNod;
    }
    /**
     * @hibernate.property column="CONSULTIVE_ROOTNOD" type="long" not-null="false" generator-class="identity"
     * @return Returns the RootNod.
     */
    public Long getRootNod()
    {
        return RootNod;
    }
    /**
     * @param rootNod The rootNod to set.
     */
    public void setRootNod(Long rootNod)
    {
        RootNod = rootNod;
    }
    
    public String getFormatCreateTime(){
        if(createTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(createTime);
        return date;
        }
    }
    
    public String getFormatReturnTime(){
        if(returnTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(returnTime);
        return date;}
    }
    
    public String getFormatConsultiveTime(){
        if(consultiveTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(consultiveTime);
        return date;
        }
    }
}
