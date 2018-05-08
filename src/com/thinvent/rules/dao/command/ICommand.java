package com.thinvent.rules.dao.command;

import com.thinvent.rules.dao.facade.IDAOFacade;

public interface ICommand {

	public void setDaofacade(IDAOFacade facade);
	
	public void execute() throws CommandArgumentMissingException, Exception;
}
