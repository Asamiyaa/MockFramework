package com.msg.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * 过程
 *      1.如果maven爆红，将对应包去掉，刷新，在添加
 *      2.还爆红 依赖冲突 https://blog.csdn.net/xcc_2269861428/article/details/92669091
 *      3.还在爆红，后面没有东西   https://blog.csdn.net/u014672466/article/details/80211340
 *        问题：如果造成问题呢？如何取舍版本
 *      3.idea许多工具不熟练  clean rebuild..  通过view查看对应视图
 *
     SpringBoot如何优雅的使用RocketMQ ：https://www.cnblogs.com/SimpleWu/p/12112351.html   -- 其实看似复杂是综合了多个方面 设计  思考的结果 所以分而治之
     问题：1.为什么配置mq的时候rocket没有提示信息？
     下午：实现对应的rocket
     1.十分钟入门RocketMQ      http://jm.taobao.org/2017/01/12/rocketmq-quick-start-in-10-minutes/  结合AmqMsgContainerFactory

     1.官网例子 *** main 因为此时还没有和spring结合
     2.问题排查过程：https://cloud.tencent.com/developer/article/1447341 - 找一篇靠谱的每一步都验证保证，不要随意跳跃可能这样就错过了最重要的额 - 日志查看 -地址通过配置文件conf中查看
        思考搭建过程中的部署生产都要注意，并且是linux中更多设置
     3.helper的使用 高于工具 又定制于项目RemotingHelper  sendResult 包含的属性并重写了toString
     4.优秀的开源框架在调用方法时注意重载、参数这些都是设计者提供你注意的地方而不是随意选择一个
     5.final int index = i;标记测试
     6.消费者受到消息后的 ，控制消息是有序的 ，如果启动线程处理，那么‘处理的先后顺序会有问题，这样同样造成了无序’
        --TODO:接收到消息等待消息条件符合在执行、如何把控触发。还要做到去重、幂等等操作 - 业务代码
        --TODO:要建哪些队列合适呢？划分
 */


public class DefaultProducer {

    /*public DefaultMQProducer getDefaultMQProducer(){
        return null ;
    };*/



    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        new DefaultProducer().synSend();
//        new DefaultProducer().asynSend();
          new DefaultProducer().consumer();
    }


    /**
     * order使用
     *      1.普通消费 2. 顺序消费 3.事务消费  https://blog.csdn.net/u010634288/article/details/57158374
     *      2.实现了MessageListenerOrderly表示一个队列只会被一个线程取到
     *      3.Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
     *      4.结合spring 关于顺序https://blog.csdn.net/weixin_34452850/article/details/82664799
     *
     */
    /**--已完成
     * broadcast  consumer.setMessageModel(MessageModel.BROADCASTING); 不是在producer那个控制而是在consumer
     * schedual
     * batch
     * filter
     */

    public void consumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("test");
        defaultMQPushConsumer.setNamesrvAddr("localhost:9876");
        defaultMQPushConsumer.subscribe("TopicTest","*");

        //添加监听器 实现‘自动查看’
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //消费后消息存在 - 那么我再次启动该程序模拟多个client,查看是否会再次消费，这种k控制需要程序还是中间件？ 不会消费，中间件完成 - 并发下呢？这种幂等是否需要程序本身控制
        defaultMQPushConsumer.start();
        System.out.printf("Consumer Started.%n");
    }


    public void asynSend()throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.start();
        for (int i = 0; i < 3; i++) {
            final int index = i;
            Message msg = new Message("TopicTest1", "TagA1" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);
            //SendResult sendResult = defaultMQProducer.send(msg);
            defaultMQProducer .send(msg, new SendCallback() {
        //异步 - 多线程解决 - outofmemeory 因为自己调整了broker的内存大小 --那么生产到底设置多少合适呢？标准来自哪里/**/
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(10000);
        System.out.println("msg send end ");
        defaultMQProducer.shutdown();//The producer service state not OK, SHUTDOWN_ALREADY 体现了异步 主线程将生产者停止，所以填入睡眠
    }

        public void synSend()throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException{
            //搭建完环境后，建立连接
            DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test");
            defaultMQProducer.setNamesrvAddr("localhost:9876");
            //defaultMQProducer.setCreateTopicKey("TopicTest");
            defaultMQProducer.start();
            //ctrl+shift+空格 显示方法详细参数  shift+" 跳到下一个“后  ctrl+shift+enter 末尾; alt+enter 提示  ctrl+/注释改行
            for (int i = 0; i < 10; i++) {
                // Message message = new Message("topicA", "this is first test".getBytes(RemotingHelper.DEFAULT_CHARSET));   No route info of this topic: topicA
                /**
                 * 有问题时一定要查看启动是否报错
                 */
                Message msg = new Message("TopicTest", "TagA" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);
                //Call send message to deliver message to one of brokers.
                SendResult sendResult = defaultMQProducer.send(msg);
                System.out.printf("%s%n", sendResult);
            }

            System.out.println("msg send end ");
            defaultMQProducer.shutdown();
        }

    }



