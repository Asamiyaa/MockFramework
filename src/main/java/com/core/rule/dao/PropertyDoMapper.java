package com.core.rule.dao;

import com.core.rule.bean.dataObj.PropertyDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PropertyDo record);

    PropertyDo selectByPrimaryKey(Long id);

    List<PropertyDo> selectAll();

    int updateByPrimaryKey(PropertyDo record);
}