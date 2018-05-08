package com.tonik.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.thinvent.utils.DateUtil;

public class MismatchProductForm {
    
    private String date = "";
    
    private List<Map<String, Object>> result;
    
    public MismatchProductForm(Date date, List<Map<String, Object>> result) {
        this.date = DateUtil.formatDate(DateUtil.YYMMDD_FORMAT, date);
        this.result = result;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public List<Map<String,Object>> getResult()
    {
        return result;
    }

    public void setResult(List<Map<String, Object>> result)
    {
        this.result = result;
    }
}
