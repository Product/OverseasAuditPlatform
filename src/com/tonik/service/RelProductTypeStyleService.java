package com.tonik.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IProductTrivialNameDAO;
import com.tonik.dao.hibernate.IRelProductTypeStyleDAO;
import com.tonik.model.ProductTrivialName;
import com.tonik.model.RelProductTypeStyle;

/**
 * @spring.bean id="RelProductTypeStyleService"
 * @spring.property name="relProductTypeStyleDAO" ref="RelProductTypeStyleDAO"
 */
public class RelProductTypeStyleService
{
    private IRelProductTypeStyleDAO relProductTypeStyleDAO;


    public IRelProductTypeStyleDAO getRelProductTypeStyleDAO()
    {
        return relProductTypeStyleDAO;
    }

    public void setRelProductTypeStyleDAO(IRelProductTypeStyleDAO relProductTypeStyleDAO)
    {
        this.relProductTypeStyleDAO = relProductTypeStyleDAO;
    }

    public String getRelProductTypeStyles(int pageIndex, int pageSize, Long productTypeId)
    {
        List<Object[]> lRTSs = relProductTypeStyleDAO.getRelProductTypeStylePaging(pageIndex, pageSize, productTypeId);
        Integer count = relProductTypeStyleDAO.getRelProductTypeStyleTotal(productTypeId);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Object[] item : lRTSs)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item[0].toString());
            map.put("thirdType", item[1].toString());
            map.put("secondType", item[2].toString());
            map.put("firstType", item[3].toString());
            map.put("productStyle", item[4].toString());
            map.put("relateTime", DateUtil.formatDate(DateUtil.DEFAULT_FORMAT, (Date)item[5]));
            map.put("status", (Integer)item[6]==1 ? "启用" : "禁用");
            list.add(map);
        }
        RelProductTypeStyleVO rtsVO = new RelProductTypeStyleVO(count, list);
        return GsonUtil.bean2Json(rtsVO);
    }

    public String saveRelProductTypeStyle(Long id, Long productType, Long productStyle, Integer status)
    {
        RelProductTypeStyle rts = new RelProductTypeStyle();
        rts.setId(id);
        rts.setProductStyleId(productStyle);
        rts.setProductTypeId(productType);
        rts.setRelateTime(new Date());
        rts.setStatus(status);
        relProductTypeStyleDAO.saveRelProductTypeStyle(rts);
        return "true";
    }

    public String delRelProductTypeStyle(Long id)
    {
        relProductTypeStyleDAO.removeRelProductTypeStyle(id);
        return "true";
    }


    public class RelProductTypeStyleVO
    {

        private Integer total;

        private List<Map<String, String>> list;


        public RelProductTypeStyleVO(Integer total, List<Map<String, String>> list)
        {
            this.total = total;
            this.list = list;
        }
    }
}
