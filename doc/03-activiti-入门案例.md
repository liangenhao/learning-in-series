---
title: 03-activiti-入门案例
date: 2019-06-02
categories: Activiti
tags: [Activiti]
---

> 以请假流程为例。

## 流程定义

新建`bpmn`文件，进行流程设计：

![holiday](images/holiday.png)

画出上方的流程图。在每个 `userTask`节点上指定负责人`Assignee`。

> 添加请假单`Assignee`：zhangsan
>
> 部门经理审批`Assignee`：lisi
>
> 总经理审批`Assignee`：wangwu

## 流程定义部署

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

> 结果：
>
> ```
> name:请假单审核流程
> id:1
> key:null
> ```

### 流程定义部署的内部过程

执行的sql：

```mysql
88  2019-06-22 16:09:55,910 2875   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
89  2019-06-22 16:09:55,923 2888   [           main] DEBUG pertyEntityImpl.insertProperty  - ==>
insert into ACT_GE_PROPERTY ( NAME_, VALUE_, REV_ ) values ( 'cfg.execution-related-entities-count', 'false', 1 );
------------------------------------------------------------------------------------------------------------------------
90  2019-06-22 16:09:55,932 2897   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'next.dbid';
------------------------------------------------------------------------------------------------------------------------
91  2019-06-22 16:09:55,935 2900   [           main] DEBUG pertyEntityImpl.updateProperty  - ==>
update ACT_GE_PROPERTY SET REV_ = 2, VALUE_ = '2501'
 WHERE NAME_ = 'next.dbid' and REV_ = 1;
------------------------------------------------------------------------------------------------------------------------
92  2019-06-22 16:09:56,037 3002   [           main] DEBUG ctLatestProcessDefinitionByKey  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null) and VERSION_ = (select max(VERSION_)
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null));
------------------------------------------------------------------------------------------------------------------------
93  2019-06-22 16:09:56,041 3006   [           main] DEBUG ProcessDefinitionKeyNoTenantId  - ==>
select J.*
 FROM ACT_RU_TIMER_JOB J
 INNER JOIN ACT_RE_PROCDEF P
 ON J.PROC_DEF_ID_ = P.ID_
 WHERE J.HANDLER_TYPE_ = 'timer-start-event' and P.KEY_ = 'holiday' and (P.TENANT_ID_ = '' or P.TENANT_ID_ is null);
------------------------------------------------------------------------------------------------------------------------
94  2019-06-22 16:09:56,046 3011   [           main] DEBUG itionInfoByProcessDefinitionId  - ==>
select *
 FROM ACT_PROCDEF_INFO
 WHERE PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
95  2019-06-22 16:09:56,048 3013   [           main] DEBUG tyImpl.insertProcessDefinition  - ==>
insert into ACT_RE_PROCDEF(ID_, REV_, CATEGORY_, NAME_, KEY_, VERSION_, DEPLOYMENT_ID_, RESOURCE_NAME_, DGRM_RESOURCE_NAME_, DESCRIPTION_, HAS_START_FORM_KEY_, HAS_GRAPHICAL_NOTATION_ , SUSPENSION_STATE_, TENANT_ID_, ENGINE_VERSION_) values ('holiday:1:3', 1, 'http://www.activiti.org/test', '请假单审批流程', 'holiday', 1, '1', 'diagram/holiday.bpmn', null, null, false, true, 1, '', null);
------------------------------------------------------------------------------------------------------------------------
96  2019-06-22 16:09:56,050 3015   [           main] DEBUG entEntityImpl.insertDeployment  - ==>
insert into ACT_RE_DEPLOYMENT(ID_, NAME_, CATEGORY_, KEY_, TENANT_ID_, DEPLOY_TIME_, ENGINE_VERSION_) values('1', '请假单审核流程', null, null, '', '2019-06-22 16:09:55.931', null);
------------------------------------------------------------------------------------------------------------------------
97  2019-06-22 16:09:56,051 3016   [           main] DEBUG ourceEntityImpl.insertResource  - ==>
insert into ACT_GE_BYTEARRAY(ID_, REV_, NAME_, BYTES_, DEPLOYMENT_ID_, GENERATED_) values ('2', 1, 'diagram/holiday.bpmn', java.io.ByteArrayInputStream@4218500f, '1', false);
------------------------------------------------------------------------------------------------------------------------

```

影响的表：

- `act_re_deployment`：部署的信息。包括部署的id，部署的名称、部署的key。
- `act_re_procdef`：流程定义的信息。包括流程定义的名称，key
- `act_ge_bytearray`：流程定义的`bpmn`文件（如果配置了图片也会有）



## 启动一个流程实例

> 流程定义好比 Java 中的一个类。
>
> 流程实例好比 Java 中的一个实例（对象）。
>
> 一个流程定义可以对应多个流程实例。

```java
// 1. 创建 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

// 2. 获取 RuntimeService 对象
RuntimeService runtimeService = processEngine.getRuntimeService();

// 3. 创建流程实例
// 需要流程定义的key : 流程定义的bpmn文件的id, 或者查看 act_re_procdef 表的KEY_ 字段。
String processDefinitionKey = "holiday";
ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);

// 4. 输出实例的相关信息
System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
System.out.println("流程实例id : " + processInstance.getId());
System.out.println("当前活动id : " + processInstance.getActivityId());
```

> 结果：
>
> ```
> 流程定义id : holiday:1:3
> 流程实例id : 2501
> 当前活动id : null
> ```

根据流程定义的 key 创建流程实例时，key的获取方式：

- 方式一：流程定义的`.bpmn`文件的id。
- 方式二：查看`act_re_procdef`表的`KEY_`字段。

### 启动流程实例的内部过程

执行的sql：

```mysql
140  2019-06-22 16:14:05,172 1577   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'schema.version';
------------------------------------------------------------------------------------------------------------------------
141  2019-06-22 16:14:05,193 1598   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
142  2019-06-22 16:14:05,198 1603   [           main] DEBUG ctLatestProcessDefinitionByKey  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null) and VERSION_ = (select max(VERSION_)
 FROM ACT_RE_PROCDEF
 WHERE KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null));
------------------------------------------------------------------------------------------------------------------------
143  2019-06-22 16:14:05,202 1607   [           main] DEBUG entEntityImpl.selectDeployment  - ==>
select *
 FROM ACT_RE_DEPLOYMENT
 WHERE ID_ = '1';
------------------------------------------------------------------------------------------------------------------------
144  2019-06-22 16:14:05,205 1610   [           main] DEBUG .selectResourcesByDeploymentId  - ==>
select *
 FROM ACT_GE_BYTEARRAY
 WHERE DEPLOYMENT_ID_ = '1' order by NAME_ asc;
------------------------------------------------------------------------------------------------------------------------
145  2019-06-22 16:14:05,249 1654   [           main] DEBUG ssDefinitionByDeploymentAndKey  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE DEPLOYMENT_ID_ = '1' and KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null);
------------------------------------------------------------------------------------------------------------------------
146  2019-06-22 16:14:05,253 1658   [           main] DEBUG itionInfoByProcessDefinitionId  - ==>
select *
 FROM ACT_PROCDEF_INFO
 WHERE PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
147  2019-06-22 16:14:05,259 1664   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'next.dbid';
------------------------------------------------------------------------------------------------------------------------
148  2019-06-22 16:14:05,261 1666   [           main] DEBUG pertyEntityImpl.updateProperty  - ==>
update ACT_GE_PROPERTY SET REV_ = 3, VALUE_ = '5001'
 WHERE NAME_ = 'next.dbid' and REV_ = 2;
------------------------------------------------------------------------------------------------------------------------
149  2019-06-22 16:14:05,283 1688   [           main] DEBUG mpl.insertHistoricTaskInstance  - ==>
insert into ACT_HI_TASKINST ( ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, CLAIM_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, FORM_KEY_, PRIORITY_, DUE_DATE_, CATEGORY_, TENANT_ID_ ) values ( '2505', 'holiday:1:3', '2501', '2502', '添加请假单', null, null, null, 'zhangsan', '2019-06-22 16:14:05.279', null, null, null, null, '_3', null, 50, null, null, '' );
------------------------------------------------------------------------------------------------------------------------
150  2019-06-22 16:14:05,286 1691   [           main] DEBUG .insertHistoricProcessInstance  - ==>
insert into ACT_HI_PROCINST ( ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, DELETE_REASON_, TENANT_ID_, NAME_ ) values ( '2501', '2501', null, 'holiday:1:3', '2019-06-22 16:14:05.255', null, null, null, '_2', null, null, null, '', null );
------------------------------------------------------------------------------------------------------------------------
151  2019-06-22 16:14:05,307 1712   [           main] DEBUG InsertHistoricActivityInstance  - ==>
insert into ACT_HI_ACTINST ( ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TENANT_ID_ ) values ('2503', 'holiday:1:3', '2501', '2502', '_2', null, null, 'StartEvent', 'startEvent', null, '2019-06-22 16:14:05.266', '2019-06-22 16:14:05.267', 1, null, '') , ('2504', 'holiday:1:3', '2501', '2502', '_3', '2505', null, '添加请假单', 'userTask', 'zhangsan', '2019-06-22 16:14:05.269', null, null, null, '');
------------------------------------------------------------------------------------------------------------------------
152  2019-06-22 16:14:05,309 1714   [           main] DEBUG mpl.insertHistoricIdentityLink  - ==>
insert into ACT_HI_IDENTITYLINK (ID_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, PROC_INST_ID_) values ('2506', 'participant', 'zhangsan', null, null, '2501');
------------------------------------------------------------------------------------------------------------------------
153  2019-06-22 16:14:05,314 1719   [           main] DEBUG EntityImpl.bulkInsertExecution  - ==>
insert into ACT_RU_EXECUTION (ID_, REV_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_,IS_EVENT_SCOPE_, IS_MI_ROOT_, PARENT_ID_, SUPER_EXEC_, ROOT_PROC_INST_ID_, SUSPENSION_STATE_, TENANT_ID_, NAME_, START_TIME_, START_USER_ID_, IS_COUNT_ENABLED_, EVT_SUBSCR_COUNT_, TASK_COUNT_, JOB_COUNT_, TIMER_JOB_COUNT_, SUSP_JOB_COUNT_, DEADLETTER_JOB_COUNT_, VAR_COUNT_, ID_LINK_COUNT_) values ('2501', 1, '2501', null, 'holiday:1:3', null, true, false, true, false, false, null, null, '2501', 1, '', null, '2019-06-22 16:14:05.255', null, false, 0, 0, 0, 0, 0, 0, 0, 0) , ('2502', 1, '2501', null, 'holiday:1:3', '_3', true, false, false, false, false, '2501', null, '2501', 1, '', null, '2019-06-22 16:14:05.265', null, false, 0, 0, 0, 0, 0, 0, 0, 0);
------------------------------------------------------------------------------------------------------------------------
154  2019-06-22 16:14:05,316 1721   [           main] DEBUG tity.TaskEntityImpl.insertTask  - ==>
insert into ACT_RU_TASK (ID_, REV_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_, CLAIM_TIME_) values ('2505', 1, '添加请假单', null, null, 50, '2019-06-22 16:14:05.269', null, 'zhangsan', null, '2502', '2501', 'holiday:1:3', '_3', null, null, 1, '', null, null );
------------------------------------------------------------------------------------------------------------------------
155 2019-06-22 16:14:05,318 1723   [           main] DEBUG kEntityImpl.insertIdentityLink
insert into ACT_RU_IDENTITYLINK (ID_, REV_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, PROC_INST_ID_, PROC_DEF_ID_) values ('2506', 1, 'participant', 'zhangsan', null, null, '2501', null);
------------------------------------------------------------------------------------------------------------------------
```

 影响的表：

- `act_hi_actinst`：历史的活动信息。

  > 该表记录流程流转过的所有节点。

  当前记录：`StartEvent`节点和`添加请假单`节点。

  `StartEvent`节点已经完成，所以有`End_TIME_`。

  `添加请假单`节点尚未完成，所以没有`End_TIME_`。

- `act_hi_identitylink`：历史的参与者信息。

  当前历史参与者：填写请假单`Assignee`：zhangsan。

- `act_hi_procinst`：历史的流程实例。

- `act_hi_taskinst`：历史的任务实例。

  > 该表值记录`usertask`内容。

  当前记录：`填写请假单`任务。

  `填写请假单`任务尚未完成，没有`END_TIME_`。

- `act_ru_execution`：运行的流程执行实例。

- `act_ru_identitylink`：运行的参与者信息。

- `act_ru_task`：运行的任务表。

  增加运行的任务：`添加请假单`。



## 任务查询

流程启动后，各个任务的负责人`Assignee`就可以查询自己当前需要处理的任务，查询出来的任务都是该用户的**待办任务**。

> 这里的任务负责人就是流程定义时，在`userTask`节点上指定的。

```java
// 1. 得到 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. 得到 TaskService 对象
TaskService taskService = processEngine.getTaskService();
// 3. 根据流程定义的key，负责人来实现当前用户的任务列表查询
String processDefinitionKey = "holiday";
String assignee = "zhangsan";
List<Task> taskList = taskService.createTaskQuery()
  .processDefinitionKey(processDefinitionKey)
  .taskAssignee(assignee)
  .list();
// 4. 任务列表的展示
taskList.forEach(task -> {
  System.out.println("流程定义id : " + task.getProcessDefinitionId());
  System.out.println("流程实例id : " + task.getProcessInstanceId());
  System.out.println("任务id : " + task.getId());
  System.out.println("任务负责人 : " + task.getAssignee());
  System.out.println("任务名称 : " + task.getName());
});
```

> 结果：
>
> ```
> 流程定义id : holiday:1:3
> 流程实例id : 2501
> 任务id : 2505
> 任务负责人 : zhangsan
> 任务名称 : 添加请假单
> ```

### 任务查询内部过程

查询sql如下：

```mysql
172  2019-06-22 16:20:57,538 1547   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'schema.version';
------------------------------------------------------------------------------------------------------------------------
173  2019-06-22 16:20:57,562 1571   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
174  2019-06-22 16:20:57,603 1612   [           main] DEBUG Impl.selectTaskByQueryCriteria  - ==>
select distinct RES.*
 FROM ACT_RU_TASK RES
 INNER JOIN ACT_RE_PROCDEF D
 ON RES.PROC_DEF_ID_ = D.ID_
 WHERE RES.ASSIGNEE_ = 'zhangsan' and D.KEY_ = 'holiday' order by RES.ID_ asc
 LIMIT 2147483647 OFFSET 0;
------------------------------------------------------------------------------------------------------------------------

```

查询的表：

- `act_ru_task`：运行的任务表。

- `act_re_procedf`：流程定义的信息。

## 任务处理

任务负责人查询代办任务，然后选择任务进行处理，完成任务。

```java
// 1. 得到 ProcessEngine 对象
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. 得到 TaskService 对象
TaskService taskService = processEngine.getTaskService();
// 3. 处理任务，结合当前用户任务列表的查询，可以得到任务的id: taskId
// 该 taskId 根据 ActivitiTaskQuery 中查询任务列表中获得。
String taskId = "2505";
taskService.complete(taskId);
```



## 任务处理内部过程

执行的sql：

```mysql
178  2019-06-22 16:32:10,808 1533   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'schema.version';
------------------------------------------------------------------------------------------------------------------------
179  2019-06-22 16:32:10,833 1558   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'cfg.execution-related-entities-count';
------------------------------------------------------------------------------------------------------------------------
180  2019-06-22 16:32:10,839 1564   [           main] DEBUG tity.TaskEntityImpl.selectTask  - ==>
select *
 FROM ACT_RU_TASK
 WHERE ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
181  2019-06-22 16:32:10,843 1568   [           main] DEBUG tyImpl.selectProcessDefinition  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
182  2019-06-22 16:32:10,846 1571   [           main] DEBUG entEntityImpl.selectDeployment  - ==>
select *
 FROM ACT_RE_DEPLOYMENT
 WHERE ID_ = '1';
------------------------------------------------------------------------------------------------------------------------
183  2019-06-22 16:32:10,848 1573   [           main] DEBUG .selectResourcesByDeploymentId  - ==>
select *
 FROM ACT_GE_BYTEARRAY
 WHERE DEPLOYMENT_ID_ = '1' order by NAME_ asc;
------------------------------------------------------------------------------------------------------------------------
184  2019-06-22 16:32:10,897 1622   [           main] DEBUG ssDefinitionByDeploymentAndKey  - ==>
select *
 FROM ACT_RE_PROCDEF
 WHERE DEPLOYMENT_ID_ = '1' and KEY_ = 'holiday' and (TENANT_ID_ = '' or TENANT_ID_ is null);
------------------------------------------------------------------------------------------------------------------------
185  2019-06-22 16:32:10,901 1626   [           main] DEBUG itionInfoByProcessDefinitionId  - ==>
select *
 FROM ACT_PROCDEF_INFO
 WHERE PROC_DEF_ID_ = 'holiday:1:3';
------------------------------------------------------------------------------------------------------------------------
186  2019-06-22 16:32:10,903 1628   [           main] DEBUG Impl.selectTasksByParentTaskId  - ==>
select *
 FROM ACT_RU_TASK
 WHERE PARENT_TASK_ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
187  2019-06-22 16:32:10,904 1629   [           main] DEBUG Impl.selectIdentityLinksByTask  - ==>
select *
 FROM ACT_RU_IDENTITYLINK
 WHERE TASK_ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
188  2019-06-22 16:32:10,905 1630   [           main] DEBUG tyImpl.selectVariablesByTaskId  - ==>
select *
 FROM ACT_RU_VARIABLE
 WHERE TASK_ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
189  2019-06-22 16:32:10,908 1633   [           main] DEBUG mpl.selectHistoricTaskInstance  - ==>
select *
 FROM ACT_HI_TASKINST
 WHERE ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
190  2019-06-22 16:32:10,910 1635   [           main] DEBUG tionEntityImpl.selectExecution  - ==>
select *
 FROM ACT_RU_EXECUTION
 WHERE ID_ = '2502';
------------------------------------------------------------------------------------------------------------------------
191  2019-06-22 16:32:10,913 1638   [           main] DEBUG yImpl.selectTasksByExecutionId  - ==>
select distinct T.*
 FROM ACT_RU_TASK T
 WHERE T.EXECUTION_ID_ = '2502';
------------------------------------------------------------------------------------------------------------------------
192  2019-06-22 16:32:10,918 1643   [           main] DEBUG stanceExecutionIdAndActivityId  - ==>
select *
 FROM ACT_HI_ACTINST RES
 WHERE EXECUTION_ID_ = '2502' and ACT_ID_ = '_3' and END_TIME_ is null;
------------------------------------------------------------------------------------------------------------------------
193  2019-06-22 16:32:10,926 1651   [           main] DEBUG pertyEntityImpl.selectProperty  - ==>
select *
 FROM ACT_GE_PROPERTY
 WHERE NAME_ = 'next.dbid';
------------------------------------------------------------------------------------------------------------------------
194  2019-06-22 16:32:10,930 1655   [           main] DEBUG pertyEntityImpl.updateProperty  - ==>
update ACT_GE_PROPERTY SET REV_ = 4, VALUE_ = '7501'
 WHERE NAME_ = 'next.dbid' and REV_ = 3;
------------------------------------------------------------------------------------------------------------------------
195  2019-06-22 16:32:10,945 1670   [           main] DEBUG tionEntityImpl.selectExecution  - ==>
select *
 FROM ACT_RU_EXECUTION
 WHERE ID_ = '2501';
------------------------------------------------------------------------------------------------------------------------
196  2019-06-22 16:32:10,947 1672   [           main] DEBUG IdentityLinksByProcessInstance  - ==>
select *
 FROM ACT_RU_IDENTITYLINK
 WHERE PROC_INST_ID_ = '2501';
------------------------------------------------------------------------------------------------------------------------
197  2019-06-22 16:32:10,952 1677   [           main] DEBUG mpl.insertHistoricTaskInstance  - ==>
insert into ACT_HI_TASKINST ( ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, CLAIM_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, FORM_KEY_, PRIORITY_, DUE_DATE_, CATEGORY_, TENANT_ID_ ) values ( '5002', 'holiday:1:3', '2501', '2502', '部门经理审批', null, null, null, 'lisi', '2019-06-22 16:32:10.944', null, null, null, null, '_4', null, 50, null, null, '' );
------------------------------------------------------------------------------------------------------------------------
198  2019-06-22 16:32:10,955 1680   [           main] DEBUG insertHistoricActivityInstance  - ==>
insert into ACT_HI_ACTINST ( ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TENANT_ID_ ) values ( '5001', 'holiday:1:3', '2501', '2502', '_4', '5002', null, '部门经理审批', 'userTask', 'lisi', '2019-06-22 16:32:10.934', null, null, null, '' );
------------------------------------------------------------------------------------------------------------------------
199  2019-06-22 16:32:10,957 1682   [           main] DEBUG mpl.insertHistoricIdentityLink  - ==>
insert into ACT_HI_IDENTITYLINK (ID_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, PROC_INST_ID_) values ('5003', 'participant', 'lisi', null, null, '2501');
------------------------------------------------------------------------------------------------------------------------
200  2019-06-22 16:32:10,959 1684   [           main] DEBUG tity.TaskEntityImpl.insertTask  - ==>
insert into ACT_RU_TASK (ID_, REV_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_, CLAIM_TIME_) values ('5002', 1, '部门经理审批', null, null, 50, '2019-06-22 16:32:10.934', null, 'lisi', null, '2502', '2501', 'holiday:1:3', '_4', null, null, 1, '', null, null );
------------------------------------------------------------------------------------------------------------------------
201  2019-06-22 16:32:10,961 1686   [           main] DEBUG kEntityImpl.insertIdentityLink  - ==>
insert into ACT_RU_IDENTITYLINK (ID_, REV_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, PROC_INST_ID_, PROC_DEF_ID_) values ('5003', 1, 'participant', 'lisi', null, null, '2501', null);
------------------------------------------------------------------------------------------------------------------------
202  2019-06-22 16:32:10,962 1687   [           main] DEBUG updateHistoricActivityInstance  - ==>
update ACT_HI_ACTINST set EXECUTION_ID_ = '2502', ASSIGNEE_ = 'zhangsan', END_TIME_ = '2019-06-22 16:32:10.92', DURATION_ = 1085651, DELETE_REASON_ = null
 WHERE ID_ = '2504';
------------------------------------------------------------------------------------------------------------------------
203  2019-06-22 16:32:10,964 1689   [           main] DEBUG mpl.updateHistoricTaskInstance  - ==>
update ACT_HI_TASKINST set PROC_DEF_ID_ = 'holiday:1:3', EXECUTION_ID_ = '2502', NAME_ = '添加请假单', PARENT_TASK_ID_ = null, DESCRIPTION_ = null, OWNER_ = null, ASSIGNEE_ = 'zhangsan', CLAIM_TIME_ = null, END_TIME_ = '2019-06-22 16:32:10.91', DURATION_ = 1085631, DELETE_REASON_ = null, TASK_DEF_KEY_ = '_3', FORM_KEY_ = null, PRIORITY_ = 50, DUE_DATE_ = null, CATEGORY_ = null
 WHERE ID_ = '2505';
------------------------------------------------------------------------------------------------------------------------
204  2019-06-22 16:32:10,966 1691   [           main] DEBUG tionEntityImpl.updateExecution  - ==>
update ACT_RU_EXECUTION set REV_ = 2, BUSINESS_KEY_ = null, PROC_DEF_ID_ = 'holiday:1:3', ACT_ID_ = '_4', IS_ACTIVE_ = true, IS_CONCURRENT_ = false, IS_SCOPE_ = false, IS_EVENT_SCOPE_ = false, IS_MI_ROOT_ = false, PARENT_ID_ = '2501', SUPER_EXEC_ = null, ROOT_PROC_INST_ID_ = '2501', SUSPENSION_STATE_ = 1, NAME_ = null, IS_COUNT_ENABLED_ = false, EVT_SUBSCR_COUNT_ = 0, TASK_COUNT_ = 0, JOB_COUNT_ = 0, TIMER_JOB_COUNT_ = 0, SUSP_JOB_COUNT_ = 0, DEADLETTER_JOB_COUNT_ = 0, VAR_COUNT_ = 0, ID_LINK_COUNT_ = 0
 WHERE ID_ = '2502' and REV_ = 1;
------------------------------------------------------------------------------------------------------------------------
205  2019-06-22 16:32:10,966 1691   [           main] DEBUG tity.TaskEntityImpl.deleteTask  - ==>
delete
 FROM ACT_RU_TASK
 WHERE ID_ = '2505' and REV_ = 1;
------------------------------------------------------------------------------------------------------------------------

```

影响的表：

- `act_hi_actinst`：历史的活动信息。

  > 该表记录流程流转过的所有节点。

  当前插入`StartEvent`节点、`添加请假单`节点和`部门经理审批`节点。

  `StartEvent`节点已经完成，所以有`End_TIME_`。

  `添加请假单`节点已经完成，所以有`End_TIME_`。

  `部门经理审批`节点尚未完成，所以没有`End_TIME_`。

- `act_hi_identitylink`：历史的参与者信息。

  当前历史参与者：填写请假单`Assignee`：zhangsan 和 部门经理`Assignee`：lisi。

- `act_hi_taskinst`：历史的任务实例。

  > 该表值记录`usertask`内容。

  当前记录：`填写请假单`任务和`部门经理审批`任务。

  `填写请假单`任务完成，有`END_TIME_`。

  `部门经理审批`任务尚未完成，没有`END_TIME_`。

- `act_ru_execution`：

- `act_ru_identitylink`：变化同`act_hi_identitylink`。

- `act_ru_task`：运行的任务表。

  删除之前任务id为2505的`添加请假单`任务，增加`部门经理审批`任务。