package thread.demo1;

import java.util.Random;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:02
 * @project BaseJava
 * @title: Main
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank(100,"test", (double) 1000);
        for(int i = 0 ; i<100; i++) {
          /*  new BankThread().transformThread(bank,(int)Math.random()*100 ,
                    (int)Math.random()*100 ,(double)Math.random()*1000);*//*
            /*new BankThread().transformThread(bank,i ,
                    i+1 ,(double)Math.random()*1000);*/
            new BankThread().transformThread(bank,i ,
                    (int)(Math.random()*5) ,(int)(Math.random()*1000)); //同步存取

        }
       // System.out.println((int)(Math.random() * 1000));
    }
}
