package com.tonik.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.dao.IRelEvaluationProductDAO;
import com.tonik.dao.hibernate.IRelProductTypeStyleDAO;
import com.tonik.model.ProductTrivialName;
import com.tonik.model.RelEvaluationProduct;
import com.tonik.model.RelProductTypeStyle;

/**
 * @spring.bean id="RelEvaluationProductService"
 * @spring.property name="relEvaluationProductDAO" ref="RelEvaluationProductDAO"
 */
public class RelEvaluationProductService
{
    private IRelEvaluationProductDAO relEvaluationProductDAO;


    public IRelEvaluationProductDAO getRelEvaluationProductDAO()
    {
        return relEvaluationProductDAO;
    }

    public void setRelEvaluationProductDAO(IRelEvaluationProductDAO relEvaluationProductDAO)
    {
        this.relEvaluationProductDAO = relEvaluationProductDAO;
    }

    public String getRelEvaluationProducts(int pageIndex, int pageSize, Long productTypeId, Integer evaluationType,
            String strQuery)
    {
        List<Object[]> lReps = relEvaluationProductDAO.getRelEvaluationProductPaging(pageIndex, pageSize,
                evaluationType, productTypeId, strQuery);
        Integer count = relEvaluationProductDAO.getRelEvaluationProductTotal(evaluationType, productTypeId, strQuery);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Object[] item : lReps)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("productId", item[0].toString());
            map.put("productInfo", item[1].toString());
            map.put("productLocation", item[2].toString());
            map.put("evaluationType", item[3].toString());
            map.put("productTypeId", item[4] == null ? "" : item[4].toString());
            map.put("productTypeName", item[5] == null ? "" : item[5].toString());
            map.put("score", new DecimalFormat("##0.##").format(item[6]));
            list.add(map);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", count);
        result.put("list", list);
        return GsonUtil.bean2Json(result);
    }

    public String saveRelEvaluationProduct(Integer evaluationType, Long productTypeId, Long productId, String score) throws Exception
    {
        delRelEvaluationProducts(evaluationType, productTypeId, productId);
        for (String item : score.split("#"))
        {
            String[] scores = item.split(",");
            RelEvaluationProduct rep = new RelEvaluationProduct().setEvaluationManagementId(Long.parseLong(scores[0]))
                    .setProductId(productId).setGrade(Double.parseDouble(scores[1]));
            relEvaluationProductDAO.saveRelEvaluationProduct(rep);
        }
        return "true";
    }

    public String delRelEvaluationProducts(Integer type, Long productTypeId, Long productId) throws Exception
    {
        relEvaluationProductDAO.delRelEvaluationProducts(type, productTypeId, productId);
        return "true";
    }

    public String initRelEvaluationProduct(Integer evaluationType, Long productType, Long productId)
    {
        List<Object[]> lRepds = relEvaluationProductDAO.getRelEvaluationProductDetail(evaluationType, productType,
                productId);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Object[] item : lRepds)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("evaluationId", item[0].toString());
            map.put("evaluationName", item[1].toString());
            map.put("grade", item[2].toString());
            map.put("weight", new DecimalFormat("##0.##").format(item[3]));
            list.add(map);
        }
        return GsonUtil.bean2Json(list);
    }
}
