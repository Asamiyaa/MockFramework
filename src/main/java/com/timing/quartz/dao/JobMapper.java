package com.timing.quartz.dao;

import com.timing.quartz.entity.Job;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Job record);

    Job selectByPrimaryKey(Integer id);

    List<Job> selectAll();

    int updateByPrimaryKey(Job record);
}