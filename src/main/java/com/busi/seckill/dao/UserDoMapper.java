
package com.busi.seckill.dao;

import com.busi.seckill.dataObj.UserDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDoMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(UserDo record);

    int insertSelective(UserDo record);

    UserDo selectByPrimaryKey(Integer id);


    List<UserDo> selectAll();

    int updateByPrimaryKey(UserDo record);
}
