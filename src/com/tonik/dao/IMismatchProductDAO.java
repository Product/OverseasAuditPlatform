package com.tonik.dao;

import java.util.Date;
import java.util.List;

import com.tonik.model.MismatchProduct;

/**
 * @desc 不符合标准的商品 DAO
 * @author liuyu
 */
public interface IMismatchProductDAO
{ 
    /**
     * @desc 通过商品id获取商品
     * @param MismatchProductId 商品id
     * @return MismatchProduct
     */
    public MismatchProduct getMismatchProduct(Long MismatchProductId);
    
    /**
     * @desc 保存商品
     * @param MismatchProduct MismatchProduct
     */
    public void saveMismatchProduct(MismatchProduct MismatchProduct);

    /**
     * @desc 删除商品
     * @param MismatchProduct MismatchProduct
     */
    public void removeMismatchProduct(MismatchProduct MismatchProduct);
    
    /**
     * @desc 获取商品分页列表
     * @param pageIndex 页码数
     * @param pageSize 每页记录数
     * @param ptl 商品类别数组
     * @param standardId 标准Id
     * @param orderBy 排序字段
     * @param orderType 升序还是降序
     * @return List<MismatchProduct>
     */
    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long standardId, String orderBy, String orderType);
 
    /**
     * @desc 获取商品分页列表
     * @param pageIndex 页码数
     * @param pageSize 每页记录数
     * @param ptl 商品类别数组
     * @param standardIdList 标准Id数组
     * @param orderBy 排序字段
     * @param orderType 升序还是降序
     * @return List<MismatchProduct>
     */
    public List<Object[]> getMismatchProductPaging(int pageIndex, int pageSize, Long[] ptl, Long[] standardIdList,
            String orderBy, String orderType);


    /**
     * @desc 获取商品总数
     * @param ptl 商品类别数组
     * @param standardId 标准Id
     * @return int
     */
    public int getMismatchProductTotal(Long[] ptl, Long standardId);

    /**
     * @desc 获取商品总数
     * @param ptl 商品类别数组
     * @param standardIdList 标准Id数组
     * @return int
     */
    public int getMismatchProductTotal(Long[] ptl, Long[] standardIdList);

    /**
     * desc: 删除不匹配的商品
     * @param id 匹配记录的id
     */
    public void removeMismatchProductById(String id);

    /**
     * desc: 删除不匹配的商品
     * @param idList 匹配记录的id数组
     */
    public void removeMultiMismatchProductById(String[] idList);
    
    /**
     * @desc: 查找不匹配的商品的不匹配信息
     * @param id 不匹配的记录id
     * @return 不匹配信息
     */
    public String getMismatchContentById(Long id);

    /**
     * @desc 查找与某商品相似的不匹配标准的商品列表
     * @param pageIndex 页码值
     * @param pageSize 每页记录数
     * @param id 不匹配的记录id
     * @return 相似记录的分页列表
     */
    public List<Object[]> getSimilarProductPaging(int pageIndex, int pageSize, Long id);

    /**
     * @desc 查找与某商品相似的不匹配标准的商品总数
     * @param id 不匹配的记录id
     * @return 商品总数
     */
    public int getSimilarProductTotal(Long id);

    /**
     * @desc: 将不符合规则的商品记录加载到MISMATCH_PRODUCT表中，演示用例，可能更改
     */
    public List<Object[]> loadingMismatchProductMaterial(Long id);

    /**
     * @desc:通过商品id 和规则id 删除记录
     * @param productId 商品id
     * @param standardId 规则id
     */
    public void delMismatchProductByProductIdAndStandardId(Long productId, Long standardId);

    /**
     * @desc:获得网站总数
     * @return 网站数
     */
    public int getWebsiteTotal();

    /**
     * @desc 获得规则总数
     * @return 规则数
     */
    public int getStandardTotal();

    /**
     * @desc 获得不匹配的商品总数
     * @return 商品总数
     */
    public int getMismatchProductTotal();

    /**
     * @desc 获得风险商品的地理分布
     * @return 返回各国存在的风险商品数
     */
    public List<Object[]> getMismatchMap();
    
    public List<Object[]> getMismatchFormByCountry(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getTopCountry(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getTopWebsite(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getTopType(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getTopBrand(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getMismatchFormByWebsite(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getMismatchFormByType(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getMismatchFormByBrand(Integer topSize, Date startTime, Date endTime);
    
    public List<Object[]> getPieChartByBrand(Integer topSize, Date startTime, Date endTime);

    public List<Object[]> getPieChartByProductType(Integer topSize, Date startTime, Date endTime);

    public List<Object[]> getPieChartByWebsite(Integer topSize, Date startTime, Date endTime);

    public List<Object[]> getPieChartByCountry(Integer topSize, Date startTime, Date endTime);
    
    List<Object[]> getEvaluationByWebsite(int pageIndex, int pageSize);
    
    Integer getEvaluationTotalByWebsite();
}
