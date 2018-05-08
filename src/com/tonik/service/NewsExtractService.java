package com.tonik.service;

import com.tonik.dao.INewsExtractDAO;
import com.tonik.model.KeyWord;

/**
 * @spring.bean id="NewsExtractService"
 * @spring.property name="newsExtractDAO" ref="NewsExtractDAO"
 */
public class NewsExtractService
{
    private INewsExtractDAO NewsExtractDAO;

    public void setNewsExtractDAO(INewsExtractDAO newsExtractDAO)
    {
        this.NewsExtractDAO = newsExtractDAO;
    }
    
    public int NewsExtractTotal(String strQuery,int category)
    {
        return NewsExtractDAO.getKeyWordTotal(strQuery, category);
    }
    
    public KeyWord NewsExtract(String strQuery,int category)
    {
        return NewsExtractDAO.getKeyWord(strQuery, category);
    }
    
    public void SaveKeyWord(KeyWord keyword)
    {
        NewsExtractDAO.SaveKeyWord(keyword);
    }
}
