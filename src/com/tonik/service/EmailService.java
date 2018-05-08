package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEmailDAO;
import com.tonik.dao.IEmailGroupDAO;
import com.tonik.dao.IUserDAO;
import com.tonik.model.Email;
import com.tonik.model.EmailGroup;

/**
 * @spring.bean id="EmailService"
 * @spring.property name="emailDAO" ref="EmailDAO"
 * @spring.property name="emailGroupDAO" ref="EmailGroupDAO"
 * @spring.property name="userDAO" ref="UserDAO"
 */
public class EmailService
{
    private IEmailDAO emailDAO;
    private IEmailGroupDAO emailGroupDAO;
    private IUserDAO UserDAO;
    
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
    
    public IUserDAO getUserDAO()
    {
        return UserDAO;
    }

    public void setUserDAO(IUserDAO userDAO)
    {
        UserDAO = userDAO;
    }
    
    public List<EmailGroup> getEmailGroup()
    {
        return emailGroupDAO.getEmailGroup();
    }
    
    public void DelEmail(Long Id)
    {
        emailDAO.removeEmail(Id);

    }
    
    public void SaveEmail(Email email)
    {
        emailDAO.saveEmail(email);
    }
    
    public Email GetEmailById(Long Id)
    {
        return emailDAO.getEmail(Id);
    }
    
    public EmailGroup getEmailGroupById(Long id)
    {
        return emailGroupDAO.getEmailGroup(id);
    }

    public String EmailPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        List<Email> ls=emailDAO.getEmailPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        String strJson = "";
        for (Email item : ls)
        {
            strJson += "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"EmailAddress\":\"" + item.getEmailAddress() + "\",\"Company\":\"" + item.getCompany() + "\",\"Subscription\":\""
                    + item.getSubscription() + "\",\"Remark\":\"" + item.getRemark() +"\",\"CreatePerson\":\"" + "aaa"
                    + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"},";
        }
        return strJson;
    }

    public String EmailTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(emailDAO.getEmailTotal(strQuery, strStraTime, strEndTime));
    }
    
    //返回单条Email信息
    public String getEmailInfo(Email email){
        String res = "";
        res += "{\"Id\":\"" + email.getId() + "\",\"Name\":\"" + email.getName()
                + "\",\"EmailAddress\":\"" + email.getEmailAddress()
                + "\",\"Company\":\"" + email.getCompany()
                + "\",\"Subscription\":\"" + email.getSubscription()
                + "\",\"Remark\":\"" + email.getRemark()
                + "\",\"CreatePerson\":\"" + email.getCreatePersonName()
                + "\",\"CreateTime\":\"" + email.getFormatCreateTime() + "\"}";
        return res;
    }

}