package com.tonik.dao.hibernate;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.thinvent.common.BaseBean;
import com.thinvent.utils.JsonUtil;
import com.tonik.dao.IMenuDAO;
import com.tonik.model.Menu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @spring.bean id="menuDAOTest"
 * @spring.property name="menuDAO" ref="MenuDAO"
 */
public class MenuDAOTest extends BaseDaoTestCase
{
    private IMenuDAO menuDAO;


    public void setMenuDAO(IMenuDAO menuDAO)
    {
        this.menuDAO = menuDAO;
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(MenuDAOTest.class);
    }
    
    public void testGetMenuByUserId() throws Exception
    {
        List<Menu> menus= menuDAO.getMenuByUserId(1l, 1);
        assertNotNull(menus);
        System.out.println(menus);
        //System.out.println(JsonUtil.objectToJson(menus));
        List<Map<String, String>> ls = Lists.newArrayList();
        for(Menu item : menus)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("Id", String.valueOf(item.getId()));
            map.put("Name", item.getName());
            map.put("NodeId", item.getNodeId());
            map.put("Level", String.valueOf(item.getLevel()));
            map.put("Sort", String.valueOf(item.getSort()));
            map.put("ParentId", String.valueOf(item.getParentId()));
            map.put("PicUrl", item.getPicUrl());
            ls.add(map);
        }
        String json = JsonUtil.objectToJson(this.new InnerClass(ls));
        String json2 = new Gson().toJson(this.new InnerClass(ls));
        String json3 = new Gson().toJson(ls);
        JSONArray jsonArray = new JSONArray();


//        String str = json.toString();
//        String json = JSONArray.fromObject(ls).toString();
        System.out.println(json);
        System.out.println(json2);
        System.out.println(json3);

        
    }

//    public void testAddAndRemovemenu() throws Exception
//    {
//        Menu menu = new Menu();
//        menu.setName("LiFeiFei");
//        menu.setSort(2);
//        menu.setParentId(6L);
//        menuDAO.saveMenu(menu);
//        setComplete();
//        endTransaction();
//
//        startNewTransaction();
//        menu = menuDAO.getMenu(1l);
//        assertNotNull(menu.getName());
//        assertNotNull(menu.getSort());
//        assertNotNull(menu.getName());
//
//        menuDAO.removeMenu(menu);
//        setComplete();
//        endTransaction();
//
//        menu = menuDAO.getMenu(1l);
//        assertNull(menu);
//    }
    public class InnerClass{
        
        private List<Map<String, String>> menuList;
    
        public InnerClass(List<Map<String, String>> ls){
            this.menuList = ls;
        }
    }
}


