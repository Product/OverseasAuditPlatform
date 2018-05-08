package com.tonik.service;

import java.util.List;

import com.tonik.dao.IInfoDiffusionDAO;
import com.tonik.model.InfoDiffusion;
/**
 * Desc: this is search info about InfoDiffusion
 * @author fuzhi
 * @spring.bean id="InfoDiffusionService"
 * @spring.property name="infoDiffusionDAOHibernate" ref="InfoDiffusionDAOHibernate"
 */
public class InfoDiffusionService
{
    IInfoDiffusionDAO infoDiffusionDAOHibernate;

    public IInfoDiffusionDAO getInfoDiffusionDAOHibernate()
    {
        return infoDiffusionDAOHibernate;
    }

    public void setInfoDiffusionDAOHibernate(IInfoDiffusionDAO infoDiffusionDAOHibernate)
    {
        this.infoDiffusionDAOHibernate = infoDiffusionDAOHibernate;
    }
    public List<InfoDiffusion> getInfoDiffusion(int pageIndex, int pageSize)
    {
        // TODO Auto-generated method stub
        return infoDiffusionDAOHibernate.getInfoDiffusion(pageIndex, pageSize);

    }
    public int getInfoDiffusionTotal()
    {
        return infoDiffusionDAOHibernate.getInfoDiffusionTotal();

    }
    public InfoDiffusion getInfoDiffusionById(int id)
    {
        return infoDiffusionDAOHibernate.getInfoDiffusionById(id);

    }
    public List<InfoDiffusion> getInfoDiffusionByKey(String key, int pageIndex, int pageSize)
    {
        return infoDiffusionDAOHibernate.getInfoDiffusionByKey(key, pageIndex, pageSize);
    }
    public int getInfoDiffusionTotalByKey(String key)
    {
        return infoDiffusionDAOHibernate.getInfoDiffusionTotalByKey(key);

    }
    public void saveInfoDiffusion(InfoDiffusion infoDiffusion)
    {
        infoDiffusionDAOHibernate.saveInfoDiffusion(infoDiffusion);
    }
}
