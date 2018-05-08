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
 * @author yekai
 * 
 * @hibernate.class table="REPORT"
 */
public class Report//报告输出
{
    private Long id;
    
    private String name;//报告名
    
    private ReportTemplate reportTemplate;//报告模板
    
    private String reportContent;//内容
    
    private String evaluationFileName;//文件名
    
    private byte[] evaluationFile;
    
    private String reportStatus;//报告状态
    
    private UserInfo createPerson;
    
    private Date createTime;
    
    /**
     * @hibernate.id column="REPORT_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="REPORT_NAME" type="string" length="50" not-null="false"
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
     *@hibernate.many-to-one column="REPORTTEMPLATE_ID" not-null="false"  lazy="false" class="com.tonik.model.ReportTemplate" cascade="all" outer-join="true"
     *@return
     */
    public ReportTemplate getReportTemplate()
    {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplate reportTemplate)
    {
        this.reportTemplate = reportTemplate;
    }

    /**
     * @hibernate.property column="REPORT_CONTENT" type="string" length="5000" not-null="false"
     * @return Returns the id.
     */
    public String getReportContent()
    {
        return reportContent;
    }

    /**
     * @param id The id to set.
     */
    public void setReportContent(String reportContent)
    {
        this.reportContent = reportContent;
    }

    /**
     * @hibernate.property column="EVALUATIONFILENAME" type="string" length="50" not-null="false"
     * @return Returns the evaluationFileName.
     */
    public String getEvaluationFileName()
    {
        return evaluationFileName;
    }
    
    /**
     * @param evaluationFileName The evaluationFileName to set.
     */
    public void setEvaluationFileName(String evaluationFileName)
    {
        this.evaluationFileName = evaluationFileName;
    }

    /**
     * @hibernate.property column="EVALUATIONFILE" type="org.springframework.orm.hibernate3.support.BlobByteArrayType" not-null="false" lazy="true" not-null="false"
     * @return Returns the evaluationFile.
     */
    public byte[] getEvaluationFile()
    {
        return evaluationFile;
    }

    /**
     * @param evaluationFile The evaluationFile to set.
     */
    public void setEvaluationFile(byte[] evaluationFile)
    {
        this.evaluationFile = evaluationFile;
    }

    /**
     * @hibernate.property column="REPORT_STATUS" type="string" not-null="false"
     * @return Returns the id.
     */
    public String getReportStatus()
    {
        return reportStatus;
    }

    /**
     * @param id The id to set.
     */
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
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
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(UserInfo createPerson)
    {
        this.createPerson = createPerson;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="REPORT_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    //返回createPerson.RealName
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
