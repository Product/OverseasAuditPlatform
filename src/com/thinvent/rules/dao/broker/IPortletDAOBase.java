package com.thinvent.rules.dao.broker;

import java.util.List;

import com.thinvent.rules.dao.businesslayer.Portlet;

public interface IPortletDAOBase  {

	/**
	* Loads all from table portlets
	* @return List of Portlet
	*/
   public List<Portlet> loadAllPortlet();


	/**
	* Loads portlets by Key
	* @param portletid
	* @return Portlet
	*/
	public Portlet loadByKeyPortlet( java.lang.String _portletid);

	/**
	* Remove portlets from database
	* @param _obj Portlet to remove
	*/
	public void delete(Portlet _obj);

	/**
	* Save(Insert) portlets to database
	* @param _obj Portlet to insert
	*/
	public void save(Portlet _obj);

	/**
	* Update portlets in the database
	* @param _obj Portlet to update
	*/
	public void update(Portlet _obj);

}
