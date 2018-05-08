package com.thinvent.rules.dao.broker.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thinvent.rules.dao.broker.IPortletDAOBase;
import com.thinvent.rules.dao.businesslayer.Portlet;
public abstract class PortletDAOBase extends HibernateDaoSupport implements IPortletDAOBase {

	/**
	* Loads all from table portlets
	* @return List of Portlet
	*/
	public List<Portlet> loadAllPortlet() {
		//this.getLog().debug("Getting all Portlet");
		return this.getHibernateTemplate().find("from Portlet");
	}



	/**
	* Loads portlets by Key
	* @param portletid
	* @return Portlet
	*/
	public Portlet loadByKeyPortlet( java.lang.String _portletid) {
		return (Portlet)this.getHibernateTemplate().get(Portlet.class, _portletid);
  }

	/**
	* Remove portlets from database
	* @param _obj Portlet to remove
	*/
	public void delete(Portlet _obj) {
		this.getHibernateTemplate().delete(_obj);
  }

	/**
	* Save(Insert) portlets to database
	* @param _obj Portlet to insert
	*/
	public void save(Portlet _obj)  {
    this.getHibernateTemplate().save(_obj);
	}

	/**
	* Update portlets in the database
	* @param _obj Portlet to update
	*/
	public void update(Portlet _obj)  {
    this.getHibernateTemplate().update(_obj);
	}

}
