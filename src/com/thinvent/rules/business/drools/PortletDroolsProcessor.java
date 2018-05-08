package com.thinvent.rules.business.drools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.thinvent.common.rules.def.IPortletRulesProcessor;
import com.thinvent.common.rules.def.RulesException;
import com.thinvent.common.rules.def.RulesExceptionType;
import com.thinvent.rules.dao.businesslayer.Portlet;
import com.thinvent.rules.dao.businesslayer.Portletdrool;
import com.thinvent.rules.dao.businesslayer.PortletdroolPK;
import com.thinvent.rules.dao.businesslayer.Portletmode;
import com.thinvent.rules.dao.businesslayer.PortletmodePK;
import com.thinvent.rules.dao.facade.IDAOFacade;

/**
 * Processor to process drools rules. Currently the rules are retrieved from the
 * database. If there is a package available in the database it is used nothing
 * is being compiled. If there is no package, DRL and DSL are read and compiled
 * into a package, which is then saved to the database.
 * 
 * @author jxfetyko
 * 
 */
public class PortletDroolsProcessor implements IPortletRulesProcessor, Serializable {

    @Override
    public void runRules(String country, String portlet, String mode, int sequence, List<Object> inserts,
            Map<String, Object> globals) throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void runRules(String country, String portlet, String mode, int sequence, List<Object> inserts,
            Map<String, Object> globals, ClassLoader cl) throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void runRules(String country, String portlet, String mode, List<Object> inserts, Map<String, Object> globals)
            throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void runRules(String country, String portlet, String mode, List<Object> inserts, Map<String, Object> globals,
            ClassLoader cl) throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flushRulesCache() throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flushRulesCache(String country, String portlet, String mode, int sequence) throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deployRule(String country, String portlet, String mode, int sequence, String drl, String dsl)
            throws RulesException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setNoRulesCache(boolean nocache)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setEnableSerialization(boolean es)
    {
        // TODO Auto-generated method stub
        
    }

	

	
}