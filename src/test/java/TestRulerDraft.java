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
import java.util.logging.Logger;


/**
 * @author YangWenjun
 * @date 2019/11/14 15:07
 * @project MockFramework
 * @title: TestRuler
 * @description:
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class TestRulerDraft {

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

        Student student = new Student("yangwenjun", 27, "普通班");//mock 前台输入封装

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
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TestRulerDraft.class);

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

        Student stu = new Student("yangwenjun",27,"好的班");

        //TODO：ruleDoMapper 空指针 - 因为没有在类上@Service吗?  不是 - 单元测试不可以做嵌套吗? - 特别是new
        //System.out.println(draftManager.bind("student", stu));
        //<student><name>yangwenjun</name><age>27</age><className>好的班</className>

        System.out.println(paramRuleCheck.check("student", stu).getResult().getCode());

        /**
         * 验证通过，但validator的逻辑正确性没有验证到 - debug 没有生成链
         */
    }


}

