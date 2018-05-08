package com.tonik.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tonik.dao.IMenuDAO;
import com.tonik.dao.IRoleMenuDAO;
import com.tonik.model.Menu;
import com.tonik.model.RoleMenu;

/**
 * @spring.bean id="RoleMenuService"
 * @spring.property name="roleMenuDAO" ref="RoleMenuDAO"
 * @spring.property name="menuDAO" ref="MenuDAO"
 */
public class RoleMenuService
{
    private IRoleMenuDAO RoleMenuDAO;
    private IMenuDAO menuDAO;


    public void setMenuDAO(IMenuDAO menuDAO)
    {
        this.menuDAO = menuDAO;
    }

    public IRoleMenuDAO getRoleMenuDAO()
    {
        return RoleMenuDAO;
    }

    public void setRoleMenuDAO(IRoleMenuDAO RoleMenuDAO)
    {
        this.RoleMenuDAO = RoleMenuDAO;
    }

    public List<RoleMenu> RoleMenuPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {
        return RoleMenuDAO.getRoleMenuPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }

    public String RoleMenuTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(RoleMenuDAO.getRoleMenuTotal(strQuery, strStraTime, strEndTime));
    }

    public void SaveRoleMenu(RoleMenu roleMenu)
    {
        RoleMenuDAO.saveRoleMenu(roleMenu);
    }

    public RoleMenu GetRoleMenuById(Long Id)
    {
        return RoleMenuDAO.getRoleMenu(Id);
    }

    public void DelRoleMenu(Long Id)
    {
        RoleMenuDAO.removeRoleMenu(Id);
    }

    public String getJson(Set<RoleMenu> r)
    {
        if (!(r.size() > 0))//无角色
        {
            return "";
        }
        Set<Menu> m = new HashSet<Menu>();
        for (RoleMenu item : r)
        {
            for (Menu mItem : RoleMenuDAO.getMenusByRoleId(item.getId()))
            {
                m.add(mItem);
            }
        }
        if (!(m.size() > 0))//角色无菜单关联数据
        {
            return "";
        }

        List<Menu> list = new ArrayList<Menu>(m);
        Collections.sort(list, new Comparator<Menu>() //给菜单类别排序
        {
            public int compare(Menu arg0, Menu arg1)
            {
                return arg0.getParentId().compareTo(arg1.getParentId());
            }
        });

        String strId = "";
        for (Menu item : list)
        {
            if (item.getLevel() == 2) //遍历桌面左侧父功能菜单
            {
                if ("".equals(strId)) //累合所有父菜单ID
                {
                    strId += item.getParentId() + ",";
                }
                else
                {
                    strId += (("," + strId).indexOf("," + item.getParentId().toString() + ",") != -1) ? ""
                            : item.getParentId() + ",";
                }
            }
        }
        if (strId.length() > 0)
        {
            strId = strId.substring(0, strId.length() - 1);
        }
        else
        {
            return "";
        }
        String strJson = getParentJson(m, strId); //生成根节点JSON

        String str = "";
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getLevel() == 2)//遍历桌面左侧父功能菜单
            {
                str += "{\"attr\":{\"id\":\"" + list.get(i).getId() + "\"},\"data\":\"" + list.get(i).getName()
                        + "\",\"children\":[";
                for (int j = 0; j < list.size(); j++)//遍历添加页面菜单
                {
                    if (list.get(j).getParentId().equals(list.get(i).getId()))
                    {
                        str += "{\"attr\":{\"id\":\"" + list.get(j).getId() + "\"},\"data\":\"" + list.get(j).getName()
                                + "\"},";
                    }
                }
                str = str.substring(0, str.length() - 1) + "]},";

                if ((i + 1) == list.size())//避免下标越界
                {
                    strJson = strJson.replace(("node_" + String.valueOf(list.get(i).getParentId())),
                            str.substring(0, str.length() - 1));
                    str = "";
                }
                else if (!(list.get(i + 1).getParentId().equals(list.get(i).getParentId())))//替换根JSON中的Children内容
                {
                    strJson = strJson.replace(("node_" + String.valueOf(list.get(i).getParentId())),
                            str.substring(0, str.length() - 1));
                    str = "";
                }
            }
        }

        return "[" + strJson + "]";
    }

    //生成根节点JSON
    public String getParentJson(Set<Menu> m, String parentIds)
    {
        String strJson = "";
        for (String id : parentIds.split(","))
        {
            for (Menu item : m)
            {
                if (id.equals(item.getId().toString()))
                {
                    strJson += "{\"attr\":{\"id\":\"" + item.getId() + "\"},\"data\":\"" + item.getName()
                            + "\",\"children\":[node_" + item.getId() + "]},";
                }
            }
        }
        if (!"".equals(strJson))
        {
            strJson = strJson.substring(0, strJson.length() - 1);
        }
        return strJson;
    }

    /**
     * 无限递归获得jsTree的json字串
     * @param parentId 父权限id
     * @return
     */
    public String getJson(long parentId)
    {
        // 把顶层的查出来
        List<Menu> ls = menuDAO.getMenuByparentId(parentId);
        String str = "";

        for (int i = 0; i < ls.size(); i++)
        {
            Menu a = ls.get(i);

            str += "{\"attr\":{\"id\":\"" + a.getId() + "\"},\"data\":\"" + a.getName() + "\"";

            String strChild = this.getJson(a.getId());
            if (strChild.length() > 0)
            {
                str += ",\"children\":[";
                str += strChild;
                str += "]";
                str += "},";
            }
            else
            {
                str += "},";
            }
        }
        if (str.length() > 0)
        {
            str = "[" + str.substring(0, str.length() - 1) + "]";
        }

        return str;
    }

    public List<RoleMenu> getRoleList()
    {
        return RoleMenuDAO.getRoleMenu();
    }
}