package com.thinvent.rules.dao.broker;

import java.util.List;

import com.thinvent.rules.dao.businesslayer.Portletmode;
import com.thinvent.rules.dao.businesslayer.PortletmodePK;

public interface IPortletmodeDAOBase {

	/**
	* Loads all from table portletmode
	* @return List of Portletmode
	*/
   public List<Portletmode> loadAllPortletmode();


	/**
	* Loads portletmode by Key
	* @param _pk PortletmodePK;
	* @return Portletmode
	*/
	public Portletmode loadByKeyPortletmode(PortletmodePK _pk);

	/**
	* Remove portletmode from database
	* @param _obj Portletmode to remove
	*/
	public void delete(Portletmode _obj);

	/**
	* Save(Insert) portletmode to database
	* @param _obj Portletmode to insert
	*/
	public void save(Portletmode _obj);

	/**
	* Update portletmode in the database
	* @param _obj Portletmode to update
	*/
	public void update(Portletmode _obj);

}
