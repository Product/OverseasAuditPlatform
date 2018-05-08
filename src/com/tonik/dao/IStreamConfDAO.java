package com.tonik.dao;

import java.util.List;

import com.tonik.model.StreamConf;

public interface IStreamConfDAO
{

    public int Count();

    public List<StreamConf> queryList(int pageIndex, int pageSize);

    public void AddOrUpdate(StreamConf streamConf);

    public StreamConf queryById(Long id);

}
