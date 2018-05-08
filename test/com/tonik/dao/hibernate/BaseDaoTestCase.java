package com.tonik.dao.hibernate;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is the base class for running Dao tests.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 */
public abstract class BaseDaoTestCase extends AbstractTransactionalDataSourceSpringContextTests
{
    protected final Log log = LogFactory.getLog(getClass());

    protected ResourceBundle resourceBundle;


    protected String[] getConfigLocations()
    {
        return new String[] { "classpath*:applicationContext.xml" };
    }

    public BaseDaoTestCase()
    {
        // Since a ResourceBundle is not required for each class, just do a simple check to see if one exists
        String className = this.getClass().getName();

        try
        {
            resourceBundle = ResourceBundle.getBundle(className);
        } catch (MissingResourceException mre)
        {
            // log.warn("No resource bundle found for: " + className);
        }
    }

    /**
     * Utility method to populate a javabean-style object with values from a Properties file.
     * @param obj
     * @return Object populated object
     * @throws Exception
     */
    protected Object populate(Object obj) throws Exception
    {
        // loop through all the beans methods and set its properties from its .properties file
        Map<String, String> map = new HashMap<String, String>();

        for (Enumeration keys = resourceBundle.getKeys(); keys.hasMoreElements();)
        {
            String key = (String) keys.nextElement();
            map.put(key, resourceBundle.getString(key));
        }

        BeanUtils.copyProperties(obj, map);

        return obj;
    }
}// EOF
