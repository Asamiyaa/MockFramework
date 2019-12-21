package thread.demo1;

import java.util.Arrays;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:00
 * @project BaseJava
 * @title: Account
 * @description:
 */
public class Bank   {

    private Account[] accounts ;

    public Bank() {

    }

    public Bank(int accNum , String name , Double accMoney) {
        accounts = new Account[accNum];
       // Arrays.fill(accounts , new Account(name , accMoney)); //代替for - api.. 不应该塞入同一个对象
       for( int i = 0 ; i<accNum ; i++){
           accounts[i] = new Account(name , accMoney);
       }
    }

    //这里简化了账户概念 直接使用下标定位账户

    public  synchronized void transform(int from , int to , Double transMoney) throws InterruptedException {
        if(accounts[from].getAccMoney().compareTo(transMoney)<0){
            System.out.println("余额不足");
            return ;}
        //模拟应该让转账操作，耗时增加。也就是增加睡眠，提高被读取和操作时不一致的出现。
        double curMoneyFrom = accounts[from].getAccMoney();
        double dd = curMoneyFrom -= transMoney;
        Thread.sleep(2000);  //一定要明确时程序问题还是线程引起。所以一般先实现，再去考虑线程。反过来，出现问题，添加锁解决则就是线程问题，速度慢了许多。
        accounts[from].setAccMoney(dd);
        //System.out.println(accounts[from].getAccMoney());
        double curMoneyTo = accounts[to].getAccMoney();
       // double dd2 = (curMoneyTo += transMoney);  // ref cause  calculate wrong
        curMoneyTo = curMoneyTo + transMoney;
       /* System.out.println(accounts[from]);
        System.out.println(accounts[to]);*/
        Thread.sleep(2000);
        accounts[to].setAccMoney(curMoneyTo);
        /*System.out.println("从"+from+" 转出" + transMoney + ",到 "+to +"转入。此时" +
                //理解上的问题，以为创建线程了。那份数据哪些是属于该线程的。哪些是
                //可能因为线程乱
                "账户from"+"----"+from +"---"+accounts[from].getAccMoney()+"  to账户"+"----"+to +"---"+accounts[to].getAccMoney());
*/
        System.out.println("账户from" + "----" + from + "---" + " to账户" + accounts[from].getAccMoney() + "----" +
                to + "---" + accounts[to].getAccMoney()+"总额"+getTotalMoney());
    }

    public double getTotalMoney(){
        double totalMoney = 0.0;
        for (Account acc :
                accounts) {
            totalMoney += acc.getAccMoney();
        }
        return totalMoney;
    }

    //other method
}
