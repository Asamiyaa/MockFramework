package com.core.rule.bean;

import com.core.constant.BaseEnum;

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
}
