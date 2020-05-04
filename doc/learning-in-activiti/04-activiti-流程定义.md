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

```java
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
```

> 结果：
>
> ```
> 流程定义id : holiday:1:3
> 流程定义名称 : 请假单审批流程
> 流程定义的key : holiday
> 流程定义的版本号 : 1
> 流程部署id : 1
> ```

执行的sql：

```mysql
215  2019-06-23 14:43:48,011 1886   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'schema.version';
------------------------------------------------------------------------------------------------------------------------
216  2019-06-23 14:43:48,038 1913   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
217  2019-06-23 14:43:48,072 1947   [           main] DEBUG cessDefinitionsByQueryCriteria  - ==>
select distinct RES.*
 FROM ACT_RE_PROCDEF RES
 WHERE RES.KEY_ = 'holiday' order by RES.VERSION_ desc
 LIMIT 2147483647 OFFSET 0;
------------------------------------------------------------------------------------------------------------------------

```



## 流程定义删除

```java
// 1. 得到 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. 创建 RepositoryService 对象
RepositoryService repositoryService = processEngine.getRepositoryService();

// 3. 执行删除流程定义
// 流程部署id : 通过 流程定义信息查询 可以获得
String deploymentId = "1";
repositoryService.deleteDeployment(deploymentId);
```

注意：

- 只有指定流程定义下没有正在运行的流程，可以使用普通删除（非级联删除）。

- 如果执行流程定义下存在已经运行的流程，使用普通删除会报错。

  可以使用级联删除方式将流程及其相关记录全部删除：

  `repositoryService.deleteDeployment(deploymentId, true);`

普通删除执行的sql：

```mysql
304  2019-06-23 15:41:17,459 1476   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'schema.version';
------------------------------------------------------------------------------------------------------------------------
305  2019-06-23 15:41:17,479 1496   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
306  2019-06-23 15:41:17,483 1500   [           main] DEBUG entEntityImpl.selectDeployment  - ==>
select *
 FROM ACT_RE_DEPLOYMENT
 WHERE ID_ = '1';
------------------------------------------------------------------------------------------------------------------------
307  2019-06-23 15:41:17,514 1531   [           main] DEBUG cessDefinitionsByQueryCriteria  - ==>
select distinct RES.*
 FROM ACT_RE_PROCDEF RES
 WHERE RES.DEPLOYMENT_ID_ = '1' order by RES.ID_ asc
 LIMIT 2147483647 OFFSET 0;
------------------------------------------------------------------------------------------------------------------------
308  2019-06-23 15:41:17,518 1535   [           main] DEBUG pl.selectModelsByQueryCriteria  - ==>
select distinct RES.*
 FROM ACT_RE_MODEL RES
 WHERE RES.DEPLOYMENT_ID_ = '1' order by RES.ID_ asc
 LIMIT 2147483647 OFFSET 0;
------------------------------------------------------------------------------------------------------------------------
309  2019-06-23 15:41:17,519 1536   [           main] DEBUG itionInfoByProcessDefinitionId  - ==>
select *
 FROM ACT_PROCDEF_INFO
 WHERE PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
310  2019-06-23 15:41:17,522 1539   [           main] DEBUG obByTypeAndProcessDefinitionId  - ==>
select J.*
 FROM ACT_RU_TIMER_JOB J
 WHERE J.HANDLER_TYPE_ = 'timer-start-event' and J.PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
311  2019-06-23 15:41:17,524 1541   [           main] DEBUG ctLatestProcessDefinitionByKey  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null) and VERSION_ = (select max(VERSION_)
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null));
------------------------------------------------------------------------------------------------------------------------
312  2019-06-23 15:41:17,526 1543   [           main] DEBUG cessDefinitionsByQueryCriteria  - ==>
select distinct RES.*
 FROM ACT_RE_PROCDEF RES
 WHERE RES.KEY_ = 'holiday' and RES.VERSION_ < 1 and (RES.TENANT_ID_ = '' or RES.TENANT_ID_ is null) order by RES.VERSION_ desc
 LIMIT 1 OFFSET 0;
------------------------------------------------------------------------------------------------------------------------
313  2019-06-23 15:41:17,528 1545   [           main] DEBUG .deleteResourcesByDeploymentId  - ==>
delete
 FROM ACT_GE_BYTEARRAY
 WHERE DEPLOYMENT_ID_ = '1';
------------------------------------------------------------------------------------------------------------------------
314  2019-06-23 15:41:17,529 1546   [           main] DEBUG entEntityImpl.deleteDeployment  - ==>
delete
 FROM ACT_RE_DEPLOYMENT
 WHERE ID_ = '1';
------------------------------------------------------------------------------------------------------------------------
315  2019-06-23 15:41:17,530 1547   [           main] DEBUG scriptionsForProcessDefinition  - ==>
delete
 FROM ACT_RU_EVENT_SUBSCR
 WHERE PROC_DEF_ID_ = 'holiday:1:3' and EXECUTION_ID_ is null and PROC_INST_ID_ is null;
------------------------------------------------------------------------------------------------------------------------
316  2019-06-23 15:41:17,531 1548   [           main] DEBUG pl.deleteIdentityLinkByProcDef  - ==>
delete
 FROM ACT_RU_IDENTITYLINK
 WHERE PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
317  2019-06-23 15:41:17,532 1549   [           main] DEBUG ocessDefinitionsByDeploymentId  - ==>
delete
 FROM ACT_RE_PROCDEF
 WHERE DEPLOYMENT_ID_ = '1';
------------------------------------------------------------------------------------------------------------------------

```

普通删除操作的表：

- `act_ge_bytearray`
- `act_re_deployment`
- `act_ru_identitylink`
- `act_re_prodef`

> 不管是普通删除还是记录删除，`act_hi_*`表的内容都不会被删除。
>
> 级联删除时，会删除`act_ru_*`表（即运行中的记录）数据。

## 流程定义资源查询

> 流程定义资源查询，主要针对`bpmn`文件和`png`文件。

由于在资源定义部署时，`bpmn`文件和`png`文件内容都存放在`act_ge_bytearray`表中，所有查询操作主要是查该表的内容。

```java
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
```



## 流程历史信息查询

历史信息，都在表`act_hi_*`表中。

```java
// 1. 得到 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. 创建 HistoryService 对象
HistoryService historyService = processEngine.getHistoryService();

// 3. 得到查询对象
HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
// 流程实例id
String processInstanceId = "2501";
List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.processInstanceId(processInstanceId)
  .orderByHistoricActivityInstanceStartTime()
  .asc()
  .list();

historicActivityInstanceList.forEach(historicActivityInstance -> {
  System.out.println("流程定义id : " + historicActivityInstance.getProcessDefinitionId());
  System.out.println("流程实例id : " + historicActivityInstance.getProcessInstanceId());
  System.out.println("活动id : " + historicActivityInstance.getActivityId());
  System.out.println("活动名称 : " + historicActivityInstance.getActivityName());
  System.out.println("============");
});
```

