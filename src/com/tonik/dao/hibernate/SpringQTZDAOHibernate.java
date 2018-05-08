package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IProductDAO;
import com.tonik.dao.ISpringQTZDAO;
import com.tonik.model.Product;
import com.tonik.model.SpringQTZ;

/**
 * <p>
 * Title: Thinvent Integration
 * </p>
 * <p>
 * Description: SpringQTZDAO接口的实现
 * </p>
 * @since May 20, 2016
 * @version 1.0
 * @author panweijun
 * @spring.bean id="SpringQTZDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class SpringQTZDAOHibernate extends BaseDaoHibernate implements ISpringQTZDAO
{

    @Override
    public List<SpringQTZ> getSpringQZTById(Long id)
    {
        try{
            final String hql = "from SpringQTZ s where s.id="+id;
//                   
            List<SpringQTZ> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            return listTable;
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public boolean updateSpringQTZbyId(Long id, int status, String run_time)
    {
        Session session = getSession();
        try{

            String sql = "UPDATE SPRINGQTZ SET STATUS = " + status + " ,RUN_TIME='"+run_time+"' where ID="+id;
            SQLQuery query = session.createSQLQuery(sql);
            int result = query.executeUpdate();
            return true;
        }catch(Exception e)
        {
            return false;
        } finally {
            if ( session != null){
                session.close();
            }
        }
        
    }

    
}
