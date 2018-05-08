package com.thinvent.QuestionAnswer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.thinvent.utils.PropertiesUtil;

/**
 * @author xiaoyu
 */
public class TextSimilarityCalulate
{
    private String appId;
    private String aipKey;
    private String aipToken;
    private String aipurl;

    /*
     * 在构造函数中初始化ａｉｐｕｒｌ及ａｉｐＴｏｋｅｎ
     */

    private static Logger logger = Logger.getLogger(TextSimilarityCalulate.class);


    public TextSimilarityCalulate()
    {
        super();
        // TODO Auto-generated constructor stub
        try
        {

            PropertiesUtil.readProperties("baiduApiKey.properties");
            this.appId = PropertiesUtil.getProperty("appId");

            this.aipKey = PropertiesUtil.getProperty("aipKey");
            this.aipToken = PropertiesUtil.getProperty("aipToken");

            this.aipurl = PropertiesUtil.getProperty("aipurl");

        } catch (Exception e)
        {
            // TODO: handle exception
            logger.error(" TextSimilarityCalulate init Error: " + e.toString(), e);

        }
    }

    public Double calulateSimilarity(String text1, String text2)
    {
        Double score = new Double(0);

        HttpPost httpPost = new HttpPost(aipurl + aipToken);
        HttpClient httpClient = new DefaultHttpClient();
        String respContent = null;

        Map<String, Object> outermap = new HashMap<String, Object>();
        Map<String, Object> intermap = new HashMap<String, Object>();

        List<Map<String, Object>> qList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> tList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map1.put("terms_sequence", text1);
        map1.put("type", 0);
        map1.put("items", new ArrayList());
        map2.put("terms_sequence", text2);
        map2.put("type", 0);
        map2.put("items", new ArrayList());
        qList.add(map1);
        tList.add(map2);
        intermap.put("qslots", qList);
        intermap.put("tslots", tList);
        intermap.put("type", 0);
        outermap.put("input", intermap);

        JSONObject jsonParam = new JSONObject(outermap);

        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp = null;
        try
        {
            resp = httpClient.execute(httpPost);
        } catch (IOException e)
        {
            logger.error(" TextSimilarityCalulate io Error: " + e.toString(), e);
            return 0.0;
        }
        if (resp.getStatusLine().getStatusCode() == 200)
        {
            HttpEntity he = resp.getEntity();
            try
            {
                respContent = EntityUtils.toString(he, "UTF-8");
            } catch (ParseException e)
            {
                logger.error(" TextSimilarityCalulate  Error: " + e.toString(), e);
                return 0.0;
            } catch (IOException e)
            {
                logger.error(" TextSimilarityCalulate io Error: " + e.toString(), e);
                return 0.0;
            }

        }
        else
        {
            return score;
        }
        if (respContent != null)
        {
            JSONObject jsonObject = null;
            try
            {
                jsonObject = new JSONObject(respContent);
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                logger.error(" TextSimilarityCalulate  Error: " + e.toString(), e);
                return 0.0;
            }
            JSONObject output = null;
            String[] names = jsonObject.getNames(jsonObject);
            for (String string : names)
            {
                if (string.equalsIgnoreCase("output"))
                {
                    try
                    {
                        output = jsonObject.getJSONObject("output");
                    } catch (JSONException e)
                    {
                        // TODO Auto-generated catch block
                        logger.error(" TextSimilarityCalulate Error: " + e.toString(), e);
                        return 0.0;
                    }
                    try
                    {
                        score = Double.parseDouble(output.get("score").toString());
                    } catch (NumberFormatException e)
                    {
                        logger.error(" TextSimilarityCalulate output Error: " + e.toString(), e);
                        return 0.0;
                    } catch (JSONException e)
                    {
                        logger.error(" TextSimilarityCalulate output Error: " + e.toString(), e);
                        return 0.0;
                    }
                }
            }

        }

        return score;

    }

}
