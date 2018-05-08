package com.thinvent.rules.dao.facade;

import java.util.List;

import com.thinvent.rules.dao.businesslayer.Portlet;
import com.thinvent.rules.dao.businesslayer.Portletdrool;
import com.thinvent.rules.dao.businesslayer.PortletdroolPK;
import com.thinvent.rules.dao.businesslayer.Portletmode;
import com.thinvent.rules.dao.businesslayer.PortletmodePK;

public interface IDAOFacadeBase {


	/**
	 * Loads all from table portletdrools
	 */
	List<Portletdrool> loadAllPortletdrool();

	/**
	 * Loads portletdrools by Key
	 */
	Portletdrool loadByKeyPortletdrool(PortletdroolPK _pk);

	/**
	 * Remove portletdrools from database
	 */
	void delete(Portletdrool _obj);

	/**
	 * Save(Insert) portletdrools to database
	 */
	void save(Portletdrool _obj);

	/**
	 * Update portletdrools in the database
	 */
	void update(Portletdrool _obj);


	/**
	 * Loads all from table portletmode
	 */
	List<Portletmode> loadAllPortletmode();

	/**
	 * Loads portletmode by Key
	 */
	Portletmode loadByKeyPortletmode(PortletmodePK _pk);

	/**
	 * Remove portletmode from database
	 */
	void delete(Portletmode _obj);

	/**
	 * Save(Insert) portletmode to database
	 */
	void save(Portletmode _obj);

	/**
	 * Update portletmode in the database
	 */
	void update(Portletmode _obj);

	/**
	 * Loads all from table portlets
	 */
	List<Portlet> loadAllPortlet();

	/**
	 * Loads portlets by Key
	 */
	Portlet loadByKeyPortlet(String _portletid);

	/**
	 * Remove portlets from database
	 */
	void delete(Portlet _obj);

	/**
	 * Save(Insert) portlets to database
	 */
	void save(Portlet _obj);

	/**
	 * Update portlets in the database
	 */
	void update(Portlet _obj);

}
