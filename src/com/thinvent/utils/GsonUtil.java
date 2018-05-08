package com.thinvent.utils;

import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public final class GsonUtil {

    private static final Logger logger = Logger.getLogger(GsonUtil.class);

    private GsonUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("GsonUtil cannot be instantiated !");
    }

    private static final Gson GSON = new Gson();

    /**
     * 把json字符串转换为JavaBean
     *
     * @param json      json字符串
     * @param beanClass JavaBean的Class
     * @return 解析成功返回JavaBean类，失败返回null
     */
    public static <T> T json2Bean(String json, Class<T> beanClass) {
        T bean = null;
        try {
            bean = GSON.fromJson(json, beanClass);
        } catch (Exception e) {
            logger.error("GsonUtil解析json数据时出现异常\njson = " + json, e);
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * 把json字符串转换为JavaBean，如果json的根节点就是一个集合，则使用此方法<p>
     * type参数的获取方式为：<p> Type type = new TypeToken<集合泛型>(){}.getType();
     *
     * @param json json字符串
     * @return 解析成功返回指定的数据类型，失败返回null
     */
    public static <T> T json2Bean(String json, Type type) {
        T bean = null;
        try {
            bean = GSON.fromJson(json, type);
        } catch (Exception e) {
            logger.error("GsonUtil解析json数据时出现异常\njson = " + json, e);
            throw new RuntimeException(e);
        }
        return bean;
    }
    
    /**
     * 把JavaBean字符串转换为json字符串
     *
     * @param object    JavaBean的对象
     * @return 解析成功返回Json字符串，失败返回null
     */
    public static String bean2Json(Object object) {
        return GSON.toJson(object);
    }

}
