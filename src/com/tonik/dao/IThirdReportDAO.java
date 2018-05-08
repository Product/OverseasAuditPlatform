package com.tonik.dao;

import java.util.List;

import com.tonik.model.ThirdReport;

public interface IThirdReportDAO
{

    public List<ThirdReport> getList(int pageIndex, int pageSize);

    public int getCount();

    public void save(ThirdReport thirdReport);

    public List<ThirdReport> getListAbroad(int pageIndex, int pageSize);

    public int getCountAbroad();

    public ThirdReport queryById(Long id);

}
