package com.tonik.dao;

import java.util.List;

import com.tonik.model.ExpertRepository;

public interface IExpertRepositoryDAO
{

    List<ExpertRepository> initList(int pageIndex, int pageSize);

    int initCount();

    List<Object[]> queryListByKey(int pageIndex, int pageSize, String keyword, Long typeId);

    int queryCountByKey(String keyword, Long typeId);

    void save(ExpertRepository expertRepository);

    void del(ExpertRepository expertRepository);

    ExpertRepository queryById(Long id);

    
}
