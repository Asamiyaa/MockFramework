package com.test;

import boot.SpringBootApplication;
import com.core.draft.DraftManager;
import com.core.rule.RuleManager;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.impl.ParamRuleCheckImpl;
import com.exception.DraftBindException;
import com.exception.ServiceCheckException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @author YangWenjun
 * @date 2019/11/14 15:07
 * @project MockFramework
 * @title: TestRuler
 * @description:
 *
 *      -- 缜密性 --- 落地性 ---
 *      volidate 校验规则 -- hibernate=spring - 切面 - 校验框架
1.为什么写单元测试
1.单元测试能有效地帮你发现代码中的 bug
2.写单元测试能帮你发现代码设计上的问题 -可测试性
如果很难为其编写单元测试，或者单元测试写起来很吃力，需要依靠单元测试框架里很高级的特性才
能完成，那往往就意味着代码设计得不够合理，比如，没有使用依赖注入、大量使用静态函
数、全局变量、代码高度耦合等。
3.单元测试是对集成测试的有力补充
一些边界条件、异常情况下，比如，除数未判空、网络超时。
而大部分异常情况都比较难在测试环境中模拟。而单元测试可以利用下一节课中讲到的
mock 的方式，控制 mock 的对象返回我们需要模拟的异常
对于复杂系统，复杂的交易场景，交易模块组合，-- 影响性范围分析
4.写单元测试的过程本身就是代码重构的过程

TODO:问题：1.我为什么思考没那么全面？所有可能 - 数据库字段/类字段 场景 合理不是过度 这些是  技术套路 + 业务套路
例子：
为了保证测试的全面性，针对 toNumber() 函数，我们需要设计下面这样几个测试用例。
如果字符串只包含数字：“123”，toNumber() 函数输出对应的整数：123。
如果字符串是空或者 null，toNumber() 函数返回：null。
如果字符串包含首尾空格：“ 123”，“123 ”，“ 123 ”，toNumber() 返回对应的
整数：123。
如果字符串包含多个首尾空格：“ 123 ”，toNumber() 返回对应的整数：123；
如果字符串包含非数字字符：“123a4”，“123 4”，toNumber() 返回 null；
TODO:2.哪些需要单元测试，不代表所有的都取测试。高效，手术刀
TODO:3.测试环境搭建  mock -解依赖
如果代码中依赖了外部系统或者不可控组件，比如，需
要依赖数据库、网络通信、文件系统等，那我们就需要将被测代码与外部系统解依赖，而这
种解依赖的方法就叫作“mock”
1.写新测试类实现一样接口，并在方法中return想要的数据。-- 真实代码通过注入对象而不是new来灵活修改测试对象
2.当依赖的对象不是自己维护，无条件修改时，可以在真实实现基础上，包装一层，返回自己需要的
3.包含跟“时间”有关的“未决行为”逻辑。一般的处理方式是将这种未决行为逻辑重新封装。--重构了代码保证可测试性
protected boolean isExpired() {
long executionInvokedTimestamp = System.currentTimestamp();
return executionInvokedTimestamp - createdTimestamp > 14days;

测试困难点：1.未决条件  2.成员变量  3.静态变量  4.继承

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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class TestMain {

    /**
     * 用于不再上下文环境中执行   比如util .. 单独模块...
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //注入方式
        /*RuleManager ruleManager = new RuleManager();
        ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));*/
        System.out.println("<student><name></name><age></age><className></className></student>".indexOf("</"));

        System.out.println("----");
        String ss = ("<student><name></name><age></age><className></className></student>"
                //.replaceAll("</.*>", "")));贪婪  没有// 这是在js中
                .replaceAll("</.*?>", ""));
        System.out.println(ss);
        String[] sss = ss.substring(1, ss.length() - 1).split("><");
        for (String s1 : sss) {
            System.out.println(s1);
        }
        System.out.println("----");

        Student student = Student.createStudent("yangwenjun", 27, "普通班");//mock 前台输入封装

        //RuleManager ruleManager = new RuleManager();
        //路径如何弄
        //  ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));

    }


/**
 * 引入dao之后会有spring注入信息，所以这里引入junit测试   问题:这样的话，非测试启动也是需要spring ，
 * 那么manager也必须进入spring 容器吗?
 *//*



     */
    /**
     * 先不做组合测试，单个单个来
     */



    @Autowired
    private DraftDoMapper draftDoMapper ;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TestMain.class);

    @Test
    public void testDaoMapper() throws IOException {
        DraftDo draftDo = new DraftDo("test1", "测试draftDao1","".getBytes());
        draftDoMapper.insert(draftDo);
        log.debug("---测试数据传入---");
        log.error("---测试数据传入---");
    }

    @Autowired
    private RuleManager ruleManager;
    @Test
    public void testPersist() throws IOException {
        ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));
        //是因为嵌套注入没有注入造成的吗？
    }


    @Test
    public  void testDraftXml() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/main/resources/studentDraft.xml")));
        String str = null ;
        StringBuffer buf = new StringBuffer();
        while((str  = bufferedReader.readLine())!= null){
//            if(str.equals("\n\r") || str.equals("\t"))  continue; --改为最终处理
            buf.append(str);
        }
        System.out.println("--读取完成--");
        System.out.println(buf.toString().replaceAll("\\s", ""));
        //<student><name></name><age></age><className></className></student>
//        System.out.println(System.getProperty("line.separator"));
    }
    @Autowired
    private DraftManager draftManager;

    @Test
    public  void testDraftXml2() throws IOException {
        draftManager.parseAndPersist(new File("src/main/resources/studentDraft.xml"));
    }
    @Test
    public void testDoSelf() throws UnsupportedEncodingException {
        System.out.println(new String(draftDoMapper.selectByDraftNo("student").getDrafttemplate(),"utf8"));
    }
    /*@Autowired
    private RuleManager ruleManager;*/ //可以没有setter
    @Autowired
    private ParamRuleCheckImpl paramRuleCheck;
    @Test
    public  void testBind() throws IOException, IllegalAccessException, ServiceCheckException, DraftBindException {

        Student stu = Student.createStudent("yangwenjun",27,"好的班");

        //TODO：ruleDoMapper 空指针 - 因为没有在类上@Service吗?  不是 - 单元测试不可以做嵌套吗? - 特别是new
        //System.out.println(draftManager.bind("student", stu));
        //<student><name>yangwenjun</name><age>27</age><className>好的班</className>

        System.out.println(paramRuleCheck.check("student", stu).getResult().getCode());

        /**
         * 验证通过，但validator的逻辑正确性没有验证到 - debug 没有生成链
         *
         */
        //Assert.assertArrayEquals(); junit中断言
    }










}
class Student{
    private String name ;
    private int age;
    private String className;

    public Student(String name, int age, String className) {
        this.name = name;
        this.age = age;
        this.className = className;
    }

    public static Student createStudent(String yangwenjun, int i, String str) {
        return null;
    }
}

