package com.enhao.learning.in.activiti.holiday_process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 部署请假流程的定义
 *  注意：每个流程定义部署一次就行
 *
 * @author enhao
 */
public class ActivitiDeployment {

    public static void main(String[] args) {
        // 方式一：classpath 方式
        // deploymentByClasspathResource();
        // 方式二：读取 inputStream 方式
        // deploymentByInputStream();
        // 方式三：压缩包方式
        deploymentByZip();
    }

    /**
     * 通过读取压缩包方式进行流程定义部署
     */
    private static void deploymentByZip() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 部署
        InputStream inputStream = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holiday.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假单审核流程")
                .deploy();

        // 4. 获取部署的一些信息
        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
        System.out.println("key:" + deployment.getKey());
    }

    /**
     * 通过读取资源的输入流方式进行流程定义部署
     */
    private static void deploymentByInputStream() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 部署
        InputStream bpmnInputStream = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holiday.bpmn");
        InputStream imageInputStream = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holiday.png");
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream("holiday.bpmn", bpmnInputStream)
                .addInputStream("holiday.png", imageInputStream)
                .name("请假单审核流程")
                .deploy();

        // 4. 获取部署的一些信息
        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
        System.out.println("key:" + deployment.getKey());
    }

    /**
     * 通过读取 classpath 下的资源方式进行流程定义部署
     */
    private static void deploymentByClasspathResource() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday.bpmn")
                .addClasspathResource("diagram/holiday.png")
                .name("请假单审核流程")
                .deploy();

        // 4. 获取部署的一些信息
        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
        System.out.println("key:" + deployment.getKey());
    }


}
