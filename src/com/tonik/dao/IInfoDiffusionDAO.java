package com.tonik.dao;

import java.util.List;

import com.tonik.model.InfoDiffusion;

public interface IInfoDiffusionDAO
{

    List<InfoDiffusion> getInfoDiffusion(int pageIndex, int pageSize);

    int getInfoDiffusionTotal();

    InfoDiffusion getInfoDiffusionById(int id);

    List<InfoDiffusion> getInfoDiffusionByKey(String key, int pageIndex, int pageSize);

    int getInfoDiffusionTotalByKey(String key);

    void saveInfoDiffusion(InfoDiffusion infoDiffusion);

}
