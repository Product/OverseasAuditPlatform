package com.tonik.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tonik.dao.IAreaDAO;
import com.tonik.dao.ICountryDAO;
import com.tonik.dao.IMaterialDAO;
import com.tonik.dao.IProductPropertyTypeDAO;
import com.tonik.dao.IProductStyleDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.dao.IRulesDAO;
import com.tonik.dao.IRulesDetailDAO;
import com.tonik.dao.IWebsiteStyleDAO;
import com.tonik.model.Area;
import com.tonik.model.Country;
import com.tonik.model.Material;
import com.tonik.model.ProductPropertyType;
import com.tonik.model.ProductStyle;
import com.tonik.model.ProductType;
import com.tonik.model.Rules;
import com.tonik.model.RulesDetail;
import com.tonik.model.WebsiteStyle;

/**
 * @spring.bean id="RulesService"
 * @spring.property name="rulesDAO" ref="RulesDAO"
 * @spring.property name="websiteStyleDAO" ref="WebsiteStyleDAO"
 * @spring.property name="productStyleDAO" ref="ProductStyleDAO"
 * @spring.property name="rulesDetailDAO" ref="RulesDetailDAO"
 * @spring.property name="materialDAO" ref="MaterialDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 * @spring.property name="productPropertyTypeDAO" ref="ProductPropertyTypeDAO"
 * @spring.property name="areaDAO" ref="AreaDAO"
 * @spring.property name="countryDAO" ref="CountryDAO"
 */
public class RulesService
{

    private IRulesDAO rulesDAO;
    private IRulesDetailDAO rulesDetailDAO;
    private IWebsiteStyleDAO websiteStyleDAO;
    private IProductStyleDAO productStyleDAO;
    private IMaterialDAO materialDAO;
    private IProductTypeDAO productTypeDAO;
    private IAreaDAO areaDAO;
    private ICountryDAO countryDAO;
    private IProductPropertyTypeDAO productPropertyTypeDAO;
    
    
    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    public IAreaDAO getAreaDAO()
    {
        return areaDAO;
    }

    public void setAreaDAO(IAreaDAO areaDAO)
    {
        this.areaDAO = areaDAO;
    }

    public ICountryDAO getCountryDAO()
    {
        return countryDAO;
    }

    public void setCountryDAO(ICountryDAO countryDAO)
    {
        this.countryDAO = countryDAO;
    }

    public IProductPropertyTypeDAO getProductPropertyTypeDAO()
    {
        return productPropertyTypeDAO;
    }

    public void setProductPropertyTypeDAO(IProductPropertyTypeDAO productPropertyTypeDAO)
    {
        this.productPropertyTypeDAO = productPropertyTypeDAO;
    }

    public IMaterialDAO getMaterialDAO()
    {
        return materialDAO;
    }

    public void setMaterialDAO(IMaterialDAO materialDAO)
    {
        this.materialDAO = materialDAO;
    }

    public IProductStyleDAO getProductStyleDAO()
    {
        return productStyleDAO;
    }

    public void setProductStyleDAO(IProductStyleDAO productStyleDAO)
    {
        this.productStyleDAO = productStyleDAO;
    }

    public IWebsiteStyleDAO getWebsiteStyleDAO()
    {
        return websiteStyleDAO;
    }

    public void setWebsiteStyleDAO(IWebsiteStyleDAO websiteStyleDAO)
    {
        this.websiteStyleDAO = websiteStyleDAO;
    }

    public IRulesDAO getRulesDAO()
    {
        return rulesDAO;
    }

    public void setRulesDAO(IRulesDAO rulesDAO)
    {
        this.rulesDAO = rulesDAO;
    }

    public IRulesDetailDAO getRulesDetailDAO()
    {
        return rulesDetailDAO;
    }

    public void setRulesDetailDAO(IRulesDetailDAO rulesDetailDAO)
    {
        this.rulesDetailDAO = rulesDetailDAO;
    }

    public void DelRules(Long Id)
    {
        rulesDAO.removeRules(Id);
    }

    public void SaveRules(Rules rules, String rds)
    {
        rulesDAO.saveRules(rules);
        String[] rd = rds.split("\\|");
        for (String str : rd)
        {
            if (str != "")
            {
                String[] obj = str.split("_");
                RulesDetail rdl = new RulesDetail();
                if ("0".equals(obj[0]))
                {
                    rdl.setCondition(obj[1]);
                    rdl.setRelationship(obj[2]);
                    rdl.setValue(obj[3]);
                    rdl.setRulesid(rules.getId());
                }
                else
                {
                    rdl.setId(Long.parseLong(obj[0]));
                    rdl.setCondition(obj[1]);
                    rdl.setRelationship(obj[2]);
                    rdl.setValue(obj[3]);
                    rdl.setRulesid(rules.getId());
                }
                rulesDetailDAO.saveRulesDetail(rdl);
            }
        }
    }

    public Rules GetRulesById(Long Id)
    {
        return rulesDAO.getRules(Id);
    }

    public WebsiteStyle getWebsiteStyleById(Long Id)
    {
        return websiteStyleDAO.getWebsiteStyle(Id);
    }

    public ProductStyle getProductStyleById(Long Id)
    {
        return productStyleDAO.getProductStyle(Id);
    }
    
    public Material getMaterialById(Long Id)
    {
        return materialDAO.getMaterialById(Id);
    }

    public List<WebsiteStyle> getWebsiteStyle()
    {
        return websiteStyleDAO.getWebsiteStyle();
    }

    public List<ProductStyle> getProductStyle()
    {
        return productStyleDAO.getProductStyle();
    }
    
    public List<Material> getMaterialsByMaterialTypeId(Long Id)
    {
        List<Object[]> ls = materialDAO.getMaterialsByMaterialTypeId(Id);
        List<Material> mt = new ArrayList<Material>();
        for(Object[] obj : ls){
            Material w = (Material)obj[0];
            mt.add(w);
        }
        return mt;
//        return materialDAO.getMaterialsByMaterialTypeId(Id);
    }

    public List<Rules> RulesPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime)
    {
        List<Rules> ls = rulesDAO.getRulesPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);
        return ls;
    }

    public String RulesTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(rulesDAO.getRulesTotal(strQuery, strStraTime, strEndTime));
    }
    
    //返回单条规则信息
    public String getRulesInfo(Rules rs)
    {   
        rs = GetRulesById(rs.getId());
        //产品属性类别
        String ProductPropertyTypes = "";
        for (ProductPropertyType item : rs.getProductPropertyTypes())
        {
            Long firId = 0L;
            Long secId = 0L;
            Long thiId = 0L;
            String firName = "";
            String secName = "";
            String thiName = "";
            String firRemark ="";
            String secRemark ="";
            String thiRemark = "";
        
        if(item.getPtid() .equals(0L))
        {
            firId = item.getId();
            firName = item.getName();
            firRemark =item.getRemark();
        }
        else 
        {
            ProductPropertyType fppt = productPropertyTypeDAO.getProductPropertyType(item.getPtid());
            if(fppt.getPtid().equals(0L))
            {
                firId = item.getPtid();
                firName = fppt.getName();
                firRemark = fppt.getRemark();
                secId = item.getId();
                secName =item.getName();
                secRemark = item.getRemark();
            }
            else
            {
                ProductPropertyType ffppt = productPropertyTypeDAO.getProductPropertyType(fppt.getPtid()); 
                firId = ffppt.getId();
                firName = ffppt.getName();
                firRemark = ffppt.getRemark();
                secId = fppt.getId();
                secName = fppt.getName();
                secRemark  =fppt.getRemark();
                thiId = item.getId();
                thiName = item.getName();
                thiRemark  = item.getRemark();
            }
        }
        
        ProductPropertyTypes += "{\"firId\":\"" + firId + "\",\"firName\":\"" + firName + "\",\"firRemark\":\"" + firRemark + "\",\"secId\":\"" + secId + "\",\"secName\":\"" + secName + "\",\"secRemark\":\"" + secRemark + "\",\"thiId\":\"" + thiId + "\",\"thiName\":\"" + thiName + "\",\"thiRemark\":\"" + thiRemark + "\"},";
    }
    
    if (ProductPropertyTypes != "")
    {
        ProductPropertyTypes = "[" + ProductPropertyTypes.substring(0, ProductPropertyTypes.length() - 1) + "]";
    }
    else
    {
        ProductPropertyTypes = "[]";
    }
    
  //产品类别
        String ProductTypes = "";
        for (ProductType item : rs.getProductTypes())
        {
            Long firPTId = 0L;
            Long secPTId = 0L;
            Long thiPTId = 0L;
            String firPTName = "";
            String secPTName = "";
            String thiPTName = "";
            String firPTRemark ="";
            String secPTRemark ="";
            String thiPTRemark = "";
    
        if(item.getPtid() .equals(0L))
            {
            firPTId = item.getId();
            firPTName = item.getName();
            firPTRemark =item.getRemark();
            }
        else 
            {
            ProductType fpt = productTypeDAO.getProductType(item.getPtid());
        if(fpt.getPtid().equals(0L))
            {
                firPTId = item.getPtid();
                firPTName = fpt.getName();
                firPTRemark = fpt.getRemark();
                secPTId = item.getId();
                secPTName =item.getName();
                secPTRemark = item.getRemark();
            }
        else
            {
                ProductType ffpt = productTypeDAO.getProductType(fpt.getPtid()); 
                firPTId = ffpt.getId();
                firPTName = ffpt.getName();
                firPTRemark = ffpt.getRemark();
                secPTId = fpt.getId();
                secPTName = fpt.getName();
                secPTRemark  =fpt.getRemark();
                thiPTId = item.getId();
                thiPTName = item.getName();
                thiPTRemark  = item.getRemark();
        }
    }
    
        ProductTypes += "{\"firPTId\":\"" + firPTId + "\",\"firPTName\":\"" + firPTName + "\",\"firPTRemark\":\"" + firPTRemark + "\",\"secIPTd\":\"" + secPTId + "\",\"secPTName\":\"" + secPTName + "\",\"secPTRemark\":\"" + secPTRemark + "\",\"thiPTId\":\"" + thiPTId + "\",\"thiPTName\":\"" + thiPTName + "\",\"thiPTRemark\":\"" + thiPTRemark + "\"},";
}

         if (ProductTypes != "")
             {
             ProductTypes = "[" + ProductTypes.substring(0, ProductTypes.length() - 1) + "]";
             }
         else
             {
             ProductTypes = "[]";
             }
         //网站类型
         String WebsiteStyles = "";
         for(WebsiteStyle item : rs.getWebsiteStyles())
         {
             Long WebsiteStyleId = 0L;
             WebsiteStyle ws = websiteStyleDAO.getWebsiteStyle(item.getId()); 
             WebsiteStyleId = ws.getId();
             WebsiteStyles += "{\"WebsiteStyleId\":\"" + WebsiteStyleId + "\" },";
          }
             if (WebsiteStyles != "")
                 {
                 WebsiteStyles = "[" + WebsiteStyles.substring(0, WebsiteStyles.length() - 1) + "]";
                 }
             else
                 {
                 WebsiteStyles = "[]";
                 }
             //原料
             String Materials = "";
             for(Material item : rs.getMaterials())
             {
                 Long materialId = item.getId();
                 String materialName = item.getName();
                 Materials += "{\"materialId\":\"" + materialId + "\",\"materialName\":\"" + materialName + "\"},";
             }
             if (Materials != "")
             {
                 Materials = "[" + Materials.substring(0, Materials.length() - 1) + "]";
             }
             else
             {
                 Materials = "[]";
             }

           //国家地区合二为一

             String Areas = "";
             String emptyAreas = "";
             Set<Country> countries = new HashSet <Country>();
             countries = rs.getCountries();
             for(Area item:rs.getAreas())
             {
                 Areas += "{\"countriesId\":\"" + item.getCountry().getId() + "\",\"countriesName\":\"" + item.getCountry().getName() + "\",\"areasId\":\"" + item.getId() + "\",\"areasName\":\"" + item.getName() + "\"},";
                 Country c = item.getCountry();
                 countries.remove(c);
                 
             }
             for(Country item:countries)
             {
                 Areas += "{\"countriesId\":\"" + item.getId() + "\",\"countriesName\":\"" + item.getName() + "\",\"areasId\":\"" + 0L + "\",\"areasName\":\"" + emptyAreas + "\"},";
             }
        
             if (Areas != "")
             {
                 Areas = "[" + Areas.substring(0, Areas.length() - 1) + "]";
             }
             else
             {
                 Areas = "[]";
             }      
         
        String rrs = "{\"Id\":\"" + rs.getId() + "\",\"Name\":\"" + rs.getName() + "\",\"Content\":\""
                 + rs.getContent() + "\",\"Remark\":\"" + rs.getRemark() + "\",\"WebsiteStyles\":" 
                 + WebsiteStyles + ",\"Areas\":" + Areas + ",\"ProductPropertyTypes\":" 
                 + ProductPropertyTypes + ",\"ProductTypes\":" + ProductTypes 
                 + ",\"Materials\":" + Materials + ",\"CreateTime\":\"" + rs.getCreateTime() + "\"}";
        return rrs;
    }
}
