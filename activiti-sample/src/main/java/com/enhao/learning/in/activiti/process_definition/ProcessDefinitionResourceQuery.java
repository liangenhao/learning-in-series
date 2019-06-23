package com.enhao.learning.in.activiti.process_definition;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 流程定义资源查询
 * bpmn 和 png
 *
 * @author enhao
 */
public class ProcessDefinitionResourceQuery {

    public static void main(String[] args) throws IOException {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 创建 RepositoryService 对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3. 创建 ProcessDefinitionQuery 对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 4. 设置查询条件
        String processDefinitionKey = "holiday";
        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionKey(processDefinitionKey)
                .singleResult();

        // 5. 流程定义的部署id
        String deploymentId = processDefinition.getDeploymentId();

        // 6. 读取bpmn文件和图片信息流
        // 参数1 : 部署id
        // 参数2 : 资源名称
        // bpmn文件名称
        String resourceName = processDefinition.getResourceName();
        // 图片资源名称
        String diagramResourceName = processDefinition.getDiagramResourceName();
        // 获取流对象
        InputStream bpmnInputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        InputStream diagramInputStream = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);

        OutputStream bpmnOs =
                new FileOutputStream(resourceName);
        OutputStream pngOs =
                new FileOutputStream(diagramResourceName);


        //7.输入流，输出流的转换
        IOUtils.copy(bpmnInputStream, bpmnOs);
        IOUtils.copy(diagramInputStream, pngOs);
        //8.关闭流
        pngOs.close();
        bpmnOs.close();
        bpmnInputStream.close();
        diagramInputStream.close();


    }
}
