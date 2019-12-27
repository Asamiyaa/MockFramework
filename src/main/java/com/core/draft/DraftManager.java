package com.core.draft;

import com.core.constant.BooleanEnum;
import com.core.rule.RuleManager;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import com.exception.DraftBindException;
import com.exception.ServiceCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/11/12 11:52
 * @project MockFramework
 * @title: DraftManager
 * @description: 区分开spring定义的解析器，他的哪种解析器是“ 提前固定好解析规则 ”
 */
//@Service

/**
 * 20191227 启动报错 ，先注掉，查看起到是否成功 。 门面模式的初始化问题
 */
public class DraftManager {

    @Autowired
    private DraftDoMapper draftDoMapper ;
    @Autowired
    private RuleManager ruleManager;

    public void parseAndPersist(File file){

        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String str = null ;
            StringBuffer buf = new StringBuffer();
            while((str  = bufferedReader.readLine())!= null){
                buf.append(str);
            }

            draftDoMapper.insert(new DraftDo("student","this is student info"
                        ,buf.toString().replaceAll("\\s", "")
                        .getBytes("GBK")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 根据报文标识号获取报文模板并绑定
     * @param draftNo
     * @param t
     * @param <T>
     */

    public <T> String bind(String draftNo , T t) throws DraftBindException, UnsupportedEncodingException, ServiceCheckException, IllegalAccessException {

        //通过ruleChain
        //Manager到底以spring方式还是new方式 - 暂时注释掉  - 为了单元测试可以注掉一些边缘，记得这是单元测试 - debug
        if(ruleManager.check(draftNo, t).getResult().getCode() == BooleanEnum.FALSE.getCode()){
            throw new ServiceCheckException("rule校验未通过,请核对数据");
        };

        DraftDo draftDo = draftDoMapper.selectByDraftNo(draftNo);
        if(draftDo.getDrafttemplate() == null){
            throw new DraftBindException(draftNo + "对应的报文模板未上传,请上传后重新绑定");
        }
        String draftTemplate = new String(draftDo.getDrafttemplate(), "gbk");

        //<student><name></name><age></age><className></className></student>
        //是选择xml绑定技术好还是xmlstring解析拼装？ --> 先解析,业务场景上来后就考虑现有的绑定技术
        Map<String, Object> nameValuePair = new HashMap<>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field property : fields) {
            property.setAccessible(true);
            Object o = property.get(t);
            nameValuePair.put(property.getName(), o);
        }
        //得到所有的<xx标签 - 轮询 - 填入 - 添加后置标签
        /*int index = -1;
        while((index = draftTemplate.indexOf("</")) != -1) {
            //draftTemplate.substring(0, )
            draftTemplate.replaceAll(, )
        }*/
        //直接全去掉  -- TestRulerDraft
        String prexPoint = draftTemplate.replaceAll("</.*?>", "");
        //查看规律 思考解决方案
        String str = prexPoint.substring(1, prexPoint.length() - 1);
        String[] names = str.split("><");
        /*for (String name : names) {
            //过滤到第一个
        }*/
        StringBuffer buf = new StringBuffer(names[0]);
        for (int i = 1; i < names.length; i++) {
            Object value = nameValuePair.get(names[i]);
            buf.append("><").append(names[i]).append(">").append(value.toString())
                    .append("</").append(names[i]);
        }
        buf.insert(0, "<").insert(buf.length(), ">");

        return buf.toString();
    }

}
