package com.tonik.model;

import java.io.Serializable;
import java.util.Date;
//import java.util.Set;
//import java.util.List;
import java.util.Set;

import com.tonik.Constant;

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
 * @hibernate.class table="USERINFO"
 */
public class UserInfo implements Serializable
{
    private Long id;
    
    private String userCode;
    
    private String userPwd;
    
    private String realName;
      
    private Set<RoleMenu> userRole; 

    private String mobile;
    
    private Long createPersonid;
    
    private Date createTime;

    /**
     * @param id The id to set.
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * @hibernate.id column="USER_ID" type="long" unsaved-value="null" generator-class="identity"
     * @return Returns the id.
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @param userCode The userCode to set.
     */
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }
    
    /**
     * @hibernate.property column="USER_CODE" type="string" length="50" not-null="false"
     * @return Returns the userCode.
     */
    public String getUserCode()
    {
        return userCode;
    }

    /**
     * @hibernate.property column="USER_PWD" type="string" length="50" not-null="false"
     * @return Returns the userPwd.
     */
    public String getUserPwd()
    {
        return userPwd;
    }

    /**
     * @param userPwd The userPwd to set.
     */
    public void setUserPwd(String userPwd)
    {
        this.userPwd = userPwd;
    }
    
    /**
     * @param realName The realName to set.
     */
    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    /**
     * @hibernate.property column="USER_REALNAME" type="string" length="50" not-null="false"
     * @return Returns the realName.
     */
    public String getRealName()
    {
        return realName;
    }

    /**
     * @hibernate.set name="userRole" table="USERROLE" lazy="false"
     * @hibernate.collection-many-to-many column="ROLE_ID" class="com.tonik.model.RoleMenu"
     * @hibernate.collection-key column="USER_ID"
     * @return Returns the userRole.
     */
    public Set<RoleMenu> getUserRole()
    {
        return userRole;
    }

    /**
     * @param userRole The userRole to set.
     */
    public void setUserRole(Set<RoleMenu> userRole)
    {
        this.userRole = userRole;
    }

    /**
     * @param mobile The mobile to set.
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    /**
     * @hibernate.property column="USER_MOBILE" type="string" length="50" not-null="false"
     * @return Returns the mobile.
     */
    public String getMobile()
    {
        return mobile;
    }
    
    /**
     * @param createPersonid The createPersonid to set.
     */
    public void setCreatePersonid(Long createPersonid)
    {
        this.createPersonid = createPersonid;
    }
    
    /**
     * @hibernate.property column="USER_CREATEPERSONID" type="long" length="50" not-null="false"
     * @return Returns the createPerson.
     */
    public Long getCreatePersonid()
    {
        return createPersonid;
    }

    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * @hibernate.property column="USER_CREATETIME" sql-type="Date" not-null="false" 
     * @return Returns the CreateTime.
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public String getFormatCreateTime(){
        if(createTime==null)
            return null;
        else{
        String date = new java.text.SimpleDateFormat(Constant.DATE_FORMAT).format(createTime);
        return date;
        }
    }
    
}
