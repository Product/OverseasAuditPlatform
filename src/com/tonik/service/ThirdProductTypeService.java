package com.tonik.service;

import java.util.List;
import java.util.Map;

import org.testng.collections.Maps;

import com.google.common.collect.Lists;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IThirdProductTypeDAO;
import com.tonik.model.ProductType;
import com.tonik.model.ThirdProductType;
import com.tonik.model.ThirdWebsite;

/**
 * @spring.bean id="ThirdProductTypeService"
 * @spring.property name="thirdProductTypeDAO" ref="ThirdProductTypeDAO"
 */
public class ThirdProductTypeService
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

    public String listThirdProductTypes(Long parent, Long thirdWebsite)
    {
        try
        {
            List<Object[]> ls = thirdProductTypeDAO.listThirdProductTypes(parent, thirdWebsite);
            List<Map<String, Object>> lResult = Lists.newArrayList();
            for (Object[] obj : ls)
            {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", obj[0].toString());
                map.put("name", obj[1].toString());
                map.put("isVisible", (int) obj[2] == 1 ? true : false);
                lResult.add(map);
            }
            return GsonUtil.bean2Json(lResult);
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String saveThirdProductTypes(Long id, String name, Long parent, String remark, Long thirdWebsite)
    {
        try
        {
            ThirdProductType tpt = new ThirdProductType();
            tpt.setId(id);
            tpt.setName(name);
            if(parent == null)
                tpt.setParent(null);
            else
                tpt.setParent(new ThirdProductType().setId(parent).setName(""));
            tpt.setRemark(remark);
            tpt.setThirdWebsite(new ThirdWebsite().setId(thirdWebsite).setName(""));
            thirdProductTypeDAO.saveObject(tpt);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String getThirdProductType(Long id)
    {
        try
        {
            ThirdProductType tpt = (ThirdProductType) thirdProductTypeDAO.loadObject(ThirdProductType.class, id);

            Map<String, Object> map = Maps.newHashMap();
            map.put("id", tpt.getId());
            map.put("name", tpt.getName());
            map.put("parent", tpt.getParent() == null ? "0":tpt.getParent().getId());
            map.put("remark", tpt.getRemark());
            return GsonUtil.bean2Json(map);
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String removeThirdProductType(Long id)
    {
        try
        {
            thirdProductTypeDAO.removeObject(ThirdProductType.class, id);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String listRelTypes(Long thirdWebsite, int pageIndex, int pageSize, String strQuery)
    {
        try
        {
            List<Object[]> ls = thirdProductTypeDAO.listRelTypes(pageIndex, pageSize, thirdWebsite, strQuery);
            int total = thirdProductTypeDAO.getlistRelTypesTotal(thirdWebsite, strQuery);
            Map<String, Object> result = Maps.newHashMap();
            List<Map<String, String>> lResult = Lists.newArrayList();
            for (Object[] obj : ls)
            {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", obj[0].toString());
                map.put("productTypeName", obj[2].toString());
                map.put("thirdProductTypeName", obj[1].toString());
                map.put("thirdWebsiteName", obj[3].toString());
                lResult.add(map);
            }
            result.put("total", total);
            result.put("list", lResult);
            return GsonUtil.bean2Json(result);
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String saveRelType(Long thirdProductType, Long productType)
    {
        ThirdProductType tpt = (ThirdProductType) thirdProductTypeDAO.getObject(ThirdProductType.class,
                thirdProductType);
        tpt.setProductType(new ProductType().setId(productType));
        thirdProductTypeDAO.saveObject(tpt);
        return "true";
    }

    public String delRelType(Long thirdProductType)
    {
        ThirdProductType tpt = (ThirdProductType) thirdProductTypeDAO.getObject(ThirdProductType.class,
                thirdProductType);
        tpt.setProductType(null);
        thirdProductTypeDAO.saveObject(tpt);
        return "true";
    }
}
