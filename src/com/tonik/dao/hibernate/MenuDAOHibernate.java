package com.tonik.dao.hibernate;

import java.util.List;

import com.tonik.dao.IMenuDAO;
import com.tonik.model.Menu;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is an example class, and it is the implementation of IMenuDAO.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 * @spring.bean id="MenuDAO"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 */
public class MenuDAOHibernate extends BaseDaoHibernate implements IMenuDAO
{
    @Override
    public List<Menu> getMenu()
    {
        return getHibernateTemplate().find("from Menu");
    }

    @Override
    public Menu getMenu(Long menuId)
    {
        return (Menu) getHibernateTemplate().get(Menu.class, menuId);
    }

    @Override
    public void saveMenu(Menu menu)
    {
        getHibernateTemplate().saveOrUpdate(menu);
    }

    @Override
    public void removeMenu(Menu menu)
    {
        getHibernateTemplate().delete(menu);
    }

    @Override
    public List<Menu> getMenuByparentId(Long parentId)
    {
        final String hql = "from Menu where parentId like :strQuery order by level,sort desc";

        String[] params = { "strQuery" };

        Object[] args = { parentId };

        return this.getHibernateTemplate().findByNamedParam(hql, params, args);
    }
    
    @Override
    public List<Menu> getMenuByUserId(Long userId, Integer system)
    {
        final String hql = "select m from Menu m "
                + "left join m.roles r left join r.users u "
                + "where u.id = :userId and m.system = :system "
                + "order by m.level, m.parentId, m.sort asc";

        String[] params = { "userId", "system" };

        Object[] args = { userId, system };

        return this.getHibernateTemplate().findByNamedParam(hql, params, args);
    }
}
