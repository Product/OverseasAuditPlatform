package com.tonik.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.thinvent.utils.DateUtil;
import com.tonik.Constant;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.Country;
import com.tonik.model.Website;
import com.tonik.model.WebsiteStyle;
import com.tonik.service.WebsiteService.WebsitePagingBean;

/**
 * @spring.bean id="WebsiteService"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 */
public class WebsiteService  
{
    private IWebsiteDAO websiteDAO;
    private IWebsiteStyleDAO websiteStyleDAO;
    
    public IWebsiteStyleDAO getWebsiteStyleDAO()
    {
        return websiteStyleDAO;
    }
    public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
    {
        this.websiteStyleDAO = websiteStyleDAO;
    }
    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }
    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }
   public void DelWebsite(Long Id)//删除
    {
        websiteDAO.removeWebsite(Id);
    }
   /*
   函数名：SaveWebsite
   作用：保存网站类型为webstyle的网站website
    */
    public void SaveWebsite(Website website, String webstyle)//保存
    {
        WebsiteStyle w = websiteStyleDAO.getWebsiteStyle(Long.parseLong(webstyle));
        website.setWebStyle(w);
        //将破坏JSon 的字符处理掉
        website.setRemark(website.getRemark().replaceAll("\\s", ""));
        website.setRemark(website.getRemark().replaceAll("\"", "“"));
        website.setRemark(website.getRemark().replaceAll("\"", "”"));
        website.setName(website.getName().replaceAll("\\s", ""));
        website.setName(website.getName().replaceAll("\"", "“"));
        website.setName(website.getName().replaceAll("\"", "”"));
        website.setAddress(website.getAddress().replaceAll("\\s", ""));
        website.setAddress(website.getAddress().replaceAll("\"", "“"));
        website.setAddress(website.getAddress().replaceAll("\"", "”"));
        websiteDAO.saveWebsite(website);
    }
    
    /*
    函数名：SaveWebsiteByWeb
    作用：保存Website 
     */
    public void SaveWebsiteByWeb(Website website)//保存
    {
        website.setRemark(website.getRemark().replaceAll("\\s", ""));
        website.setRemark(website.getRemark().replaceAll("\"", "“"));
        website.setRemark(website.getRemark().replaceAll("\"", "”"));
        website.setRemark(website.getName().replaceAll("\\s", ""));
        website.setRemark(website.getName().replaceAll("\"", "“"));
        website.setRemark(website.getName().replaceAll("\"", "”"));
        website.setAddress(website.getAddress().replaceAll("\\s", ""));
        website.setAddress(website.getAddress().replaceAll("\"", "“"));
        website.setAddress(website.getAddress().replaceAll("\"", "”"));
        
        
        
        websiteDAO.saveWebsite(website);
    }
    public Website GetWebsiteById(Long Id)
    {
        return websiteDAO.getWebsite(Id);
    }

    /*
    函数名：WebsitePaging
    作用：获得第pageIndex页的国内website列表
     */
    public List<Website> WebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls = websiteDAO.getWebsitePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Website> ws = new ArrayList<Website>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            Website w = (Website)obj[0];
            ws.add(w);
        }
        return ws;
    }
    /*
    函数名：WebsitePaging
    作用：获得第pageIndex页的国外website列表
     */
    public List<Website> outWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls = websiteDAO.getOutWebsitePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Website> ws = new ArrayList<Website>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            Website w = (Website)obj[0];
            ws.add(w);
        }
        return ws;
    }

    public List<WebsitePagingBean> GetNativeWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, 
            String strEndTime,Long countryId) throws ParseException{
        // List<Object[]> list = websiteDAO.getWebsiteListByCountryId(countryId);
         List<Website>websites=websiteDAO.NewgetWebsiteListByCountryId(countryId);
         List<WebsitePagingBean> newList=new ArrayList<WebsitePagingBean>(); //实体类
         WebsitePagingBean websitePagingBean=new WebsitePagingBean();
           for (Website website : websites)
        {    
               
            if(website.getCountry().getId()==countryId)
            {
                websitePagingBean.setId(website.getId().toString());
                websitePagingBean.setWebLocation(website.getLocation());
                websitePagingBean.setWebName(website.getName());
                websitePagingBean.setWebType(Integer.toString(website.getWebsiteType()));
                websitePagingBean.setWebStyleName(website.getWebStyleName());
            }
            newList.add(websitePagingBean);
        }
      
       
     
         return newList;
     }
    public List<WebsitePagingBean> NewWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime,String webType){
        List<Object[]> ls = websiteDAO.NewgetWebsitePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime,webType);
        List<WebsitePagingBean> ws = new ArrayList<WebsitePagingBean>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            WebsitePagingBean w = new WebsitePagingBean(obj);
            ws.add(w);
        }
        return ws;
    }
    public List<WebsitePagingBean> NewOutWebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime,String webType){
        List<Object[]> ls = websiteDAO.NewgetOutWebsitePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime,webType);
        List<WebsitePagingBean> ws = new ArrayList<WebsitePagingBean>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            WebsitePagingBean w = new WebsitePagingBean(obj);
            ws.add(w);
        }
        return ws;
    }
    
    public class WebsitePagingBean{
        private String id;
        private String webLocation;
        private String webName;
        private String createTime;
        private String webStyleName;
        private String webType;
        
        public WebsitePagingBean()
        {
            super();
            // TODO Auto-generated constructor stub
        }

        public WebsitePagingBean(Object[] obj)
        {
            this.id = obj[0] == null ? "" : String.valueOf(obj[0]);
            this.webLocation = obj[1] == null ? "" : String.valueOf(obj[1]);
            this.webName = obj[2] == null ? "" : String.valueOf(obj[2]);
            try
            {
                this.createTime = obj[3] == null ? "" : DateUtil.formatDate(DateUtil.parseDate(obj[3].toString()));
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            this.webStyleName = obj[4] == null ? "" : String.valueOf(obj[4]);
            this.webType = obj[5] == null ? "" : String.valueOf(obj[5]);
        }
        
        public String getId()
        {
            return id;
        }
        public void setId(String id)
        {
            this.id = id;
        }
        public String getWebLocation()
        {
            return webLocation;
        }
        public void setWebLocation(String webLocation)
        {
            this.webLocation = webLocation;
        }
        public String getWebName()
        {
            return webName;
        }
        public void setWebName(String webName)
        {
            this.webName = webName;
        }
        public String getCreateTime()
        {
            return createTime;
        }
        public void setCreateTime(String createTime)
        {
            this.createTime = createTime;
        }

        public String getWebStyleName()
        {
            return webStyleName;
        }

        public void setWebStyleName(String webStyleName)
        {
            this.webStyleName = webStyleName;
        }

        public String getWebType()
        {
            return webType;
        }

        public void setWebType(String webType)
        {
            this.webType = webType;
        }
        
        
        
        
    }
    /*
    函数名：WebsiteTotal
    作用：获得查询到的国内Website条目总数
     */
    public String WebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(websiteDAO.getWebsiteTotal(strQuery, strStraTime, strEndTime));
    } 
    /*
    函数名：WebsiteTotal
    作用：获得查询到的国外Website条目总数
     */
    public String outWebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(websiteDAO.getOutWebsiteTotal(strQuery, strStraTime, strEndTime));
    } 
    /*
    函数名：WebsiteTotal
    作用：获得查询到的国内Website条目总数
     */
    public String NewWebsiteTotal(String strQuery, String strStraTime, String strEndTime,String webType)
    {
        return Integer.toString(websiteDAO.NewgetWebsiteTotal(strQuery, strStraTime, strEndTime,webType));
    } 
    /*
    函数名：WebsiteTotal
    作用：获得查询到的国外Website条目总数
     */
    public String NewOutWebsiteTotal(String strQuery, String strStraTime, String strEndTime,String webType)
    {
        return Integer.toString(websiteDAO.NewgetOutWebsiteTotal(strQuery, strStraTime, strEndTime,webType));
    } 
    /*
    函数名：getWebsiteStyle
    作用：获得所有的WebsiteStyle列表
     */
    public List<WebsiteStyle> getWebsiteStyle()
    {
        return websiteStyleDAO.getWebsiteStyle();
    }
    
    /*
    函数名：GatherwebsitePaging
    作用：获得第pageIndex页的可用于DataGather对象的website列表
     */
    public List<Website> GatherwebsitePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        List<Object[]> ls = websiteDAO.getGatherWebsitePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        List<Website> ws = new ArrayList<Website>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            Website w = (Website)obj[0];
            ws.add(w);
        }
        return ws;
    }
    
    /*
    函数名：GatherwebsiteTotal
    作用：获得查询到的可用于DataGather对象的Website条目总数
     */
    public String GatherwebsiteTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(websiteDAO.getGatherWebsiteTotal(strQuery, strStraTime, strEndTime));
    } 
    /*
    函数名：getWebsiteInfo
    作用：返回w的各项数据信息
     */
    
    public String getWebsiteInfo(Website w){
        String AreaName="";
        String AreaId="";
        String CountryName ="";
        String CountryId = "";
        if(w.getArea()!=null)
        {
            AreaId = Long.toString(w.getArea().getId());
            AreaName = w.getArea().getName();
        }
        if(w.getCountry()!=null)
        {
            CountryId = Long.toString(w.getCountry().getId());
            CountryName = w.getCountry().getName();
        }
        String res = "";
        res =  "{\"Id\":\"" + w.getId() + "\",\"Name\":\"" + w.getName() + "\",\"Location\":\""
                + w.getLocation() + "\",\"WebStyle\":\""+ w.getWebStyle().getName() + "\",\"Address\":\"" + w.getAddress() + "\",\"Remark\":\""
                + w.getRemark() + "\",\"GatherId\":\"" + w.getGatherid() + "\",\"CreatePerson\":\"" + w.getCreatePerson() + "\",\"CreateTime\":\""
                + w.getCreateTime() + "\",\"AreaId\":\"" + AreaId + "\",\"AreaName\":\"" + AreaName + "\",\"CountryId\":\"" 
                + CountryId + "\",\"CountryName\":\"" + CountryName + "\",\"Integrity\":\"" + w.getIntegrityDegree() + "\",\"Score\":\"" + w.getComprehensiveScore() + "\"}";
        return res;
    }

    public String getWebsiteTotalByCountryId(Long countryid)
    {
        return Integer.toString(websiteDAO.getWebsiteTotalByCountryId(countryid));
    }
    
    public List<Website> getWebsiteListByCountryId(Long countryid)
    {
        List<Object[]> ls = websiteDAO.getWebsiteListByCountryId(countryid);
        List<Website> ws = new ArrayList<Website>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            Website w = (Website)obj[0];
            ws.add(w);
        }
        return ws;
        
    }
  //组合大神board中网站情况监管纵览的相关信息，传递到Servlet层
    public String getWorldMapWebsiteInfo(String ptl)
    {   
        List<Object[]> wvl = websiteDAO.getWorldMapWebsiteTotal(ptl);
        List<Object[]> wtl = websiteDAO.getWorldMapWebsiteNameList(ptl);
        String strCNVList ="";
        String strCNTList = "";
        for(Object[] obj :wvl)
        {
            strCNVList +=  "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[2] + "\"},";
        }
        String name = "";
        int counter = 0;
        String title = "代表网站：";
        for(Object[] obj: wtl){
            name = "".equals(name) ? obj[0].toString():name;
            if(name.equals(obj[0].toString())){
                if(counter++ < 10){
                    title += obj[1]+",";
                    if(counter % 3 == 0)
                        title += "<br/>";
                }
                else
                    continue;
            }else{
                if(title.length() > 0)
                    title = title.substring(0, title.length()-1);
                strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
                title = "代表网站："+obj[1]+",";
                counter = 1;
                name = obj[0].toString();
            }
        }
        if(title.length() > 0)
            title = title.substring(0, title.length()-1);
        strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
        if(strCNVList.length() > 0)
            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
        if(strCNTList.length() > 0)
            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);
             
        return("{\"webCNVList\":[" + strCNVList + "]" + ",\"webCNTList\":[" + strCNTList + "]}");
    }
  
    
    public String getWebsiteTotal()
    {
        return Long.toString(websiteDAO.getWebsiteTotal());
    }
    public String getWebsiteTotalByProduct(String ptl, Country c)
    {
        String res = "";
        if(ptl.length() == 0){
            if(c == null){
                res = Long.toString(websiteDAO.getWebsiteTotal());
            }else
                res = Integer.toString(websiteDAO.getWebsiteTotalByCountryId(c.getId()));
        }else{
            if(c == null){
                res = Integer.toString(websiteDAO.getWebsiteTotalByProduct(ptl));
            }else{
                res = Integer.toString(websiteDAO.getWebsiteTotalByProductAndCountry(ptl,c.getId()));
            }
        }
            
        return res;
    }
    
    public List<Object[]> getWebsitePagingList(String ptl, Country c, String start, String len, String order, String dir)
    {
        String strOrder = "";
        switch(order){
            case "0":strOrder = "WEBSITE_NAME";break;
            case "1":strOrder = "LOCATION";break;
            case "2":strOrder = "WEBSITESTYLE_NAME";break;
        }
        List<Object[]> res;
        if(ptl == null || ptl == ""){
            res = websiteDAO.getWebsiteNameLists(c, start, len, strOrder, dir);
        }else{
            res = websiteDAO.getWebsiteNameListsByProduct(ptl, c, start, len, strOrder, dir);
        }
        return res;
    }
    
    
    public static String getWebsiteJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1])
                    + "\",\"" + Constant.val(obj[2]) + "\"]";
        return res;
    }
    public String getWebsiteTotalByCountryAndProduct(Country c, String ptl)
    {
        String res = "0";
        if(c != null && ptl != ""){
            res = Integer.toString(websiteDAO.getWebsiteTotalByProductAndCountry(ptl, c.getId()));
        }else if(c != null){
            res = Integer.toString(websiteDAO.getWebsiteTotalByCountryId(c.getId()));
        }else if(ptl != ""){
            res = Integer.toString(websiteDAO.getWebsiteTotalByProduct(ptl));
        }else{
            res = Long.toString(websiteDAO.getWebsiteTotal());
        }
        return res;
    }    
}

