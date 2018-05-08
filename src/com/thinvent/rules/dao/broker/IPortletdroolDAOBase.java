package com.thinvent.rules.dao.broker;

import java.util.List;

import com.thinvent.rules.dao.businesslayer.Portletdrool;
import com.thinvent.rules.dao.businesslayer.PortletdroolPK;

public interface IPortletdroolDAOBase{

	/**
	* Loads all from table portletdrools
	* @return List of Portletdrool
	*/
   public List<Portletdrool> loadAllPortletdrool();


	/**
	* Loads portletdrools by Key
	* @param _pk PortletdroolPK;
	* @return Portletdrool
	*/
	public Portletdrool loadByKeyPortletdrool(PortletdroolPK _pk);

	/**
	* Remove portletdrools from database
	* @param _obj Portletdrool to remove
	*/
	public void delete(Portletdrool _obj);

	/**
	* Save(Insert) portletdrools to database
	* @param _obj Portletdrool to insert
	*/
	public void save(Portletdrool _obj);

	/**
	* Update portletdrools in the database
	* @param _obj Portletdrool to update
	*/
	public void update(Portletdrool _obj);

}
