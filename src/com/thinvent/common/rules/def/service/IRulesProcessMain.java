package com.thinvent.common.rules.def.service;

/**
 * This is the main class to load the rules engine. It is used in the MBean.
 * It has hardcoded spring config files that it will load.
 * It uses class implementing IRulesProcessorService to initialize the engine.
 * @author jxfetyko
 * 
 */
public interface IRulesProcessMain {

	public abstract IRulesProcessorService getService();

}