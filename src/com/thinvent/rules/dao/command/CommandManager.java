package com.thinvent.rules.dao.command;

import java.io.Serializable;

import com.thinvent.rules.dao.facade.IDAOFacade;

public class CommandManager implements ICommandManager,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IDAOFacade daofacade;
	
	public void execute(ICommand command) throws Exception {
		command.setDaofacade(daofacade);
		command.execute();
	}

	public void notransexecute(ICommand command) throws Exception {
		command.setDaofacade(daofacade);
		command.execute();
	}

	public void setDaofacade(IDAOFacade daofacade) {
		this.daofacade = daofacade;
	}

	
}
