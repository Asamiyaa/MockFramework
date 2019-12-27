package seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com"},exclude = SecurityAutoConfiguration.class)
@RestController
@MapperScan("com.secKill.dao")
@EnableScheduling
public class boot {

    /*@Autowired
    private UserDoMapper userDoMapper;

    @RequestMapping("/")
    public String home(){
        if(!StringUtils.isEmpty(userDoMapper.selectByPrimaryKey(1))) {
            return "hello hook project";
        }return "不存在用户";
    }*/

    /**
     * ---测试mq---
     * @param args
     *

    @Autowired
    AmqSender client;
    @Autowired
    AmqReceiver reciver;

    @PostConstruct
    public void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            client.send("发送消息----yangwenjunTestMq-----"+i);
        }
        //reciver.receive(//) 直接监听就好，不是调用
        stopWatch.stop();
        System.out.println("发送消息耗时: " + stopWatch.getTotalTimeMillis());
    }
**/
    public static void main(String[] args) {
        System.out.println("abc");
        SpringApplication.run(boot.class,args);

        /*for(;;){
            new Thread(()->{
                new HashMap();
            });
        }*/

    }


}
