package com.thinvent.rules.dao.command;

public interface ICommandManager {

	public void execute(ICommand command) throws Exception;

	public void notransexecute(ICommand command) throws Exception;

}
