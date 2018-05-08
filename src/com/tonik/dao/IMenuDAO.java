package com.tonik.dao;

import java.util.List;

import com.tonik.model.Menu;

public interface IMenuDAO extends IDAO
{
    public List<Menu> getMenu();

    public Menu getMenu(Long MenuId);

    public void saveMenu(Menu Menu);

    public void removeMenu(Menu Menu);
    
    public List<Menu> getMenuByparentId(Long parentId);

    List<Menu> getMenuByUserId(Long userId, Integer system);
}
