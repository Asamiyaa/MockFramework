package com.core.rule.dao;

import com.core.rule.bean.dataObj.DraftDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DraftDoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DraftDo record);

    DraftDo selectByPrimaryKey(Long id);

    List<DraftDo> selectAll();

    int updateByPrimaryKey(DraftDo record);
}