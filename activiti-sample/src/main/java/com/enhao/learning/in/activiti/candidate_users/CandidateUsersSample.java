package com.enhao.learning.in.activiti.candidate_users;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组任务-设置候选人案例
 *
 * @author enhao
 */
public class CandidateUsersSample {

    /**
     * 流程定义key，bpmn文件的id
     */
    private static String processDefinitionKey = "holiday_candidate-users";

    public static void main(String[] args) {
        // 流程定义部署，只要执行一次
        // deployProcessDefinition();

        // 启动一个流程实例，并设置流程变量
        startProcessInstance();

        // 执行任务
        // 填写请假单
        completeTask("zhangsan");

        // 部门经理审批
        String candidateUser1 = "lisi";
        String candidateUser2 = "wangwu";
        // 候选人1拾取组任务
        claimTask(candidateUser1);
        /* 归还任务测试 ->
            // 归还任务
            returnTaskToGroupTask(candidateUser1);
            // 候选人2拾取组任务
            claimTask(candidateUser2);
            // 候选人完成任务
            completeTask(candidateUser2);
        */

        /* 交接任务测试 -> */
        // 交接任务
        handoverTask(candidateUser1, candidateUser2);
        // 候选人2完成任务
        completeTask(candidateUser2);

        // 总经理审批
        completeTask("zhaoliu");
    }

    /**
     * 交接任务
     * @param assignee 该任务目前负责人
     * @param handoverAssignee 交接的负责人
     */
    private static void handoverTask(String assignee, String handoverAssignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee).list();

        if (taskList.size() > 0) {
            Task task = taskList.get(0);
            // 任务交接 : 只需要将 assignee 设置为 交接人
            taskService.setAssignee(task.getId(), handoverAssignee);
            System.out.println(assignee + "一个任务交接给" + handoverAssignee + "成功!");
        }
    }


    /**
     * 归还任务到组任务
     * @param assignee 负责人
     */
    private static void returnTaskToGroupTask(String assignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee).list();

        if (taskList.size() > 0) {
            Task task = taskList.get(0);
            // 归还任务 : 只需要将 assignee 设置为 null
            taskService.setAssignee(task.getId(), null);
            System.out.println(assignee + "一个任务归还成功!");
        }
    }

    /**
     * 查询候选人的组任务，并拾取任务
     * 拾取任务就是将任务候选人转换为真正任务的负责人
     */
    private static void claimTask(String candidateUser) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        // 查询候选人的组任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskCandidateUser(candidateUser) // 候选人
                .list();

        if (taskList.size() > 0) {
            Task task = taskList.get(0);
            System.out.println(candidateUser + "的组任务 : ");
            System.out.println("流程实例id : " + task.getProcessInstanceId());
            System.out.println("任务id : " + task.getId());
            System.out.println("任务名称 : " + task.getName());

            // 拾取任务
            taskService.claim(task.getId(), candidateUser);
            System.out.println(candidateUser + "拾取任务成功!");
        }
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
        variables.put("assignee1", "zhangsan"); // 填写请假单assignee
        variables.put("candidate_user_1", "lisi"); // 部门经理审批候选人1
        variables.put("candidate_user_2", "wangwu"); // 部门经理审批候选人2
        variables.put("assignee3", "zhaoliu"); // 总经理审核assignee

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
                .addClasspathResource("diagram/holiday_candidate-users.bpmn")
                .name("请假单审批流程-组任务")
                .deploy();

        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
    }
}
