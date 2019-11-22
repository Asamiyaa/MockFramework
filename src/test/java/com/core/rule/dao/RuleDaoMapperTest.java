package com.core.rule.dao;

import boot.SpringBootApplication;
import com.core.rule.IParamRule;
import com.core.rule.RuleManager;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.bean.dataObj.DraftDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author YangWenjun
 * @date 2019/11/18 11:13
 * @project MockFramework
 * @title: RuleDaoMapperTest
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class RuleDaoMapperTest {

    @Autowired
    DraftDoMapper draftDoMapper ;
    @Test
    public void testDaoMapper() throws IOException {
        DraftDo draftDo = new DraftDo("test2", "测试draftDao2","".getBytes());
        draftDoMapper.insert(draftDo);
    }

    /**
     * 进行复合操作
       错误： 只能在junit这里注入才生效，所以下面的‘希望通过junit入口将已有代码中的autowired生效，是不行的’

     so  我手动注入 可以吗？   -- 注入是不生效的。

     TODO:是否需要test 套件？
     */
    //@Autowired
    //IParamRule paramRule ;

    @Autowired
    IParamRule paramRule1 ;
    @Autowired
    private DraftDoMapper draftDoMapper1;
    @Autowired
    private PropertyDoMapper propertyDoMapper;
    @Autowired
    private RuleDoMapper ruleDoMapper;
    @Test
    public void testPersist() throws IOException {
        RuleManager ruleManager = new RuleManager();
        ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));
    }

    /**
     * 直接注入servcie，servcie中的dao也会注入
     */
    @Autowired
    IParamRule paramRule ;
    @Test
    public void testPersist2() throws IOException {
        paramRule.updateParamRule(new CombinedRuler("test","test",new HashMap<>()));
    }



}
