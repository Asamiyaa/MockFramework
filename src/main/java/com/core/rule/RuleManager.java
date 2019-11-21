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
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.impl.ParamRuleCheckImpl;
import com.core.rule.impl.ParamRuleImpl;
import com.exception.ServiceCheckException;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*public IParamCheck instance(){
        return new ParamRuleCheckImpl();  //TODO:工厂模式 -- 抽象工厂 ..
    }*/


    /*获得链是对内拆分，没有必要暴露给外模块
    public RulerChain getRulerChainByDraftNo(String draftNo){
        return null ;
    }*/
    public <T> CheckResult check(String draftNo ,T t) throws ServiceCheckException {
        /**
         * 未面向接口编程
         * 引入泛型T
         */
        return new ParamRuleCheckImpl().check( draftNo , t);
    }


    /**
     *  首次加载报文规则进行解析持久化
     * @param file  报文规则excel
     */
    public void parseAndPersist(File file) throws IOException {
        /**
         * 当不对外调用，不作为一个service时，可以new 无需复用
         */

        //前置判断  参数准备
        doParseAndPersist( file);
    }

    private void doParseAndPersist(File file) throws IOException {

        IParamRule  paramRule =  new ParamRuleImpl();  //TODO :spring 获取 vs 工厂  vs  new
        ICache ruleCache = new RuleCache();
        //异常处理优化，是抛出还是捕获..
        CombinedRuler combinedRuler = parse(file);

        System.out.println(combinedRuler);


        //ruleCache.synch(combinedRuler);
        paramRule.updateParamRule(combinedRuler);

    }

    private CombinedRuler parse(File file) throws IOException {

        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        StringBuffer properFile = new StringBuffer();
        //List propertyList = new ArrayList<String>();
        //List ruleForProperty = new ArrayList();
        Map propertyRule = new HashMap<String,List<String>>();  //最终是map<String,List>模式
        Map classNamePropertys = new HashMap<String,Map<String,List>>();

       //解析配置文件
        try (InputStream fileInputStream = new FileInputStream(file)) {

            inputStreamReader =  new InputStreamReader(fileInputStream);
            br = new BufferedReader(inputStreamReader);
            //while(br.readLine() != null){
            String line ;
            while(( line = br.readLine()) != null){
                if(line.startsWith("#")){continue;}
                if(line.isEmpty()){continue;} //空行跳过
                properFile.append(line.trim()); //不可多次读取 即不可重复 - 去掉前后空格
                properFile.append("-");
            }
            System.out.println(properFile.substring(0, properFile.toString().length()-1).toString());

            //统一处理逻辑  TODO:commonUtil 使用 --判空操作  分割 - 集合工具类操作
            String[] properInfo = properFile.substring(0, properFile.toString().length()-1).toString().split("-");
            String className = properInfo[0];
            for (int x = 1; x<properInfo.length; x++){
                String[] properInner = properInfo[x].split(":");
                //for(int y = 0; y<properInner.length; y++){
                    //解析 --没必要，保存数据结构。表结构 map<String,map<String,List>> -- <类名,<属性，规则list>>

                //}
               // propertyRule.put(properInfo[x], Arrays.asList(properInner));
                propertyRule.put(properInner[0], Arrays.asList(properInner));

            }
            //classNamePropertys.put(className, propertyRule);

            //输出中list map ...输出要是分不清
            //CombinedRuler{draftNo='student', draftDescribe='testDraft', propertyRule={1=[1, java.lang.String], name=[name, 0, 99],
            // className=[className, 0, 1, 20, java.lang.Integer], age=[age, 0, 0, 2, java.lang.Integer]}}
            return new CombinedRuler(className, "testDraft", propertyRule);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStreamReader != null){
                inputStreamReader.close();
            }
            if(br != null){
                br.close();
            }
        }
        return null;
    }

    private class RulerChain {

    }
}
