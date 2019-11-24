package com.busi.seckill.service;

import com.secKill.error.BusiException;
import com.secKill.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusiException;
}
