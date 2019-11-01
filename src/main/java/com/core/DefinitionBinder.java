package com.core;

import com.core.DtoGenerate.DtoGenerator;

/**
 * @author YangWenjun
 * @date 2019/11/1 10:55
 * @project MockFramework
 * @title: DifinitionBinder
 * @description:将xml和进行绑定
 *
 * 区分开spring定义的解析器，他的哪种解析器是“ 提前固定好解析规则 ”
 */
public class DefinitionBinder {


    public void marshal(){
        /**
         * 将从页面来的值和xml标签匹配赋值
         */
       new DtoGenerator().createDto();
    }

    public void unmarshal(){
        /**
         * 将从报文来的值赋值到对象
         */

    }




}
