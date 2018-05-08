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
import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.model.EvaluationIndex;
import com.tonik.model.EvaluationManagement;
import com.tonik.model.ProductFeature;
import com.tonik.model.ProductTrivialName;

/**
 * @spring.bean id="ProductTrivialNameService"
 * @spring.property name="productTrivialNameDAO" ref="ProductTrivialNameDAO"
 */
public class ProductTrivialNameService
{
    private IProductTrivialNameDAO productTrivialNameDAO;


    public IProductTrivialNameDAO getProductTrivialNameDAO()
    {
        return productTrivialNameDAO;
    }

    public void setProductTrivialNameDAO(IProductTrivialNameDAO productTrivialNameDAO)
    {
        this.productTrivialNameDAO = productTrivialNameDAO;
    }

    public String getProductTrivialNames(int pageIndex, int pageSize, String productTrivialName,
            String productScientificName)
    {
        List<ProductTrivialName> lPTNs = productTrivialNameDAO.getProductTrivialNamePaging(pageIndex, pageSize,
                productTrivialName, productScientificName);
        Integer count = productTrivialNameDAO.getProductTrivialNameTotal(productTrivialName, productScientificName);
        List<Map<String, String>> list = Lists.newArrayList();
        for (ProductTrivialName item : lPTNs)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item.getId().toString());
            map.put("productTrivialName", item.getProductTrivialName());
            map.put("productScientificName", item.getProductScientificName());
            map.put("createTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, item.getCreateTime()));
            list.add(map);
        }
        ProductTrivialNameVO pfVO = new ProductTrivialNameVO(count, list);
        return GsonUtil.bean2Json(pfVO);
    }

    public String saveProductTrivialName(Long id, String productTrivialName, String productScientificName)
    {
        ProductTrivialName psn = new ProductTrivialName();
        psn.setId(id);
        psn.setProductScientificName(productScientificName);
        psn.setProductTrivialName(productTrivialName);
        psn.setCreateTime(new Date());
        productTrivialNameDAO.saveProductTrivialName(psn);
        return "true";
    }

    public String delProductTrivialName(Long id)
    {
        productTrivialNameDAO.removeProductTrivialName(id);
        return "true";
    }

    public String getProductTrivialName(Long id)
    {
        ProductTrivialName ptn = (ProductTrivialName) productTrivialNameDAO.getObject(ProductTrivialName.class, id);
        Map<String, String> map = Maps.newHashMap();
        map.put("id", ptn.getId().toString());
        map.put("productTrivialName", ptn.getProductTrivialName());
        map.put("productScientificName", ptn.getProductScientificName());
        map.put("createTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, ptn.getCreateTime()));
        return GsonUtil.bean2Json(map);
    }


    public class ProductTrivialNameVO
    {

        private Integer total;

        private List<Map<String, String>> list;


        public ProductTrivialNameVO(Integer total, List<Map<String, String>> list)
        {
            this.total = total;
            this.list = list;
        }
    }
}
