package com.tonik.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IDAO;
import com.tonik.dao.IEvaluationManagementDAO;
import com.tonik.dao.IProductFeatureDAO;
import com.tonik.model.EvaluationIndex;
import com.tonik.model.EvaluationManagement;
import com.tonik.model.Feature;
import com.tonik.model.ProductFeature;

/**
 * @spring.bean id="ProductFeatureService"
 * @spring.property name="productFeatureDAO" ref="ProductFeatureDAO"
 */
public class ProductFeatureService
{
    private IProductFeatureDAO productFeatureDAO;


    public IProductFeatureDAO getProductFeatureDAO()
    {
        return productFeatureDAO;
    }

    public void setProductFeatureDAO(IProductFeatureDAO productFeatureDAO)
    {
        this.productFeatureDAO = productFeatureDAO;
    }

    public String getProductFeatures(int pageIndex, int pageSize)
    {
        List<ProductFeature> lPFs = productFeatureDAO.getProductFeaturePaging(pageIndex, pageSize);
        Integer count = productFeatureDAO.getProductFeatureTotal();
        List<Map<String, String>> proList = Lists.newArrayList();
        for (ProductFeature item : lPFs)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item.getId().toString());
            map.put("proType", item.getProductType());
            map.put("features",
                    ((Feature) productFeatureDAO.getObject(Feature.class, item.getMajorFeature().longValue())).getName());
            if(!item.getOptionalFeatures().isEmpty())
            {
                StringBuilder sb = new StringBuilder();
                for (String str : item.getOptionalFeatures().split(","))
                {
                    Feature f = ((Feature) productFeatureDAO.getObject(Feature.class, Long.parseLong(str)));
                    sb.append(f.getName() + ",");
                }
                if(sb.length() > 0)
                    sb.deleteCharAt(sb.length()-1);
                map.put("option", sb.toString());
            }
            else
                map.put("option", "");
            map.put("updateTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, item.getUpdateTime()));
            proList.add(map);
        }
        ProductFeatureVO pfVO = new ProductFeatureVO(count, proList);
        return GsonUtil.bean2Json(pfVO);
    }

    public String saveProductFeatures(Long id, Integer majorFeature, String optionalFeatures, String productType)
    {
        ProductFeature pf = new ProductFeature();
        pf.setId(id);
        pf.setMajorFeature(majorFeature);
        pf.setOptionalFeatures(optionalFeatures);
        pf.setProductType(productType);
        pf.setUpdateTime(new Date());
        productFeatureDAO.saveProductFeature(pf);
        return "true";
    }

    public String delProductFeatures(Long id)
    {
        productFeatureDAO.removeProductFeature(id);
        return "true";
    }

    public String getProductFeatures(Long id)
    {
        ProductFeature pf = (ProductFeature) productFeatureDAO.getObject(ProductFeature.class, id);
        Map<String, String> map = Maps.newHashMap();
        map.put("id", pf.getId().toString());
        map.put("proType", pf.getProductType());
        map.put("features", pf.getMajorFeature().toString());
        map.put("option", pf.getOptionalFeatures());
        return GsonUtil.bean2Json(map);
    }


    public class ProductFeatureVO
    {

        private Integer total;

        private List<Map<String, String>> proList;


        public ProductFeatureVO(Integer total, List<Map<String, String>> proList)
        {
            this.total = total;
            this.proList = proList;
        }
    }
}
