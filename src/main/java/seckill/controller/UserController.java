package seckill.controller;

import seckill.controller.viewObj.UserVO;
import seckill.error.BusiException;
import seckill.error.EmBusiError;
import seckill.response.CommonReturnType;
import seckill.service.UserService;
import seckill.service.model.UserModel;
import com.utils.EncodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders = "*" ,maxAge = 3600)
public class UserController extends  BaseController{

    @Autowired
    private UserService userService ;
    @Autowired
    private HttpServletRequest httpServletRequest ;

    @RequestMapping(value="/register",method={RequestMethod.POST},consumes={"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String  telphone,
                                     @RequestParam(name="otpCode")String  otpCode,
                                     @RequestParam(name="name")String  name ,
                                     //@RequestParam(name="gender")Boolean  gender , boolean 是没有传递的
                                     @RequestParam(name="gender")Integer  gender ,
                                     @RequestParam(name="age") Integer age,
                                     @RequestParam(name="password") String password) throws Exception {
        //验证对应的手机号和otp码
        //String otp = (String) httpServletRequest.getSession().getAttribute("telphone");不要盲目用串，要明确
        String otp = (String) httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otp,otpCode)){
             throw  new BusiException(EmBusiError.PARAMETER_VALIDATE_ERROR,"otp码不正确");
        }
        //register 具体流程
        //TODO : 封装req方法
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setGender(gender.intValue()==1?true:false);
        userModel.setAge(age);
        userModel.setRegMode("byPhone");
        //userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));
        userModel.setEncrptPassword(EncodeUtil.EncodeByMD5(password));
        userService.register(userModel);

        return CommonReturnType.create(null);
    }


    @RequestMapping(value="/getOtp",method={RequestMethod.POST},consumes={"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String  telphone ) throws Exception {
        //1.生成随机数                                 TODO : 随机数生成工具(不同的规则 ，加密 ,解密 ..) -- 图片验证  ocr ...
        Random random = new Random();
        int otp = random.nextInt(999999);
        String otpStr = String.valueOf(otp);
        if(otpStr.length() != 6){
            for(int i = otpStr.length() ; i < 6 ; i++){ //TODO: -- 处理
                  otpStr = "0"+ otpStr;
            }
        }

        //2.映射手机号和opt值 ，目前为sesssion中完成 ，TODO : redis    在框架下，如何获取需要的底层资源并关联
        /***
         * 通过spring注入httpServlet 获取session ,
         * 单例 vs 多线程并发
         * spring进行了threadLocal处理
         */
        httpServletRequest.getSession().setAttribute(telphone , otpStr);

        //3.调用短信接口发送                           TODO : 调用外部接口
        //模拟
        System.out.println("phone is  " + telphone + "  otp is  " + otpStr); //生产日志绝对不可以泄露用户数据
        return  CommonReturnType.create(null);
    }


    @RequestMapping("/get")
    @ResponseBody
    //public UserVO getUserById(@RequestParam(name="id") Integer id ){
    public CommonReturnType getUserById(@RequestParam(name="id") Integer id ) throws Exception {
        UserModel userModel = userService.getUserById(id);

        if(userModel == null){
            throw  new BusiException(EmBusiError.USER_NO_EXIT);
            //为了测试未知异常情况的封装效果
            // throw new NullPointerException();
        }

        UserVO userVO =   convertUserVOFromUserModel(userModel) ;
        return CommonReturnType.create(userVO);
        /***
         *   模拟简单实用CommonReturnType处理异常情况
             1. return CommonReturnType.create(new Object() ,"fail");
         *      Whitelabel Error Page</h1><p>This application has no explicit mapping for /error, so you are seeing this as a fallback.<
         *      直接将后台错误抛给web容器，容器转换为500错误，一方面这是不符合真实情况的，不是服务器不可访问，只是后台
         *                                                    二方面前台也是无法处理，未进行封装处理
         *    2.so:在类中为fail情况
         *      创建对应的errorCode errorMsg。
         *      return new CommonReturnType("fail", "10001","用户不存在");
         //     {"status":"fail","data":null,"errorCode":"10001","errorMsg":"用户不存在"}
            --基本效果达到

              3.定义统一的异常，可以在service中根据场景抛出，到controller自动包装到commonReturnType中
                1.创建
         *
         */


    }

    private UserVO convertUserVOFromUserModel(UserModel userModel) {
        //入参判断 --分而治之 ，一定不要因为模块大了而造成对每个小模块必要判断的丢失
        if(userModel == null){
            return null ;
        }
        UserVO userVO = new UserVO() ;
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }
    //controller层作为web处理的最后关口，定义一个好的钩子 spring思想
    //定义exceptionHandler解决未被controller层吸收的额exception
    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request , Exception ex){
      //通过debug查看钩子是否起作用
      //由于返回的ex的反序列化，所以这里需要将原来的异常进行窄化,并构造在固定的map中返回
      //整理代码结构使用create方法统一，不是new，因为这里的create参数也是obj
         //如果ex不是该类型呢？if判断    对代码进行公共抽取
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof  BusiException){
            BusiException busiException = (BusiException)ex ;
            responseData.put("errorCode",busiException.getErrorCode());
            responseData.put("errorMsg",busiException.getErrorMsg());
        }else{
        //从这里可以看到返回是可以和错误码直接作用的，中间夹了exception就是为了方便在service层抛出
            responseData.put("errorCode",EmBusiError.UNKNOWN.getErrorCode());
            responseData.put("errorMsg",EmBusiError.UNKNOWN.getErrorMsg());
        }
        return CommonReturnType.create(responseData,"fail");
       //2. CommonReturnType commonReturnType = new CommonReturnType();
       //2.commonReturnType.setStatus("fail");
       //1.commonReturnType.setData(ex);
       //2. commonReturnType.setData(responseData);
       //3 return commonReturnType ;
    }*/
    @RequestMapping("/getTest")
    public void getTest(String id){
        System.out.println(id);
    }
}
