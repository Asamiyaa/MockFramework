package com.busi.seckill.service.impl;

import com.busi.seckill.dao.UserDoMapper;
import com.busi.seckill.dao.UserPasswordDoMapper;
import com.busi.seckill.dataObj.UserDo;
import com.busi.seckill.dataObj.UserPasswordDo;
import com.busi.seckill.error.BusiException;
import com.busi.seckill.error.EmBusiError;
import com.busi.seckill.service.UserService;
import com.busi.seckill.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDoMapper userDoMapper;
    @Autowired
    private UserPasswordDoMapper userPasswordDoMapper;
    /***
     * 思考1：为什么不直接将dao层的UserDao返回给前台？
     *        service层必须要有 Model 模型概念，避免字段透传 ，新建Model目录，并常见UserModel类,并修改void返回为UserModel
     * @param id
     */
    @Override
    public UserModel getUserById(Integer id) {
        //获取userDo和userPasswordDo对象 ，并转换为userModel
        UserDo userDo  = userDoMapper.selectByPrimaryKey(id);
        //UserPasswordDo userPasswordDo = userPasswordDoMapper.selectByPrimaryKey(id);
        //改造userPasswordDoMapper及对应的xml实现类，通过userId获取密码
        /**
         * 思考2：使用哪种判空？ == null vs  != null
         *        获得的结果对是否具有‘特殊性’没有影响的，决定在‘后面如何使用，比如.get...’
         *        这里如果userDo == null 其实不仅userDo.getId（）不可执行，结果已经定了就是null,所以返回
         *        和下面的对比：
         *               if(userPasswordDo != null) {
         userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
         }
         */
        if(userDo == null){
            return null ;
        }
        UserPasswordDo userPasswordDo = userPasswordDoMapper.selectByUserId(userDo.getId());
        return convertFromDataObj(userDo,userPasswordDo);
    }

    /**
     * @param userModel
     * @throws BusiException\
     *
     * xml配置
     *
                <!-- 声明事务管理器 -->
                <bean id="txManager"
                class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                <property name="dataSource" ref="dataSource" />
                </bean>
                <!-- 需要实施事务增强的目标业务Bean -->
                <bean id="libraryTarget" class="com.mucfc.dao.LibraryDaoImpl"
                p:jdbcTemplate-ref="jdbcTemplate" />

                <!-- 使用事务代理工厂类为目标业务Bean提供事务增强 -->
                <bean id="libraryFactory"
                class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean"
                p:transactionManager-ref="txManager" p:target-ref="libraryTarget">
                <!-- 事务属性配置 -->
                <property name="transactionAttributes">
                <props>
                <!-- 以get开头的方法采用只读型事务控制类型 -->
                <prop key="get*">PROPAGATION_REQUIRED,readOnly</prop>
                <!-- 所有方法均进行事务控制，如果当前没有事务，则新建一个事务 -->
                <prop key="addBook">PROPAGATION_REQUIRED</prop>
                </props>
                </property>

                </bean>

     *
     */

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusiException {
        if(userModel == null){
            throw new BusiException(EmBusiError.PARAMETER_VALIDATE_ERROR);
        }
        //已正常向下所有点都执行为基线，不符合的则提前判断  - 校验一层一层向下
        if(StringUtils.isEmpty(userModel.getName())||
                userModel.getGender() == null ||
                userModel.getAge() == null    ||
                StringUtils.isEmpty(userModel.getEncrptPassword())||
                StringUtils.isEmpty(userModel.getRegMode())||
                StringUtils.isEmpty(userModel.getTelphone())
                ){
            throw new BusiException(EmBusiError.PARAMETER_VALIDATE_ERROR);
        }
        UserDo userDo = convertFromUserModel(userModel);
        //userDoMapper.insert(userDo);
        userDoMapper.insertSelective(userDo);

        //此时的userDo已经有了id值
        userModel.setId(userDo.getId());

        UserPasswordDo userPasswordDo = convertPasswordFromUserModel(userModel);
        //userPasswordDoMapper.insert(userPasswordDo);
        userPasswordDoMapper.insertSelective(userPasswordDo);
    }

    private UserPasswordDo  convertPasswordFromUserModel(UserModel userModel) {
        UserPasswordDo  userPasswordDo = new UserPasswordDo();
        //BeanUtils.copyProperties(usermodel);
        userPasswordDo.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDo.setUserId(userModel.getId());
        return userPasswordDo;
    }

    private UserDo convertFromUserModel(UserModel userModel) {
        UserDo userDo  =  new UserDo();
        BeanUtils.copyProperties(userModel,userDo);
        return userDo ;
    }

    private UserModel convertFromDataObj(UserDo userDo, UserPasswordDo userPasswordDo) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDo,userModel);
        if(userPasswordDo != null) {
            userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
        }
        return userModel ;
    }

}
