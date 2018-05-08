package com.thinvent.common.rules.def;

import java.io.Serializable;

public class RulesException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private RulesExceptionType type;
	
	public RulesException(RulesExceptionType type) {
		super();
		this.type = type;
	}

	public RulesException(RulesExceptionType type,String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.type = type;
	}

	public RulesException(RulesExceptionType type,String arg0) {
		super(arg0);
		this.type = type;
	}

	public RulesException(RulesExceptionType type,Throwable arg0) {
		super(arg0);
		this.type = type;
	}

	public RulesExceptionType getType() {
		return this.type;
	}
	
}
