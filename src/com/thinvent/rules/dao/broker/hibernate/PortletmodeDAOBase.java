package com.thinvent.rules.dao.broker.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thinvent.rules.dao.broker.IPortletmodeDAOBase;
import com.thinvent.rules.dao.businesslayer.Portletmode;
import com.thinvent.rules.dao.businesslayer.PortletmodePK;
public abstract class PortletmodeDAOBase extends HibernateDaoSupport implements IPortletmodeDAOBase {

	/**
	* Loads all from table portletmode
	* @return List of Portletmode
	*/
	public List<Portletmode> loadAllPortletmode() {
		//this.getLog().debug("Getting all Portletmode");
		return this.getHibernateTemplate().find("from Portletmode");
	}



	/**
	* Loads portletmode by Key
	* @param portletid
	* @param portletmodeid
	* @return Portletmode
	*/
	public Portletmode loadByKeyPortletmode(PortletmodePK _pk) {
		return (Portletmode)this.getHibernateTemplate().get(Portletmode.class,_pk);
  }

	/**
	* Remove portletmode from database
	* @param _obj Portletmode to remove
	*/
	public void delete(Portletmode _obj) {
		this.getHibernateTemplate().delete(_obj);
  }

	/**
	* Save(Insert) portletmode to database
	* @param _obj Portletmode to insert
	*/
	public void save(Portletmode _obj)  {
    this.getHibernateTemplate().save(_obj);
	}

	/**
	* Update portletmode in the database
	* @param _obj Portletmode to update
	*/
	public void update(Portletmode _obj)  {
    this.getHibernateTemplate().update(_obj);
	}

}
