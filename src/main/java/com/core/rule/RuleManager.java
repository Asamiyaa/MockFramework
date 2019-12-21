package com.core.rule;

/**
 * @author YangWenjun
 * @date 2019/11/6 15:02
 * @project MockFramework
 * @title: RuleHandler
 * @description:     过滤器模式对 当前页面对象过滤
 *                    参考：Ruler    从报文页面配置(是否超时...) 父类/抽象类 --> 页面填入参数 进行判断
 *
 *
 *      tips:
            1. return (a == b) || (a != null && a.equals(b));  vs flag  vs 本身是同一个对象就无需进行其他浪费操作
            2. if(a == b)
                return true;
                else if(a == null || b == null)
                return false;
                else
                return Arrays.deepEquals0(a, b);
                null 判断  - 通过if判断将“筛选条件过滤掉” 所以这里的if..条件不是一个维度的  这里的else if 也可以写成if.表示向下的流
                比较数组长度

            3.T[] copy = ((Object) newType == (Object) Object[].class)
                    ? (T[]) new Object[newLength]
                    : (T[]) Array.newInstance(newType.getComponentType(), newLength);
                    System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));

            4.map 遍历  map.entrySet
            5.super(h) 在构造中调用父类..
            6. static final char[] digits = {'0', '1', '2', '3'}
            7.受检异常:来自api 或者 自定义try{}catch()..throw出来，表示这段逻辑本身

                根据自己逻辑，抛出异常；并不完全依赖于调用第三方别人定义的异常，也可以根据自己逻辑进行抛出
                尽可能先使用已定义异常(统一性)，再去定义自己的异常

            8.使用中断使代码流程更加明确，明确else场景下程序的处理逻辑
            9.精度问题 int 转float 精度丢失   转double 不会  科学计数法处理 bd.setScale(2, BigDecimal.ROUND_HALF_UP
            10.当想要增加功能 ， 增加接口 ， 对应的实现去实现该接口
            11.强制类型转换 instanceof  ....  / Class判断
            12.Assert.notNull(code, "Code must not be null"); https://uule.iteye.com/blog/898842
 */

import com.core.Cache.ICache;
import com.core.Cache.impl.RuleCache;
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.impl.ParamRuleCheckImpl;
import com.core.rule.impl.ParamRuleImpl;
import com.exception.ServiceCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
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

    @Autowired
    private ICache cache ;
    private void doParseAndPersist(File file) throws IOException {

        IParamRule  paramRule =  new ParamRuleImpl();  //TODO :spring 获取 vs 工厂  vs  new
        //ICache ruleCache = new RuleCache();
        //异常处理优化，是抛出还是捕获..
        CombinedRuler combinedRuler = parse(file);

        System.out.println(combinedRuler);


        cache.synch(combinedRuler);
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
