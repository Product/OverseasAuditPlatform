package com.tonik.dao;

import java.util.List;

import com.tonik.model.Test;

public interface ITestDAO extends IDAO
{
    public List<Test> getTest();

    public Test getTest(Long testId);

    public void saveTest(Test test);

    public void removeTest(Test test);

    public List<Object[]> getTestPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getTestTotal(String strQuery, String strStraTime, String strEndTime);
}
