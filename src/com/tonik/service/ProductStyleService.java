package com.tonik.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.GsonUtil;
import com.tonik.dao.IProductStyleDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.model.ProductStyle;

/**
 * @spring.bean id="ProductStyleService"
 * @spring.property name="productStyleDAO" ref="ProductStyleDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 */
public class ProductStyleService  
{
    private IProductStyleDAO ProductStyleDAO;
    private IProductTypeDAO  productTypeDAO;
    
    public IProductStyleDAO getProductStyleDAO()
    {
        return ProductStyleDAO;
    }
    public void setProductStyleDAO(IProductStyleDAO productStyleDAO)
    {
        this.ProductStyleDAO = productStyleDAO;
    }
    
    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }
    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }
    public void DelProductStyle(Long Id)//删除
    {
        ProductStyleDAO.removeProductStyle(Id);
    }
    
    public void SaveProductStyle(ProductStyle productStyle)//保存
    {
        productStyle.setRemark(productStyle.getRemark().replaceAll("\\s", ""));
        productStyle.setRemark(productStyle.getRemark().replaceAll("\"", "“"));
        productStyle.setRemark(productStyle.getRemark().replaceAll("\"", "”"));
        productStyle.setName(productStyle.getName().replaceAll("\\s", ""));
        productStyle.setName(productStyle.getName().replaceAll("\"", "“"));
        productStyle.setName(productStyle.getName().replaceAll("\"", "”"));
        ProductStyleDAO.saveProductStyle(productStyle);
    }
    
    public ProductStyle GetProductStyleById(Long Id)//通过Id获取ProductStyle
    {
        return ProductStyleDAO.getProductStyle(Id);
    }

    /*
    函数名：ProductStylePaging
    作用：获得第pageIndex页的ProductStyle列表
     */
    
    public List<ProductStyle> ProductStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime){
        return ProductStyleDAO.getProductStylePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }
    /*
    函数名：ProductStyleTotal
    作用：获得查询到的ProductStyle条目总数
     */
    public String ProductStyleTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(ProductStyleDAO.getProductStyleTotal(strQuery, strStraTime, strEndTime));
    }
    
    
    /*
    函数名：getProductStyleInfo
    作用：返回item的各项数据信息
     */
    public String getProductStyleInfo(ProductStyle item){
       
        String res = "";
        res =  "{\"Id\":\"" + item.getId() + "\",\"Name\":\"" + item.getName() + "\",\"Remark\":\""
                + item.getRemark() + "\",\"CreatePerson\":\"" + item.getCreatePerson()
                + "\",\"CreateTime\":\"" + item.getCreateTime() + "\"}";
        return res;
    }
    /*
     * 反回所有商品类别*/
    public List<ProductStyle> ProductStyleAll(){
        return ProductStyleDAO.getProductStyle();
    }
    
    public String getAllProductStyles()
    {
        List<ProductStyle> ls = ProductStyleDAO.getObjects(ProductStyle.class);
        List<Map<String, String>> list = Lists.newArrayList();
        for (ProductStyle item : ls)
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

}
