package com.tonik.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.thinvent.utils.GsonUtil;

/**
 * <b>function:</b> WebService传递复杂对象，如JavaBean、Array、List、Map等
 */
@WebService(endpointInterface = "com.tonik.webservice.IDataService")
public class DataService implements IDataService
{
    public String helloWorld()
    {
        System.out.println("Hello world!");
        return GsonUtil.bean2Json("Hello world!");
    }
}