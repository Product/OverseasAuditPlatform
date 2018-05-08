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
 * @hibernate.class table="EVALUATIONINDEX"
 */
public class EvaluationIndex// 评价指标
{
    private Long id;

    private String name;// 指标名

    private EvaluationStyle evaluationStyle;// 评价类型

    private Integer weight;// 权重

    private String remark;

    private UserInfo createPerson;

    private Date createTime;

    private Integer type;

    private Integer none_min;

    private Integer none_max;

    private Integer one_min;

    private Integer one_max;

    private Integer two_min;

    private Integer two_max;

    private Integer three_min;

    private Integer three_max;

    private Integer score_yes;

    private Integer score_no;


    /**
     * @param id The id to set.
     */
    public EvaluationIndex setId(Long id)
    {
        this.id = id;
        return this;
    }

    /**
     * @hibernate.id column="EVALUATIONINDEX_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @hibernate.many-to-one column="EVALUATIONSTYLE_ID" not-null="false" lazy="false" class="com.tonik.model.EvaluationStyle"
     * @return Returns the evaluationStyle.
     */
    public EvaluationStyle getEvaluationStyle()
    {
        return evaluationStyle;
    }

    /**
     * @param evaluationStyle The evaluationStyle to set.
     */
    public void setEvaluationStyle(EvaluationStyle evaluationStyle)
    {
        this.evaluationStyle = evaluationStyle;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_WEIGHT" type="int" not-null="false"
     * @return Returns the weight.
     */
    public Integer getWeight()
    {
        return weight;
    }

    /**
     * @param weight The weight to set.
     */
    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_REMARK" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="EVALUATIONINDEX_CREATEPERSON" not-null="false" cascade="none" lazy="false" class="com.tonik.model.UserInfo"
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
     * @hibernate.property column="EVALUATIONINDEX_CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_TYPE" type="int" not-null="false"
     * @return Returns the type.
     */
    public Integer getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(Integer type)
    {
        this.type = type;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_NONE_MIN" type="int" not-null="false"
     * @return Returns the none_min.
     */
    public Integer getNone_min()
    {
        return none_min;
    }

    /**
     * @param none_min The none_min to set.
     */
    public void setNone_min(Integer none_min)
    {
        this.none_min = none_min;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_NONE_MAX" type="int" not-null="false"
     * @return Returns the none_max.
     */
    public Integer getNone_max()
    {
        return none_max;
    }

    /**
     * @param none_max The none_max to set.
     */
    public void setNone_max(Integer none_max)
    {
        this.none_max = none_max;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_ONE_MIN" type="int" not-null="false"
     * @return Returns the one_min.
     */
    public Integer getOne_min()
    {
        return one_min;
    }

    /**
     * @param one_min The one_min to set.
     */
    public void setOne_min(Integer one_min)
    {
        this.one_min = one_min;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_ONE_MAX" type="int" not-null="false"
     * @return Returns the one_max.
     */
    public Integer getOne_max()
    {
        return one_max;
    }

    /**
     * @param one_max The one_max to set.
     */
    public void setOne_max(Integer one_max)
    {
        this.one_max = one_max;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_TWO_MIN" type="int" not-null="false"
     * @return Returns the two_min.
     */
    public Integer getTwo_min()
    {
        return two_min;
    }

    /**
     * @param two_min The two_min to set.
     */
    public void setTwo_min(Integer two_min)
    {
        this.two_min = two_min;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_TWO_MAX" type="int" not-null="false"
     * @return Returns the two_max.
     */
    public Integer getTwo_max()
    {
        return two_max;
    }

    /**
     * @param two_max The two_max to set.
     */
    public void setTwo_max(Integer two_max)
    {
        this.two_max = two_max;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_THREE_MIN" type="int" not-null="false"
     * @return Returns the three_min.
     */
    public Integer getThree_min()
    {
        return three_min;
    }

    /**
     * @param three_min The three_min to set.
     */
    public void setThree_min(Integer three_min)
    {
        this.three_min = three_min;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_THREE_MAX" type="int" not-null="false"
     * @return Returns the three_max.
     */
    public Integer getThree_max()
    {
        return three_max;
    }

    /**
     * @param three_max The three_max to set.
     */
    public void setThree_max(Integer three_max)
    {
        this.three_max = three_max;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_SCORE_YES" type="int" not-null="false"
     * @return Returns the score_yes.
     */
    public Integer getScore_yes()
    {
        return score_yes;
    }

    /**
     * @param score_yes The score_yes to set.
     */
    public void setScore_yes(Integer score_yes)
    {
        this.score_yes = score_yes;
    }

    /**
     * @hibernate.property column="EVALUATIONINDEX_SCORE_NO" type="int" not-null="false"
     * @return Returns the score_no.
     */
    public Integer getScore_no()
    {
        return score_no;
    }

    /**
     * @param score_no The score_no to set.
     */
    public void setScore_no(Integer score_no)
    {
        this.score_no = score_no;
    }

    public String getCreatePersonName()
    {
        if (createPerson == null)
            return null;
        else
            return createPerson.getRealName();
    }

    public Long getEvaluationStyleId()
    {
        if (evaluationStyle == null)
            return null;
        else
            return evaluationStyle.getId();
    }

    public String getEvaluationStyleName()
    {
        if (evaluationStyle == null)
            return null;
        else
            return evaluationStyle.getName();
    }

    public String getFormatCreateTime()
    {
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return date;
    }
}
