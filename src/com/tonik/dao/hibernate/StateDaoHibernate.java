package com.tonik.dao.hibernate;

import java.util.List;

import com.tonik.dao.IStateDAO;
import com.tonik.model.State;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IStateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="stateDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class StateDaoHibernate extends BaseDaoHibernate implements IStateDAO
{

    public List getStates()
    {
        return getHibernateTemplate().find("from State");
    }

    public State getState(Long stateId)
    {
        return (State) getHibernateTemplate().get(State.class, stateId);
    }

    public State getStateByCode(String stateCode)
    {
        List states = getHibernateTemplate().find("from State where stateCode=?", stateCode);
        if (states.isEmpty())
        {
            return null;
        }
        else
        {
            return (State) states.get(0);
        }
    }

    public void saveState(State state)
    {
        getHibernateTemplate().saveOrUpdate(state);
    }

    public void removeState(State state)
    {
        getHibernateTemplate().delete(state);
    }
}// EOF
