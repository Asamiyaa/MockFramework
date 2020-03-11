import boot.SpringBootApplication;
import com.core.Cache.impl.RuleCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class TestCache {

    @Autowired
    RuleCache ruleCache;
    //@GetMapping(value = "/")
    //缓存后，一次性就出来了
    @Test
    public void  TestHello(){
        for(int i=0;i<5;i++){
            System.out.println(new Date() + " " + ruleCache.cacheFunctionForRedis(i));
        }
    }


    /*@Autowired
    RedisCacheConfiguer redisCacheConfiguer;
    @Test
    public void  TestHelloFromCacheManager(){
        for(int i=0;i<5;i++){
            System.out.println(new Date() + " " + redisCacheConfiguer.);
        }
    }*/

    //到底是redis设置有问题还是缓存数据的方法不对导致的呢？
    @Autowired
    private RedisTemplate redisTemplate ;
    @Test
    public void testRedis(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("3","333",1, TimeUnit.MINUTES);
        System.out.println("---store success---");
        System.out.println(ops.get("3"));
        //可以取到，为什么在client没有找到呢？https://bbs.csdn.net/topics/392173069?page=1
        System.out.println("---store success---");
    }
}
