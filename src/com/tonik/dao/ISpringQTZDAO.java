package com.tonik.dao;

import java.util.List;

import com.tonik.model.Product;
import com.tonik.model.SpringQTZ;

public interface ISpringQTZDAO extends IDAO
{
    public List<SpringQTZ> getSpringQZTById(Long id);
    public boolean updateSpringQTZbyId(Long id, int status, String run_time);
}
