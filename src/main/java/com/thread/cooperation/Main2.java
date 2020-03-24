package com.thread.cooperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马俊昌
 * Main2是将“老马”的生产者消费者进行学习
 *    1.将容器包装为MyBlockingQueue，且包装对应的put()和take()
 *    2.巧妙的验证
 */
public class Main2 {
public static void main(String[] args) {
    MyBlockingQueue2<String> queue = new MyBlockingQueue2<>(10);
    new Producer2(queue).start();
    new Consumer2(queue).start();
  }
}
