package com.process.controller;
/**
 *  @author  YangWenjun
 *  @date   2018年11月14日
 *  @description
 *               ***bbsp中砍掉了这部分功能***bbsp中使用mule和jbpm做的  学习：mule /activity
 *               1.为什么将jBPM与Mule一起使用？                                 https://blogs.mulesoft.com/dev/mule-dev/why-use-jbpm-with-mule/
 *               2.企业数据总线(ESB)和注册服务管理(dubbo)的区别          https://www.cnblogs.com/liangqihui/p/7905307.html
 *               3.纵观 jBPM：从 jBPM3 到 jBPM5 以及 Activiti5             https://www.infoq.cn/article/rh-jbpm5-activiti5
 *               4. Activiti5                                                                    https://www.jianshu.com/p/988cedf2ca7c
 *               5.activiti5 工作流的入门								                     https://blog.csdn.net/fgstudent/article/details/39901255
 *               6. 特别响、非常近——BPMN2 新规范与 Activiti5            https://www.infoq.cn/article/bpmn2-activiti5
 *               7.mule   muleESB的第一个开发实例-HelloWorld（二）   https://blog.csdn.net/jiuqiyuliang/article/details/49516457
 *

 */

import com.secKill.controller.BaseController;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("apply")
@RequestMapping("/apply")
public class applyController  extends BaseController{

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping("/firstDemo")
    public void getOtp() throws Exception {
        //根据bpmn文件部署流程
       // Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/applyRest_p.bpmn").deploy();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("testDeploy")
                .addClasspathResource("processes/applyRest_p.bpmn")
                .deploy();
       // System.out.println(deploy.getId() + "  " + deploy.getName());

    //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println(processDefinition + "");
        //启动流程定义，返回流程实例
       ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());
        String processId = pi.getId();
       System.out.println("流程创建成功，当前流程实例ID："+processId);


     /*  Task task=taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("第一次执行前，任务名称："+task.getName());
        taskService.complete(task.getId());
        //相关判断才能执行完成
        System.out.println("--暂停---");*/

       //开启流程
        //可以塞入初始参数  map形式
      //      runtimeService.startProcessInstanceById(deploy.getId());
    }
}
