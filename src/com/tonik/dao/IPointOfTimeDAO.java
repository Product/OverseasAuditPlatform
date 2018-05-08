package com.tonik.dao;

import com.tonik.model.PointOfTime;

public interface IPointOfTimeDAO
{
    public PointOfTime getPointOfTime();
    
    public void UpdateTime(PointOfTime newDate);
}
