package com.process.controller;


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
