package com.pattern.k;

import boot.SpringBootApplication;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.bean.dataObj.RuleDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.dao.RuleDoMapper;
import com.msg.Amq.AmqSender;
import com.pattern.k.execute.IDelegateOrderCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * 事务选择
 * 事务补偿验证
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class Transactions {

        @Autowired
        private RuleDoMapper ruleDoMapper;
        @Autowired
        private DraftDoMapper draftDoMapper;
        @Autowired
        private IDelegateOrderCenter delegateOrderCenter;

    /**
     * 事务选择
     * @throws Exception
     */
    @Test
        @Transactional
        public void test() throws Exception {
            RuleDo ruleDo = new RuleDo();
            ruleDo.setDraftno("12345");
            ruleDo.setProperty("12345");
            ruleDo.setIsregex(1);
            ruleDo.setIsjsengine(1);
            ruleDo.setIsempty(1);
            ruleDo.setLength(1);
            ruleDo.setType("123456");
            DraftDo draftDo = new DraftDo();
            draftDo.setDrafttemplate("121212".getBytes());
            draftDo.setDraftdescribe("1221");
            draftDo.setDraftno("12345");

            ruleDoMapper.insert(ruleDo);
            System.out.println("ruleDo inserted");
            if(1==1) {
                throw new Exception("eeee");
            }
            draftDoMapper.insert(draftDo);
            System.out.println("draftDo inserted");
        }

    /***
     * 验证补偿
     * @throws Exception
     */
    @Test
    @Transactional
    public void test2() throws Exception {

        DraftDo draftDo = new DraftDo();
        draftDo.setDrafttemplate("121212".getBytes());
        draftDo.setDraftdescribe("1221");
        draftDo.setDraftno("12345");

        draftDoMapper.insert(draftDo);
        System.out.println("drfatDo inserted");
        delegateOrderCenter.notifyOrder(draftDo);
    }


}
