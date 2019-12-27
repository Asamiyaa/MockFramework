package seckill.service;

import seckill.error.BusiException;
import seckill.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusiException;
}
