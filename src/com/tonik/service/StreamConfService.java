package com.tonik.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonik.dao.IStreamConfDAO;
import com.tonik.model.StreamConf;

/**
 * @spring.bean id="StreamConfService"
 * @author fuzhi
 * @spring.property name="streamConfHibernate" ref="StreamConfHibernate"
 */
public class StreamConfService
{
    private IStreamConfDAO streamConfHibernate;


    public IStreamConfDAO getStreamConfHibernate()
    {
        return streamConfHibernate;
    }

    public void setStreamConfHibernate(IStreamConfDAO streamConfHibernate)
    {
        this.streamConfHibernate = streamConfHibernate;
    }

    public String queryList(int pageIndex, int pageSize)
    {
        List<StreamConf> list = streamConfHibernate.queryList(pageIndex, pageSize);
        int count = streamConfHibernate.Count();
        JSONObject jsonObjectResult = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try
        {
            for (StreamConf streamConf : list)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", streamConf.getId());
                jsonObject.put("property", streamConf.getProperty());
                jsonObject.put("value", streamConf.getValue());
                jsonObject.put("detail", streamConf.getDetail());
                jsonArray.put(jsonObject);
            }
            jsonObjectResult.put("count", count);
            jsonObjectResult.put("list", jsonArray);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObjectResult.toString();
    }
    
    public String queryById(String id)
    {
        StreamConf streamConf = streamConfHibernate.queryById(Long.valueOf(id));
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("id", streamConf.getId());
            jsonObject.put("property", streamConf.getProperty());
            jsonObject.put("value", streamConf.getValue());
            jsonObject.put("detail", streamConf.getDetail());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    
    public String AddOrUpdate(String id, String value)
    {
        if(null != id && !"".equals(id)){            
            StreamConf streamConf = streamConfHibernate.queryById(Long.valueOf(id));
            streamConf.setValue(value);
            streamConfHibernate.AddOrUpdate(streamConf);
            return "success";
        }else{
            return "false";
        }
    }
}
