package com.core.rule.bean;

import com.core.constant.BaseEnum;
import com.core.constant.BooleanEnum;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:20
 * @project MockFramework
 * @title: CheckResult
 * @description:
 */
public class CheckResult {



   /* private String resultCode ;
       private String resultMsg;*/
   private BaseEnum result ;


   //再次封装-因为很多场景直接创建一个true,不满足就创建一个false,直接构造会混淆，所以创建一个静态方法
   public static CheckResult CheckTrue(){
      return new CheckResult(BooleanEnum.TRUE);
   }

   public CheckResult checkFalse(){
      return this.setResult(BooleanEnum.FALSE);
   }

   //----------------
   public CheckResult(BaseEnum result) {
      this.result = result;
   }



   //为了方便
   /*public void setResult(BaseEnum result) {
      this.result = result;
   }*/

   public CheckResult setResult(BaseEnum result) {
      this.result = result;
      return this ;
   }

   public BaseEnum getResult() {
      return result;
   }
}
