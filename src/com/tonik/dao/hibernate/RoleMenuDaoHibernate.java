package com.tonik.dao.hibernate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tonik.dao.IRoleMenuDAO;
import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IRoleMenuDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="RoleMenuDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class RoleMenuDaoHibernate extends BaseDaoHibernate implements IRoleMenuDAO
{
    @Override
    public List<RoleMenu> getRoleMenu()
    {
        return getHibernateTemplate().find("from RoleMenu fetch all properties");
    }

    @SuppressWarnings("unchecked")
    @Override
    public RoleMenu getRoleMenu(Long RoleId)
    {
        try
        {// return getHibernateTemplate().find("from RoleMenu");
            final String hql = "from RoleMenu t left join t.menus left join t.users where t.id = "+RoleId;
            List<Object[]> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    List list = query.list();
                    return list;
                }
            });
            
            return (RoleMenu)listTable.get(0)[0];
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void saveRoleMenu(RoleMenu Role)
    {
        getHibernateTemplate().saveOrUpdate(Role);
    }

    @Override
    public void removeRoleMenu(RoleMenu Role)
    {
        getHibernateTemplate().delete(Role);
    }
    
    @Override
    public void removeRoleMenu(Long roleMenuId)
    {
        getHibernateTemplate().delete(getRoleMenu(roleMenuId));
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleMenu> getRoleMenuPaging(final int pageIndex, final int pageSize, final String strQuery,
            final String strStraTime,final String strEndTime)
    {
        try
        {// return getHibernateTemplate().find("from RoleMenu");
            final Date StraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStraTime);
            final Date EndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            
            final String hql = "from RoleMenu where name like :strQuery "
                    + "and createTime>=:strStraTime and createTime<=:strEndTime order by createTime desc";
            List<RoleMenu> listTable = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session) throws HibernateException, SQLException
                {
                    Query query = session.createQuery(hql);
                    query.setParameter("strQuery", "%" + strQuery + "%");
                    query.setParameter("strStraTime", StraTime);
                    query.setParameter("strEndTime", EndTime);
                    query.setFirstResult((pageIndex - 1) * pageSize);
                    query.setMaxResults(pageSize);
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
    public int getRoleMenuTotal(String strQuery, String strStraTime, String strEndTime)
    {
        try
        {
            final String hql = "from RoleMenu where name like :strQuery and createTime>="
                    + " :strStraTime and createTime<=:strEndTime order by createTime desc";

            String[] params = { "strQuery", "strStraTime", "strEndTime" };

            Object[] args = { "%" + strQuery + "%", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strStraTime),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime) };

            return this.getHibernateTemplate().findByNamedParam(hql, params, args).size();
            
        } catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<Menu> getMenusByRoleId(Long roleId)
    {
        return this.getRoleMenu(roleId).getMenus();
    }

}