package com.tonik.dao;

import java.util.List;

import com.tonik.model.TestStyle;

public interface ITestStyleDAO extends IDAO
{
    public List<TestStyle> getTestStyle();

    public TestStyle getTestStyle(Long testStyleId);

    public void saveTestStyle(TestStyle testStyle);

    public void removeTestStyle(TestStyle testStyle);

    public List<Object[]> getTestStylePaging(int pageIndex, int pageSize, String strQuery, String strStraTime, String strEndTime);

    public int getTestStyleTotal(String strQuery, String strStraTime, String strEndTime);
}
