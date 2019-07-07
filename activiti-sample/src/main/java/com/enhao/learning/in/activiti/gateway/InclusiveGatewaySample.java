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
 * 包含网关应用
 * @author enhao
 */
public class InclusiveGatewaySample {
    /**
     * 流程定义key，bpmn文件的id
     */
    private static String processDefinitionKey = "examine_inclusiveGateway";

    public static void main(String[] args) {
        // 流程定义部署，只要执行一次
        // deployProcessDefinition();

        // 启动一个流程实例，并设置流程变量
        startProcessInstance();

        // 执行任务
        // 领取体检单
        completeTask("zhangsan");
        // 常规体检项
        completeTask("lisi");
        // 抽血化验
        completeTask("wangwu");
        // 附加体检项
        completeTask("zhaoliu");
        // 早餐
        completeTask("sunqi");
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
        variables.put("userType", UserType.GENERAL_STAFF.status); // 用户类型
        variables.put("assignee1", "zhangsan"); // 领取体检单assignee
        variables.put("assignee2", "lisi"); // 常规体检项assignee
        variables.put("assignee3", "wangwu"); // 抽血化验assignee
        variables.put("assignee4", "zhaoliu"); // 附加体检项ssignee
        variables.put("assignee5", "sunqi"); // 早餐ssignee


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
                .addClasspathResource("diagram/examine_inclusiveGateway.bpmn")
                .name("体检流程-包含网关")
                .deploy();

        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
    }

    /**
     * 用户类型
     */
    private static enum UserType {

        GENERAL_STAFF(1, "普通员工"),
        MANAGER(2, "管理者");

        int status;
        String desc;

        UserType(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }
}
