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
 * @author zby
 * 
 * @hibernate.class table="TEST"
 */
public class Test
{
    private Long id;
    private String name;
    private int num;
    private int score;
    private String remark;
    private TestStyle testtype;
    private Date starttime;
    private Date endtime;
    private UserInfo createperson;
    
    /**
     * @hibernate.id column="TEST_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="TEST_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="TEST_NUM" type="int" length="50" not-null="false"
     * @return Returns the num.
     */
    public int getNum()
    {
        return num;
    }
    
    /**
     * @param num The num to set.
     */
    public void setNum(int num)
    {
        this.num = num;
    }
    
    /**
     * @hibernate.property column="TEST_REMARK" type="string" length="50" not-null="false"
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
     * @hibernate.many-to-one column="TESTSTYLE" not-null="false" lazy="false" class="com.tonik.model.TestStyle"
     * @return Returns the testtype.
     */
    public TestStyle getTesttype()
    {
        return testtype;
    }
    
    /**
     * @param testtype The testtype to set.
     */
    public void setTesttype(TestStyle testtype)
    {
        this.testtype = testtype;
    }
    
    /**
     * @hibernate.property column="TEST_STARTTIME" sql-type="Date" not-null="false"
     * @return Returns the starttime.
     */
    public Date getStarttime()
    {
        return starttime;
    }
    
    /**
     * @param starttime The starttime to set.
     */
    public void setStarttime(Date starttime)
    {
        this.starttime = starttime;
    }
    
    /**
     * @hibernate.property column="TEST_ENDTIME" sql-type="Date" not-null="false"
     * @return Returns the endtime.
     */
    public Date getEndtime()
    {
        return endtime;
    }
    
    /**
     * @param endtime The endtime to set.
     */
    public void setEndtime(Date endtime)
    {
        this.endtime = endtime;
    }

    /**
     * @hibernate.property column="TEST_SCORE" type="int" length="50" not-null="false"
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
     * @hibernate.many-to-one column="TEST_CREATEPERSON" not-null="false" lazy="false" class="com.tonik.model.UserInfo"
     * @return Returns the createperson.
     */
    public UserInfo getCreateperson()
    {
        return createperson;
    }

    /**
     * @param createperson The createperson to set.
     */
    public void setCreateperson(UserInfo createperson)
    {
        this.createperson = createperson;
    }
    
    public String getCreatePersonName(){
        if(createperson == null)
            return null;
        return createperson.getRealName();
    }
    
    public Long getTestTypeId(){
        if(testtype == null)
            return null;
        return testtype.getId();
    }
    
    public int getTestStyleType(){
        if(testtype == null)
            return 0;
        return testtype.getType();
    }
    
    public String getFormatStartTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(starttime);
        return date;
    }
    
    public String getFormatEndTime(){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endtime);
        return date;
    }
    
}
