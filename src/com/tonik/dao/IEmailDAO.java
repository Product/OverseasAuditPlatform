package com.tonik.dao;

import java.util.List;

import com.tonik.model.Email;

public interface IEmailDAO extends IDAO
{
    public List<Email> getEmail();

    public Email getEmail(Long emailId);

    public void saveEmail(Email email);

    public void removeEmail(Email email);
    
    public void removeEmail(Long emailId);
    
    public List<Email> getEmailManage(Long emailGroupId);
    
    public List<Email> getEmailPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getEmailTotal(String strQuery, String strStraTime, String strEndTime);
}
