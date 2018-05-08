package com.thinvent.common.rules.def.service;

import com.thinvent.common.rules.def.IPortletRulesProcessor;

/**
 * This service initializes Rules Processor.
 * It takes the Spring config files in the constructor and once spring is initialized it retrieves
 * the RulesProcessor bean from the context and keeps a local reference to it.
 * @author jxfetyko
 */
public interface IRulesProcessorService {

	public abstract IPortletRulesProcessor getRulesprocessor();

	/**
	 * Set to true if the rules cache should not be used, this is good if the rules are changed often, otherwise the rules will be in the cache
	 * @param nocache
	 */
	public abstract void setNoRulesCache(boolean nocache);
	
	/**
	 * Set to true to enable serialization which means that the rules package will be serialized and stored in the DB
	 * @param es
	 */
	public abstract void setEnableSerialization(boolean es);

}