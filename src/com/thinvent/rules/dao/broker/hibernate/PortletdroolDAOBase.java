package com.thinvent.rules.dao.broker.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thinvent.rules.dao.broker.IPortletdroolDAOBase;
import com.thinvent.rules.dao.businesslayer.Portletdrool;
import com.thinvent.rules.dao.businesslayer.PortletdroolPK;
public abstract class PortletdroolDAOBase extends HibernateDaoSupport implements IPortletdroolDAOBase {

	/**
	* Loads all from table portletdrools
	* @return List of Portletdrool
	*/
	public List<Portletdrool> loadAllPortletdrool() {
		//this.getLog().debug("Getting all Portletdrool");
		return this.getHibernateTemplate().find("from Portletdrool");
	}



	/**
	* Loads portletdrools by Key
	* @param country
	* @param portletid
	* @param portletmodeid
	* @param sequence
	* @return Portletdrool
	*/
	public Portletdrool loadByKeyPortletdrool(PortletdroolPK _pk) {
		return (Portletdrool)this.getHibernateTemplate().get(Portletdrool.class,_pk);
  }

	/**
	* Remove portletdrools from database
	* @param _obj Portletdrool to remove
	*/
	public void delete(Portletdrool _obj) {
		this.getHibernateTemplate().delete(_obj);
  }

	/**
	* Save(Insert) portletdrools to database
	* @param _obj Portletdrool to insert
	*/
	public void save(Portletdrool _obj)  {
    this.getHibernateTemplate().save(_obj);
	}

	/**
	* Update portletdrools in the database
	* @param _obj Portletdrool to update
	*/
	public void update(Portletdrool _obj)  {
    this.getHibernateTemplate().update(_obj);
	}

}
