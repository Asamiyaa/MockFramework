package com.process.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {
    //获取符合条件的审批人，演示方便这里写死，使用时应用实际代码
    @Autowired
    private TaskService taskService ;
    @Autowired
    private RuntimeService runtimeService;

    public void findUsers(DelegateExecution execution) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", true); // 这里直接给予true表示通过
        //String taskId = "1"; // 更改成上个方法查询出的任务Id
       // taskService.complete( taskVariables);
        System.out.println("-----------执行了joinService---------");
    }

    public void joinGroup(DelegateExecution execution) {
        Boolean bool = execution.getVariable("joinApproved",Boolean.class);
        if (bool) {
            Long personId = execution.getVariable("personId", Long.class);
            Long compId = execution.getVariable("compId",Long.class);
            /*Comp comp = compRepository.findById(compId).get();
            Person person = personRepository.findById(personId).get();
            person.setComp(comp);
            personRepository.save(person);*/
            System.out.println("加入组织成功"+personId+"  -- "+compId);
        } else {
            System.out.println("加入组织失败");
        }
    }



    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
}


