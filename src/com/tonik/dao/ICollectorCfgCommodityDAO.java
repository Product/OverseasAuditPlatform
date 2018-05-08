package com.tonik.dao;

import java.util.List;

import com.tonik.model.CollectorCfgCommodity;

public interface ICollectorCfgCommodityDAO
{
    int findCount();
    CollectorCfgCommodity findById(Long id);
    List<Object[]> findList(int pageIndex, int pageSize);
    void addOrUpdate(CollectorCfgCommodity collectorCfgCommodity);
    void delete(CollectorCfgCommodity collectorCfgCommodity);
}
