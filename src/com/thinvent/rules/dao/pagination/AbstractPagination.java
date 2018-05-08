package com.thinvent.rules.dao.pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.thinvent.rules.pagination.Page;

public abstract class AbstractPagination extends AbstractGenericPagination {

  Log log = LogFactory.getLog(AbstractPagination.class);

  public abstract DetachedCriteria createCriteria(final Page page);

  @SuppressWarnings("unchecked")
public List<Object> getObjects(final Page page) {
  	log.debug("AbstractPagination.getObjects");
    final DetachedCriteria criteria = this.createCriteria(page);
    if (criteria == null) {
      return new ArrayList<Object>();
    }

    List<Object> objects = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) {
        Connection con = null;
        List<Object> objects = null;
        try {
          // This is a DB specific call, it will most likely create problems on other DBs.
          con = executePaginationOptimizer(session);

          //  Normal DB independent call.
          objects = criteria.getExecutableCriteria(session).list();

          session.flush();
          session.clear();
        } catch (Exception e) {
        	e.printStackTrace();
          log.error("[AbstractPagination.getObjects.doInHibernate] Error while executing query!");
          log.error(e.getMessage());
        } finally {
          if (con != null) {
            try {
              con.close();
            } catch (SQLException e) {
              log.error("[AbstractPagination.getObjects.doInHibernate] Could not close connection!");
              log.error(e.getMessage());
            }
          }
        }

        return objects;
      }
    });

    return objects;
  }

}
