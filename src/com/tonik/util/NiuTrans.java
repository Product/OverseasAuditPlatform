package com.tonik.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;


import net.sf.json.JSONObject;

/**
 * 翻译函数
 * @param src_text 待翻译字符串
 * @author fuzhi
 * @return 返回翻译字符串
 *
 */
public class NiuTrans
{
    public static NiuTrans niuTrans = null;
    private HttpClient client;
    private PostMethod method;
    String url = "";
    
    public void setConnection(){
        String ip = "";
        String from = "";
        String to = "";
        Properties properties = new Properties();
        //这里有人用new FileInputStream(fileName),不过这种方式找不到配置文件。有人说是在classes下，我调过了，不行。  
        InputStream in = NiuTrans.class.getResourceAsStream("/translation.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }  
            }
        }
        if(properties.containsKey("tranlationServerIp")){  
            ip = properties.getProperty("tranlationServerIp");  
        }
        if(properties.containsKey("from")){  
            from = properties.getProperty("from");  
        }
        if(properties.containsKey("to")){  
            to = properties.getProperty("to");  
        }
        
        url="http://" + ip + "/niutrans/translation?from=" + from + "&to=" + to;
        client = new HttpClient();
    }
    public String translate(String content){
        String temp="";
        String tgt_text="输入错误！错误码：10020";
        method = new UTF8PostMethod(url);
        method.setParameter("src_text", content);
        try {
            client.executeMethod(method);
            temp= method.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
            return "连接超时！错误码：10021";
        } catch (IOException e) {
            e.printStackTrace();
            return "连接错误！错误码：10022";
        }
        //执行请求  
        JSONObject json = JSONObject.fromObject(temp);
        if(temp.indexOf("tgt_text")!=-1){
            tgt_text=json.get("tgt_text").toString();
        }
        method.releaseConnection();
        return tgt_text;

    }
    private class UTF8PostMethod extends PostMethod {   
        public UTF8PostMethod(String url) {   
            super(url);   
        }   
        @Override   
        public String getRequestCharSet() {   
            return "UTF-8";   
        }
    }
    public static NiuTrans getNiuTrans(){
        if(null == niuTrans){
            niuTrans = new NiuTrans();
            niuTrans.setConnection();
        }
        return niuTrans;
        
    }
}
