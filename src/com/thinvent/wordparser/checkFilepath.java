package com.thinvent.wordparser;

import java.io.File;

import com.google.protobuf.UnknownFieldSet.Field;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thinvent.utils.PropertiesUtil;

public class checkFilepath
{
    public boolean  checkExists()
    {     
        PropertiesUtil.readProperties("hanlp.properties");
        String  filepath=PropertiesUtil.getProperty("root");
        //System.out.println(filepath);
        
        File  file=new File(filepath);
        if(file.isDirectory())
        { 
            return  true;
                
        }
        else {
            return false;
        }
        
    }
    
 
}
