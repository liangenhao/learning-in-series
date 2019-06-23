package com.enhao.learning.in.activiti.process_definition;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;

/**
 * 流程定义信息删除
 * 删除已经部署的流程定义
 *
 * @author enhao
 */
public class ProcessDefinitionDelete {

    public static void main(String[] args) {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 创建 RepositoryService 对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3. 执行删除流程定义
        // 流程部署id : 通过 流程定义信息查询 可以获得
        String deploymentId = "1";
        repositoryService.deleteDeployment(deploymentId);
    }

}
