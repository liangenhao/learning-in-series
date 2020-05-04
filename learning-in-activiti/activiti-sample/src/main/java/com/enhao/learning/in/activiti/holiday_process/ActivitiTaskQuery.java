package com.enhao.learning.in.activiti.holiday_process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 查询当前用户的任务列表
 * @author enhao
 */
public class ActivitiTaskQuery {
    public static void main(String[] args) {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 得到 TaskService 对象
        TaskService taskService = processEngine.getTaskService();
        // 3. 根据流程定义的key，负责人来实现当前用户的任务列表查询
        String processDefinitionKey = "holiday";
        String assignee = "zhangsan";
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee)
                .list();
        // 4. 任务列表的展示
        taskList.forEach(task -> {
            System.out.println("流程定义id : " + task.getProcessDefinitionId());
            System.out.println("流程实例id : " + task.getProcessInstanceId());
            System.out.println("任务id : " + task.getId());
            System.out.println("任务负责人 : " + task.getAssignee());
            System.out.println("任务名称 : " + task.getName());
        });
    }
}
