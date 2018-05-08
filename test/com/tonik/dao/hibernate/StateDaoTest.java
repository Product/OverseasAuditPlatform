package com.tonik.dao.hibernate;

import com.tonik.dao.IStateDAO;
import com.tonik.model.State;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example testcase for testing StateDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * 
 * @spring.bean id="stateDaoTest"
 * @spring.property name="stateDAO" ref="stateDAO"
 */
public class StateDaoTest extends BaseDaoTestCase
{
    private IStateDAO stateDao;


    public void setStateDAO(IStateDAO stateDao)
    {
        this.stateDao = stateDao;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(StateDaoTest.class);
    }

    public void testAddAndRemoveState() throws Exception
    {
        State state = new State();
        state.setStateCode("CA");
        state.setStateName("CANADA");
        stateDao.saveState(state);
        setComplete();
        endTransaction();

        startNewTransaction();
        state = stateDao.getStateByCode("CA");
        assertNotNull(state.getStateCode());
        assertNotNull(state.getStateName());

        stateDao.removeState(state);
        setComplete();
        endTransaction();

        state = stateDao.getStateByCode("CA");
        assertNull(state);
    }
}// EOF
