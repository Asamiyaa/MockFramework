package com.core.rule.impl;

import com.core.rule.IParamRule;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.bean.dataObj.RuleDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.dao.RuleDoMapper;
import com.register.dao.PropertyDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author YangWenjun
 * @date 2019/11/12 10:53
 * @project MockFramework
 * @title: ParamRuleImpl
 * @description:
 */
@Service
public class ParamRuleImpl implements IParamRule {

    @Autowired
    private DraftDoMapper draftDoMapper;
    //@Autowired
    private PropertyDoMapper propertyDoMapper;
    @Autowired
    private RuleDoMapper ruleDoMapper;

    @Override
    public Integer updateParamRule(CombinedRuler combinedRuler) {
        //TODO:数据库关联设计 -- 完成  ByPrimaryKey 主键的是否需要外界传入
        String draftNo = combinedRuler.getDraftNo();

        //TODO:二者区别
        //draftDoMapper.updateByPrimaryKey(new DraftDo(draftNo, combinedRuler.getDraftDescribe()));

        //将这段逻辑放到draft module
        //draftDoMapper.insert(new DraftDo(draftNo, combinedRuler.getDraftDescribe()));

        Set<Map.Entry<String, List<String>>> entries = combinedRuler.getPropertyRule().entrySet();
        //TODO: 对 map .entry 遍历  iterator遍历好于for?

        /*for (int i = 0; i < entries.size(); i++) {

            //propertyDoMapper.updateByPrimaryKey(new PropertyDo(entries[i].get));
        }*/

        //TODO:启线程  -->
        for (Map.Entry < String, List <String>> entry : entries){
//            propertyDoMapper.updateByPrimaryKey(new PropertyDo(draftNo,entry.getKey()));
//            propertyDoMapper.insert(new PropertyDo(draftNo,entry.getKey(),entry.getValue().get(1)));
        }

        for (Map.Entry < String, List <String>> entry : entries){
            //TODO:可能时string / int 如何写 如何传递最优
//            List<String> values = entry.getValue();
            List<String> values = entry.getValue();
            ruleDoMapper.insert(new RuleDo(draftNo,values.get(0),Integer.parseInt(values.get(2)),
                    Integer.parseInt(values.get(3)),
                    Integer.parseInt(values.get(4)),
                    Integer.parseInt(values.get(5)),
                    values.get(6)));
        }

        return null;
    }

    @Override
    public CombinedRuler getParamRuleList(String draftNo) {

        return null;
    }

    /**
     *
     *
     * 事务：
     *      0.演变过程：事务就是将多个update（统一数据源的）操作放到一个try...catch...中，统一最后提交(***注意是最后一起提交不是先执行提交在回退)
     *                            对于调用外部系统，....等持久化操作是要try...catch来完成或者说手动干预回滚- 补偿。----分布式事务*** ---对异常的处理传递
     *
     *
     *      1.transactional是spring对数据库事务的封装，底层依赖各个数据的事务支持  -- 锁
     *           四大特性- 事务管理器、隔离级别、传播行为、超时、异常、只读   https://juejin.im/post/5b00c52ef265da0b95276091
     *
     *      2.事务不生效原因
     *          解决Transactional注解不回滚
     *               0、事务管理器  springboot使用mybatis事务失效（多数据源原因）：https://blog.csdn.net/amaxiaochen/article/details/95073466
                     1、检查你方法是不是public的   https://blog.csdn.net/Darrensty/article/details/80285301 使用apectj代替默认的aop实现非public代理
                     2、你的异常类型是不是unchecked异常
                     3、数据库引擎要支持事务，如果是MySQL，注意表要使用支持事务的引擎，比如innodb，如果是myisam，事务是不起作用的
                     4、是否开启了对注解的解析
                     5、spring是否扫描到你这个包，如下是扫描到org.test下面的包
                     6、检查是不是同一个类中的方法调用（如a方法调用同一个类中的b方法）  --- 原理是：动态代理 类级别的
                     7、异常是不是被你catch住了
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */









    @Transactional  //不提交，将统一的commit放到最后，所有的
    public void doTransational(){

        DraftDo draftDo = new DraftDo("789","789","789".getBytes());
        System.out.println("----");
        draftDoMapper.insert(draftDo);

        System.out.println("----");
        RuleDo ruleDo = new RuleDo("789","789",789,789,789,789,"789");
        System.out.println("----");
        ruleDoMapper = null;//抛出异常看是否生效
        ruleDoMapper.insert(ruleDo);
    }












}
