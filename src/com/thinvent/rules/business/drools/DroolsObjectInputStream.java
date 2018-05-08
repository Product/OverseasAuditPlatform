/**
 * 
 */
package com.thinvent.rules.business.drools;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jxfetyko
 * 
 */
public class DroolsObjectInputStream extends ObjectInputStream {

	private ClassLoader classloader = null;
	private Log log = LogFactory.getLog(DroolsObjectInputStream.class);

	/**
	 * @throws IOException
	 * @throws SecurityException
	 */
	public DroolsObjectInputStream() throws IOException, SecurityException {
	}

	/**
	 * @param arg0
	 * @throws IOException
	 */
	public DroolsObjectInputStream(InputStream arg0) throws IOException {
		super(arg0);
	}

	public DroolsObjectInputStream(InputStream arg0, ClassLoader cl) throws IOException {
		super(arg0);
		this.classloader = cl;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		if (log.isDebugEnabled())
			log.debug("Resolving class:" + desc);
		Class<?> clazz = null;
		try {
			if (this.classloader != null && desc != null) {
				// let's try our custom class loader if it is available
				clazz = this.classloader.loadClass(desc.getName());
			} else {
				// no custom loader
				clazz = super.resolveClass(desc);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			if (this.classloader != null && desc != null) {
				log.error("Exception loading class, trying non custom loader");
				clazz = super.resolveClass(desc);
			} else {
				log.error("Classloader is null or ObjectStreamClass is null");
			}
		}
		if (clazz == null) {
			throw new ClassNotFoundException("Class not found");
		}
		return clazz;
	}

}
