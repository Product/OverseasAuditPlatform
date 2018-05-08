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
 * @hibernate.class table="VENTUREANALYSIS"
 */
public class VentureAnalysis//预定分析、分析报告
{
    private Long id;
    
    private String name;//分析、报告名称
    
    private VentureAnalysisStyle style;//分析类别
   
    private WebsiteStyle websiteStyle;//网站类别
    
    private Website website;//分析网站
    
    private String analysisFileName;//分析报告名称
    
    private byte[] analysisFile;//分析报告文件
    
    private int releaseState;//发布状态
    
    private String illustration;//说明
    
    private String template;//使用模板
    
    private UserInfo createPerson;//创建人
    
    private Date createTime;//创建时间
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @hibernate.property column="NAME" type="string" length="50" not-null="false"
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
    * @hibernate.many-to-one column="VENTUREANALYSISSTYLE_ID" not-null="false" cascade="none" class="com.tonik.model.VentureAnalysisStyle" 
    * @return Returns the Question.
    */
    public VentureAnalysisStyle getStyle()
    {
        return style;
    }

    /**
     * @param style The style to set.
     */
    public void setStyle(VentureAnalysisStyle style)
    {
        this.style = style;
    }

    /**
     * @hibernate.property column="ANALYSISFILENAME" type="string" length="50" not-null="false"
     * @return Returns the analysisFileName.
     */
    public String getAnalysisFileName()
    {
        return analysisFileName;
    }

    /**
     * @param analysisFileName The analysisFileName to set.
     */
    public void setAnalysisFileName(String analysisFileName)
    {
        this.analysisFileName = analysisFileName;
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
     *@hibernate.many-to-one column="WEBSITESTYLE_ID" not-null="false"  lazy="false" class="com.tonik.model.WebsiteStyle" 
     *                        cascade="all" outer-join="true"
     * @return
     */
    public WebsiteStyle getWebsiteStyle()
    {
        return websiteStyle;
    }

    public void setWebsiteStyle(WebsiteStyle websiteStyle)
    {
        this.websiteStyle = websiteStyle;
    }
    
    /**
     * @hibernate.property column="ANALYSISFILE"type="org.springframework.orm.hibernate3.support.BlobByteArrayType" not-null="false" lazy="true" not-null="false"
     * @return Returns the analysisFile.
     */
    public byte[] getAnalysisFile()
    {
        return analysisFile;
    }

    /**
     * @param analysisFile The analysisFile to set.
     */
    public void setAnalysisFile(byte[] analysisFile)
    {
        this.analysisFile = analysisFile;
    }
   
    /**
     * @hibernate.property column="RELEASESTATE" type="string" length="2000" not-null="false"
     * @return Returns the releaseState.
     */
    public int getReleaseState()
    {
        return releaseState;
    }

    /**
     * @param illustration The illustration to set.
     */
    public void setReleaseState(int releaseState)
    {
        this.releaseState = releaseState;
    }
        
    /**
     * @hibernate.property column="ILLUSTRATION" type="string" length="2000" not-null="false"
     * @return Returns the illustration.
     */
    public String getIllustration()
    {
        return illustration;
    }

    /**
     * @param illustration The illustration to set.
     */
    public void setIllustration(String illustration)
    {
        this.illustration = illustration;
    }

    
    /**
     * @hibernate.property column="ANALYSISFILETEMPLATE" type="string" length="50" not-null="false"
     * @return Returns the template.
     */
    public String getTemplate()
    {
        return template;
    }

    /**
     * @param template The template to set.
     */
    public void setTemplate(String template)
    {
        this.template = template;
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
     * @hibernate.property column="CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
}