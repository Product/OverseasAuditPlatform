package com.tonik.model;

import java.util.Set;

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
 * @hibernate.class table="MENU"
 */
public class Menu
{
    private Long id;
    
    private String name;
    
    private String nodeId;
    
    private String picUrl;
    
    private Integer sort;
    
    private Long parentId;
    
    private int level;
    
    private Set<RoleMenu> roles;
    
    private Integer system;


    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="MENU_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @hibernate.property column="MENU_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @hibernate.property column="MENU_NODEID" type="string" length="50" not-null="false"
     * @return Returns the nodeId.
     */
    public String getNodeId()
    {
        return nodeId;
    }

    /**
     * @param nodeId The nodeId to set.
     */
    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }
    
    /**
     * @hibernate.property column="MENU_PICURL" type="string" length="500" not-null="false"
     * @return Returns the picUrl.
     */
    public String getPicUrl()
    {
        return picUrl;
    }
    
    /**
     * @param picUrl The picUrl to set.
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }
    
    /**
     * @param sort The sort to set.
     */
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    
    /**
     * @hibernate.property column="MENU_SORT" type="int" not-null="false"
     * @return Returns the sort.
     */
    public Integer getSort()
    {
        return sort;
    }

    /**
     * @param parentId The parentId to set.
     */
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }
    
    /**
     * @hibernate.property column="MENU_PARENTID" type="long" not-null="false"
     * @return Returns the parentId.
     */
    public Long getParentId()
    {
        return parentId;
    }

    /**
     * @hibernate.property column="MENU_LEVEL" type="int" not-null="false"
     * @return Returns the level.
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level The level to set.
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @hibernate.set name="roles" table="ROLEMENUS" lazy="false"
     * @hibernate.collection-many-to-many column="ROLE_ID" class="com.tonik.model.RoleMenu"
     * @hibernate.collection-key column="MENU_ID"
     * @return Returns the roles.
     */
    public Set<RoleMenu> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<RoleMenu> roles)
    {
        this.roles = roles;
    }

    /**
     * @hibernate.property column="MENU_SYSTEM" type="int" not-null="false"
     * @return Returns the system.
     */
    public Integer getSystem()
    {
        return system;
    }

    public void setSystem(Integer system)
    {
        this.system = system;
    }
}
