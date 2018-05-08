package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IEventAffectedMaterialDAO;
import com.tonik.model.EventAffectedArea;
import com.tonik.model.EventAffectedMaterial;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: 事件影响配方原料 DAO层接口的实现
 * </p>
 * @since Nov 19, 2015
 * @version 1.0
 * @author liuyu
 * @spring.bean id="EventAffectedMaterialDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class EventAffectedMaterialDAOHibernate extends BaseDaoHibernate implements IEventAffectedMaterialDAO
{

 
    /**
     * @desc: 通过id获取该事件影响的所有配方原料
     * @Override
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterials(Long eventId)
    {
        return getHibernateTemplate().find("from EventAffectedMaterial where eventId="+eventId);
    }

    /**
     * @desc: 通过EventAffectedMaterial的id获取该EventAffectedMaterial
     * @Override     
     * @param id the id of EventAffectedMaterial
     * @return EventAffectedArea
     */
    public EventAffectedMaterial getEventAffectedMaterialById(Long id)
    {
        return (EventAffectedMaterial) getHibernateTemplate().get(EventAffectedMaterial.class,id);
    }

    /**
     * @desc 保存影响配方原料
     * @Override
     * @param eventAffectedArea
     */
    public void saveEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial)
    {
       getHibernateTemplate().saveOrUpdate(eventAffectedMaterial);
    }

    /**
     * @desc 删除影响的配方原料
     * @Override
     * @param eventAffectedArea
     */
    public void removeEventAffectedMaterial(EventAffectedMaterial eventAffectedMaterial)
    {
        getHibernateTemplate().delete(eventAffectedMaterial);
    }

    /**
     * @desc: 获取受影响的配方原料分页列表
     * @Override
     * @param pageIndex 页码
     * @param pageSize 每页记录数
     * @param strQuery 关键字
     * @param eventId 事件id
     * @return List<EventAffectedArea>
     */
    public List<EventAffectedMaterial> getEventAffectedMaterialPaging(int pageIndex, int pageSize, String strQuery,
            Long eventId)
    {
        try{
            final String hql = "from EventAffectedMaterial where eventId=:strEventId and( materialTypeName like :strQuery or materialName like :strQuery)";
                     
            List<EventAffectedMaterial> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
                    {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strEventId", eventId);
                    query.setParameter("strQuery", "%"+strQuery+"%");
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    List<EventAffectedArea> list = query.list();
                    return list;
                    }
                });
            return listTable;
            }catch(Exception e){
                return null;
                }
    }

    /**
     * @desc: 获取事件影响的配方原料总数
     * @Override
     * @param eventId 事件id
     * @return int 配方原料总数
     */
    public int getEventAffectedMaterialTotal(Long eventId)
    {
        return getEventAffectedMaterials(eventId).size();
    }

}
