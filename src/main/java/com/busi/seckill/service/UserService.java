package com.busi.seckill.service;

import com.busi.seckill.error.BusiException;
import com.busi.seckill.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusiException;
}
