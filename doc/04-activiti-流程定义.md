---
title: 04-activiti-流程定义
date: 2019-06-23
categories: Activiti
tags: [Activiti]
---

> 工程：`activity-sample`
>
> 包：`com.enhao.learning.in.activiti.process_definition`

在 [入门案例](03-activiti-入门案例.md) 中，我们知道通过`bpmn`进行流程定义设计和流程定义的部署。

## 流程定义部署方式

在 [入门案例](03-activiti-入门案例.md) 中，我们采用读取`classpath`下的资源路径方式部署流程定义。其实还有其他方式。

### ClasspathResource方式

> 该方式即入门案例的方式。

```java
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
```

### InputStream方式

> 该方式和ClasspathResource方式差不多，都属于单文件部署方式。

```java
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
```

### 压缩包方式

> 该方式不同于以上两种方式。

```java
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
```

## 流程定义查询



## 流程定义删除





## 流程定义资源查询



## 流程历史信息查看