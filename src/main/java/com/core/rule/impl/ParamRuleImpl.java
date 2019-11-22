package com.core.rule.impl;

import com.core.rule.IParamRule;
import com.core.rule.bean.CombinedRuler;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.bean.dataObj.PropertyDo;
import com.core.rule.bean.dataObj.RuleDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.dao.PropertyDoMapper;
import com.core.rule.dao.RuleDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
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
}
