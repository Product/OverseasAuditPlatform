package com.tonik.dao;

import java.util.List;

import com.tonik.model.CollectorCfg;

public interface ICollectorCfgDAO
{
    public List<CollectorCfg> getListInternal(int pageIndex, int pageSize);

    public int getCountInternal();

    public List<CollectorCfg> getListAbroad(int pageIndex, int pageSize);

    public int getCountAbroad();

    public List<CollectorCfg> getListPublicOpinion(int pageIndex, int pageSize);

    public int getCountPublicOpinion();


    public List<CollectorCfg> getListWebsite(int pageIndex, int pageSize);

    public int getCountWebsite();

    public CollectorCfg queryById(int id);

    public void addOrUpdate(CollectorCfg collectorCfg);

    public void delById(CollectorCfg collectorCfg);

    public List<CollectorCfg> getListExpertRepository(int pageIndex, int pageSize);

    public int getCountExpertRepository();
}
