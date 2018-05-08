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
 * @hibernate.class table="EVALUATION"
 */
public class Evaluation//评价
{
    private Long id;

    private Website website;
    
    private int score;//总分
    
    private int evaluationStatus;//状态
    
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
     * @hibernate.id column="EVALUATION_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.many-to-one column="WEBSITE" not-null="false" lazy="false" class="com.tonik.model.Website"
     * @return Returns the website.
     */
    public Website getWebsite()
    {
        return website;
    }

    public void setWebsite(Website website)
    {
        this.website = website;
    }

    /**
     * @hibernate.property column="EVALUATIONSCORE" type="int" not-null="false"
     * @return Returns the score.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * @param score The score to set.
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * @hibernate.property column="EVALUATIONSTATUS" type="int" not-null="false"
     * @return Returns the evaluationStatus.
     */
    public int getEvaluationStatus()
    {
        return evaluationStatus;
    }

    /**
     * @param evaluationStatus The evaluationStatus to set.
     */
    public void setEvaluationStatus(int evaluationStatus)
    {
        this.evaluationStatus = evaluationStatus;
    }

    /**
     * @hibernate.property column="EVALUATIONREMARK" type="string" length="1000" not-null="false"
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
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.many-to-one column="USERINFO" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
     * @return Returns the createPerson.
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
     * @hibernate.property column="EVALUATIONCREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public String getCreatePersonName(){
        if(createPerson == null)
            return null;
        else
            return createPerson.getRealName();
    }
    
    public String getFormatCreateTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    }
}
