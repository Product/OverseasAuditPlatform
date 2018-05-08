package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEventAffectedMaterialDAO;
import com.tonik.model.EventAffectedMaterial;

/**
* @desc:事件影响配方原料
* @spring.bean id="eventAffectedMaterialService"
* @spring.property name="eventAffectedMaterialDAO" ref="EventAffectedMaterialDAO"
*/
public class EventAffectedMaterialService
{
    private IEventAffectedMaterialDAO EventAffectedMaterialDAO;
    
    public IEventAffectedMaterialDAO getEventAffectedMaterialDAO()
    {
        return EventAffectedMaterialDAO;
    }

    public void setEventAffectedMaterialDAO(IEventAffectedMaterialDAO eventAffectedMaterialDAO)
    {
        EventAffectedMaterialDAO = eventAffectedMaterialDAO;
    }

    /**
     * @desc:通过事件id获取某事件影响的所有配方原料
     * @param eventId 事件id
     * @return List<EventAffectedMaterial>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterials(Long eventId)
    {
        return EventAffectedMaterialDAO.getEventAffectedMaterials(eventId);
    }

    /**
     * @desc 通过EventAffectedMaterial的id获取EventAffectedMaterial
     * @param id the id of EventAffectedMaterial
     * @return EventAffectedMaterial
     */
    public EventAffectedMaterial getEventAffectedMaterialById(Long id)
    {
        return EventAffectedMaterialDAO.getEventAffectedMaterialById(id);
    }

    /**
     * @desc 保存事件影响配方原料
     * @param eventAffectedMaterial
     */
    public void saveEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial)
    {
        EventAffectedMaterialDAO.saveEventAffectedMaterial(eventAffectedMaterial);
    }

    /**
     * @desc 删除对应配方原料
     * @param eventAffectedMaterial
     */
    public void removeEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial)
    {
        EventAffectedMaterialDAO.removeEventAffectedMaterial(eventAffectedMaterial);     
    }

    /**
     * @desc 获取事件影响的配方原料分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedMaterial>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterialPaging(int pageIndex, int pageSize,String strQuery,Long eventId){
 
        return EventAffectedMaterialDAO.getEventAffectedMaterialPaging(pageIndex, pageSize, strQuery,eventId);
    }
    
    /**
     * @desc 获取对应配方原料总数
     * @param eventId 事件id
     * @return String
     */
    public String getEventAffectedMaterialTotal(Long eventId)
    {
        return Integer.toString(EventAffectedMaterialDAO.getEventAffectedMaterialTotal(eventId));
    }
}
