package com.thinvent.rules.dao.pagination;

import org.hibernate.criterion.DetachedCriteria;

import com.thinvent.rules.pagination.Page;

public interface IPagination {
	 
	/**
	 * This class takes a detached criteria which contains the criterion for pagination and returns a page
	 * @param criteria
	 * @return
	 */
	public Page getPaginatedResults(Page page);
	
	/**
	 * This method takes the page object and creates a detachedCriteria with filters and order by in page object
	 * @param page
	 * @return
	 */
	public DetachedCriteria createCriteria(Page page);

	/**
	 * This method returns the page which contains the passed object
	 * This method will use a equals method in the passed object to find the equality and if the object doesn't exist will return the first page
	 * Please override the equals and the hashcode method for this method to return the accurate page
	 * @param page
	 * @param object
	 * @return Page object
	 */
	public Page getPaginatedResultsContainingObject(Page page, Object object);
}
