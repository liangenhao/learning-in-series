package com.enhao.learning.in.activiti.gateway;

import com.enhao.learning.in.activiti.process_variable.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 并行网关应用
 *
 * @author enhao
 */
public class ParallelGatewaySample {
    /**
     * 流程定义key，bpmn文件的id
     */
    private static String processDefinitionKey = "holiday_parallelGateway";

    public static void main(String[] args) {
        // 流程定义部署，只要执行一次
        // deployProcessDefinition();

        // 启动一个流程实例，并设置流程变量
        startProcessInstance();

        // 执行任务
        // 填写请假单
        completeTask("zhangsan");
        // 部门经理审批
        completeTask("lisi");
        // 总经理审批
        completeTask("wangwu");
        // 人事经理审批
        completeTask("zhaoliu");
        // 财务会计
        completeTask("sunqi");
        // 行政考勤
        completeTask("zhouba");
    }

    /**
     * 完成指定责任人的一个任务
     *
     * @param assignee 责任人
     */
    private static void completeTask(String assignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee).list();

        if (taskList.size() > 0) {
            Task task = taskList.get(0);
            taskService.complete(task.getId());
            System.out.println(assignee + "一个任务执行完毕");
        }
    }

    /**
     * 启动一个新的流程实例
     */
    private static void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 启动流程实例时设置流程变量
        Map<String, Object> variables = new HashMap<>();
        Holiday holiday = new Holiday();
        holiday.setNum(1F);
        variables.put("holiday", holiday);
        variables.put("assignee1", "zhangsan"); // 填写请假单assignee
        variables.put("assignee2", "lisi"); // 部门经理审核assignee
        variables.put("assignee3", "wangwu"); // 总经理审核assignee
        variables.put("assignee4", "zhaoliu"); // 人事经理审核assignee
        variables.put("assignee5", "sunqi"); // 财务会计assignee
        variables.put("assignee6", "zhouba"); // 行政考勤assignee

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

        System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id : " + processInstance.getId());
    }

    /**
     * 部署流程定义
     */
    private static void deployProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday_parallelGateway.bpmn")
                .name("请假单审批流程-并行网关")
                .deploy();

        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
    }
}
