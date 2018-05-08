package com.tonik.dao;

import java.util.List;

import com.tonik.model.State;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is the interface for State DAO, and this interface is an example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 */
public interface IStateDAO extends IDAO
{
    public List<State> getStates();

    public State getState(Long stateId);

    public State getStateByCode(String stateCode);

    public void saveState(State state);

    public void removeState(State state);
}// EOF
