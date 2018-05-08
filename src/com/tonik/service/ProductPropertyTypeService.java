package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.dao.IProductPropertyTypeDAO;
import com.tonik.model.ProductPropertyType;



/**
 * @spring.bean id="ProductPropertyTypeService"
 * @spring.property name="productPropertyTypeDAO" ref="ProductPropertyTypeDAO"

 */
public class ProductPropertyTypeService
{

    private IProductPropertyTypeDAO productPropertyTypeDAO;
    
    public IProductPropertyTypeDAO getProductPropertyTypeDAO()
    {
        return productPropertyTypeDAO;
    }
    
    public void setProductPropertyTypeDAO(IProductPropertyTypeDAO productPropertyTypeDAO)
    {
        this.productPropertyTypeDAO = productPropertyTypeDAO;
    }
    
    public ProductPropertyType GetProductPropertyTypeById(Long id)
    {
        ProductPropertyType pt = productPropertyTypeDAO.getProductPropertyType(id);
        if (pt.getPtid() == 0)
            pt.setFatherProductTypeName(pt.getName());
        else
        {
            ProductPropertyType faPt = productPropertyTypeDAO.getProductPropertyType(pt.getPtid());
            pt.setFatherProductTypeName(faPt.getName());
        }
        return pt;
    }
    
    public List <ProductPropertyType> getChildProductPropertyType(Long PropertyTypeId)
    {
        List<Object[]> ls = productPropertyTypeDAO.getChildrenProductPropertyType(PropertyTypeId);
        List<ProductPropertyType> pts = new ArrayList<ProductPropertyType>();
        for (Object[] obj : ls)
        {
            ProductPropertyType ppt = (ProductPropertyType) obj[0];
            pts.add(ppt);
        }
        return pts;
        
    }
   public List<ProductPropertyType> getRootProductPropertyType()
   {
       return productPropertyTypeDAO.getRootProductPropertyType();
   }
    public String getProductPropertyTypeInfo(ProductPropertyType pt)
    {
        pt = GetProductPropertyTypeById(pt.getId());
      
        
        String res = "{\"Id\":\"" + pt.getId() + "\",\"Name\":\"" + pt.getName() + "\",\"PName\":\""
                + pt.getFatherProductTypeName() + "\",\"PtId\":\"" + pt.getPtid() + "\",\"Level\":\"" + pt.getLevel()
                + "\",\"Remark\":\"" + pt.getRemark() + "\",\"CreateTime\":\"" + pt.getFormatCreateTime()
                + "\",\"CreatePerson\":\"" + pt.getCreatePersonName() + "\"}";
        return res;
    }

}
