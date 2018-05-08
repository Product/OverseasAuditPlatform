package com.tonik.service;

import java.util.List;
import java.util.Map;

import org.testng.collections.Maps;

import com.google.common.collect.Lists;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IThirdProductTypeDAO;
import com.tonik.dao.IThirdWebsiteDAO;
import com.tonik.model.ThirdProductType;
import com.tonik.model.ThirdWebsite;

/**
 * @spring.bean id="ThirdWebsiteService"
 * @spring.property name="thirdProductTypeDAO" ref="ThirdProductTypeDAO"
 */
public class ThirdWebsiteService
{
    private IThirdProductTypeDAO thirdProductTypeDAO;


    public IThirdProductTypeDAO getThirdProductTypeDAO()
    {
        return thirdProductTypeDAO;
    }

    public void setThirdProductTypeDAO(IThirdProductTypeDAO thirdProductTypeDAO)
    {
        this.thirdProductTypeDAO = thirdProductTypeDAO;
    }

    @SuppressWarnings("unchecked")
    public String listThirdWebsites()
    {
        try
        {
            List<ThirdWebsite> ls = thirdProductTypeDAO.getObjects(ThirdWebsite.class);
            List<Map<String, String>> lResult = Lists.newArrayList();
            for (ThirdWebsite item : ls)
            {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", item.getId().toString());
                map.put("name", item.getName());
                lResult.add(map);
            }
            return GsonUtil.bean2Json(lResult);
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String saveThirdWebsite(Long id, String name)
    {
        try
        {
            ThirdWebsite tw = new ThirdWebsite();
            tw.setId(id);
            tw.setName(name);
            thirdProductTypeDAO.saveObject(tw);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String removeThirdWebsite(Long id)
    {
        try
        {
            thirdProductTypeDAO.removeThirdProductTypes(id);
            thirdProductTypeDAO.removeObject(ThirdWebsite.class, id);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }
}
