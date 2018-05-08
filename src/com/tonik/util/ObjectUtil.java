package com.tonik.util;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectUtil
{
    static Log log = LogFactory.getLog(ObjectUtil.class);
    
    /**
     * 检查 某对象是否存在某属性
     * @param owner
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static boolean checkProperty(Object owner, String fieldName){
        boolean result = false;
        Field field = null;
        try
        {
            field = owner.getClass().getDeclaredField(fieldName);      
            
        } catch (Exception e)
        {
            log.info("field not exists.");
        }
        
        if(field != null){
            result = true;
        }
        
        return result;
    }
    
    /**
     * 给对象的某个属性设值
     * @param owner
     * @param fieldName
     * @param fieldValue
     * @throws Exception
     */
    public static boolean setProperty(Object owner, String fieldName, String fieldValue){
        boolean result = false;
        try
        {
            Field field = owner.getClass().getDeclaredField(fieldName);      
            
            if(field != null){
                field.setAccessible(true);      
                field.set(owner, fieldValue);      
                field.setAccessible(false);   
                result = true;
            }
        } catch (Exception e)
        {
            log.error("error occourred when invoke ObjectUtil.setProperty()");
        } 
        
        return result;
    }
    
}
