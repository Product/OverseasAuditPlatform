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
 * @hibernate.class table="REPORTTEMPLATE"
 */
public class ReportTemplate//报告模板
{
    private Long id;
    
    private String name;//模板名

    private String templateStyle;//模板类型
    
    private String templateContent;//模板内容
    
    private String templateFile;//模板文件(HTML字符串)
    
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
     * @hibernate.id column="REPORTTEMPLATE_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @hibernate.property column="REPORTTEMPLATE_NAME" type="string" length="50" not-null="false"
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
     * @hibernate.property column="REPORTTEMPLATE_STYLE" type="string" length="50" not-null="false"
     * @return Returns the templateStyle.
     */
    public String getTemplateStyle()
    {
        return templateStyle;
    }

    /**
     * @param templateStyle The templateStyle to set.
     */
    public void setTemplateStyle(String templateStyle)
    {
        this.templateStyle = templateStyle;
    }

//    /**
//     * @hibernate.property column="TEMPLATESTYLENAME" type="string" length="50" not-null="false"
//     * @return Returns the templateStyleName.
//     */
//    public String getTemplateStyleName()
//    {
//        return templateStyleName;
//    }
//
//    /**
//     * @param templateStyleName The templateStyleName to set.
//     */
//    public void setTemplateStyleName(String templateStyleName)
//    {
//        this.templateStyleName = templateStyleName;
//    }

    /**
     * @hibernate.property column="REPORTTEMPLATE_CONTENT" type="string" length="50" not-null="false"
     * @return Returns the templateContent.
     */
    public String getTemplateContent()
    {
        return templateContent;
    }

    /**
     * @param templateContent The templateContent to set.
     */
    public void setTemplateContent(String templateContent)
    {
        this.templateContent = templateContent;
    }

    /**
     * @hibernate.property column="REPORTTEMPLATE_FILE" type="string" length="5000" not-null="false"
     * @return Returns the templateFile.
     */
    public String getTemplateFile()
    {
        return templateFile;
    }

    /**
     * @param templateFile The templateFile to set.
     */
    public void setTemplateFile(String templateFile)
    {
        this.templateFile = templateFile;
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
     * @hibernate.property column="REPORTTEMPLATE_CREATETIME" sql-type="Date" not-null="false"
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
