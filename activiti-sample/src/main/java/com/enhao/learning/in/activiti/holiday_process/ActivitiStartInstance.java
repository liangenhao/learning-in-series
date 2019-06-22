package com.enhao.learning.in.activiti.holiday_process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 启动一个流程实例
 *
 * 前提：现已经完成流程定义和部署
 *
 * @author enhao
 */
public class ActivitiStartInstance {

    public static void main(String[] args) {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取 RuntimeService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3. 创建流程实例
        // 需要流程定义的key : 流程定义的bpmn文件的id, 或者查看 act_re_procdef 表的KEY_ 字段。
        String processDefinitionKey = "holiday";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);

        // 4. 输出实例的相关信息
        System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id : " + processInstance.getId());
        System.out.println("当前活动id : " + processInstance.getActivityId());
        // System.out.println("流程部署id : " + processInstance.getDeploymentId());


    }
}
