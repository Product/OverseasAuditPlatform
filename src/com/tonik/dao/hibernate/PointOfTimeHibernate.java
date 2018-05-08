package com.tonik.dao.hibernate;

import com.tonik.dao.IPointOfTimeDAO;
import com.tonik.model.PointOfTime;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IPointOfTimeDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="PointOfTimeDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class PointOfTimeHibernate extends BaseDaoHibernate implements IPointOfTimeDAO
{

    @Override
    public PointOfTime getPointOfTime()
    {
        return (PointOfTime) getHibernateTemplate().find("from PointOfTime").get(0);
    }
    
    public void UpdateTime(PointOfTime newDate)
    {
        getHibernateTemplate().saveOrUpdate(newDate);
    }

}
