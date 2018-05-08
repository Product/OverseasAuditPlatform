package com.thinvent.rules.dao.command;

import com.thinvent.rules.dao.facade.IDAOFacade;


public abstract class AbstractCommand implements ICommand {

	private IDAOFacade daofacade;

	public IDAOFacade getDaofacade() {
		return daofacade;
	}

	public void setDaofacade(IDAOFacade daofacade) {
		this.daofacade = daofacade;
	}
	
	
}
