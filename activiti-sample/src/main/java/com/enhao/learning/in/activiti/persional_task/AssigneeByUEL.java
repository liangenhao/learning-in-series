package com.enhao.learning.in.activiti.persional_task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过UEL表达式分配任务负责人
 *
 * @author enhao
 */
public class AssigneeByUEL {

    public static void main(String[] args) {
        // 部署流程定义，部署只需要执行一次
        // 流程定义是 holiday_UEL.bpmn
        deployProcessDefinition();

        // 启动流程实例
        startProcessInstance();
    }

    private static void deployProcessDefinition() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday_UEL.bpmn")
                .name("请假单审核流程(UEL)")
                .deploy();

        // 4. 获取部署的一些信息
        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
        System.out.println("key:" + deployment.getKey());
    }

    private static void startProcessInstance() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取 RuntimeService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3. 设置 assignee 的取值
        Map<String, Object> map = new HashMap<>();
        map.put("assignee1", "zhangsan1");
        map.put("assignee2", "lisi1");
        map.put("assignee3", "wangwu1");

        // 4. 启动流程实例
        // 需要流程定义的key : 流程定义的bpmn文件的id, 或者查看 act_re_procdef 表的KEY_ 字段。
        String processDefinitionKey = "holiday_UEL";
        // 业务主键
        String businessKey = "1001";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, map);

        // 4. 输出实例的相关信息
        System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id : " + processInstance.getId());
        System.out.println("当前活动id : " + processInstance.getActivityId());
        System.out.println("业务主键 : " + processInstance.getBusinessKey());
    }
}
