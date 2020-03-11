package com.register.dao;

import com.register.bean.dataObj.PropertyDo;
import java.util.List;


public interface PropertyDoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PropertyDo record);

    PropertyDo selectByPrimaryKey(Long id);

    List<PropertyDo> selectAll();

    int updateByPrimaryKey(PropertyDo record);
}
