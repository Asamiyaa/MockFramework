package com.pattern.j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author YangWenjun
 * @date 2020/4/14 16:16
 * @project MockFramework
 * @title: SelvletDispatcher
 * @description:  springController 委派模式
 */
public class SelvletDispatcher {

    //这里也可以用map 对象来保存Hanlder对象
    private List<Handler> handlerMapping = new ArrayList<Handler>();

    public SelvletDispatcher() {
        //简单实现一个controller的映射
        try {
            Class clazz  = MemberAction.class;
            /**
             * 通过注解扫描进行注册而无需手动  vs 数据库
             */
            handlerMapping.add(new Handler()
                    .setController(clazz.newInstance())
                    .setMethod(clazz.getMethod("getMemberById",new Class[]{String.class}))
                    .setUrl("/web/getMemberById.json")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  doService(HttpServletRequest request, HttpServletResponse response){
        //隐藏内部实现技术
        doDispatch(request,response);
    }

    /**
     * 请求的分发工作
     * @param request
     * @param response
     */
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        //1.获取用户请求的url
        String uri =   request.getRequestURI();
        Handler handler =null;

        ////2、根据uri 去handlerMapping找到对应的hanler
        for(Handler h :handlerMapping){
            if(uri.equals(h.getUrl())){
                handler = h;
                break;
            }
        }
        //3.将具体的任务分发给Method（通过反射去调用其对应的方法）
        Object obj = null;
        try {

            //通过反射进行调用 实现通用
            obj =  handler.getMethod().invoke(handler.getController(),request.getParameter("mid"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4、获取到Method执行的结果，通过Response返回出去
        // response.getWriter().write();

    }
    /**
     * 具体的hanlder对象 --- 那些有用的信息
     */
    class Handler{
        //controller对象
        private Object controller;
        //controller对象映射的方法
        private  String url;
        //ulr对应的方法
        private Method method;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return  this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }
    }
}

class MemberAction {

    public void getMemberById(String mid){

    }
}