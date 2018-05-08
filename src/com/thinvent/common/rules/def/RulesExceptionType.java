package com.thinvent.common.rules.def;

public enum RulesExceptionType {
	
	//fatal
	RULES_ENGINE_INIT_FAILED,
	RULES_ENGINE_PARSER_FAILED,
	RULES_ENGINE_IO_FAILED,
	RULE_EXECUTION_FAILED,

	//non-fatal
	RULE_DOES_NOT_EXISTS,

	//cache
	RULE_CACHE_CLEAR_FAILED;	
}
