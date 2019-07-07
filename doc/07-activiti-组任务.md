---
title: 07-activiti-组任务
date: 2019-07-07
categories: learning-in-activiti
tags: [Activiti]
---

## Candidate-users:候选人

> 工程：`activity-sample`
>
> 类：`com.enhao.learning.in.activiti.candidate_users.CandidateUsersSample`

### 设置任务候选人

在之前的案例中，流程定义中`userTask`节点都是设置固定的任务负责人。

还有一种方式，可以给任务设置多个候选人，可以从候选人中选择一个人来完成任务。

在`bpmn`流程图中的任务节点`userTask`中设置候选人`candidate-users`。多个候选人之间用逗号分开。

例如：

```xml
<userTask activiti:assignee="${assignee1}" activiti:exclusive="true" id="_3" name="添加请假单"/>
<userTask activiti:candidateUsers="${candidate_user_1},${candidate_user_2}" activiti:exclusive="true" id="_4" name="部门经理审批"/>
<userTask activiti:assignee="${assignee3}" activiti:exclusive="true" id="_5" name="总经理审批"/>
<endEvent id="_6" name="EndEvent"/>
```

> 第二个`userTask`节点设置两个候选人。

### 办理组任务

#### 组任务办理流程

1. 第一步：查询组任务

   指定候选人，查询该候选人当前的待办任务。

   **候选人不能办理任务。**

2. 第二步：拾取任务

   该组任务的所有候选人都能拾取任务。

   将候选人的组任务变成个人任务。原来候选人就变成了该任务的负责人。

   > 如果拾取后不想办理该任务，需要将已经拾取的个人任务归还到组里，将个人任务变成组任务。

3. 第三步：查询个人任务

   查询方式同个人任务部分，根据`assignee`查询用户负责的个人任务。

4. 第四步：办理个人任务。

#### 查询并拾取组任务

```java
private static void claimTask(String candidateUser) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  // 查询候选人的组任务
  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskCandidateUser(candidateUser) // 候选人
    .list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);
    System.out.println(candidateUser + "的组任务 : ");
    System.out.println("流程实例id : " + task.getProcessInstanceId());
    System.out.println("任务id : " + task.getId());
    System.out.println("任务名称 : " + task.getName());

    // 拾取任务
    taskService.claim(task.getId(), candidateUser);
    System.out.println(candidateUser + "拾取任务成功!");
  }
}
```

#### 查询并办理个人任务

和之前的案例一样。

```java
private static void completeTask(String assignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskAssignee(assignee).list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);
    taskService.complete(task.getId());
    System.out.println(assignee + "一个任务执行完毕");
  }
}
```

#### 归还组任务

如果个人不想办理该组任务，可以将该任务归还组任务，归还后该用户不再是该任务的负责人。

因此**归还组任务的方式就是将该任务的`assignee`设置为null。**

```java
private static void returnTaskToGroupTask(String assignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskAssignee(assignee).list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);
    // 归还任务 : 只需要将 assignee 设置为 null
    taskService.setAssignee(task.getId(), null);
    System.out.println(assignee + "一个任务归还成功!");
  }
}
```

测试：

```java
// 启动一个流程实例，并设置流程变量
startProcessInstance();

// 执行任务
// 填写请假单
completeTask("zhangsan");

// 部门经理审批
// 候选人拾取组任务
String candidateUser1 = "lisi";
claimTask(candidateUser1);
// 归还任务
returnTaskToGroupTask(candidateUser1);
// 其他候选人拾取组任务
String candidateUser2 = "wangwu";
claimTask(candidateUser2);
// 候选人完成任务
completeTask(candidateUser2);

// 总经理审批
completeTask("zhaoliu");
```

#### 任务交接

任务交接是任务负责人将任务交给其他候选人办理该任务。

就是将该任务的`assignee`设置为需要交接的其他候选人。

```java
private static void handoverTask(String assignee, String handoverAssignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskAssignee(assignee).list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);
    // 任务交接 : 只需要将 assignee 设置为 交接人
    taskService.setAssignee(task.getId(), handoverAssignee);
    System.out.println(assignee + "一个任务交接给" + handoverAssignee + "成功!");
  }
}
```

测试：

```java
// 启动一个流程实例，并设置流程变量
startProcessInstance();

// 执行任务
// 填写请假单
completeTask("zhangsan");

// 部门经理审批
String candidateUser1 = "lisi";
String candidateUser2 = "wangwu";
// 候选人1拾取组任务
claimTask(candidateUser1);
// 交接任务
handoverTask(candidateUser1, candidateUser2);
// 候选人2完成任务
completeTask(candidateUser2);
```



#### 操作的数据表

- `act_ru_task`：当前执行的任务，若任务是组任务，则`ASSIGNEE_`为空，当拾取任务后该字段就是拾取的用户。

- `act_ru_identitylink`/`act_hi_identitylink`：任务参与者。

  候选人的`TYPE_`为：`candidate`。

  参与者的`TYPE_`为：`participant`。