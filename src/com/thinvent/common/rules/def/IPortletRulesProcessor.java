package com.thinvent.common.rules.def;

import java.util.List;
import java.util.Map;

/**
 * Processes rules
 * @author ben.chen
 *
 */
public interface IPortletRulesProcessor {

	/**
	 * Method to run the rules for given parameters
	 * @param country
	 * @param portlet portlet id (developer defined)
	 * @param mode portlet mode (view,edit,help, etc.) (developer defined) 
	 * @param sequence 1,2,3...
	 * @param inserts Object that will be inserted into the rules
	 * @param globals Objects that will be used as globals in the rules
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */
	public void runRules(String country, String portlet, String mode, int sequence,List<Object> inserts,Map<String,Object> globals) throws RulesException;

	/**
	 * Method to run the rules for given parameters
	 * @param country
	 * @param portlet portlet id (developer defined)
	 * @param mode portlet mode (view,edit,help, etc.) (developer defined) 
	 * @param sequence 1,2,3...
	 * @param inserts Object that will be inserted into the rules
	 * @param globals Objects that will be used as globals in the rules
	 * @param cl Custom ClassLoader to use to load the classes for inserts and globals
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */
	public void runRules(String country, String portlet, String mode, int sequence,List<Object> inserts,Map<String,Object> globals,ClassLoader cl) throws RulesException;

	/**
	 * Method to run all the rules for given parameters in the sequence order
	 * @param country
	 * @param portlet portlet id (developer defined)
	 * @param mode portlet mode (view,edit,help, etc.) (developer defined) 
	 * @param inserts Object that will be inserted into the rules
	 * @param globals Objects that will be used as globals in the rules
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */
	public void runRules(String country, String portlet, String mode, List<Object> inserts,Map<String,Object> globals) throws RulesException;

	/**
	 * Method to run all the rules for given parameters in the sequence order
	 * @param country
	 * @param portlet portlet id (developer defined)
	 * @param mode portlet mode (view,edit,help, etc.) (developer defined) 
	 * @param inserts Object that will be inserted into the rules
	 * @param globals Objects that will be used as globals in the rules
	 * @param cl Custom ClassLoader to use to load the classes for inserts and globals
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */
	public void runRules(String country, String portlet, String mode, List<Object> inserts,Map<String,Object> globals, ClassLoader cl) throws RulesException;

	/**
	 * Flushes / Clears the rules cache
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */
	public void flushRulesCache() throws RulesException;

	/**
	 * Flushes / Clears the rules cache for a particular rule
	 * @param country country code
	 * @param portlet portlet id (developer defined)
	 * @param mode portlet mode (view,edit,help, etc.) (developer defined) 
	 * @param sequence 1,2,3...
	 * @throws RulesException is thrown if there is some error or if there are no rules available
	 */	
	public void flushRulesCache(String country, String portlet, String mode, int sequence) throws RulesException;
	
	/**
	 * Updates and Saves rules. Used from clients to deploy new rules.  
	 * @param country
	 * @param portlet
	 * @param mode
	 * @param sequence
	 * @throws RulesException
	 */
	public void deployRule(String country, String portlet, String mode, int sequence, String drl, String dsl) throws RulesException;
	
	/**
	 * Set to true if the rules cache should not be used, this is good if the rules are changed often, otherwise the rules will be in the cache
	 * @param nocache
	 */
	public void setNoRulesCache(boolean nocache);
	
	/**
	 * Set to true to enable serialization which means that the rules package will be serialized and stored in the DB
	 * @param es
	 */
	public void setEnableSerialization(boolean es);
	
}
