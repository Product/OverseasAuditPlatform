package com.tonik.service;

import java.util.List;

import com.tonik.Constant;
import com.tonik.dao.IProductDefinitionDAO;
import com.tonik.model.Country;
import com.tonik.model.ProductDefinition;

/**
 * @spring.bean id="ProductDefinitionService"
 * @spring.property name="productDefinitionDAO" ref="ProductDefinitionDAO"
 */
public class ProductDefinitionService
{
    private IProductDefinitionDAO ProductDefinitionDAO;

    public void setProductDefinitionDAO(IProductDefinitionDAO productDefinitionDAO)
    {
        ProductDefinitionDAO = productDefinitionDAO;
    }
    
    public String ProductDefinitionTotal(String strQuery,String strStraTime, String strEndTime)
    {
        return Integer.toString(ProductDefinitionDAO.getProductDefinitionTotal(strQuery, strStraTime, strEndTime));
    }
    
    public List<ProductDefinition> ProductDefinitionPaging(int pageIndex,int pageSize,String strQuery,String strStraTime, String strEndTime)
    {
        return ProductDefinitionDAO.getProductDefinitionPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }
    
    public void SaveProductDefinition(ProductDefinition productdefinition)
    {
        ProductDefinitionDAO.SaveProductDefinition(productdefinition);
    }
 
    public void RemoveProductDefinition(Long ProductDefinitionId)
    {
        ProductDefinitionDAO.RemoveProductDefinition(ProductDefinitionId);
    }
    
    public ProductDefinition getProductDefinitionById(Long ProductDefinitionId)
    {
        return ProductDefinitionDAO.getProductDefinitionById(ProductDefinitionId);
    }
    
    public int ProductDefinitionByBrandTotal(Long id)
    {
        return ProductDefinitionDAO.ProductDefinitionByBrandTotal(id);
    }
    
    public List<ProductDefinition> getProductDefinitionList()
    {
        return ProductDefinitionDAO.getProductDefinition();
    }
    
    public List<ProductDefinition> getProductDefinitionByBrand(Long brandId)
    {
        return ProductDefinitionDAO.getProductDefinitionByBrand(brandId);
    }

    public String getWorldMapProductTypeInfo(String ptl)
    {
        List<Object[]> wvl = ProductDefinitionDAO.getWorldMapProductDefinitionTotal(ptl);
        List<Object[]> wtl = ProductDefinitionDAO.getWorldMapProductDefinitionNameList(ptl);
        String strCNVList ="";
        String strCNTList = "";
        for(Object[] obj :wvl)
        {
            strCNVList +=  "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[2] + "\"},";
        }
        
        String name = "";
        int counter = 0;
        String title = "代表产品：";
        for(Object[] obj: wtl){
            name = "".equals(name) ? obj[0].toString():name;
            if(name.equals(obj[0].toString())){
                if(counter++ < 10){
                    title += obj[1]+",";
                    if(counter % 3 == 0)
                        title += "<br/>";
                }
                else
                    continue;
            }else{
                if(title.length() > 0)
                    title = title.substring(0, title.length()-1);
                strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
                title = "代表产品："+obj[1]+",";
                counter = 1;
                name = obj[0].toString();
            }
            
        }
        if(title.length() > 0)
            title = title.substring(0, title.length()-1);
        strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
        if(strCNVList.length() > 0)
            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
        if(strCNTList.length() > 0)
            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);
             
        return("{\"proDefCNVList\":[" + strCNVList + "]" + ",\"proDefCNTList\":[" + strCNTList + "]}");
    }

    public String getProductDefinitionTotalByCountry(Long id)
    {
        return Integer.toString(ProductDefinitionDAO.ProductDefinitionTotalByCountry(id));
    }

    public String getProductDefinitionTotal()
    {
        return Integer.toString(ProductDefinitionDAO.getProductDefinitTotal());
    }

    public String getProductTypeTotal(Long id)
    {
        return Integer.toString(ProductDefinitionDAO.getProductTypeTotal(id));
    }

    public String getProductDefTotalByProduct(String ptl, Country c)
    {
        String res = "";
        if(ptl.length() == 0){
            if(c == null){
                res = Integer.toString(ProductDefinitionDAO.getProductDefinitTotal());
            }else
                res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByCountryId(c.getId()));
        }else{
            if(c == null){
                res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByType(ptl));
            }else{
                res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByTypeAndCountry(ptl,c.getId()));
            }
        }
        return res;
    }

    public List<Object[]> getProductDefinitionPagingList(String ptl, Country c, String start, String len, String order,
            String dir)
    {
        String strOrder = "";
        switch(order){
            case "0":strOrder = "isnull(PRODUCTDEFINITION_NAMECN, PRODUCTDEFINITION_NAMEEN)";break;
            case "1":strOrder = "PRODUCTDEFINITION_PICTURE";break;
            case "2":strOrder = "ISNULL(PRODUCTDEFINITION_FEATUREONE, ISNULL(PRODUCTDEFINITION_FEATURETWO, PRODUCTDEFINITION_FEATURETHREE))";break;
        }
        List<Object[]> res;
        if(ptl == null || ptl == ""){
            res = ProductDefinitionDAO.getProductDefinitionLists(c, start, len, strOrder, dir);
        }else{
            res = ProductDefinitionDAO.getProductDefinitionListsByFeature(ptl, c, start, len, strOrder, dir);
        }
        return res;
    }

    public String getProductDefinitionJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1])
                + "\",\"" + Constant.val(obj[2]) + "\"]";
        return res;
    }

    public String getProductDefineTotalByCountryAndProduct(Country c, String ptl)
    {
        String res = "0";
        if(c != null && ptl != ""){
            res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByTypeAndCountry(ptl, c.getId()));
        }else if(c != null){
            res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByCountryId(c.getId()));
        }else if(ptl != ""){
            res = Integer.toString(ProductDefinitionDAO.getProductDefinitionTotalByType(ptl));
        }else{
            res = Integer.toString(ProductDefinitionDAO.getProductDefinitTotal());
        }
        return res;
    }
    
  
}
