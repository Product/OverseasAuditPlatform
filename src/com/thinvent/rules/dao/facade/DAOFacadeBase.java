package com.thinvent.rules.dao.facade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinvent.rules.dao.broker.IPortletDAO;
import com.thinvent.rules.dao.broker.IPortletdroolDAO;
import com.thinvent.rules.dao.broker.IPortletmodeDAO;
import com.thinvent.rules.dao.businesslayer.Portlet;
import com.thinvent.rules.dao.businesslayer.Portletdrool;
import com.thinvent.rules.dao.businesslayer.PortletdroolPK;
import com.thinvent.rules.dao.businesslayer.Portletmode;
import com.thinvent.rules.dao.businesslayer.PortletmodePK;


// TODO: Refactor Facade into several smaller facades where DAOs are grouped by functionality or some other criteria.

public abstract class DAOFacadeBase  implements IDAOFacadeBase {
	private static Log log = LogFactory.getLog(DAOFacadeBase.class);
	/**
	 * protected reference to DAOs
	 */
	protected IPortletdroolDAO portletdrooldao;
	protected IPortletmodeDAO portletmodedao;
	protected IPortletDAO portletdao;
	
	

	public void setPortletdrooldao(IPortletdroolDAO portletdrooldao) {
		this.portletdrooldao = portletdrooldao;
	}

	public void setPortletmodedao(IPortletmodeDAO portletmodedao) {
		this.portletmodedao = portletmodedao;
	}

	public void setPortletdao(IPortletDAO portletdao) {
		this.portletdao = portletdao;
	}

	/**
	 * Loads all from table portletdrools
	 */
	public List<Portletdrool> loadAllPortletdrool() {
		return portletdrooldao.loadAllPortletdrool();
	}

	/**
	 * Loads portletdrools by Key
	 */
	public Portletdrool loadByKeyPortletdrool(PortletdroolPK _pk) {
		return this.portletdrooldao.loadByKeyPortletdrool(_pk);
	}

	/**
	 * Remove portletdrools from database
	 */
	public void delete(Portletdrool _obj) {
		this.portletdrooldao.delete(_obj);
	}

	/**
	 * Save(Insert) portletdrools to database
	 */
	public void save(Portletdrool _obj) {
		this.portletdrooldao.save(_obj);
	}

	/**
	 * Update portletdrools in the database
	 */
	public void update(Portletdrool _obj) {
		this.portletdrooldao.update(_obj);
	}


	/**
	 * Loads all from table portletmode
	 */
	public List<Portletmode> loadAllPortletmode() {
		return portletmodedao.loadAllPortletmode();
	}

	/**
	 * Loads portletmode by Key
	 */
	public Portletmode loadByKeyPortletmode(PortletmodePK _pk) {
		return this.portletmodedao.loadByKeyPortletmode(_pk);
	}

	/**
	 * Remove portletmode from database
	 */
	public void delete(Portletmode _obj) {
		this.portletmodedao.delete(_obj);
	}

	/**
	 * Save(Insert) portletmode to database
	 */
	public void save(Portletmode _obj) {
		this.portletmodedao.save(_obj);
	}

	/**
	 * Update portletmode in the database
	 */
	public void update(Portletmode _obj) {
		this.portletmodedao.update(_obj);
	}


	/**
	 * Loads all from table portlets
	 */
	public List<Portlet> loadAllPortlet() {
		return portletdao.loadAllPortlet();
	}

	/**
	 * Loads portlets by Key
	 */
	public Portlet loadByKeyPortlet(String _portletid) {
		return this.portletdao.loadByKeyPortlet(_portletid);
	}

	/**
	 * Remove portlets from database
	 */
	public void delete(Portlet _obj) {
		this.portletdao.delete(_obj);
	}

	/**
	 * Save(Insert) portlets to database
	 */
	public void save(Portlet _obj) {
		this.portletdao.save(_obj);
	}

	/**
	 * Update portlets in the database
	 */
	public void update(Portlet _obj) {
		this.portletdao.update(_obj);
	}

	
}
