package com.tonik.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinvent.utils.DateUtil;
import com.thinvent.utils.GsonUtil;
import com.tonik.Constant;
import com.tonik.dao.IProductDAO;
import com.tonik.dao.IProductDefinitionDAO;
import com.tonik.dao.IProductStyleDAO;
import com.tonik.dao.IProductTypeDAO;
import com.tonik.dao.IWebsiteDAO;
import com.tonik.model.ChattingMessage;
import com.tonik.model.Country;
import com.tonik.model.Product;
import com.tonik.model.ProductDefinition;
import com.tonik.model.ProductStyle;
import com.tonik.model.Website;

/**
 * @spring.bean id="ProductService"
 * @spring.property name="productDAO" ref="ProductDAO"
 * @spring.property name="websiteDAO" ref="WebsiteDAO"
 * @spring.property name="productStyleDAO" ref="ProductStyleDAO"
 * @spring.property name="productTypeDAO" ref="ProductTypeDAO"
 * @spring.property name="productDefinitionDAO" ref="ProductDefinitionDAO"
 */
public class ProductService
{
    private IProductDAO productDAO;
    private IProductStyleDAO productStyleDAO;
    private IWebsiteDAO websiteDAO;
    private IProductTypeDAO productTypeDAO;
    private IProductDefinitionDAO productDefinitionDAO;


    public IWebsiteDAO getWebsiteDAO()
    {
        return websiteDAO;
    }

    public void setWebsiteDAO(IWebsiteDAO websiteDAO)
    {
        this.websiteDAO = websiteDAO;
    }

    public IProductStyleDAO getProductStyleDAO()
    {
        return productStyleDAO;
    }

    public void setProductStyleDAO(IProductStyleDAO productStyleDAO)
    {
        this.productStyleDAO = productStyleDAO;
    }

    public IProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    public void setProductTypeDAO(IProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    public IProductDefinitionDAO getProductDefinitionDAO()
    {
        return productDefinitionDAO;
    }

    public void setProductDefinitionDAO(IProductDefinitionDAO productDefinitionDAO)
    {
        this.productDefinitionDAO = productDefinitionDAO;
    }

    public IProductDAO getProductDAO()
    {
        return productDAO;
    }

    public void setProductDAO(IProductDAO ProductDAO)
    {
        this.productDAO = ProductDAO;
    }

    public void DelProduct(Long Id)// 删除
    {
        productDAO.removeProduct(Id);
    }

    /*
     * 函数名：SaveProduct 作用：保存商品类型为productType，在website网站销售的商品
     */
    public void SaveProduct(Product product)
    {

        product.setRemark(product.getRemark().replaceAll("\\s", ""));
        product.setRemark(product.getRemark().replaceAll("\"", "“"));
        product.setRemark(product.getRemark().replaceAll("\"", "”"));
        productDAO.saveProduct(product);
    }

    public Product GetProductById(Long Id)
    {
        return productDAO.getProduct(Id);
    }

    /*
     * 函数名：ProductPaging 作用：获得第pageIndex页的Product列表
     */
    public List<Product> ProductPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {

        return productDAO.getProductPaging(pageIndex, pageSize, strQuery, strStraTime, strEndTime);

    }

    public List<Object[]> ProductPaging1(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime)
    {

        return productDAO.getProductPaging1(pageIndex, pageSize, strQuery, strStraTime, strEndTime);

    }

    public List<Object[]> getProductPaging(int pageIndex, int pageSize, String orderBy, String orderType)
    {
        return productDAO.getProductPaging(pageIndex, pageSize, orderBy, orderType);
    }

    public Long getProductTotal1()
    {
        return productDAO.getProductTotal();
    }

    /*
     * 函数名：WebsiteStyleTotal 作用：获得查询到的Websitestyle条目总数
     */
    public String ProductTotal(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(productDAO.getProductTotal(strQuery, strStraTime, strEndTime));
    }

    public String ProductTotal1(String strQuery, String strStraTime, String strEndTime)
    {
        return Integer.toString(productDAO.getProductTotal1(strQuery, strStraTime, strEndTime));
    }

    /*
     * 函数名：getProductStyle 作用：获得所有的ProductStyle条目列表
     */
    public List<ProductStyle> getProductStyle()
    {
        return productStyleDAO.getProductStyle();
    }

    public List<Website> getWebsite()
    {
        return websiteDAO.getWebsite();
    }

    public String getProductInfo(Product p)
    {
        String AreaName = "";
        String AreaId = "";
        String CountryName = "";
        String CountryId = "";
        Long firType = 0L;
        Long secType = 0L;
        Long thiType = 0L;
        String firTypeName = "";
        String secTypeName = "";
        String thiTypeName = "";
        String ProDefName = "";
        if (p.getPdid() != 0)
        {
            ProductDefinition pd = productDefinitionDAO.getProductDefinitionById(p.getPdid());
            ProDefName = pd.getName_cn();
        }
        if (p.getFirstlevelType() != null)
        {
            firType = p.getFirstlevelType().getId();
            firTypeName = p.getFirstlevelType().getName();
        }
        if (p.getSecondlevelType() != null)
        {
            secType = p.getSecondlevelType().getId();
            secTypeName = p.getSecondlevelType().getName();

        }
        if (p.getThirdlevelType() != null)
        {
            thiType = p.getThirdlevelType().getId();
            thiTypeName = p.getThirdlevelType().getName();
        }
        if (p.getArea() != null)
        {
            AreaId = Long.toString(p.getArea().getId());
            AreaName = p.getArea().getName();
        }
        if (p.getCountry() != null)
        {
            CountryId = Long.toString(p.getCountry().getId());
            CountryName = p.getCountry().getName();
        }
        String res = "";
        res = "{\"Id\":\"" + p.getId() + "\",\"Name\":\"" + p.getName() + "\",\"Brand\":\"" + p.getBrand()
                + "\",\"Price\":\"" + p.getPrice() + "\",\"Freight\":\"" + p.getFreight() + "\",\"CustomsDuty\":\""
                + p.getCustomsDuty() + "\",\"PurchaseTime\":\"" + p.getPurchaseTime() + "\",\"OnlineTime\":\""
                + p.getOnlineTime() + "\",\"FirstType\":\"" + firType + "\",\"SecondType\":\"" + secType
                + "\",\"ThirdType\":\"" + thiType + "\",\"PdId\":\"" + p.getPdid() + "\",\"Sales\":\"" + p.getSales()
                + "\",\"FirstTypeName\":\"" + firTypeName + "\",\"SecondTypeName\":\"" + secTypeName
                + "\",\"ThirdTypeName\":\"" + thiTypeName + "\",\"WebsiteId\":\"" + p.getWebsite().getId()
                + "\",\"Location\":\"" + p.getLocation() + "\",\"Picture\":\"" + p.getPicture() + "\",\"ProDefName\":\""
                + ProDefName + "\",\"ProducingArea\":\"" + p.getProducingArea() + "\",\"SuitableAge\":\""
                + p.getSuitableAge() + "\",\"WebsiteName\":\"" + p.getWebsiteName() + "\",\"Standard\":\""
                + p.getStandard() + "\",\"ExpirationDate\":\"" + p.getExpirationDate() + "\",\"Remark\":\""
                + p.getRemark() + "\",\"CreatePerson\":\"" + p.getCreatePerson() + "\",\"CreateTime\":\""
                + p.getCreateTime() + "\",\"AreaId\":\"" + AreaId + "\",\"AreaName\":\"" + AreaName
                + "\",\"CountryId\":\"" + CountryId + "\",\"CountryName\":\"" + CountryName + "\"}";
        return res;
    }

    public String getProductTotalByTypeIdsAndCountryId(Long countryid, Long[] ptl)
    {
        return Integer.toString(productDAO.getProductTotalByTypeIdsAndCountryId(countryid, ptl));
    }

    public List<Product> getProductListByTypeIdsAndCountryId(Long countryid, Long[] ptl)
    {
        List<Object[]> ls = productDAO.getProductListByTypeIdsAndCountryId(countryid, ptl);
        List<Product> pl = new ArrayList<Product>();
        for (Object[] obj : ls)
        {
            Product p = (Product) obj[0];
            pl.add(p);
        }
        return pl;

    }

    // 获得世界各国生产的商品信息，（名字，数量，代表商品列表）
    public String getWorldMapProductInfo(String ptl)
    {
        List<Object[]> wvl = productDAO.getWorldMapProductTotal(ptl);
        List<Object[]> wtl = productDAO.getWorldMapProductNameList(ptl);
        String strCNVList = "";
        String strCNTList = "";
        for (Object[] obj : wvl)
        {
            strCNVList += "{\"name\":\"" + obj[1] + "\",\"value\":\"" + obj[2] + "\"},";
        }

        String name = "";
        int counter = 0;
        String title = "代表商品：";
        for (Object[] obj : wtl)
        {
            name = "".equals(name) ? obj[0].toString() : name;
            if (name.equals(obj[0].toString()))
            {
                if (counter++ < 10)
                {
                    title += obj[1] + ",";
                    if (counter % 3 == 0)
                        title += "<br/>";
                }
                else
                    continue;
            }
            else
            {
                if (title.length() > 0)
                    title = title.substring(0, title.length() - 1);
                strCNTList += "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
                title = "代表商品：" + obj[1] + ",";
                counter = 1;
                name = obj[0].toString();
            }

        }
        if (title.length() > 0)
            title = title.substring(0, title.length() - 1);
        strCNTList += "{\"name\":\"" + name + "\",\"title\":\"" + title + "\"},";
        if (strCNVList.length() > 0)
            strCNVList = strCNVList.substring(0, strCNVList.length() - 1);
        if (strCNTList.length() > 0)
            strCNTList = strCNTList.substring(0, strCNTList.length() - 1);

        return ("{\"proCNVList\":[" + strCNVList + "]" + ",\"proCNTList\":[" + strCNTList + "]}");
    }

    public String getProductTotal()
    {
        return Long.toString(productDAO.getProductTotal());
    }

    public List<Product> getProductByEventWebsite(Long webistId)
    {
        return productDAO.getProductByEventWebsite(webistId);
    }

    public List<Product> getProductByEventBrand(String brandName)
    {
        return productDAO.getProductByEventBrand(brandName);
    }

    public List<Product> getProductList()
    {
        return productDAO.getProduct();
    }

    public List<Product> getProductByProductDefinitionId(Long ProductDefinitionId)
    {
        return productDAO.getProductByProductDefinitionId(ProductDefinitionId);
    }

    public String MatchingProductDefinition(int pageSize)
    {
        boolean r = true;
        int proDefTotal = productDefinitionDAO.getProductDefTotal();
        int pageTotal = (proDefTotal + 9) / pageSize;
        for (int i = 1; i <= pageTotal; i++)
        {
            List<Object[]> pdf = productDefinitionDAO.getProductDefinitionFeaturesPaging(i, pageSize);
            r = productDAO.MatchingProductDefinition(pdf);
            if (r == false)
                return "false";
        }
        return "true";
    }

    /**
     * @desc 通过产品类型获得对应的商品列表
     * @param ptl 产品类型id数组
     * @return 商品列表
     */
    public List<Product> getProductListByTypesIds(Long[] ptl)
    {
        return productDAO.getProductListByTypeIds(ptl);
    }

    /**
     * @desc 通过原料获得对应的商品列表
     * @param ptl 原料id数组
     * @return 商品列表
     */
    public List<Product> getProductListByMaterialIds(Long[] ptl)
    {
        return productDAO.getProductListByMaterialIds(ptl);
    }

    public String getProductTotalByProduct(String ptl, Country c)
    {
        String res = "";
        if (ptl.length() == 0)
        {
            if (c == null)
            {
                res = Long.toString(productDAO.getProductTotal());
            }
            else
                res = Integer.toString(productDAO.getProductTotalByTypeIdsAndCountryId(c.getId(), new Long[0]));
        }
        else
        {
            if (c == null)
            {
                res = Integer.toString(productDAO.getProductTotalByStyle(ptl));
            }
            else
            {
                res = Integer.toString(productDAO.getProductTotalByStyleAndCountry(ptl, c.getId()));
            }
        }

        return res;
    }

    public List<Object[]> getProductPagingList(String ptl, Country c, String start, String len, String order,
            String dir)
    {
        String strOrder = "";
        switch (order)
        {
            case "0":
                strOrder = "isnull(PRODUCT_NAME, PRODUCT_REMARK)";
                break;
            case "1":
                strOrder = "WEBSITE.WEBSITE_NAME";
                break;
            case "2":
                strOrder = "PRODUCT_LOCATION";
                break;
        }
        List<Object[]> res;
        if (ptl == null || ptl == "")
        {
            res = productDAO.getProductLists(c, start, len, strOrder, dir);
        }
        else
        {
            res = productDAO.getProductListsByStyle(ptl, c, start, len, strOrder, dir);
        }
        return res;
    }

    public String getProductJsonInfo(Object[] obj)
    {
        String res = "[\"" + Constant.val(obj[0]) + "\",\"" + Constant.val(obj[1]) + "\",\"" + Constant.val(obj[2])
                + "\"]";
        return res;
    }

    public String getProductTotalByCountryAndProduct(Country c, String ptl)
    {
        String res = "0";
        if (c != null && ptl != "")
        {
            res = Integer.toString(productDAO.getProductTotalByStyleAndCountry(ptl, c.getId()));
        }
        else if (c != null)
        {
            res = Integer.toString(productDAO.getProductTotalByTypeIdsAndCountryId(c.getId(), new Long[0]));
        }
        else if (ptl != "")
        {
            res = Integer.toString(productDAO.getProductTotalByStyle(ptl));
        }
        else
        {
            res = Long.toString(productDAO.getProductTotal());
        }
        return res;
    }

    public void execSyncProductsTask(String dateFrom)
    {
        productDAO.execSyncProductsTask(dateFrom);
    }

    public void execSyncProductsTaskById(Long id, String remark)
    {
        productDAO.execSyncProductsTaskById(id, remark);
    }

    public List<Object[]> getContrabandProduct(int type)
    {
        return productDAO.getContrabandProduct(type);
    }

    public List<Object[]> getContrabandProductWebsite()
    {
        return productDAO.getContrabandProductWebsite();
    }

    public List<Object[]> getContrabandProductBrand()
    {
        return productDAO.getContrabandProductBrand();
    }

    public List<Object[]> getContrabandProductOrigin()
    {
        return productDAO.getContrabandProductOrigin();
    }

    public List<Object[]> getContrabandProductMaterial()
    {
        return productDAO.getContrabandProductMaterial();
    }

    public List<Object[]> getContrabandProductSentiment()
    {
        return productDAO.getContrabandProductSentiment();
    }

    public List<Product> getProductByDate(Date fromDate)
    {
        return productDAO.getProductByDate(fromDate);
    }

    public String getMatchProducts(int pageIndex, int pageSize, String strQuery, Long productType, Boolean isMatched)
    {
        List<Object[]> ls = productDAO.getMatchProductPaging(pageIndex, pageSize, strQuery, productType, isMatched);
        Integer count = productDAO.getMatchProductTotal(strQuery, productType, isMatched);
        List<Map<String, String>> list = Lists.newArrayList();
        Map<String, Object> result = Maps.newHashMap();
        for (Object[] item : ls)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item[0].toString());
            map.put("name", item[1].toString());
            map.put("productTypeId", item[3] == null ? "" : item[3].toString());
            map.put("productTypeName", item[4] == null ? "" : item[4].toString());
            map.put("brand", item[5] == null ? "" : item[5].toString());
            map.put("country", item[6] == null ? "" : item[6].toString());
            map.put("createTime", DateUtil.formatDate(DateUtil.YYMMDDHHMM_FORMAT, (Date) item[7]));
            map.put("isMatched", item[8] == null ? "未匹配" : "已匹配");
            map.put("countryId", item[9] == null ? "" : item[9].toString());
            list.add(map);
        }
        result.put("total", count);
        result.put("list", list);
        return GsonUtil.bean2Json(result);
    }

    public String getRandTopProducts()
    {
        List<Product> ls = productDAO.getRandTopProducts();
        List<Map<String, String>> list = Lists.newArrayList();
        Map<String, Object> result = Maps.newHashMap();
        for (Product item : ls)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", item.getId().toString());
            map.put("name", item.getRemark());
            list.add(map);
        }
        result.put("list", list);
        return GsonUtil.bean2Json(result);
    }

}
