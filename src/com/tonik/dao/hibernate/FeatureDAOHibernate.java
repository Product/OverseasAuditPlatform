package com.tonik.dao.hibernate;

import com.tonik.dao.IFeatureDAO;

/**
 * @spring.bean id="FeatureDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class FeatureDAOHibernate extends BaseDaoHibernate implements IFeatureDAO
{
}