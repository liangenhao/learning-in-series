---
title: 03-activiti-入门案例
date: 2019-06-02
categories: Activiti
tags: [Activiti]
---

> 以请假流程为例。

## 流程定义

### 定义流程

新建`bpmn`文件，进行流程设计：

![holiday](images/holiday.png)

### 流程定义部署

部署流程定义，就是将上面绘制的流程定义`.bpmn`部署在工作流引擎 activiti 中。

```java
// 1. 创建 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. 获取 RepositoryService
RepositoryService repositoryService = processEngine.getRepositoryService();
// 3. 部署
Deployment deployment = repositoryService.createDeployment()
  .addClasspathResource("diagram/holiday.bpmn")
  .name("请假单审核流程")
  .deploy();

// 4. 获取部署的一些信息
System.out.println("name:" + deployment.getName());
System.out.println("id:" + deployment.getId());
System.out.println("key:" + deployment.getKey());
```

### 流程定义部署的内部过程

影响的表：

- `act_re_deployment`：部署的信息。包括部署的id，部署的名称、部署的key。
- `act_re_procdef`：流程定义的信息。包括流程定义的名称，key
- `act_ge_bytearray`：流程定义的`bpmn`文件（如果配置了图片也会有）



 