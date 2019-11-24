package com.utils;

/**
 * @author YangWenjun
 * @date 2019/8/26 17:50
 * @project hook
 * @title: SerialNumUtil
 * @description:
 *          1.几种方式( db-seq /时间戳+随机数/时间戳+会员ID/GUID/UUID )  <-- 不同规则 db可以使用redis
 *                      https://segmentfault.com/q/1010000002939663
 *          2.多线程 / 分布式
 *          3.嵌套都 --上层--的框架中，无需调用者手动生成，保证正确性-机具 Automic 增加  --> concurrentHashmap
 *
 *
 *          sequence
 *              create sequence name
                increment by x //x为增长间隔
                start with x //x为初始值
                maxvalue x //x为最大值
                minvalue x //x为最小值
                cycle //循环使用，到达最大值或者最小值时，从新建立对象
                cache x //制定存入缓存（也就是内存）序列值的个数

                如果不设定cycle循环的话，每一个序列号是唯一的。 当一个序列号生成时，序列是递增
                当使用到序列的事务发生回滚。会造成序列号不连续。
                cache的作用：当大量语句发生请求，申请序列时，为了避免序列在运用层实现序列而引起的性能瓶颈。
                             Oracle序列允许将序列提前生成 cache x个先存入内存，在发生大量申请序列语句时，
                                可直接到运行最快的内存中去得到序列。但cache个数也不能设置太大，因为在数据库重启时，
    会清空内存信息，预存在内存中的序列会丢失，当数据库再次启动后，序列从上次内存中最大的序列号+1 开始存入cache x个
 */
public class SerialNumUtil {

        //private SeqDao seqDao ;               //这里是库来维护落地，那么如果不使用db,如何保存？--static - 启动后消失，不同的来访问呢
                                                //不会 垃圾回收吗？

        //public synchronized String getMsgId(){  //唯一性并发控制
        //      String seq = "";
        //      Date date = System.currentTimeMillis(); //mac - 业务id ...  -- Calendar操作日期
        //      seq = date + seqDao.getDB2NexSeq();
        //      return seq ;
        // }

}

class SeqDao {

    /*public String  getDB2NextSeq(){
         List list = getHibernate().excuteFind(
                 new HibernateCallBack(){
                     //重写
                     Query query = session.createSQLQuery("values nextval for msgid_sequece");
                 }
         );
         if(list ！= null && !list.isEmpty()){
             BigInteger next= list.get(0);
             String nextStr = next.toString();
             while(next.length<8){
                 nextStr = "0"+nextStr;                  --->nextStr = StringUtils.leftPad(nextStr,8,"0");
             }
         }

         return nextString ;
     }*/

}
