import boot.SpringBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author YangWenjun
 * @date 2019/11/18 16:58
 * @project MockFramework
 * @title: ContextTest
 * @description:  测试上下文  context  -- 参考流程控制
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class ContextTest {

    @Autowired
    private Environment environment ;
    @Test
    public void testProperty(){
        String  str = environment.getProperty("a.b.c");
        System.out.println(str);
    }

    @Value("${a.b.c}")
    private String abc ;
    @Test
    public void testProperty2(){
        System.out.println(abc);
    }

    //3.onfigurationProperties

}
