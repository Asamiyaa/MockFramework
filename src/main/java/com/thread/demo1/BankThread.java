package com.thread.demo1;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:02
 * @project BaseJava
 * @title: BankThread
 * @description:
 */
@SuppressWarnings("ALL")
public class BankThread {

    //Bank bank = new Bank("");

    public synchronized void transformThread(Bank bank , int from , int to ,double transMoney){ //线程中信息注入 依赖vs关联....
        new Thread(new Runnable() {
            @Override
            public void run() {

                //new Bank().transform(from ,to ,transMoney); //不合理
                try {
                    bank.transform(from ,to ,transMoney); //不合理
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //Thread.sleep(1000);  //便于观察 所有都睡眠一样，几乎顺序输出  耗时越长，可能在for中获取到的值不同的可能越大。
                    Thread.sleep(2000);
                   // System.out.println("总额"+bank.getTotalMoney());为什么不易展现呢?
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
