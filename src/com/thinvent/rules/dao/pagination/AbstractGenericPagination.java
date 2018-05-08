package com.thinvent.rules.dao.pagination;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thinvent.rules.pagination.Page;

public abstract class AbstractGenericPagination extends HibernateDaoSupport implements IPagination {

  Log log = LogFactory.getLog(AbstractGenericPagination.class);

  /**
   * Always return null.
   * @param session
   * @return connection that was used, otherwise null.
   */
  public Connection executePaginationOptimizer(final Session session) {
    log.debug("AbstractGenericPagination.executePaginationOptimizer");

    return null;
  }

  /**
   * Lookup the objects in the database.
   * @param page The lookup parameters.
   * @return The list of objects.
   */
  abstract List<Object> getObjects(final Page page);

  public Page getPaginatedResults(final Page page) {
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: startPosition=" + page.getStartPosition() + ", resultsPerPage=" + page.getResultsPerPage());
    }

    List<Object> objects = this.getObjects(page);

    // Total Records. 
    int totalRecords = objects.size();
    double totalRecordsDouble = new Double(totalRecords).doubleValue();
    page.setResultsCount(totalRecords);
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: resultsCount=" + page.getResultsCount());
    }

    double pageSizeDouble = new Double(page.getResultsPerPage()).doubleValue();

    // Total Pages.
    int totalNumberOfPages = new Double(Math.ceil(totalRecordsDouble / pageSizeDouble)).intValue();
    page.setTotalPages(totalNumberOfPages);
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: totalPages=" + page.getTotalPages());
    }

    // Page Number.
    int pageNumber = (page.getStartPosition() / page.getResultsPerPage()) + 1;
    if (pageNumber > totalNumberOfPages) {
      pageNumber = totalNumberOfPages;
    }
    page.setPageNumber(pageNumber);
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: pageNumber=" + page.getPageNumber());
    }

    // From Index.
    int fromIndex = (pageNumber - 1) * page.getResultsPerPage();
    if (fromIndex < 0) {
      fromIndex = 0;
    }
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: fromIndex=" + fromIndex);
    }

    // To Exclude Index.
    int toExcludeIndex = fromIndex + page.getResultsPerPage();
    if (toExcludeIndex > totalRecords) {
      toExcludeIndex = totalRecords;
    }
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults: toExcludeIndex=" + toExcludeIndex);
    }

    // Objects.
    List<Object> paginatedList = objects.subList(fromIndex, toExcludeIndex);
    page.setObjectList(paginatedList);

    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResults result: resultsCount=" + page.getResultsCount() + ", totalPages=" + page.getTotalPages() + ", pageNumber=" + page.getPageNumber());
    }

    return page;
  }

  public Page getPaginatedResultsContainingObject(final Page page, final Object object) {
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject: startPosition=" + page.getStartPosition() + ", resultsPerPage=" + page.getResultsPerPage());
    }

    List<Object> objects = this.getObjects(page);

    int indexOfUser = objects.indexOf(object);
    if (log.isDebugEnabled()) {
      log.debug("index of passed user is :" + indexOfUser);
    }

    // Total Records. 
    int totalRecords = objects.size();
    double totalRecordsDouble = new Double(totalRecords).doubleValue();
    page.setResultsCount(totalRecords);

    double pageSizeDouble = new Double(page.getResultsPerPage()).doubleValue();

    // Total Pages.
    int totalNumberOfPages = new Double(Math.ceil(totalRecordsDouble / pageSizeDouble)).intValue();
    page.setTotalPages(totalNumberOfPages);
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject: totalPages=" + page.getTotalPages());
    }

    // Page Number.
    int pageNumber;
    if (indexOfUser > 0) {
      pageNumber = (indexOfUser / page.getResultsPerPage()) + 1;
    } else {
      pageNumber = 1; // no object goes to the first page.
    }
    page.setPageNumber(pageNumber);
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject: pageNumber=" + page.getPageNumber());
    }

    // From Index.
    int fromIndex = (pageNumber - 1) * page.getResultsPerPage();
    if (fromIndex < 0) {
      fromIndex = 0;
    }
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject: fromIndex=" + fromIndex);
    }

    // To Exclude Index.
    int toExcludeIndex = fromIndex + page.getResultsPerPage();
    if (toExcludeIndex > totalRecords) {
      toExcludeIndex = totalRecords;
    }
    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject: toExcludeIndex=" + toExcludeIndex);
    }

    // Objects.
    List<Object> paginatedList = objects.subList(fromIndex, toExcludeIndex);
    page.setObjectList(paginatedList);

    if (log.isDebugEnabled()) {
      log.debug("AbstractGenericPagination.getPaginatedResultsContainingObject result: resultsCount=" + page.getResultsCount() + ", totalPages=" + page.getTotalPages() + ", pageNumber=" + page.getPageNumber());
    }

    return page;
  }

}
