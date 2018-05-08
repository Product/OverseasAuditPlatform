package com.thinvent.translate;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * @spring.bean id="Translation" 
 * 
 */

public class Translation
{
    private static Logger logger = Logger.getLogger(Translation.class);
    private HttpClient client;
    private PostMethod method;
    private String ip;
    private String from;
    private String to;

    public Translation(){}

    /**
     * 构造函数
     * @param ip 机器翻译服务器IP地址及端口号。示例："192.168.0.1:1517"
     * @param from 源语言标示符
     * @param to 目标语言标示符
     */

    public Translation(String ip, String from, String to)
    {
        // initTranslationConnection();
        client = new HttpClient();
        this.ip = ip;
        this.from = from;
        this.to = to;

    }

    /**
     * 翻译函数
     * @param src_text 待翻译字符串
     * @return 返回翻译字符串
     */
    public String doJob(String src_text)
    {
        String url = "http://" + ip + "/niutrans/translation?from=" + from + "&to=" + to;
        String temp = "";
        String tgt_text = "输入错误！错误码：10020";
        method = new UTF8PostMethod(url);
        method.setParameter("src_text", src_text);
        try
        {
            client.executeMethod(method);
            temp = method.getResponseBodyAsString();//
        } catch (HttpException e)
        {
            // TODO Auto-generated catch block
            logger.error("Translation Error:连接错误！错误码：10021 " + e.toString(), e);
            return "";
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
           
            logger.error("Translation Error:连接错误！错误码：10022 " + e.toString(), e);
            return "";
        } // 执行请求
        JSONObject json = JSONObject.fromObject(temp);
        // System.out.println(json);
        if (temp.indexOf("tgt_text") != -1)
            tgt_text = json.get("tgt_text").toString();

        return tgt_text;

    }


    private class UTF8PostMethod extends PostMethod
    {
        public UTF8PostMethod(String url)
        {
            super(url);
        }

        @Override
        public String getRequestCharSet()
        {
            return "UTF-8";
        }
    }
}
