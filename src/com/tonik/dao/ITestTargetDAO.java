package com.tonik.dao;

import java.util.List;

import com.tonik.model.TestTarget;

public interface ITestTargetDAO extends IDAO
{
    public List<TestTarget> getTestTargets();

    public TestTarget getTestTarget(Long testTargetId);

    public void saveTestTarget(TestTarget testTarget);

    public void removeTestTarget(TestTarget testTarget);

    public List<TestTarget> getTestTargetPaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getTestTargetTotal(String strQuery, String strStraTime, String strEndTime);

    List<Object[]> getTestTargetByTest(Long id);
}
