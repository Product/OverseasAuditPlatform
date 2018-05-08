package com.tonik.service;

import java.util.List;

import com.tonik.Constant;
import com.tonik.dao.IAreaDAO;
import com.tonik.dao.IBrandDAO;
import com.tonik.dao.ICountryDAO;
import com.tonik.dao.IEventAffectedAreaDAO;
import com.tonik.dao.IEventAffectedBrandDAO;
import com.tonik.dao.IEventAffectedMaterialDAO;
import com.tonik.dao.IEventAffectedProductTypeDAO;
import com.tonik.dao.IEventDAO;
import com.tonik.dao.IEventTypeDAO;
import com.tonik.dao.IKeyWordDAO;
import com.tonik.dao.IMaterialDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.model.Area;
import com.tonik.model.Brand;
import com.tonik.model.Country;
import com.tonik.model.Event;
import com.tonik.model.EventAffectedArea;
import com.tonik.model.EventAffectedBrand;
import com.tonik.model.EventAffectedMaterial;
import com.tonik.model.EventAffectedProductType;
import com.tonik.model.EventType;
import com.tonik.model.KeyWord;
import com.tonik.model.Material;
import com.tonik.model.ProductType;

/**
 * @desc:事件概览，service层
 * @spring.bean id="eventService"
 * @spring.property name="eventDAO" ref="EventDAO"
 * @spring.property name="keyWordDAO" ref="KeyWordDAO"
 * @spring.property name="eventAffectedBrandDAO" ref="EventAffectedBrandDAO"
 * @spring.property name="eventAffectedProductTypeDAO" ref="EventAffectedProductTypeDAO"
 * @spring.property name="eventAffectedAreaDAO" ref="EventAffectedAreaDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 * @spring.property name="brandDAO" ref="BrandDAO"
 * @spring.property name="countryDAO" ref="CountryDAO"
 * @spring.property name="areaDAO" ref="AreaDAO"
 * @spring.property name="eventTypeDAO" ref="EventTypeDAO"
 * @spring.property name="materialDAO" ref="MaterialDAO"
 * @spring.property name="eventAffectedMaterialDAO" ref="EventAffectedMaterialDAO"
 */
public class EventService
{
    private IEventDAO EventDAO;
    private IKeyWordDAO KeyWordDAO;
    private IEventAffectedBrandDAO EventAffectedBrandDAO;
    private IEventAffectedProductTypeDAO EventAffectedProductTypeDAO;
    private IEventAffectedAreaDAO EventAffectedAreaDAO;
    private IEventAffectedMaterialDAO EventAffectedMaterialDAO;
    private IProductTypeDAO ProductTypeDAO;
    private IBrandDAO BrandDAO;
    private ICountryDAO CountryDAO;
    private IAreaDAO AreaDAO;
    private IEventTypeDAO EventTypeDAO;
    private IMaterialDAO MaterialDAO;
    
    
    public IEventAffectedMaterialDAO getEventAffectedMaterialDAO()
    {
        return EventAffectedMaterialDAO;
    }

    public void setEventAffectedMaterialDAO(IEventAffectedMaterialDAO eventAffectedMaterialDAO)
    {
        EventAffectedMaterialDAO = eventAffectedMaterialDAO;
    }

    public IMaterialDAO getMaterialDAO()
    {
        return MaterialDAO;
    }

    public void setMaterialDAO(IMaterialDAO materialDAO)
    {
        MaterialDAO = materialDAO;
    }

    public IEventDAO getEventDAO()
    {
        return EventDAO;
    }

    public void setEventDAO(IEventDAO eventDAO)
    {
        EventDAO = eventDAO;
    }
    
    public IKeyWordDAO getKeyWordDAO()
    {
        return KeyWordDAO;
    }

    public void setKeyWordDAO(IKeyWordDAO keyWordDAO)
    {
        KeyWordDAO = keyWordDAO;
    }
    
    public IEventAffectedBrandDAO getEventAffectedBrandDAO()
    {
        return EventAffectedBrandDAO;
    }

    public void setEventAffectedBrandDAO(IEventAffectedBrandDAO eventAffectedBrandDAO)
    {
        EventAffectedBrandDAO = eventAffectedBrandDAO;
    }

    public IEventAffectedProductTypeDAO getEventAffectedProductTypeDAO()
    {
        return EventAffectedProductTypeDAO;
    }

    public void setEventAffectedProductTypeDAO(IEventAffectedProductTypeDAO eventAffectedProductTypeDAO)
    {
        EventAffectedProductTypeDAO = eventAffectedProductTypeDAO;
    }

    public IEventAffectedAreaDAO getEventAffectedAreaDAO()
    {
        return EventAffectedAreaDAO;
    }

    public void setEventAffectedAreaDAO(IEventAffectedAreaDAO eventAffectedAreaDAO)
    {
        EventAffectedAreaDAO = eventAffectedAreaDAO;
    }

    public IProductTypeDAO getProductTypeDAO()
    {
        return ProductTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        ProductTypeDAO = productTypeDAO;
    }
    

    public IBrandDAO getBrandDAO()
    {
        return BrandDAO;
    }

    public void setBrandDAO(IBrandDAO brandDAO)
    {
        BrandDAO = brandDAO;
    }

    public ICountryDAO getCountryDAO()
    {
        return CountryDAO;
    }

    public void setCountryDAO(ICountryDAO countryDAO)
    {
        CountryDAO = countryDAO;
    }
    
    public IAreaDAO getAreaDAO()
    {
        return AreaDAO;
    }

    public void setAreaDAO(IAreaDAO areaDAO)
    {
        AreaDAO = areaDAO;
    }

    public IEventTypeDAO getEventTypeDAO()
    {
        return EventTypeDAO;
    }

    public void setEventTypeDAO(IEventTypeDAO eventTypeDAO)
    {
        EventTypeDAO = eventTypeDAO;
    }

    //获取某页所有事件
    public List<Event> EventPaging(int pageIndex, int pageSize, String strQuery, String strStartTime,
            String strEndTime)
    {  
        return EventDAO.getEventPaging(pageIndex, pageSize, strQuery, strStartTime, strEndTime);
    }

    //获取事件总数
    public String EventTotal(String strQuery, String strStartTime, String strEndTime)
    {
       return Integer.toString(EventDAO.getEventTotal(strQuery, strStartTime, strEndTime));
    }

    //保存事件
    public void SaveEvent(Event event)
    {
        EventType et = EventTypeDAO.getEventTypeById(event.getTypeId());
        event.setTypeName(et.getName());
        EventDAO.saveEvent(event);
    }

    //通过Id查找事件
    public Event GetEventById(long eventId)
    {
        return EventDAO.getEvent(eventId);
    }

    //删除事件
    public void DelEvent(Event event)
    {
        EventDAO.removeEvent(event);
    }

    public String getEventInfo(Event item){
        return "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName()
                + "\",\"TypeName\":\"" + item.getTypeName() + "\",\"BeginDate\":\""
                + Constant.getFormatTime(item.getBeginDate()) + "\",\"EndDate\":\""
                + Constant.getFormatTime(item.getEndDate()) + "\",\"Remark\":\"" + item.getRemark()
                + "\"}";
    }
    
	/**
	 *@保存事件影响的相关数据
	 *@param: e 事件
	 *@param: keyId 关键字的id
	 */
    public void SaveEventBind(Event e, Long keyId)
    {
        KeyWord kw = (KeyWord)KeyWordDAO.getObject(KeyWord.class, keyId);
        switch(kw.getCategory()){
            case 1://商品种类
                ProductType pt = ProductTypeDAO.getProductType(kw.getCategoryId());
                EventAffectedProductType eapt = new EventAffectedProductType();
                eapt.setEventId(e.getId());
                if(pt.getLevel() == 3){
                    eapt.setProductThirdTypeId(pt.getId());
                    eapt.setProductThirdTypeName(pt.getName());
                    pt = ProductTypeDAO.getProductType(pt.getPtid());
                }
                if(pt.getLevel() == 2){
                    eapt.setProductSecondTypeId(pt.getId());
                    eapt.setProductSecondTypeName(pt.getName());
                    pt = ProductTypeDAO.getProductType(pt.getPtid());
                }
                if(pt.getLevel() == 1){
                    eapt.setProductFirstTypeId(pt.getId());
                    eapt.setProductFirstTypeName(pt.getName());
                }
                EventAffectedProductTypeDAO.saveEventAffectedProductType(eapt);
                break;
            case 2://品牌
                Brand brand = BrandDAO.getBrandById(kw.getCategoryId());
                EventAffectedBrand eab = new EventAffectedBrand();
                eab.setBrandId(brand.getId());
                eab.setBrandName(brand.getName_cn());
                eab.setEventId(e.getId());
                EventAffectedBrandDAO.saveEventAffectedBrand(eab);
                break;
            case 3://国家
                Country country = CountryDAO.getCountryById(kw.getCategoryId());
                EventAffectedArea eaa = new  EventAffectedArea();
                eaa.setCountryId(country.getId());
                eaa.setCountryName(country.getName());
                eaa.setEventId(e.getId());
                EventAffectedAreaDAO.saveEventAffectedArea(eaa);
                break;
            case 4://原料
                Material material = MaterialDAO.getMaterialById(kw.getCategoryId());
                EventAffectedMaterial eam = new EventAffectedMaterial();
                eam.setMaterialId(material.getId());
                eam.setMaterialName(material.getName());
                eam.setMaterialTypeId(material.getMaterialtype().getId());
                eam.setMaterialTypeName(material.getMaterialtype().getName());
                eam.setEventId(e.getId());
                EventAffectedMaterialDAO.saveEventAffectedMaterial(eam);
                break;
            case 5://地区
                Area area = AreaDAO.getAreaById(kw.getCategoryId());
                EventAffectedArea eaaa = new  EventAffectedArea();
                eaaa.setAreaId(area.getId());
                eaaa.setAreaName(area.getName());
                eaaa.setCountryId(area.getCountry().getId());
                eaaa.setCountryName(area.getCountry().getName());
                eaaa.setEventId(e.getId());
                EventAffectedAreaDAO.saveEventAffectedArea(eaaa);
                break;
        }  
    }

    public String getEventAffectedDetailInfo(Event e)
    {
        String res = "";
        String keys="";
        String keysContent = "";
        EventType et = EventTypeDAO.getEventTypeById(e.getTypeId());
        if(et.getAffectArea() == 1){
            List<EventAffectedArea> eaas = EventAffectedAreaDAO.getEventAffectedAreas(e.getId());
            if(eaas.size() > 0){
                keys += "\"影响地区\",";
                keysContent +="\"";
                for(EventAffectedArea eaa:eaas){
                    if(eaa.getRegionName() != null){
                        keysContent += eaa.getRegionName()+"/";
                    }
                    if(eaa.getCountryName() != null){
                        keysContent += eaa.getCountryName()+"/";
                    }
                    if(eaa.getAreaName() != null){
                        keysContent += eaa.getAreaName()+"/";
                    }
                    keysContent += " ";
                }
                keysContent +="\",";
            }
        }
        if(et.getAffectBrand() == 1){
            List<EventAffectedBrand> eabs = EventAffectedBrandDAO.getEventAffectedBrandsByEvent(e.getId());
            if(eabs.size() > 0){
                keys += "\"影响品牌\",";
                keysContent +="\"";
                for(EventAffectedBrand eba:eabs){
                    keysContent += eba.getBrandName()+" ";
                }
                keysContent +="\",";
            }
        }
        if(et.getAffectProductType() == 1){
            List<EventAffectedProductType> eapts = EventAffectedProductTypeDAO.getEventAffectedProductTypes(e.getId());
            if(eapts.size() > 0){
                keys += "\"影响商品类别\",";
                keysContent +="\"";
                for(EventAffectedProductType eapt:eapts){
                    if(eapt.getProductFirstTypeName() != null){
                        keysContent += eapt.getProductFirstTypeName()+"/";
                    }
                    if(eapt.getProductSecondTypeName() != null){
                        keysContent += eapt.getProductSecondTypeName()+"/";
                    }
                    if(eapt.getProductThirdTypeName() != null){
                        keysContent += eapt.getProductThirdTypeName()+"/";
                    }
                    keysContent += " ";
                }
                keysContent +="\",";
            }
        }
        if(keys.length() > 0){
            keys = keys.substring(0, keys.length()-1);
            keysContent = keysContent.substring(0, keysContent.length()-1);
        }
        res = "\"keys\":["+keys+"], \"keysContent\":["+keysContent+"]";
        return res;
    }
}
