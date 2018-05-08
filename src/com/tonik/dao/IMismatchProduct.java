package com.tonik.dao;

import java.util.List;

import com.tonik.model.MismatchProduct;

/**
 * 
 * @author liuyu
 * @desc:商品标准 DAO
 */
public interface IMismatchProduct
{
    /**
     * desc:获得所有商品标准对应列表
     * @return
     */
    public List<MismatchProduct> getMismatchProducts();
    
    /**
     * desc:保存商品标准
     * @param MismatchProduct 商品标准对象
     */
    public void saveMismatchProduct(MismatchProduct MismatchProduct);
    
    /**
     * desc:通过id删除商品标准
     * @param id
     */
    public void removeMismatchProduct(Long id);
    
    /**
     * desc:通过id查询商品标准
     * @param id
     * @return 商品标准对象
     */
    public MismatchProduct getMismatchProductById(Long id);
    
    /**
     * desc:查询商品标准总数
     * @param strQuery 查询条件
     * @return 符合条件的商品标准总数
     */
    public int getMismatchProductsTotal(String strQuery);
    
    /**
     * desc: 查询商品标准分页列表
     * @param pageIndex 当前页码
     * @param pageSize 每页记录数
     * @param strQuery 查询条件
     * @return 商品标准的分页列表
     */
    public List<MismatchProduct> getMismatchProductsPaging(int pageIndex,int pageSize,String strQuery);
}
