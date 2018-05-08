package com.tonik.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonik.dao.IThirdReportDAO;
import com.tonik.model.ThirdReport;

/**
 * desc: 服务层
 * @author fuzhi
 * @spring.bean id="ThirdReportService"
 * @spring.property name="thirdReportDAO" ref="ThirdReportDAO"
 */
public class ThirdReportService
{
    
    
    private IThirdReportDAO thirdReportDAO;

    public IThirdReportDAO getThirdReportDAO()
    {
        return thirdReportDAO;
    }

    public void setThirdReportDAO(IThirdReportDAO thirdReportDAO)
    {
        this.thirdReportDAO = thirdReportDAO;
    }
    public String getList(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size))
        {
            pageIndex = Integer.valueOf(index);
            pageSize = Integer.valueOf(size);
        }
        
        List<ThirdReport> list = thirdReportDAO.getList(pageIndex, pageSize);
        int count = thirdReportDAO.getCount();

        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ThirdReport thirdReport : list)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", thirdReport.getId());
            jsonObject.put("title", thirdReport.getTitle());
            jsonObject.put("url", thirdReport.getUrl());
            jsonObject.put("creatTime", thirdReport.getCreateTime());
            jsonObject.put("getherTime", thirdReport.getGether());
            jsonObject.put("siteName", thirdReport.getSiteName());
            jsonObject.put("infoType", thirdReport.getInfoType());
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        return jsonTotal.toString();
    }

    public String getListAbroad(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size))
        {
            pageIndex = Integer.valueOf(index);
            pageSize = Integer.valueOf(size);
        }
        List<ThirdReport> list = thirdReportDAO.getListAbroad(pageIndex, pageSize);
        int count = thirdReportDAO.getCountAbroad();
        
        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ThirdReport thirdReport : list)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", thirdReport.getId());
            jsonObject.put("title", thirdReport.getTitle());
            if(null != thirdReport.getTitleTrans() && !"".equals(thirdReport.getTitleTrans())){
                jsonObject.put("titleTrans", thirdReport.getTitleTrans());
            }else{
                jsonObject.put("titleTrans", "");
            }
            jsonObject.put("url", thirdReport.getUrl());
            jsonObject.put("country", thirdReport.getCountry());
            jsonObject.put("brand", thirdReport.getBrand());
            jsonObject.put("creatTime", thirdReport.getCreateTime());
            if(null !=thirdReport.getContent() && !"".equals(thirdReport.getContent())){
                jsonObject.put("content", thirdReport.getContent());
            }else{
                jsonObject.put("content", "");
            }
            if(null != thirdReport.getContentTrans() && !"".equals(thirdReport.getContentTrans())){
                jsonObject.put("contentTrans", thirdReport.getContentTrans());
            }else{
                jsonObject.put("contentTrans", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        return jsonTotal.toString();
    }

    public String queryById(String id) throws JSONException
    {
        ThirdReport thirdReport = thirdReportDAO.queryById(Long.valueOf(id));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", thirdReport.getId());
        jsonObject.put("title", thirdReport.getTitle());
        if(null != thirdReport.getTitleTrans() && !"".equals(thirdReport.getTitleTrans())){
            jsonObject.put("titleTrans", thirdReport.getTitleTrans());
        }else{
            jsonObject.put("titleTrans", "");
        }
        jsonObject.put("url", thirdReport.getUrl());
        jsonObject.put("country", thirdReport.getCountry());
        jsonObject.put("brand", thirdReport.getBrand());
        jsonObject.put("creatTime", thirdReport.getCreateTime());
        if(null !=thirdReport.getContent() && !"".equals(thirdReport.getContent())){
            jsonObject.put("content", thirdReport.getContent());
        }else{
            jsonObject.put("content", "");
        }
        if(null != thirdReport.getContentTrans() && !"".equals(thirdReport.getContentTrans())){
            jsonObject.put("contentTrans", thirdReport.getContentTrans());
        }else{
            jsonObject.put("contentTrans", "");
        }
        return jsonObject.toString();
    }
}
