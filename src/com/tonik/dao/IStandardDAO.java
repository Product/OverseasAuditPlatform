package com.tonik.dao;

import java.util.List;

import com.tonik.model.Standard;

/**
 * @author liuyu
 * @desc:匹配标准 DAO
 */
public interface IStandardDAO
{
    /**
     * desc:获得所有标准列表
     * @return 所有标准列表
     */
    public List<Standard> getStandards();
    
    /**
     * desc:保存标准
     * @param Standard 标准对象
     */
    public void saveStandard(Standard standard);
    
    /**
     * desc:通过id删除标准
     * @param id
     */
    public void removeStandard(Long id);
    
    /**
     * desc:通过id查询标准
     * @param id
     * @return 标准对象
     */
    public Standard getStandardById(Long id);
    
    /**
     * desc:查询标准总数
     * @param strQuery 查询条件
     * @return 符合条件的标准总数
     */
    int getStandardsTotal(String strQuery, Integer system);
    
    /**
     * desc: 查询标准分页列表
     * @param pageIndex 当前页码
     * @param pageSize 每页记录数
     * @param strQuery 查询条件
     * @return 标准的分页列表
     */
    List<Standard> getStandardsPaging(int pageIndex, int pageSize, String strQuery, Integer system);
}
