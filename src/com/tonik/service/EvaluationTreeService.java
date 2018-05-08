package com.tonik.service;

import java.util.List;
import java.util.Map;

import org.testng.collections.Maps;

import com.google.common.collect.Lists;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IThirdProductTypeDAO;
import com.tonik.model.EvaluationTree;

/**
 * @spring.bean id="EvaluationTreeService"
 * @spring.property name="thirdProductTypeDAO" ref="ThirdProductTypeDAO"
 */
public class EvaluationTreeService
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
    public String listEvaluationTrees()
    {
        try
        {
            List<EvaluationTree> ls = thirdProductTypeDAO.getObjects(EvaluationTree.class);
            List<Map<String, String>> lResult = Lists.newArrayList();
            for (EvaluationTree item : ls)
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

    public String saveEvaluationTree(Long id, String name)
    {
        try
        {
            EvaluationTree et = new EvaluationTree();
            et.setId(id);
            et.setName(name);
            thirdProductTypeDAO.saveObject(et);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }

    public String removeEvaluationTree(Long id)
    {
        try
        {
            thirdProductTypeDAO.removeObject(EvaluationTree.class, id);
            return "true";
        } catch (Exception e)
        {
            return "false";
        }
    }
}
