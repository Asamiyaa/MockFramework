import org.springframework.stereotype.Service;

/**
 * @author YangWenjun
 * @date 2019/7/29 14:19
 * @project hook
 * @title: springFathrer
 * @description:
 */

@Service
public class TestExtends {

    private String  name  = "xx";

    static{
        System.out.println("static");
    }

    {
        System.out.println("structor kuai");
    }

    public TestExtends() {
        super();
        System.out.println("springFathrer");
    }

    public  void  abc(){
        System.out.println("abc");
    }
}
