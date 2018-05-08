package com.tonik.dao.hibernate;

import java.util.List;

import com.tonik.model.RelProductTypeStyle;

public interface IRelProductTypeStyleDAO
{

    List<Object[]> getRelProductTypeStylePaging(int pageIndex, int pageSize, Long productTypeId);

    Integer getRelProductTypeStyleTotal(Long productTypeId);

    void saveRelProductTypeStyle(RelProductTypeStyle relProductTypeStyle);

    void removeRelProductTypeStyle(Long id);

}
