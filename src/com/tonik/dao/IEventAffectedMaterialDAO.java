package com.tonik.dao;

import java.util.List;

import com.tonik.model.EventAffectedMaterial;

/**
 * @desc 事件影响配方原料 DAO
 * @author thinvent
 *
 */
public interface IEventAffectedMaterialDAO
{
    /**
     * @desc: 通过id获取该事件影响的所有配方原料
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterials(Long eventId);

    /**
     * @desc: 通过EventAffectedMaterial的id获取该EventAffectedMaterial
     * @param id the id of EventAffectedMaterial
     * @return EventAffectedArea
     */
    public EventAffectedMaterial getEventAffectedMaterialById(Long id);

    /**
     * @desc 保存影响配方原料
     * @param eventAffectedArea
     */
    public void saveEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial);

    /**
     * @desc 删除影响的配方原料
     * @param eventAffectedArea
     */
    public void removeEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial);

    /**
     * @desc: 获取受影响的配方原料分页列表
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterialPaging(int pageIndex, int pageSize, String strQuery,
            Long eventId);

    /**
     * @desc: 获取事件影响的配方原料总数
     * @param eventId 事件id
     * @return int 产品配方总数
     */
    public int getEventAffectedMaterialTotal(Long eventId);

}
