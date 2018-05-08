package com.tonik.model;
/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响配方原料
 * </p>
 * @since Nov 19, 2015
 * @version 1.0
 * @author liuyu
 * 
 * @hibernate.class table="EVENTAFFECTEDMATERIAL"
 */
public class EventAffectedMaterial
{
    private Long id;
    
    private Long eventId;//对应的事件Id
    
    private Long materialTypeId;//对应的配方原料类别Id
    
    private String materialTypeName;//对应的配方原料类别名称
    
    private Long materialId;//对应的配方原料Id
    
    private String materialName;//对应的配方原料名称

    /**
     * @hibernate.id column="ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="EVENT_ID" type="long" not-null="false"
     * @return Returns the eventId.
     */
    public Long getEventId()
    {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    /**
     * @hibernate.property column="MATERIALTYPE_ID" type="long" not-null="false"
     * @return Returns the materialTypeId.
     */
    public Long getMaterialTypeId()
    {
        return materialTypeId;
    }

    public void setMaterialTypeId(Long materialTypeId)
    {
        this.materialTypeId = materialTypeId;
    }

    /**
     * @hibernate.property column="MATERIALTYPE_NAME" type="string" length="50" not-null="false"
     * @return Returns the materialTypeName.
     */
    public String getMaterialTypeName()
    {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName)
    {
        this.materialTypeName = materialTypeName;
    }

    /**
     * @hibernate.property column="MATERIAL_ID" type="long" not-null="false"
     * @return Returns the materialId.
     */
    public Long getMaterialId()
    {
        return materialId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    /**
     * @hibernate.property column="MATERIAL_NAME" type="string" length="50" not-null="false"
     * @return Returns the materialName.
     */
    public String getMaterialName()
    {
        return materialName;
    }

    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }
    
}
