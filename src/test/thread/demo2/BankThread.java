package thread.demo2;

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

    public synchronized void transformThread(Bank bank , int from , int to , double transMoney){ //线程中信息注入 依赖vs关联....
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    bank.transform(from ,to ,transMoney);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
