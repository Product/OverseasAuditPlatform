package com.tonik.service;



import java.util.List;

import com.tonik.dao.IProductDAO;
import com.tonik.dao.ISpringQTZDAO;
import com.tonik.model.SpringQTZ;


/**
 * @spring.bean id="SpringQTZService"
 * @spring.property name="springQTZDAO" ref="SpringQTZDAO"

 */

public class SpringQTZService {

    private ISpringQTZDAO springQTZDAO;
    
    
    public ISpringQTZDAO getSpringQTZDAO()
    {
        return springQTZDAO;
    }

    public void setSpringQTZDAO(ISpringQTZDAO springQTZDAO)
    {
        this.springQTZDAO = springQTZDAO;
    }

    public List<SpringQTZ> getSpringQZTById(Long id)
    {
        return springQTZDAO.getSpringQZTById(id);
    }
    
    public boolean updateTaskStatus(Long id, int status, String run_time)
    {
        return springQTZDAO.updateSpringQTZbyId(id, status, run_time);
    }
    
    public boolean updateTaskStatusAndTime(Long id, int status, String run_time)
    {
        return springQTZDAO.updateSpringQTZbyId(id, status, run_time);
    }
    
}

