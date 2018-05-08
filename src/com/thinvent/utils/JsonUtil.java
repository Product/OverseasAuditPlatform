package com.thinvent.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.tonik.util.TextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

/**
 * JSON转换工具类
 */
public class JsonUtil {

    /**
     * 对象转换成JSON字符串，首字母小写
     * 
     * @param obj 需要转换的对象
     * @return 对象的string字符
     */
    public static String objectToJson(Object obj) {
        
        JSONObject jSONObject = JSONObject.fromObject(obj);
        return jSONObject.toString();
    }
    
    /**
     * 对象转换成JSON字符串，首字母大写
     * 
     * @param obj 需要转换的对象
     * @return 对象的string字符
     */
    public static String objectToJsonCap(Object obj) {
        JSONObject jSONObject = JSONObject.fromObject(obj);

        JSONObject resultJSONObject = transObject(jSONObject);
        return resultJSONObject.toString();
    }
    
    /**
     * @desc : JSONObject 中的key的首字母变成大写
     * 
     * @param o1
     * @return
     */
    private static JSONObject transObject(JSONObject o1){
        JSONObject o2=new JSONObject();
         Iterator it = o1.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object object = o1.get(key);
                if(object.getClass().toString().endsWith("String")){
//                    o2.accumulate(key.toUpperCase(), object);
                    o2.accumulate(TextUtil.toUpperCap(key), object);
                }else if(object.getClass().toString().endsWith("JSONObject")){
                    //o2.accumulate(key.toLowerCase(), transObject((JSONObject)object));
                    o2.accumulate(TextUtil.toUpperCap(key), transObject((JSONObject)object));
                }else if(object.getClass().toString().endsWith("JSONArray")){
                    //o2.accumulate(key.toLowerCase(), transArray(o1.getJSONArray(key)));
                    o2.accumulate(TextUtil.toUpperCap(key), transArray(o1.getJSONArray(key)));
                    
                }
                else
                {
                    o2.accumulate(TextUtil.toUpperCap(key), object);
                }
            }
            return o2;
    }
    
    private static JSONArray transArrayCap(JSONArray ArrayList )
    {
        JSONArray resultObjectList = new JSONArray();
        for(Object item : ArrayList)
        {
            JSONObject object = transObject((JSONObject)item);
            resultObjectList.add(object);
        }
        return resultObjectList;
    }
    
    private static JSONArray transArray(JSONArray o1){
        JSONArray o2 = new JSONArray();
        for (int i = 0; i < o1.size(); i++) {
            Object jArray=o1.getJSONObject(i);
            if(jArray.getClass().toString().endsWith("JSONObject")){
                o2.add(transObject((JSONObject)jArray));
            }else if(jArray.getClass().toString().endsWith("JSONArray")){
                o2.add(transArray((JSONArray)jArray));
            }
        }
        return o2;
    }
    
    public static String listToJson(List list){
        JSONArray json = JSONArray.fromObject(list);
        
        return json.toString();
    }
    
    public static String listToJsonCap(List list){
        JSONArray json = JSONArray.fromObject(list);
        JSONArray resultJson = transArrayCap(json);
        return resultJson.toString();

        
    }
    public static String mapToJson(Map<String, String> map)
    {

        Set<String> keys = map.keySet();

        String key = "";

        String value = "";

        StringBuffer jsonBuffer = new StringBuffer();

        jsonBuffer.append("{");

        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {

            key = (String) it.next();

            value = map.get(key);

            jsonBuffer.append(key + ":" + value);

            if (it.hasNext())
            {

                jsonBuffer.append(",");

            }

        }

        jsonBuffer.append("}");

        return jsonBuffer.toString();

    }

    /**
     * JSON字符串转换成对象
     * 
     * @param jsonString  需要转换的字符串, 首字母小写
     * @param type 目标对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String jsonString, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        return (T) JSONObject.toBean(jsonObject, type);
    }
    
    /**
     * JSON字符串转换成对象
     * 
     * @param jsonString  需要转换的字符串, 首字母大写
     * @param type 目标对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJsonCap(String jsonString, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        JsonConfig config = new JsonConfig();
        
        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {

            @Override
            public String transformToJavaIdentifier(String str) {
                
                return TextUtil.toLowerCap(str);
            }

        });
        
        return (T) JSONObject.toBean(jsonObject, type);
    }

    /**
     * 将JSONArray对象转换成list集合
     * 
     * @param jsonArr
     * @return
     */
    public static List<Object> jsonToList(JSONArray jsonArr) {
        List<Object> list = new ArrayList<Object>();
        for (Object obj : jsonArr) {
            if (obj instanceof JSONArray) {
                list.add(jsonToList((JSONArray) obj));
            } else if (obj instanceof JSONObject) {
                list.add(jsonToMap((JSONObject) obj));
            } else {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 将json字符串转换成map对象
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        JSONObject obj = JSONObject.fromObject(json);
        return jsonToMap(obj);
    }

    /**
     * 将JSONObject转换成map对象
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(JSONObject obj) {
        Set<?> set = obj.keySet();
        Map<String, Object> map = new HashMap<String, Object>(set.size());
        for (Object key : obj.keySet()) {
            Object value = obj.get(key);
            if (value instanceof JSONArray) {
                map.put(key.toString(), jsonToList((JSONArray) value));
            } else if (value instanceof JSONObject) {
                map.put(key.toString(), jsonToMap((JSONObject) value));
            } else {
                map.put(key.toString(), obj.get(key));
            }

        }
        return map;
    }
    
}
