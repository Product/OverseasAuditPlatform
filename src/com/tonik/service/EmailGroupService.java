package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEmailDAO;
import com.tonik.dao.IEmailGroupDAO;
import com.tonik.model.Email;
import com.tonik.model.EmailGroup;

/**
 * @spring.bean id="EmailGroupService"
 * @spring.property name="emailGroupDAO" ref="EmailGroupDAO"
 * @spring.property name="emailDAO" ref="EmailDAO"
 */
public class EmailGroupService  
{
    private IEmailGroupDAO emailGroupDAO;
    
    private IEmailDAO emailDAO;
    
    public IEmailGroupDAO getEmailGroupDAO()
    {
        
        return emailGroupDAO;
    }
    
    public void setEmailGroupDAO(IEmailGroupDAO emailGroupDAO)
    {
        this.emailGroupDAO = emailGroupDAO;
    }
    
    public IEmailDAO getEmailDAO()
    {
        return emailDAO;
    }

    public void setEmailDAO(IEmailDAO emailDAO)
    {
        this.emailDAO = emailDAO;
    }

    public void DelEmailGroup(Long Id)
    {
        emailGroupDAO.removeObject(EmailGroup.class, Id);
    }
    
    public void SaveEmailGroup(EmailGroup emailGroup)
    {
        emailGroupDAO.saveEmailGroup(emailGroup);

    }
    
    public EmailGroup GetEmailGroupById(Long Id)
    {
        return emailGroupDAO.getEmailGroup(Id);
    }

    public List<Email> getEmailList(Long emailGroupId)
    {
        return emailDAO.getEmailManage(emailGroupId);
    }
    
    public List<EmailGroup> EmailGroupPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return emailGroupDAO.getEmailGroupPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String EmailGroupTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(emailGroupDAO.getEmailGroupTotal(strQuery, strStraTime, strEndTime));
    }
    
    public List<EmailGroup> getGroupList()
    {
        return emailGroupDAO.getEmailGroup();
    }
    
    //返回单条EmailGroup信息
    public String getEmailGroupInfo(EmailGroup emailGroup){
        String res = "";
        res += "{\"Id\":\"" + emailGroup.getId() + "\",\"Name\":\"" + emailGroup.getName()
                + "\",\"CreatePerson\":\"" + emailGroup.getCreatePersonName()
                + "\",\"CreateTime\":\"" + emailGroup.getFormatCreateTime() + "\"}";
        return res;
    }
    

}