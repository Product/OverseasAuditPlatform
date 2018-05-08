package com.thinvent.common.util.rule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinvent.common.rules.def.IPortletRulesProcessor;
import com.thinvent.common.rules.def.RulesException;

public class DroolsDeployer implements IRuleDeployer {

	private Log log = LogFactory.getLog(DroolsDeployer.class);

	private IPortletRulesProcessor rulesservice;

	public DroolsDeployer(List<RuleFileDefinition> rulefiles,IPortletRulesProcessor rs) {
		super();
		if (rulefiles != null) {
			this.rulesservice = rs;
			for (RuleFileDefinition def : rulefiles) {
				// load files
				try {
					if (log.isInfoEnabled())
						log.info("Deploying drools:" + def.getCountry() + "," + def.getPortlet() + "," + def.getMode() + "," + def.getSequence() + ","
								+ def.getDrlfile() + "," + def.getDslfile());
					String drl = this.getFileContents(def.getDrlfile());
					String dsl = this.getFileContents(def.getDslfile());
					this.deployRule(def.getCountry(), def.getPortlet(), def.getMode(), def.getSequence(), drl, dsl);
				} catch (FileNotFoundException e) {
					log.fatal("File not found:" + e.getMessage());
					log.fatal("Cannot continue deployment of drools");
				} catch (IOException e) {
					log.fatal("IO Exception:" + e.getMessage());
					log.fatal("Cannot continue deployment of drools");
				} catch (RuleDeployFailedException e) {
					log.fatal("FAILED TO DEPLOY DROOLS:" + e.getMessage());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.twg.wlsconnect.portlet.util.rule.IRuleDeployer#deployRule(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public void deployRule(String country, String portlet, String mode, int sequence, String drl, String dsl) throws RuleDeployFailedException {
		try {
			this.rulesservice.deployRule(country, portlet, mode, sequence, drl, dsl);
		} catch (Exception e) {
			throw new RuleDeployFailedException(e.getMessage());
		}
	}
	
	public void flushRulesCache() throws RulesException
	{
	    this.rulesservice.flushRulesCache();
	}
	
	

	public void setRulesservice(IPortletRulesProcessor rulesservice) {
		this.rulesservice = rulesservice;
	}

	private String getFileContents(String file) throws FileNotFoundException, IOException {
		StringBuffer contents = new StringBuffer();
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream is = cl.getResourceAsStream(file);
		if (is!=null) {
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(is, "UTF-8");
				int ch = -1;
				ch = isr.read();
				while (ch!=-1) {
					char c = (char)ch;
					contents.append(c);
					ch = isr.read();
				}
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
			finally {
				if (isr!=null) {
					isr.close();
				}
				if (is!=null) {
					is.close();
				}
			}
		} else {
			throw new FileNotFoundException("File :" + file + " was not found");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning:"+contents.toString());
		}
		return contents.toString();
	}
}
