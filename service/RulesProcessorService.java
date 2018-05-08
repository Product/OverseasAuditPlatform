package com.thinvent.rules.service;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinvent.common.rules.def.IPortletRulesProcessor;
import com.thinvent.common.rules.def.service.IRulesProcessorService;


/**
 * This service initializes Rules Processor.
 * It takes the Spring config files in the constructor and once spring is initialized it retrieves
 * the RulesProcessor bean from the context and keeps a local reference to it.
 * @author jxfetyko
 */
public class RulesProcessorService extends ClassPathXmlApplicationContext implements IRulesProcessorService,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPortletRulesProcessor rulesprocessor;
	private boolean noRulesCache = false,enableSerialization=true;
	
	public RulesProcessorService(String[] configLocations) throws BeansException {
		super(configLocations);
		this.rulesprocessor = (IPortletRulesProcessor)this.getBean("RulesProcessor");
		this.rulesprocessor.setNoRulesCache(this.noRulesCache);
		this.rulesprocessor.setEnableSerialization(this.enableSerialization);
	}

	
	public void setNoRulesCache(boolean nc) {
		this.noRulesCache = nc;
	}


	public void setEnableSerialization(boolean enableSerialization) {
		this.enableSerialization = enableSerialization;
	}


	/* (non-Javadoc)
	 * @see com.twg.wlsconnect.drools.main.IRulesProcessorService#getRulesprocessor()
	 */
	public IPortletRulesProcessor getRulesprocessor() {
		return rulesprocessor;
	}

	public void setRulesprocessor(IPortletRulesProcessor rulesprocessor) {
		this.rulesprocessor = rulesprocessor;
	}

	
	
}
