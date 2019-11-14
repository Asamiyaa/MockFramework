import com.core.rule.RuleManager;

import java.io.File;
import java.io.IOException;

/**
 * @author YangWenjun
 * @date 2019/11/14 15:07
 * @project MockFramework
 * @title: TestRuler
 * @description:
 */
public class TestRuler {

    public static void main(String[] args) throws IOException {

        Student student = new Student("yangwenjun", 27, "普通班");//mock 前台输入封装

        RuleManager ruleManager = new RuleManager();
        //路径如何弄
        ruleManager.parseAndPersist(new File("src/main/resources/studentRule"));

    }
}
