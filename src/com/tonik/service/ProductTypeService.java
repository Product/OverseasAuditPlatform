package com.tonik.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.thinvent.utils.GsonUtil;
import com.tonik.Constant;
import com.tonik.dao.IProductPropertyTypeDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.model.Country;
import com.tonik.model.ProductPropertyType;
import com.tonik.model.ProductType;


/**
 * @spring.bean id="ProductTypeService"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 * @spring.property name="productPropertyTypeDAO" ref="ProductPropertyTypeDAO"
 */
public class ProductTypeService
{
    private IProductTypeDAO productTypeDAO;
    private IProductPropertyTypeDAO productPropertyTypeDAO;


    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    public IProductPropertyTypeDAO getProductPropertyTypeDAO()
    {
        return productPropertyTypeDAO;
    }

    public void setProductPropertyTypeDAO(IProductPropertyTypeDAO productPropertyTypeDAO)
    {
        this.productPropertyTypeDAO = productPropertyTypeDAO;
    }

    public List<ProductType> ProductTypePaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime, int level)
    {
        List<Object[]> ls = productTypeDAO.getProductTypePaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime,
                level);
        List<ProductType> pts = new ArrayList<ProductType>();
        for (Object[] obj : ls)
        {
            ProductType pt = (ProductType) obj[0];
            pts.add(pt);
        }
        return pts;
    }

    public String ProductTypeTotal(String strQuery, String strStraTime, String strEndTime, int level)
    {
        return Integer.toString(productTypeDAO.getProductTypeTotal(strQuery, strStraTime, strEndTime, level));
    }

    public void SaveProductType(ProductType pt)
    {
        if (pt.getPtid() == 0)
            pt.setLevel(1);
        else
        {
            ProductType fapt = productTypeDAO.getProductType(pt.getPtid());
            pt.setLevel(fapt.getLevel() + 1);
        }
        productTypeDAO.saveProductType(pt);
    }

    public Boolean DelProductType(Long id)
    {
        ProductType pt = productTypeDAO.getProductType(id);
        // 找到有响应的商品的话，返回false
        /*
         * if() return false;
         */
        List<ProductType> pts = getChildProductType(pt.getId());
        for (ProductType p : pts)
        {
            // 找到有响应的商品的话，返回false
            /*
             * if() return false;
             */
        }
        for (ProductType p : pts)
        {
            productTypeDAO.removeObject(ProductType.class, p.getId());
        }
        productTypeDAO.removeObject(ProductType.class, id);
        return true;
    }

    public ProductType GetProductTypeById(Long id)
    {
        ProductType pt = productTypeDAO.getProductType(id);
        if (pt.getPtid() == 0)
            pt.setFatherProductTypeName(pt.getName());
        else
        {
            ProductType faPt = productTypeDAO.getProductType(pt.getPtid());
            pt.setFatherProductTypeName(faPt.getName());
        }
        return pt;
    }

    public List<ProductType> getChildProductType(Long productTypeId)
    {
        List<Object[]> ls = productTypeDAO.getChildrenProductType(productTypeId);
        List<ProductType> pts = new ArrayList<ProductType>();
        for (Object[] obj : ls)
        {
            ProductType pt = (ProductType) obj[0];
            pts.add(pt);
        }
        return pts;
        
       
        
    }

    public String getProductTypeInfo(ProductType pt)
    {
        pt = GetProductTypeById(pt.getId());
        String propertyTypes = "";
        for (ProductPropertyType item : pt.getPropertyTypes())
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
                    firId = fppt.getId();
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
            
            propertyTypes += "{\"firId\":\"" + firId + "\",\"firName\":\"" + firName + "\",\"firRemark\":\"" + firRemark + "\",\"secId\":\"" + secId + "\",\"secName\":\"" + secName + "\",\"secRemark\":\"" + secRemark + "\",\"thiId\":\"" + thiId + "\",\"thiName\":\"" + thiName + "\",\"thiRemark\":\"" + thiRemark + "\"},";
        }
        
        if (propertyTypes != "")
        {
            propertyTypes = "[" + propertyTypes.substring(0, propertyTypes.length() - 1) + "]";
        }
        else
        {
            propertyTypes = "[]";
        }
           
        
        String res = "{\"Id\":\"" + pt.getId() + "\",\"Name\":\"" + pt.getName() + "\",\"PName\":\""
                + pt.getFatherProductTypeName() + "\",\"PtId\":\"" + pt.getPtid() + "\",\"Level\":\"" + pt.getLevel()
                + "\",\"Remark\":\"" + pt.getRemark() + "\",\"CreateTime\":\"" + pt.getFormatCreateTime()
                + "\",\"CreatePerson\":\"" + pt.getCreatePersonName() + "\",\"propertyTypes\":" + propertyTypes + "}";
        return res;
    }

    public String getProductTypeTotal()
    {
        Integer tot = productTypeDAO.getProductTypeMaxTotal();
        if (tot == null)
            return "1";
        return tot.toString();
    }
    
    //add by lxt
    public List<ProductType> getRootProductType()
    {
        return productTypeDAO.getRootProductType();
        
    }
    //add by lxt
    public String getProductTypeTotalByCountryId(Long countryid,int level)
    {
        return Integer.toString(productTypeDAO.getProductTypeTotalByCountryId(countryid,level));
    }
   //add by lxt
    public List<String> getProductTypeListByCountryId(Long countryid,int level)
    {
        List<Object[]> pt = productTypeDAO.getProductTypeListByCountryId(countryid,level);
        List<String>ptl = new ArrayList<String>();
        for(Object[] item :pt)
        {
           String p =(String)item[0];
            ptl.add(p);
        }
        return ptl;
        
    }
    //add by lxt
//获得世界地图所需的产品分类信息（主产种类）
    public String getWorldMapProductTypeInfo(String ptl)
    {
        List<Object[]> wvl = productTypeDAO.getWorldMapProductTypeTotal(ptl);
        List<Object[]> wtl = productTypeDAO.getWorldMapProductTypeInfo(ptl);
        String strCNVList ="";
        String strCNTList = "";
        for(Object[] obj :wvl)
        {
            strCNVList +=  "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[2] + "\"},";
        }
        
        String name = "";
        int counter = 0;
        String title = "主要产品类型：";
        for(Object[] obj: wtl){
            name = "".equals(name) ? obj[1].toString():name;
            if(name.equals(obj[1].toString())){
                if(counter++ < 10){
                    title += obj[0]+",";
                    if(counter % 3 == 0)
                        title += "<br/>";
                }
                else
                    continue;
            }else{
                if(title.length() > 0)
                    title = title.substring(0, title.length()-1);
                strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
                title = "主要产品类型："+obj[0]+",";
                counter = 1;
                name = obj[1].toString();
            }
            if(title.length() > 0)
                title = title.substring(0, title.length()-1);
            strCNTList +=  "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
        }
        if(strCNVList.length() > 0)
            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
        if(strCNTList.length() > 0)
            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);
             
        return("{\"proTypeCNVList\":[" + strCNVList + "]" + ",\"proTypeCNTList\":[" + strCNTList + "]}");
    }
    
    //add by lxt
    public String getProTypeTotal()
    {
        return Long.toString(productTypeDAO.getProductTypeTotal());
    }
    public List<ProductType> getProductTypeByLevel(int level)
    {
        List<ProductType> ls = new ArrayList<ProductType>();
        for (ProductType item : productTypeDAO.getProductType())
        {
            if (item.getLevel() == level)
            {
                ls.add(item);
            }
        }
        return ls;
    }

    public List<ProductType> getProductTypeByParentId(Long parentId)
    {
        List<ProductType> ls = new ArrayList<ProductType>();
        for (ProductType item : productTypeDAO.getProductType())
        {
            if (item.getPtid().longValue() == parentId.longValue())
            {
                ls.add(item);
            }
        }
        return ls;
    }
    
    public List<ProductType> getProductTypeList()
    {
        return productTypeDAO.getProductType();
    }

    public String getProductTypeTotal(String ptl, Country c)
    {
        String res = "";
        if(ptl.length() == 0){
            if(c == null){
                res = Long.toString(productTypeDAO.getProductTypeTotal());
            }else
                res = Integer.toString(productTypeDAO.getProductTypeByCountry(c.getId()));
        }else{
            if(c == null){
                res = Integer.toString(productTypeDAO.getProductTypeByProduct(ptl));
            }else{
                res = Integer.toString(productTypeDAO.getProductTypeByProductAndCountry(ptl,c.getId()));
            }
        }
            
        return res;
    }

    public List<Object[]> getProductPagingList(String ptl, Country c, String start, String len, String order, String dir)
    {
        String strOrder = "";
        switch(order){
            case "0":strOrder = "PRODUCTTYPE_NAME";break;
            case "1":strOrder = "PRODUCTTYPE_REMARK";break;
        }
        List<Object[]> res;
        if(ptl == null || ptl == ""){
            res = productTypeDAO.getProductTypeLists(c, start, len, strOrder, dir);
        }else{
            res = productTypeDAO.getProductTypeListsByProduct(ptl, c, start, len, strOrder, dir);
        }
        return res;
    }

    public String getProductJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1]) + "\"]";
    return res;
    }

    public String getProductTypeTotalByCountryAndProduct(Country c, String ptl)
    {
        String res = "0";
        if(c != null && ptl != ""){
            res = Integer.toString(productTypeDAO.getProductTypeByProductAndCountry(ptl, c.getId()));
        }else if(c != null){
            res = Integer.toString(productTypeDAO.getProductTypeByCountry(c.getId()));
        }else if(ptl != ""){
            res = Integer.toString(productTypeDAO.getProductTypeByProduct(ptl));
        }else{
            res = Long.toString(productTypeDAO.getProductTypeTotal());
        }
        return res;
    }
    
    public String getProductTypeDirectory(Long ptId)
    {
        List<TypeDirectoryVO> tdVOs = Lists.newArrayList();
        /*该菜单下的一级子菜单*/
        List<Object[]> ls = productTypeDAO.getChildProductType(ptId);
        for(Object[] item : ls)
        {
            TypeDirectoryVO tdVO = new TypeDirectoryVO(
                    item[0].toString(),
                    item[1].toString(),
                    (int)item[2] == 1?true:false);
            tdVOs.add(tdVO);
        }
        return GsonUtil.bean2Json(tdVOs);        
    }
    
    public String getChildProductTypeWithPDNum(Long ptId)
    {
        List<TypeDirectoryVO> tdVOs = Lists.newArrayList();
        /*该菜单下的一级子菜单*/
        List<Object[]> ls = productTypeDAO.getChildProductTypeWithPDNum(ptId);
        for(Object[] item : ls)
        {
            TypeDirectoryVO tdVO = new TypeDirectoryVO(
                    item[0].toString(),
                    item[1].toString(),
                    (int)item[2] == 1?true:false,
                    (int)item[3]);
            tdVOs.add(tdVO);
        }
        return GsonUtil.bean2Json(tdVOs);        
    }
    
    public class TypeDirectoryVO{
        
        private String id = "";
        
        private String name = "";
        
        private Boolean isVisible;      //是否有下级
        
        private Integer num;            //该类别产品数量
    
        public TypeDirectoryVO(String id, String name, Boolean isVisible){
            this.id = id;
            this.name = name;
            this.isVisible = isVisible;
        }
        
        public TypeDirectoryVO(String id, String name, Boolean isVisible, Integer num){
            this.id = id;
            this.name = name;
            this.isVisible = isVisible;
            this.num = num;
        }
    }
}
