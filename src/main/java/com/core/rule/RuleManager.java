package com.core.rule;

/**
 * @author YangWenjun
 * @date 2019/11/6 15:02
 * @project MockFramework
 * @title: RuleHandler
 * @description:     过滤器模式对 当前页面对象过滤
 *                    参考：Ruler    从报文页面配置(是否超时...) 父类/抽象类 --> 页面填入参数 进行判断
 */

import com.core.Cache.ICache;
import com.core.Cache.impl.RuleCache;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.impl.ParamCheckImpl;
import com.core.rule.impl.ParamRuleImpl;

import java.io.File;
import java.io.InputStream;

/**
 * 参考：一个轻量级的参数校验框架  https://juejin.im/post/5a28942cf265da431c70302c  非常优秀 结合了许多知识点 抽象层次也非常高
 *
 *
 * 统一入口  所以这里提供获得对应对象的(这里service...)方法 。。框架级 。。因为不是业务级的所以没有必要使用‘ 注入 ’
 *
 *
 */
//public class RuleHandler {   --> 一个框架中组件 。这里使用manager 因为作为获取对应service实现【checK-assemb】
//    以及保存/更新rule

    //对外暴露 firstLoad  以及check调用

public class RuleManager {

    public IParamCheck instance(){
        return new ParamCheckImpl();  //TODO:工厂模式 -- 抽象工厂 ..
    }

    /**
     *  首次加载报文规则进行解析持久化
     * @param file  报文规则excel
     */
    public void parseAndPersist(File file){
        /**
         * 当不对外调用，不作为一个service时，可以new 无需复用
         */

        //前置判断  参数准备
        doParseAndPersist( file);
    }

    private void doParseAndPersist(File file) {

        IParamRule  paramRule =  new ParamRuleImpl();  //TODO :spring 获取 vs 工厂  vs  new
        ICache ruleCache = new RuleCache();

        CombinedRuler combinedRuler = parse(file);

        paramRule.updateParamRule(combinedRuler);

        ruleCache.synch(combinedRuler);

    }

    private CombinedRuler parse(File file) {
        return null;
    }

}
