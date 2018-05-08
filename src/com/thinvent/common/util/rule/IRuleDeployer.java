package com.thinvent.common.util.rule;


public interface IRuleDeployer {

	/**
	 * Deploys a set of rules
	 * @param country
	 * @param portlet
	 * @param mode
	 * @param drl
	 * @param dsl
	 * @throws RuleDeployFailedException
	 */
	public void deployRule(String country, String portlet, String mode, int sequence, String drl, String dsl)
			throws RuleDeployFailedException;
}
