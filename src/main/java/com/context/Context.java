package com.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author YangWenjun
 * @date 2019/11/1 14:40
 * @project MockFramework
 * @title: Context
 * @description:   注意配合spring作为总的线，作为一站式信息流转 。。。 关联上传的信息，进行反显，避免输入错误
 *                  placeHolder
 *                  JBPM源码对流程影响，其实所有的不都是为了流程控制么
 *
 *                  提供统一的异常处理、返回  basexxx / abstractxxx
 */
public class Context implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * 从上下文信息中关联、绑定、页面反显
     */
    public void  bindContext(){

    }
}
