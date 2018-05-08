package com.tonik.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonik.dao.ICollectorCfgDAO;
import com.tonik.model.CollectorCfg;
import com.tonik.util.Collection;

/**
 * DESC: this is CollectorConfig
 * @author fuzhi
 * @spring.bean id="CollectorCfgService"
 * @spring.property name="collectorCfgDAO" ref="CollectorCfgDAO"
 * @spring.property name="collection" ref="Collection"
 */
public class CollectorCfgService
{
    private ICollectorCfgDAO collectorCfgDAO;
    private Collection collection;



    public ICollectorCfgDAO getCollectorCfgDAO()
    {
        return collectorCfgDAO;
    }

    public void setCollectorCfgDAO(ICollectorCfgDAO collectorCfgDAO)
    {
        this.collectorCfgDAO = collectorCfgDAO;
    }
    

    public Collection getCollection()
    {
        return collection;
    }

    public void setCollection(Collection collection)
    {
        this.collection = collection;
    }


    /**
     * DESC:初始化列表
     * @param index
     * @param size
     * @return 
     * @throws JSONException
     */
    public String getListInternal(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size)){
            pageIndex = Integer.valueOf(index);
            pageSize = Integer.valueOf(size);
        }

        List<CollectorCfg> list = collectorCfgDAO.getListInternal(pageIndex, pageSize);
        int count = collectorCfgDAO.getCountInternal();

        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CollectorCfg collectorCfg : list){
            JSONObject jsonObject = new JSONObject();
            if(null != collectorCfg.getId()){
                jsonObject.put("id", collectorCfg.getId());
            }else{
                jsonObject.put("id", "");
            }
            if(null != collectorCfg.getUrl()){
                jsonObject.put("url", collectorCfg.getUrl());
            }else{
                jsonObject.put("url", "");
            }
            jsonObject.put("pageTotal", collectorCfg.getPageTotal());
            if(null != collectorCfg.getTitleSign()){
                jsonObject.put("titleSign", collectorCfg.getTitleSign());
            }else{
                jsonObject.put("titleSign", "");
            }
            if(null != collectorCfg.getCreateTimeSign()){
                jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
            }else{
                jsonObject.put("createTimeSign", "");
            }
            if(null != collectorCfg.getNextSign()){
                jsonObject.put("nextSign", collectorCfg.getNextSign());
            }else{
                jsonObject.put("nextSign", "");
            }
            if(null != collectorCfg.getSiteName()){
                jsonObject.put("siteName", collectorCfg.getSiteName());
            }else{
                jsonObject.put("siteName", "");
            }
            if(null != collectorCfg.getName()){
                jsonObject.put("name", collectorCfg.getName());
            }else{
                jsonObject.put("name", "");
            }
            if(null != collectorCfg.getCreateTime()){
                jsonObject.put("createTime", collectorCfg.getCreateTime());
            }else{
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", collectorCfg.getRate());
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        
        
        return jsonTotal.toString();
    }
    
    /**
     * desc:查詢id并根据词条配置信息进行采集
     * @param id
     * @return
     */
    public String excuteByIdInternal(String id)
    {
        try
        {
            CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
            collection.setUrl(collectorCfg.getUrl());
            collection.setTitleSign(collectorCfg.getTitleSign());
            collection.setCreateTimeSign(collectorCfg.getCreateTimeSign());
            collection.setNextSign(collectorCfg.getNextSign());
            collection.setPageTotal(collectorCfg.getPageTotal());
            collection.setSiteName(collectorCfg.getSiteName());
            collection.setType(collectorCfg.getType());
            new Thread(collection).start();
            return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "爬虫出错";
        }
    }
    
    /**
     * DESC:初始化列表
     * @param index
     * @param size
     * @return 
     * @throws JSONException
     */
    public String getListAbroad(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size)){
            pageIndex = Integer.valueOf(index);
            pageSize = Integer.valueOf(size);
        }

        List<CollectorCfg> list = collectorCfgDAO.getListAbroad(pageIndex, pageSize);
        int count = collectorCfgDAO.getCountAbroad();

        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CollectorCfg collectorCfg : list){
            JSONObject jsonObject = new JSONObject();
            if(null != collectorCfg.getId()){
                jsonObject.put("id", collectorCfg.getId());
            }else{
                jsonObject.put("id", "");
            }
            if(null != collectorCfg.getUrl()){
                jsonObject.put("url", collectorCfg.getUrl());
            }else{
                jsonObject.put("url", "");
            }
            jsonObject.put("pageTotal", collectorCfg.getPageTotal());
            if(null != collectorCfg.getTitleSign()){
                jsonObject.put("titleSign", collectorCfg.getTitleSign());
            }else{
                jsonObject.put("titleSign", "");
            }
            if(null != collectorCfg.getCreateTimeSign()){
                jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
            }else{
                jsonObject.put("createTimeSign", "");
            }
            if(null != collectorCfg.getNextSign()){
                jsonObject.put("nextSign", collectorCfg.getNextSign());
            }else{
                jsonObject.put("nextSign", "");
            }
            if(null != collectorCfg.getSiteName()){
                jsonObject.put("siteName", collectorCfg.getSiteName());
            }else{
                jsonObject.put("siteName", "");
            }
            if(null != collectorCfg.getName()){
                jsonObject.put("name", collectorCfg.getName());
            }else{
                jsonObject.put("name", "");
            }
            if(null != collectorCfg.getCreateTime()){
                jsonObject.put("createTime", collectorCfg.getCreateTime());
            }else{
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", collectorCfg.getRate());
            if(null != collectorCfg.getBlockSign()){
                jsonObject.put("blockSign", collectorCfg.getBlockSign());
            }else{
                jsonObject.put("blockSign", "");
            }
            if(null != collectorCfg.getContentSign()){
                jsonObject.put("contentSign", collectorCfg.getContentSign());
            }else{
                jsonObject.put("contentSign", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        
        
        return jsonTotal.toString();
    }
    
    /**
     * desc:查詢id并根据词条配置信息进行采集
     * @param id
     * @return
     */
    public String excuteByIdAbroad(String id)
    {
        try
        {
            CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
            collection.setUrl(collectorCfg.getUrl());
            collection.setTitleSign(collectorCfg.getTitleSign());
            collection.setCreateTimeSign(collectorCfg.getCreateTimeSign());
            collection.setNextSign(collectorCfg.getNextSign());
            collection.setPageTotal(collectorCfg.getPageTotal());
            collection.setSiteName(collectorCfg.getSiteName());
            collection.setType(collectorCfg.getType());
            collection.setBlockSign(collectorCfg.getBlockSign());
            collection.setContentSign(collectorCfg.getContentSign());
            new Thread(collection).start();
            return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "爬虫出错";
        }
        
    }
    
    /**
     * DESC:舆情初始化列表
     * @param index
     * @param size
     * @return 
     * @throws JSONException
     */
    public String getListPublicOpinion(String index, String size) throws JSONException
    {
        int pageIndex = 1;
        int pageSize = 10;
        if (null != index && !"".equals(index) && null != size && !"".equals(size)){
            pageIndex = Integer.valueOf(index);
            pageSize = Integer.valueOf(size);
        }

        List<CollectorCfg> list = collectorCfgDAO.getListPublicOpinion(pageIndex, pageSize);
        int count = collectorCfgDAO.getCountPublicOpinion();

        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CollectorCfg collectorCfg : list){
            JSONObject jsonObject = new JSONObject();
            if(null != collectorCfg.getId()){
                jsonObject.put("id", collectorCfg.getId());
            }else{
                jsonObject.put("id", "");
            }
            if(null != collectorCfg.getUrl()){
                jsonObject.put("url", collectorCfg.getUrl());
            }else{
                jsonObject.put("url", "");
            }
            jsonObject.put("pageTotal", collectorCfg.getPageTotal());
            if(null != collectorCfg.getTitleSign()){
                jsonObject.put("titleSign", collectorCfg.getTitleSign());
            }else{
                jsonObject.put("titleSign", "");
            }
            if(null != collectorCfg.getCreateTimeSign()){
                jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
            }else{
                jsonObject.put("createTimeSign", "");
            }
            if(null != collectorCfg.getNextSign()){
                jsonObject.put("nextSign", collectorCfg.getNextSign());
            }else{
                jsonObject.put("nextSign", "");
            }
            if(null != collectorCfg.getSiteName()){
                jsonObject.put("siteName", collectorCfg.getSiteName());
            }else{
                jsonObject.put("siteName", "");
            }
            if(null != collectorCfg.getName()){
                jsonObject.put("name", collectorCfg.getName());
            }else{
                jsonObject.put("name", "");
            }
            if(null != collectorCfg.getCreateTime()){
                jsonObject.put("createTime", collectorCfg.getCreateTime());
            }else{
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", collectorCfg.getRate());
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        
        
        return jsonTotal.toString();
    }
    
    /**
     * desc:查詢id并根据词条配置信息进行采集
     * @param id
     * @return
     */
    public String excuteByIdPublicOpinion(String id)
    {
        try
        {
            CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
            collection.setUrl(collectorCfg.getUrl());
            collection.setTitleSign(collectorCfg.getTitleSign());
            collection.setCreateTimeSign(collectorCfg.getCreateTimeSign());
            collection.setNextSign(collectorCfg.getNextSign());
            collection.setPageTotal(collectorCfg.getPageTotal());
            collection.setSiteName(collectorCfg.getSiteName());
            collection.setType(collectorCfg.getType());
            collection.setContentSign(collectorCfg.getContentSign());
            collection.setBlockSign(collectorCfg.getBlockSign());
            new Thread(collection).start();
            return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "爬虫出错";
        }
        
    }
    
    /**
     * 获取网站列表
     * @param index
     * @param size
     * @return
     * @throws JSONException 
     */
    public String getListWebsite(String index, String size) throws JSONException
    {
        int pageIndex = Integer.valueOf(index);
        int pageSize = Integer.valueOf(size);
        List<CollectorCfg> list= collectorCfgDAO.getListWebsite(pageIndex, pageSize);
        int count = collectorCfgDAO.getCountWebsite();
        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CollectorCfg collectorCfg : list){
            JSONObject jsonObject = new JSONObject();
            if(null != collectorCfg.getId()){
                jsonObject.put("id", collectorCfg.getId());
            }else{
                jsonObject.put("id", "");
            }
            if(null != collectorCfg.getUrl()){
                jsonObject.put("url", collectorCfg.getUrl());
            }else{
                jsonObject.put("url", "");
            }
            jsonObject.put("pageTotal", collectorCfg.getPageTotal());
            if(null != collectorCfg.getTitleSign()){
                jsonObject.put("titleSign", collectorCfg.getTitleSign());
            }else{
                jsonObject.put("titleSign", "");
            }
            if(null != collectorCfg.getCreateTimeSign()){
                jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
            }else{
                jsonObject.put("createTimeSign", "");
            }
            if(null != collectorCfg.getNextSign()){
                jsonObject.put("nextSign", collectorCfg.getNextSign());
            }else{
                jsonObject.put("nextSign", "");
            }
            if(null != collectorCfg.getSiteName()){
                jsonObject.put("siteName", collectorCfg.getSiteName());
            }else{
                jsonObject.put("siteName", "");
            }
            if(null != collectorCfg.getName()){
                jsonObject.put("name", collectorCfg.getName());
            }else{
                jsonObject.put("name", "");
            }
            if(null != collectorCfg.getCreateTime()){
                jsonObject.put("createTime", collectorCfg.getCreateTime());
            }else{
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", collectorCfg.getRate());
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        
        return jsonTotal.toString();
    }

    /**
     * 执行爬虫功能网站部分
     * @param id
     * @return
     */
    public String excuteByIdWebsite(String id)
    {
        try
        {
            CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
            collection.setUrl(collectorCfg.getUrl());
            collection.setTitleSign(collectorCfg.getTitleSign());
            collection.setCreateTimeSign(collectorCfg.getCreateTimeSign());
            collection.setNextSign(collectorCfg.getNextSign());
            collection.setPageTotal(collectorCfg.getPageTotal());
            collection.setSiteName(collectorCfg.getSiteName());
            collection.setType(collectorCfg.getType());
            new Thread(collection).start();
            return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "爬虫出错";
        }
    }

    public String getListExpertRepository(String index, String size) throws JSONException
    {
        int pageIndex = Integer.valueOf(index);
        int pageSize = Integer.valueOf(size);
        List<CollectorCfg> list= collectorCfgDAO.getListExpertRepository(pageIndex, pageSize);
        int count = collectorCfgDAO.getCountExpertRepository();
        JSONObject jsonTotal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CollectorCfg collectorCfg : list){
            JSONObject jsonObject = new JSONObject();
            if(null != collectorCfg.getId()){
                jsonObject.put("id", collectorCfg.getId());
            }else{
                jsonObject.put("id", "");
            }
            if(null != collectorCfg.getUrl()){
                jsonObject.put("url", collectorCfg.getUrl());
            }else{
                jsonObject.put("url", "");
            }
            jsonObject.put("pageTotal", collectorCfg.getPageTotal());
            if(null != collectorCfg.getTitleSign()){
                jsonObject.put("titleSign", collectorCfg.getTitleSign());
            }else{
                jsonObject.put("titleSign", "");
            }
            if(null != collectorCfg.getCreateTimeSign()){
                jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
            }else{
                jsonObject.put("createTimeSign", "");
            }
            if(null != collectorCfg.getNextSign()){
                jsonObject.put("nextSign", collectorCfg.getNextSign());
            }else{
                jsonObject.put("nextSign", "");
            }
            if(null != collectorCfg.getSiteName()){
                jsonObject.put("siteName", collectorCfg.getSiteName());
            }else{
                jsonObject.put("siteName", "");
            }
            if(null != collectorCfg.getName()){
                jsonObject.put("name", collectorCfg.getName());
            }else{
                jsonObject.put("name", "");
            }
            if(null != collectorCfg.getCreateTime()){
                jsonObject.put("createTime", collectorCfg.getCreateTime());
            }else{
                jsonObject.put("createTime", "");
            }
            jsonObject.put("rate", collectorCfg.getRate());
            if(null != collectorCfg.getBlockSign()){
                jsonObject.put("blockSign", collectorCfg.getBlockSign());
            }else{
                jsonObject.put("blockSign", "");
            }
            jsonArray.put(jsonObject);
        }
        jsonTotal.put("count", count);
        jsonTotal.put("infoList", jsonArray);
        
        return jsonTotal.toString();
    }

    public String excuteByIdExpertRepository(String id)
    {
        try
        {
            CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
            collection.setUrl(collectorCfg.getUrl());
            collection.setTitleSign(collectorCfg.getTitleSign());
            collection.setCreateTimeSign(collectorCfg.getCreateTimeSign());
            collection.setNextSign(collectorCfg.getNextSign());
            collection.setPageTotal(collectorCfg.getPageTotal());
            collection.setSiteName(collectorCfg.getSiteName());
            collection.setType(collectorCfg.getType());
            collection.setBlockSign(collectorCfg.getBlockSign());
            new Thread(collection).start();
            return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "爬虫出错";
        }
    }
    
    /**
     * desc 添加或更新数据
     * @param id
     * @param url
     * @param pageTotal
     * @param titleSign
     * @param createTimeSign
     * @param nextSign
     * @param siteName
     * @param type
     * @param name
     * @param rate
     * @return
     * @throws JSONException
     */
    public String addOrUpdate(String id, String url, String pageTotal, String titleSign, String createTimeSign, String nextSign, String siteName, int type, String name, String rate,String blockSign, String contentSign) throws JSONException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        CollectorCfg collectorCfg = new CollectorCfg();
        if(null != id && !"".equals(id)){
            collectorCfg.setId(Long.valueOf(id));
        }
        collectorCfg.setUrl(url);
        if(null != pageTotal && !pageTotal.equals("")){
            collectorCfg.setPageTotal(Integer.valueOf(pageTotal));
        }else{
            collectorCfg.setPageTotal(0);
        }
        collectorCfg.setTitleSign(titleSign);
        collectorCfg.setCreateTimeSign(createTimeSign);
        collectorCfg.setNextSign(nextSign);
        collectorCfg.setSiteName(siteName);
        collectorCfg.setType(type);
        collectorCfg.setName(name);
        collectorCfg.setCreateTime(createTime);
        if(null != rate && !rate.equals("")){
            collectorCfg.setRate(Integer.valueOf(rate));
        }else{
            collectorCfg.setRate(Integer.valueOf(0));
        }
        collectorCfg.setBlockSign(blockSign);
        collectorCfg.setContentSign(contentSign);
        collectorCfgDAO.addOrUpdate(collectorCfg);
        JSONObject jsonObject = new JSONObject();
        if(null != collectorCfg.getId()){
            jsonObject.put("id", collectorCfg.getId());
        }else{
            jsonObject.put("id", "");
        }
        if(null != collectorCfg.getUrl()){
            jsonObject.put("url", collectorCfg.getUrl());
        }else{
            jsonObject.put("url", "");
        }
        jsonObject.put("pageTotal", collectorCfg.getPageTotal());
        if(null != collectorCfg.getTitleSign()){
            jsonObject.put("titleSign", collectorCfg.getTitleSign());
        }else{
            jsonObject.put("titleSign", "");
        }
        if(null != collectorCfg.getCreateTimeSign()){
            jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
        }else{
            jsonObject.put("createTimeSign", "");
        }
        if(null != collectorCfg.getNextSign()){
            jsonObject.put("nextSign", collectorCfg.getNextSign());
        }else{
            jsonObject.put("nextSign", "");
        }
        if(null != collectorCfg.getSiteName()){
            jsonObject.put("siteName", collectorCfg.getSiteName());
        }else{
            jsonObject.put("siteName", "");
        }
        if(null != collectorCfg.getName()){
            jsonObject.put("name", collectorCfg.getName());
        }else{
            jsonObject.put("name", "");
        }
        if(null != collectorCfg.getCreateTime()){
            jsonObject.put("createTime", collectorCfg.getCreateTime());
        }else{
            jsonObject.put("createTime", "");
        }
        jsonObject.put("rate", collectorCfg.getRate());
        if(null != collectorCfg.getBlockSign()){
            jsonObject.put("blockSign", collectorCfg.getBlockSign());
        }else{
            jsonObject.put("blockSign", "");
        }
        if(null != collectorCfg.getContentSign()){
            jsonObject.put("contentSign", collectorCfg.getContentSign());
        }else{
            jsonObject.put("contentSign", "");
        }
        return jsonObject.toString();
    }

    /**
     * @desc: 根据id查询配置信息
     * @param id
     * @return
     * @throws JSONException 
     */
    public String queryById(String id) throws JSONException
    {
        CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
        JSONObject jsonObject = new JSONObject();
        if(null != collectorCfg.getId()){
            jsonObject.put("id", collectorCfg.getId());
        }else{
            jsonObject.put("id", "");
        }
        if(null != collectorCfg.getUrl()){
            jsonObject.put("url", collectorCfg.getUrl());
        }else{
            jsonObject.put("url", "");
        }
        jsonObject.put("pageTotal", collectorCfg.getPageTotal());
        if(null != collectorCfg.getTitleSign()){
            jsonObject.put("titleSign", collectorCfg.getTitleSign());
        }else{
            jsonObject.put("titleSign", "");
        }
        if(null != collectorCfg.getCreateTimeSign()){
            jsonObject.put("createTimeSign", collectorCfg.getCreateTimeSign());
        }else{
            jsonObject.put("createTimeSign", "");
        }
        if(null != collectorCfg.getNextSign()){
            jsonObject.put("nextSign", collectorCfg.getNextSign());
        }else{
            jsonObject.put("nextSign", "");
        }
        if(null != collectorCfg.getSiteName()){
            jsonObject.put("siteName", collectorCfg.getSiteName());
        }else{
            jsonObject.put("siteName", "");
        }
        if(null != collectorCfg.getName()){
            jsonObject.put("name", collectorCfg.getName());
        }else{
            jsonObject.put("name", "");
        }
        if(null != collectorCfg.getCreateTime()){
            jsonObject.put("createTime", collectorCfg.getCreateTime());
        }else{
            jsonObject.put("createTime", "");
        }
        jsonObject.put("rate", collectorCfg.getRate());
        if(null != collectorCfg.getBlockSign()){
            jsonObject.put("blockSign", collectorCfg.getBlockSign());
        }else{
            jsonObject.put("blockSign", "");
        }
        return jsonObject.toString();
    }

    /**
     * desc 根据ID删除数据
     * @param id
     * @return
     */
    public String delById(String id)
    {
        CollectorCfg collectorCfg = collectorCfgDAO.queryById(Integer.valueOf(id));
        collectorCfgDAO.delById(collectorCfg);
        return "success";
    }
}
