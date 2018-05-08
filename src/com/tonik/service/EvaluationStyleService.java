package com.tonik.service;

import java.util.List;
import java.util.Map;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinvent.utils.GsonUtil;
import com.thinvent.utils.HibernateProxyTypeAdapter;
import com.tonik.dao.IEvaluationStyleDAO;
import com.tonik.model.EvaluationStyle;
import com.tonik.model.EvaluationTree;

/**
 * @spring.bean id="EvaluationStyleService"
 * @spring.property name="evaluationStyleDAO" ref="EvaluationStyleDAO"
 */
public class EvaluationStyleService
{
    private IEvaluationStyleDAO EvaluationStyleDAO;


    public IEvaluationStyleDAO getEvaluationStyleDAO()
    {
        return EvaluationStyleDAO;
    }

    public void setEvaluationStyleDAO(IEvaluationStyleDAO evaluationStyleDAO)
    {
        EvaluationStyleDAO = evaluationStyleDAO;
    }

    public List<EvaluationStyle> GetEvaluationStyles()
    {
        return EvaluationStyleDAO.getEvaluationStyle();
    }

    public String saveEvaluationStyle(Long id, String name, Long parent, Long tree)
    {
        EvaluationStyle es = new EvaluationStyle();
        es.setId(id);
        es.setName(name);
        es.setParent(parent == null ? null : new EvaluationStyle().setId(parent));
        es.setTree(new EvaluationTree().setId(tree));
        EvaluationStyleDAO.saveEvaluationStyle(es);
        return "true";
    }

    public String delEvaluationStyle(Long id)
    {
        EvaluationStyle es = new EvaluationStyle();
        es.setId(id);
        EvaluationStyleDAO.removeEvaluationStyle(es);
        return "true";
    }

    public String listEvaluationStyles(Long parent, Long tree)
    {
        List<Object[]> ls = EvaluationStyleDAO.listEvaluationStyles(parent, tree);
        List<Map<String, String>> result = Lists.newArrayList();
        for (Object[] item : ls)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item[0].toString());
            map.put("name", item[1].toString());
            map.put("flag", item[2].toString());
            result.add(map);
        }
        // Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
        // gson.toJson(ls);
        // return gson.toJson(ls);
        return GsonUtil.bean2Json(result);
    }
    
    public String getEvaluationStyle(Long id)
    {
        EvaluationStyle es = (EvaluationStyle) EvaluationStyleDAO.loadObject(EvaluationStyle.class, id);
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
        return gson.toJson(es);
    }
}
