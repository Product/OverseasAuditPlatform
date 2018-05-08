package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IEventTypeDAO;
import com.tonik.dao.IKeyWordDAO;
import com.tonik.model.EventType;
import com.tonik.model.KeyWord;

/**
 * @spring.bean id="KeyWordService"
 * @spring.property name="keyWordDAO" ref="KeyWordDAO"
 * @spring.property name="eventTypeDAO" ref="EventTypeDAO"
 */
public class KeyWordService
{
    private IKeyWordDAO keyWordDAO;
    private IEventTypeDAO eventTypeDAO;

    public IKeyWordDAO getKeyWordDAO()
    {
        return keyWordDAO;
    }

    public void setKeyWordDAO(IKeyWordDAO keyWordDAO)
    {
        this.keyWordDAO = keyWordDAO;
    }
    
    public void DelKeyWord()
    {
        keyWordDAO.DelKeyWord();
    }
 
    public IEventTypeDAO getEventTypeDAO()
    {
        return eventTypeDAO;
    }

    public void setEventTypeDAO(IEventTypeDAO eventTypeDAO)
    {
        this.eventTypeDAO = eventTypeDAO;
    }

    public KeyWord GetKeyWordById(Long id){
        KeyWord kw = (KeyWord) keyWordDAO.getObject(KeyWord.class, id);
        return kw;
    }
    
    public String GetKeyWordsRelationEventType(KeyWord kw){
        List<EventType> ets = new ArrayList<EventType>();
        switch(kw.getCategory()){
            case 1:ets = eventTypeDAO.getEventTypesAboutProductType();break;
            case 2:ets = eventTypeDAO.getEventTypesAboutBrand();break;
            case 3:ets = eventTypeDAO.getEventTypesAboutArea();break;
            case 4:ets = eventTypeDAO.getEventTypesAboutMaterial();break;
            case 5:ets = eventTypeDAO.getEventTypesAboutArea();break;
        }
        String res = "", names="", ids="";
        for(EventType et: ets){
            names += "\""+et.getName()+"\",";
            ids += "\""+et.getId()+"\",";
        }
        if(names.length() > 0){
            names = names.substring(0, names.length()-1);
            ids = ids.substring(0, ids.length()-1);
        }
        res = "{\"names\":["+names+"], \"ids\":["+ids+"]}";
        return res;
    }

    public String getKeyWordInfo(KeyWord kw)
    {
        String res = "{\"Id\":\"" + kw.getId() + "\",\"Name\":\"" + kw.getName()+"\"}";
        return res;
    }
}
