
import boot.SpringBootApplication;
import com.core.rule.RuleManager;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.dao.PropertyDoMapper;
import com.core.rule.dao.RuleDoMapper;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;


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
public class TestRuler {

    /**
     * 用于不再上下文环境中执行   比如util .. 单独模块...
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Student student = new Student("yangwenjun", 27, "普通班");//mock 前台输入封装

        RuleManager ruleManager = new RuleManager();
        //路径如何弄
           ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));

    }


/**
     * 引入dao之后会有spring注入信息，所以这里引入junit测试   问题:这样的话，非测试启动也是需要spring ，
     * 那么manager也必须进入spring 容器吗?
     *//*


    @Test
    public void testPersist() throws IOException {
        */
/*RuleManager ruleManager = new RuleManager();
        //路径如何弄
        ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));*//*

    }
    */
/**
     * 先不做组合测试，单个单个来
     */


    @Autowired
    DraftDoMapper draftDoMapper ;
    @Test
    public void testDaoMapper() throws IOException {
        DraftDo draftDo = new DraftDo("test", "测试draftDao");
        draftDoMapper.insert(draftDo);
    }


}

