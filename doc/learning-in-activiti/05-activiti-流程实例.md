---
title: 05-activiti-流程实例
date: 2019-06-24
categories: learning-in-activiti
tags: [Activiti]
---

> 工程：`activity-sample`
>
> 包：`com.enhao.learning.in.activiti.process_instance`

## 流程实例

在 [入门案例](03-activiti-入门案例.md) 中，当我们完成流程定义后，启动了一个流程实例。

流程实例是一个具体的工作流。

对于同一个流程定义，可以同时启动多个流程实例。每个流程实例执行互不影响。

## 整合业务系统

在一个具体的系统中，针对具体的业务都会有相应的业务表。

如果想讲具体的业务和工作流关联上。就需要在启动流程实例时，指定具体的`businessKey`。

`businessKey`通常为业务表的主键。业务标识与流程实例一一对应：

```java
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

```

> 上面的代码与 [入门案例](03-activiti-入门案例.md) 的唯一不同是，调用`startProcessInstanceByKey()`方法时，第二个参数指定了业务主键。

操作的数据表：

- `act_ru_execution`：流程实例执行，如果当前只有一个分支时，一个流程实例只有一条记录且执行表的主键 id 和流程实例 id 相同；如果当前有多个分支正在运行则该执行表中有多条记录，存在执行表的主键和流程实例id 不相同的记录。**不论当前有几个分支总会有一条记录的执行表的主键和流程实例 id 相同**。一个流程实例运行完成，此表中与流程实例相关的记录删除。

- `act_ru_task`：指定任务表。

  记录当前执行的任务。启动流程实例。流程当前执行到第一个任务结点，此表会插入一条记录表示当前任务的执行 情况，如果任务完成则记录删除。 

- `act_ru_identitylink`：任务参与者。

- `act_hi_procinst`：流程实例历史表。

  启动流程实例，会在此表中插入一条数据。流程实例运行完成不会删除。

- `act_hi_taskinst`：历史任务表。记录所有任务。

  开始一个任务，不仅在 act_ru_task 表插入记录，也会在历史任务表插入一条记录，任务历史表的主键就是任务 id，任务完成此表记录不删除。

- `act_hi_actinst`：活动历史表。

  活动包括任务，所以此表中不仅记录了任务，还记录了流程执行过程的其它活动，比如开始事件、 结束事件。 

## 流程实例的挂起和激活

### 全部流程实例的挂起和激活

```java
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
```



### 单个流程实例的挂起和激活

```java
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
```

若对挂起的流程实例执行任务，则会抛出`ActivitiException:Cannot complete a suspended task`的异常。