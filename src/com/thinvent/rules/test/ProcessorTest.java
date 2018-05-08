package com.thinvent.rules.test;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinvent.common.rules.def.test.IProcessorTest;

public class ProcessorTest implements IProcessorTest,Serializable {

	private static Log log = LogFactory.getLog(ProcessorTest.class);
	
	public void runTest(String str1, List<Object> list) {
		//org.springframework.jndi.JndiObjectFactoryBean;
		//org.springframework.jndi.JndiObjectLocator
		//org.springframework.jndi.JndiLocatorSupport
		//\org.springframework.jndi.JndiAccessor
		log.info("Running method with parameters:");
		log.info(str1);
		log.info(list.toString());
	}

}
