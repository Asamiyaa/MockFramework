package com.thread.demo3;

import java.util.concurrent.*;

/**
 * @author YangWenjun
 * @date 2019/7/17 12:01
 * @project BaseJava
 * @title: PoolAndFutureCallable
 * @description:
 */
public class PoolAndFutureCallable {

    /**
     * 1.一般需要根据任务的类型来配置线程池大小：
     　　如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 NCPU+1
     　　如果是IO密集型任务，参考值可以设置为2*NCPU
     　　当然，这只是一个参考值，具体的设置还需要根据实际情况进行调整，比如可以先将线程池大小设置为参考值，再观察任务运行情况\
          和系统负载、资源利用率来进行适当调整。''
       2.Runnable run无返回值 池：execute     Callable  call()有返回值  池：submit  <T> Future<T> submit(Callable<T> task);/Future<?> submit(Runnable task);
       3.Future
         　　1）判断任务是否完成；
         　　2）能够中断任务；
         　　3）能够获取任务执行结果。

             cancel方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
             isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
             isDone方法表示任务是否已经完成，若任务完成，则返回true；
             get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> submit = executorService.submit(/*new Runnable() {
            @Override
            public void run() {

                System.out.println("abc");
            }

        }*/
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(3000);
                        return "addd";
                    }
                });
        System.out.println(submit.get()); //addd  --阻塞
        /**
         * 那么这里get()不是和线程异步呢？
         */

        System.out.println("aa");
    }

}
