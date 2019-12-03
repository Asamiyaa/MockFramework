package com.register.bean;

/**
 * @author YangWenjun
 * @date 2019/12/3 15:25
 * @project MockFramework
 * @title: studySearchBean
 * @description:  对于入参复杂的方法，可以将参数封装为searchBean对象
 */
public class MethodSearchBean {

    private String module ;
    private String interfaceName ;
    private String methodName ;
    private String[] params ;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
