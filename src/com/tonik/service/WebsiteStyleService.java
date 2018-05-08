package com.tonik.service;

import java.util.List;

import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.WebsiteStyle;

/**                 
 * @spring.bean id="WebsiteStyleService"
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 */
public class WebsiteStyleService
{
    private IWebsiteStyleDAO websiteStyleDAO;

    public IWebsiteStyleDAO getWebsiteStyleDAO()
    {
        return websiteStyleDAO;
    }

    public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
    {
        this.websiteStyleDAO = websiteStyleDAO;
    }
    
    public void DelWebsiteStyle(Long Id)//删除
    {
        websiteStyleDAO.removeWebsiteStyle(Id);
    }
    
    public void SaveWebsiteStyle(WebsiteStyle websiteStyle)//保存
    {
        websiteStyle.setRemark(websiteStyle.getRemark().replaceAll("\\s", ""));
        websiteStyle.setRemark(websiteStyle.getRemark().replaceAll("\"", "“"));
        websiteStyle.setRemark(websiteStyle.getRemark().replaceAll("\"", "”"));
        websiteStyle.setName(websiteStyle.getName().replaceAll("\\s", ""));
        websiteStyle.setName(websiteStyle.getName().replaceAll("\"", "“"));
        websiteStyle.setName(websiteStyle.getName().replaceAll("\"", "”"));
        websiteStyleDAO.saveWebsiteStyle(websiteStyle);
    }
    
    public WebsiteStyle GetWebsiteStyleById(Long Id)//获取websitestyle
    {
        return websiteStyleDAO.getWebsiteStyle(Id);
    }
    /*
    函数名：WebsiteStylePaging
    作用：获得第pageIndex页的websitestyle列表
     */
    public List<WebsiteStyle> WebsiteStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return websiteStyleDAO.getWebsiteStylePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }
    /*
    函数名：WebsiteStyleTotal
    作用：获得查询到的Websitestyle条目总数
     */
    public String WebsiteStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(websiteStyleDAO.getWebsiteStyleTotal(strQuery, strStraTime, strEndTime));
    }
    /*
    函数名：getWebsiteStyleInfo
    作用：返回item的各项数据信息
     */
    public String getWebsiteStyleInfo(WebsiteStyle item){
        String res = "";
        res =  "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Remark\":\""
                + item.getRemark() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"}";
        return res;
    }
 
    

}
