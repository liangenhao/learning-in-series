package com.enhao.learning.in.activiti.process_instance;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * activiti 整合业务系统
 * @author enhao
 */
public class IntegrationWithBusinessSystems {

    public static void main(String[] args) {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取 RuntimeService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3. 创建流程实例
        // 需要流程定义的key : 流程定义的bpmn文件的id, 或者查看 act_re_procdef 表的KEY_ 字段。
        String processDefinitionKey = "holiday";
        // 业务主键
        String businessKey = "1001";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);

        // 4. 输出实例的相关信息
        System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id : " + processInstance.getId());
        System.out.println("当前活动id : " + processInstance.getActivityId());
        System.out.println("业务主键 : " + processInstance.getBusinessKey());

    }
}
