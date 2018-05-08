package com.tonik.dao;

import java.util.List;

import com.tonik.model.WebsiteStyle;

public interface IWebsiteStyleDAO extends IDAO
{
    public List<WebsiteStyle> getWebsiteStyle();

    public WebsiteStyle getWebsiteStyle(Long websiteStyleId);

    public void saveWebsiteStyle(WebsiteStyle websiteStyle);

    public void removeWebsiteStyle(WebsiteStyle websiteStyle);
    
    public void removeWebsiteStyle(Long websiteStyleId);
    
    public List<WebsiteStyle> getWebsiteStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getWebsiteStyleTotal(String strQuery, String strStraTime, String strEndTime);
}
