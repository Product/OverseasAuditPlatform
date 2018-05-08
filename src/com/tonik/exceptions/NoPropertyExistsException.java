package com.tonik.exceptions;

public class NoPropertyExistsException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -7558418088354879095L;
    
    public NoPropertyExistsException() {} 
    
    public NoPropertyExistsException(String message) { //用来创建指定参数对象
        super(message); //调用超类构造器
    }

}
