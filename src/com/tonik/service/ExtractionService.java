package com.tonik.service;

import java.util.List;

import com.tonik.dao.IExtractionDAO;
import com.tonik.dao.IProductDAO;
import com.tonik.model.Extraction;
import com.tonik.model.Product;
import com.tonik.model.Rules;

/**
 * @spring.bean id="ExtractionService"
 * @spring.property name="extractionDAO" ref="ExtractionDAO"
 * @spring.property name="productDAO" ref="ProductDAO"
 *
 */
public class ExtractionService
{
    private IExtractionDAO ExtractionDAO;
    private IProductDAO ProductDAO;

    public void setExtractionDAO(IExtractionDAO extractionDAO)
    {
        this.ExtractionDAO = extractionDAO;
    }
    
    public void setProductDAO(IProductDAO ProductDAO)
    {
        this.ProductDAO = ProductDAO;
    }
    
    public List<Extraction> getExtractionList()
    {
        return ExtractionDAO.getExtraction();
    }
    
    public void deleteByRulesId(Long rules)
    {
        ExtractionDAO.deleteByRulesId(rules);
    }
    
    public List<Product> getProductByrules(Rules rules,List<String> values)
    {
        return ProductDAO.getProductByRules(rules,values);
    }
    
    public void saveExtraction(Extraction extraction)
    {
        ExtractionDAO.SaveExtraction(extraction);
    }
    
    public List<Extraction> getExtractionListByRulesId(Long rulesId)
    {
        return ExtractionDAO.getExtractionListByRulesId(rulesId);
    }
    
    public String ExtractionTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(ExtractionDAO.getExtractionTotal(strQuery, strStraTime, strEndTime));
    }
    
    public List<Extraction> ExtractionPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,String strEndTime)
    {
        return ExtractionDAO.getExtractionPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
    }
    
    public List<Extraction> ExtractionSpecial(String strQuery, String strStraTime,String strEndTime)
    {
        return ExtractionDAO.getExtractionSpecial(strQuery, strStraTime, strEndTime);
    }
}
