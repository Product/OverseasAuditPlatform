package com.tonik.dao;

import java.util.List;

import com.tonik.model.KeyWord;

public interface INewsExtractDAO
{
    public List<KeyWord> getKeyWord();
    
    public int getKeyWordTotal(String strQuery,int category);
    
    public KeyWord getKeyWord(String strQuery,final int category);
    
    public void SaveKeyWord(KeyWord keyword);
}
