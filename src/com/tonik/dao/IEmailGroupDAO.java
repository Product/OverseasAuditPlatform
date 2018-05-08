package com.tonik.dao;

import java.util.List;

import com.tonik.model.EmailGroup;

public interface IEmailGroupDAO extends IDAO
{
    public List<EmailGroup> getEmailGroup();

    public EmailGroup getEmailGroup(Long emailGroupId);

    public void saveEmailGroup(EmailGroup emailGroup);

    public void removeEmailGroup(EmailGroup emailGroup);
    
    public void removeEmailGroup(Long emailGroupId);
    
    public List<EmailGroup> getEmailGroupPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getEmailGroupTotal(String strQuery, String strStraTime, String strEndTime);
}