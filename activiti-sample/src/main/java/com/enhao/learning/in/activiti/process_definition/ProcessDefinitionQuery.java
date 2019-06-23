package com.enhao.learning.in.activiti.process_definition;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 流程定义信息查询
 *
 * @author enhao
 */
public class ProcessDefinitionQuery {

    public static void main(String[] args) {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 创建 RepositoryService 对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3. 得到 ProcessDefinitionQuery 对象
        org.activiti.engine.repository.ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 4. 设置条件
        String processDefinitionKey = "holiday";
        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();

        definitionList.forEach(processDefinition -> {
            System.out.println("流程定义id : " + processDefinition.getId());
            System.out.println("流程定义名称 : " + processDefinition.getName());
            System.out.println("流程定义的key : " + processDefinition.getKey());
            System.out.println("流程定义的版本号 : " + processDefinition.getVersion());
            System.out.println("流程部署id : " + processDefinition.getDeploymentId());
        });

    }
}
