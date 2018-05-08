package com.thinvent.rules.service;

import java.io.Serializable;

import com.thinvent.common.rules.def.service.IRulesProcessMain;
import com.thinvent.common.rules.def.service.IRulesProcessorService;

/**
 * This is the main class to load the rules engine. It is used in the MBean.
 * It has hardcoded spring config files that it will load.
 * It uses class implementing IRulesProcessorService to initialize the engine.
 * @author jxfetyko
 * 
 */
public class RulesProcessorMain implements IRulesProcessMain,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IRulesProcessorService service;

	public RulesProcessorMain() {
		String[] config = new String[] { "portletdrool-dao-context.xml", "portletdrool-datasource-context.xml", "portletdrool-facade-context.xml" ,"portletdrool-hibernate-context.xml","portletdrool-rules-context.xml"};
		this.service = new RulesProcessorService(config);
	}
	
	/* (non-Javadoc)
	 * @see com.twg.wlsconnect.drools.service.IRulesProcessFacade#getService()
	 */
	public IRulesProcessorService getService() {
		return service;
	}	
	
}
