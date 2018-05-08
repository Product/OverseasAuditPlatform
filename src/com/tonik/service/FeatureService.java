package com.tonik.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IFeatureDAO;
import com.tonik.dao.IRelEvaluationProductDAO;
import com.tonik.model.Feature;
import com.tonik.model.RelEvaluationProduct;

/**
 * @spring.bean id="FeatureService"
 * @spring.property name="featureDAO" ref="FeatureDAO"
 */
public class FeatureService
{
    private IFeatureDAO featureDAO;


    public IFeatureDAO getFeatureDAO()
    {
        return featureDAO;
    }

    public void setFeatureDAO(IFeatureDAO featureDAO)
    {
        this.featureDAO = featureDAO;
    }

    public String listAllFeatures()
    {
        List<Feature> ls = featureDAO.getObjects(Feature.class);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Feature item : ls)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item.getId().toString());
            map.put("name", item.getName());
            list.add(map);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("list", list);
        return GsonUtil.bean2Json(result);
    }

    public String saveFeature(Long id, String name) throws Exception
    {
        featureDAO.saveObject(new Feature().setId(id).setName(name));
        return "true";
    }
}
