package com.tonik.dao;

import java.util.List;

import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;

/**
 * <p>
 * Title: Tonik Integration
 * </p>
 * <p>
 * Description: This is the interface for RoleMenu DAO, and this interface is an example.
 * </p>
 * @since Sep 04, 2006
 * @version 1.0
 * @author bchen
 */
public interface IRoleMenuDAO extends IDAO
{
    public List<RoleMenu> getRoleMenu();

    public RoleMenu getRoleMenu(Long roleId);

    public void saveRoleMenu(RoleMenu role);

    public void removeRoleMenu(RoleMenu role);
    
    public void removeRoleMenu(Long roleMenuId);
    
    public List<Menu> getMenusByRoleId(Long roleId);
    
    public List<RoleMenu> getRoleMenuPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);
    
    public int getRoleMenuTotal(String strQuery, String strStraTime, String strEndTime);
}
