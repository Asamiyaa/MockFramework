package com.core;

import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/10/30 13:56
 * @project MockFramework
 * @title: ParseDefinitionXml
 * @description: 解析根据规则定义的xml文件  - 是否接入spring bean 模式 识别xml - bind
 */
public class DraftDefinitionParser {


    public Map<Object,Object> parse(){

        //precheck
        //load 从库 /文件
        return doParse();
    }

    /**
     * 从配置中加载码值  --> cache
     */
    public void parseCode(){
        //落库
        //cache
        //TODO:页面如何生成页面关系
    }

    private Map doParse() {

        return null ;
    }

}
