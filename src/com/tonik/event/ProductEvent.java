package com.tonik.event;

import org.springframework.context.ApplicationEvent;

public class ProductEvent extends ApplicationEvent
{
     private static final long serialVersionUID = 1L ;
     private  String msg;
     
     public  ProductEvent(Object source, String msg) {
         super (source);
         this.msg =  msg;
     }
     
     public  String getMessage() {
        return  msg;
     }
}
