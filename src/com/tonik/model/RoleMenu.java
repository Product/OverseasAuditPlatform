package com.tonik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
 * @hibernate.class table="ROLEMENU"
 */
public class RoleMenu implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7386194900813723205L;

    private Long id;
    
    private String name;
    
    private Integer sort;
    
    private List<Menu> menus;
    
    private String createPerson;
    
    private Date createTime;
    
    private Set<UserInfo> users;
    

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="ROLE_ID" type="long" unsaved-value="null" generator-class="identity"
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
     * @hibernate.property column="ROLE_NAME" type="string" length="50" not-null="false"
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param sort The sort to set.
     */
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    /**
     * @hibernate.property column="ROLE_SORT" type="int" not-null="false"
     * @return Returns the sort.
     */
    public Integer getSort()
    {
        return sort;
    }

    /**
     * @hibernate.bag name="Menus" table="ROLEMENUS" lazy="false"
     * @hibernate.collection-many-to-many column="MENU_ID" class="com.tonik.model.Menu"
     * @hibernate.collection-key column="ROLE_ID"
     * @return Returns the Menus.
     */
    public List<Menu> getMenus()
    {
        return menus;
    }

    /**
     * @param menus The menus to set.
     */
    public void setMenus(List<Menu> menus)
    {
        this.menus = menus;
    }
    
    /**
     * @param createPerson The createPerson to set.
     */
    public void setCreatePerson(String createPerson)
    {
        this.createPerson = createPerson;
    }
    
    /**
     * @hibernate.property column="ROLE_CREATEPERSON" type="string" length="50" not-null="false"
     * @return Returns the createPerson.
     */
    public String getCreatePerson()
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
     * @hibernate.property name="CREATETIME" sql-type="Date" not-null="false"
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    /**
     * @hibernate.set name="users" table="USERROLE" lazy="false"
     * @hibernate.collection-many-to-many column="USER_ID" class="com.tonik.model.UserInfo"
     * @hibernate.collection-key column="ROLE_ID"
     * @return Returns the users.
     */
    public Set<UserInfo> getUsers()
    {
        return users;
    }

    public void setUsers(Set<UserInfo> users)
    {
        this.users = users;
    }
}
