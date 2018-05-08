package com.tonik.model;

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
 * @hibernate.class table="GATHERTASKITEMPATH"
 */
public class GatherTaskItemPath
{
    private Long id;
    
    private String itemType; //字段名
    
    private String itemStr;  //路径
    
    private String itemAttr; //类型
    
    private String itemValPath;
    
    private Long gtid;

    /**
     * @hibernate.id column="TASKPARAMS_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="ITEMTYPE" type="string" length="500" not-null="false"
     * @return Returns the itemType.
     */
    public String getItemType()
    {
        return itemType;
    }

    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    /**
     * @hibernate.property column="ITEMSTR" type="string" length="500" not-null="false"
     * @return Returns the itemStr.
     */
    public String getItemStr()
    {
        return itemStr;
    }

    public void setItemStr(String itemStr)
    {
        this.itemStr = itemStr;
    }

    /**
     * @hibernate.property column="ITEMATTR" type="string" length="500" not-null="false"
     * @return Returns the itemAttr.
     */
    public String getItemAttr()
    {
        return itemAttr;
    }

    public void setItemAttr(String itemAttr)
    {
        this.itemAttr = itemAttr;
    }

    /**
     * @hibernate.property column="ITEMVALPATH" type="string" length="500" not-null="false"
     * @return Returns the itemValPath.
     */
    public String getItemValPath()
    {
        return itemValPath;
    }

    public void setItemValPath(String itemValPath)
    {
        this.itemValPath = itemValPath;
    }

    /**
     * @hibernate.property column="ITEMGATHERTASKID" type="long" not-null="false"
     * @return Returns the gtid.
     */
    public Long getGtid()
    {
        return gtid;
    }

    public void setGtid(Long gtid)
    {
        this.gtid = gtid;
    }
}
