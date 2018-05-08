package com.tonik.service;

import java.util.List;

import com.tonik.dao.IEpidemicDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.model.Epidemic;
import com.tonik.model.ProductType;


/**
 * @spring.bean id="EpidemicService"
 * @spring.property name="epidemicDAO" ref="EpidemicDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 */
public class EpidemicService
{
    private IEpidemicDAO EpidemicDAO;
    private IProductTypeDAO ProductTypeDAO;


    public IEpidemicDAO getEpidemicDAO()
    {
        return EpidemicDAO;
    }

    public void setEpidemicDAO(IEpidemicDAO EpidemicDAO)
    {
        this.EpidemicDAO = EpidemicDAO;
    }

    public IProductTypeDAO getProductTypeDAO()
    {
        return ProductTypeDAO;
    }
    
    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.ProductTypeDAO = productTypeDAO;
    }

    public List<Object[]> getEpidemic()
    {
        return EpidemicDAO.getEpidemic();
    }
    
    public void DelEpidemic(Long Id)
    {
        EpidemicDAO.removeEpidemic(Id);
    }

    public void SaveEpidemic(Epidemic Epidemic)
    {
        EpidemicDAO.saveEpidemic(Epidemic);
    
    }

    public Epidemic GetEpidemicById(Long Id)
    {
        return EpidemicDAO.getEpidemic(Id);
    }

    public List<ProductType> getProductType()
    {
        return ProductTypeDAO.getProductType();
    }
    
    public ProductType getProductTypeById(Long Id)
    {
        return ProductTypeDAO.getProductType(Id);
    }

    public List<Epidemic> EpidemicPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime)
    {
        List<Epidemic> ls = EpidemicDAO.getEpidemicPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    public String EpidemicTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(EpidemicDAO.getEpidemicTotal(strQuery, strStraTime, strEndTime));
    }
    
    //返回单条疫情信息
    public String getEpidemicInfo(Epidemic ep){
        String eps="";
        eps = "{\"Id\":\"" + ep.getId() + "\",\"Title\":\"" + ep.getTitle() + "\",\"Content\":\""
                 + ep.getContent() + "\",\"Remark\":\"" + ep.getRemark() 
                 + "\",\"CreateTime\":\"" + ep.getCreateTime() + "\"}";
        return eps;
    }
}
