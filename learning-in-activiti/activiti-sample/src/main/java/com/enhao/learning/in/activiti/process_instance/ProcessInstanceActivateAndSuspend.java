package com.enhao.learning.in.activiti.process_instance;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 流程实例的激活和挂起
 *
 * @author enhao
 */
public class ProcessInstanceActivateAndSuspend {

    public static void main(String[] args) {
        // 激活或挂起所有的流程实例
        // activateAndSuspendAllProcessInstance();

        // 激活或挂起单个的流程实例
        activateAndSuspendSingleProcessInstance();
    }

    /**
     * 激活或挂起单个的流程实例
     */
    private static void activateAndSuspendSingleProcessInstance() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RuntimeService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3. 得到流程实例对象
        String processInstanceId = "2501";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // String processInstanceId = processInstance.getId();
        boolean suspended = processInstance.isSuspended();

        if (suspended) {
            // 挂起状态，执行激活
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例" + processInstanceId + "被激活");
        } else {
            // 激活状态，执行挂起
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例" + processInstanceId + "被挂起");
        }
    }

    /**
     * 激活或挂起所有的流程实例
     */
    private static void activateAndSuspendAllProcessInstance() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3. 得到流程定义对象
        String processDefinitionKey = "holiday";
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .singleResult();

        // 4. 得到流程定义的实例是否都为挂起状态
        boolean suspended = processDefinition.isSuspended();

        String processDefinitionId = processDefinition.getId();
        if (suspended) {
            // 挂起状态，执行激活
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
            System.out.println("流程定义" + processDefinitionId + "被激活");
        } else {
            // 激活状态，执行挂起
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            System.out.println("流程定义" + processDefinitionId + "被挂起");
        }
    }
}
