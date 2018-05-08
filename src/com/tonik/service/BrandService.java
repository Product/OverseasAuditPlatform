package com.tonik.service;

import java.util.ArrayList;
import java.util.List;

import com.tonik.Constant;
import com.tonik.dao.IBrandDAO;
import com.tonik.model.Brand;
import com.tonik.model.Country;

/**
 * @spring.bean id="BrandService"
 * @spring.property name="brandDAO" ref="BrandDAO"
 */
public class BrandService
{
    private IBrandDAO BrandDAO;

    public void setBrandDAO(IBrandDAO brandDAO)
    {
        this.BrandDAO = brandDAO;
    }
    
    public List<Brand> getBrandList()
    {
        return BrandDAO.getBrandList();
    }
    
    public String BrandTotal(String strQuery,String strStraTime, String strEndTime)
    {
        return Integer.toString(BrandDAO.getBrandTotal(strQuery,strStraTime,strEndTime));
    }
    
    public List<Brand> BrandPaging(int pageIndex,int pageSize,String strQuery,String strStraTime, String strEndTime)
    {
        return BrandDAO.getBrandPaging(pageIndex, pageSize, strQuery,strStraTime,strEndTime);
    }
    
    public void SaveBrand(Brand brand)
    {
        BrandDAO.SaveBrand(brand);
    }
    
    public void RemoveBrand(Long brandId)
    {
        BrandDAO.RemoveBrand(brandId);
    }
    
    public Brand getBrandById(Long id)
    {
        return BrandDAO.getBrandById(id);
    }
    
    //add by lxt
    public String getBrandTotalByCountryId(Long countryid)
    {
        return Integer.toString(BrandDAO.getBrandTotalByCountryId(countryid));
    }
    //add by lxt
    public List<Brand> getBrandListByCountryId(Long countryid)
    {
        List<Object[]> ls = BrandDAO.getBrandListByCountryId(countryid);
        List<Brand> bt = new ArrayList<Brand>();//将object中的Object[0]（website 类型对象）提取到新的website列表中
        for(Object[] obj : ls){
            Brand b = (Brand)obj[0];
            bt.add(b);
        }
        return bt;
        
    }
//取出世界地图所需的品牌信息（各个国家包含的品牌数和代表品牌列表）
    public String getWorldMapBrandInfo(String ptl)
    {
        List<Object[]> wvl = BrandDAO.getWorldMapBrandTotal(ptl);
        List<Object[]> wtl = BrandDAO.getWorldMapBrandNameList(ptl);
        
        String strCNVList ="";
        String strCNTList = "";
        for(Object[] obj :wvl)
        {
            strCNVList +=  "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[2] + "\"},";
        }
        
        String name = "";
        int counter = 0;
        String title = "代表品牌：";
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
                title = "代表品牌："+obj[1]+",";
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
             
        return("{\"brandCNVList\":[" + strCNVList + "]" + ",\"brandCNTList\":[" + strCNTList + "]}");
    }
    public String getBrandTotal()
    {
        return Long.toString(BrandDAO.getBrandTotal());
    }

    public String getBrandTotalByProduct(String ptl, Country c)
    {
        String res = "";
        if(ptl.length() == 0){
            if(c == null){
                res = Long.toString(BrandDAO.getBrandTotal());
            }else
                res = Integer.toString(BrandDAO.getBrandTotalByCountryId(c.getId()));
        }else{
            if(c == null){
                res = Integer.toString(BrandDAO.getBrandTotalByProduct(ptl));
            }else{
                res = Integer.toString(BrandDAO.getBrandTotalByProductAndCountry(ptl,c.getId()));
            }
        }
            
        return res;
    }

    public String getBrandJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1])
                + "\",\"" + Constant.val(obj[2]) + "\"]";
        return res;
    }

    public List<Object[]> getBrandtPagingList(String ptl, Country c, String start, String len, String order, String dir)
    {
        String strOrder = "";
        switch(order){
            case "0":strOrder = "BRAND_NAMECN";break;
            case "1":strOrder = "BRAND_NAMEEN";break;
            case "2":strOrder = "COUNTRY_NAME";break;
        }
        List<Object[]> res;
        if(ptl == null || ptl == ""){
            res = BrandDAO.getBrandLists(c, start, len, strOrder, dir);
        }else{
            res = BrandDAO.getBrandListsByProduct(ptl, c, start, len, strOrder, dir);
        }
        return res;
    }

    public String getBrandTotalByCountryAndProduct(Country c, String ptl)
    {
        String res = "0";
        if(c != null && ptl != ""){
            res = Integer.toString(BrandDAO.getBrandTotalByProductAndCountry(ptl, c.getId()));
        }else if(c != null){
            res = Integer.toString(BrandDAO.getBrandTotalByCountryId(c.getId()));
        }else if(ptl != ""){
            res = Integer.toString(BrandDAO.getBrandTotalByProduct(ptl));
        }else{
            res = Long.toString(BrandDAO.getBrandTotal());
        }
        return res;
    }
}
