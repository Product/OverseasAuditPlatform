package com.tonik.model;

import java.util.Date;
/**
 * <p>
 * Description: this is a class for infomation diffusion
 * </p>
 * @author fuzhi
 * @hibernate.class table="InfoDiffusion"
 */
public class InfoDiffusion
{
    private Long id;
    private String title;
    private String author;
    private Date date;
    private String keyword;
    private int type;
    private String context;
    private Long target;

    /**
     * @hibernate.id column="InfoDiffusion_Id" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="InfoDiffusion_Title" type="string" length="200" not-null="false"
     * @return Returns the name.
     */
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * @hibernate.property column="InfoDiffusion_Author" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    /**
     * @hibernate.property column="InfoDiffusion_Date" sql-type="Date" not-null="false"
     * @return Returns the createTime.
     */
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    
    /**
     * @hibernate.property column="InfoDiffusion_Keyword" type="string" length="200" not-null="false"
     * @return Returns the name.
     */
    public String getKeyword()
    {
        return keyword;
    }
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    /**
     * @hibernate.property column="InfoDiffusion_Type" type="int" not-null="false"
     * @return Returns the sample.
     */
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * @hibernate.property column="InfoDiffusion_Content" type="text" not-null="false"
     * @return
     */
    public String getContext()
    {
        return context;
    }
    public void setContext(String context)
    {
        this.context = context;
    }
    /**
     * @hibernate.property column="InfoDiffusion_Target" type="long" not-null="false"
     * @return
     */
    public Long getTarget()
    {
        return target;
    }
    public void setTarget(Long target)
    {
        this.target = target;
    }
    
}