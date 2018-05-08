package com.tonik.dao;

import java.util.Date;
import java.util.List;

import com.tonik.model.Country;
import com.tonik.model.Product;
import com.tonik.model.Rules;

public interface IProductDAO extends IDAO
{
    public List<Product> getProduct();

    public Product getProduct(Long productId);

    public void saveProduct(Product product);

    public void removeProduct(Product product);

    public void removeProduct(Long productId);

    public List<Product> getProductPaging(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime);

    public List<Object[]> getProductPaging1(int pageIndex, int pageSize, String strQuery, String strStraTime,
            String strEndTime);

    public List<Object[]> getProductPaging(int pageIndex, int pageSize, String orderBy, String orderType);

    public int getProductTotal(String strQuery, String strStraTime, String strEndTime);

    public List<Product> getProductByRules(Rules rules, List<String> rulesvalue);

    public Product getProductByLocation(String location);

    public int getProductTotalByTypeIds(Long[] ptl);

    public List<Product> getProductListByTypeIds(Long[] ptl);

    public List<Object[]> getProductListByTypeIdsAndCountryId(Long countryid, Long[] ptl);

    public int getProductTotalByTypeIdsAndCountryId(Long countryid, Long[] ptl);

    public Long getProductTotal();

    public List<Object[]> getWorldMapProductTotal(String ptl);

    // public List<Object[]> getWorldMapProductNameList(Long []ptl);

    public List<Product> getProductByEventWebsite(Long websiteId);

    public List<Product> getProductByEventBrand(String brandName);

    public List<Product> getProductByProductDefinitionId(Long ProductDefinitionId);

    public boolean MatchingProductDefinition(List<Object[]> pdf);

    /**
     * @desc 查找含有某些配方原料的商品列表
     * @param ptl 配方原料id数组
     * @return List<Product> 商品列表
     */
    public List<Product> getProductListByMaterialIds(Long[] ptl);

    public List<Object[]> getWorldMapProductNameList(String ptl);

    public Integer getProductTotalByStyle(String ptl);

    public Integer getProductTotalByStyleAndCountry(String ptl, Long id);

    public List<Object[]> getProductLists(Country c, String start, String len, String strOrder, String dir);

    public List<Object[]> getProductListsByStyle(String ptl, Country c, String start, String len, String strOrder,
            String dir);

    public void execSyncProductsTask(String dateFrom);

    /*
     * @desc 根据ID和remark, 更新product
     * @param id 商品id
     * @param remark 商品备注
     * @return void
     */
    public void execSyncProductsTaskById(Long id, String remark);

    public List<Object[]> getContrabandProduct(int type);

    public List<Object[]> getContrabandProductWebsite();

    public List<Object[]> getContrabandProductBrand();

    public List<Object[]> getContrabandProductOrigin();

    public List<Object[]> getContrabandProductMaterial();

    public List<Object[]> getContrabandProductSentiment();

    public List<Product> getProductByDate(Date fromDate);

    int getProductTotal1(String strQuery, String strStartTime, String strEndTime);

    List<Object[]> getMatchProductPaging(final int pageIndex, final int pageSize, final String strQuery,
            final Long productType, final Boolean isMatched);

    Integer getMatchProductTotal(final String strQuery, final Long productType, final Boolean isMatched);

    List<Product> getRandTopProducts();

}
