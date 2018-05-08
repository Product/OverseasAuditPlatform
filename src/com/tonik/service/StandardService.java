package com.tonik.service;

import java.util.List;

import com.tonik.dao.IStandardDAO;
import com.tonik.model.Standard;

/**
 * @spring.bean id="StandardService"
 * @spring.property name="standardDAO" ref="StandardDAO"
 */
public class StandardService
{
    private IStandardDAO StandardDAO;


    public IStandardDAO getStandardDAO()
    {
        return StandardDAO;
    }

    public void setStandardDAO(IStandardDAO standardDAO)
    {
        StandardDAO = standardDAO;
    }

    public List<Standard> getStandards()
    {
        return StandardDAO.getStandards();
    }

    public void saveStandard(Standard standard)
    {
        StandardDAO.saveStandard(standard);
    }

    public void removeStandard(Long id)
    {
        StandardDAO.removeStandard(id);
    }

    public Standard getStandardById(Long id)
    {
        return StandardDAO.getStandardById(id);
    }

    public int getStandardsTotal(String strQuery, Integer system)
    {
        return StandardDAO.getStandardsTotal(strQuery, system);
    }

    public List<Standard> getStandardsPaging(int pageIndex, int pageSize, String strQuery, Integer system)
    {
        return StandardDAO.getStandardsPaging(pageIndex, pageSize, strQuery, system);
    }
}
