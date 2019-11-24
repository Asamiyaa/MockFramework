package com.timing.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *数据库脚本参考：https://www.cnblogs.com/ealenxie/p/9134602.html
 */
//@Component
public class seckillService {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        /**
         * 每隔5秒钟触发一次
         */
       // @Scheduled(fixedRate = 5000)
        public void reportCurrentTime() {
            System.out.println(("现在时间：" + dateFormat.format(new Date())));
        }

        /**
         * 通过cron来设置定时规则，每隔10秒
         * 对应含义为：
         *   字段         允许值         允许的特殊字符
         秒          0-59 ,          - * /
         分          0-59 ,          - * /
         小时         0-23 ,          - * /
         日期         1-31 ,          - * ? / L W C
         月份         1-12 或者 JAN-DEC , - * /
         星期         1-7 或者 SUN-SAT , - * ? / L C #
         年（可选） 留空, 1970-2099 , - * /
         */
        //@Scheduled(cron = "0/1 * * * * ?")
        public void executeByTenSecond() {
            System.out.println(("通过cron来设置每隔10s执行..."));
        }
    }

