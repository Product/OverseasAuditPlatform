package com.tonik.dao.hibernate;

import com.tonik.dao.IMerchandiseDAO;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMerchandiseDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author nimengfei
 * @spring.bean id="MerchandiseDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MerchandiseHibernate extends BaseDaoHibernate implements IMerchandiseDAO
{

}
