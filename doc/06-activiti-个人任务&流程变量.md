---
title: 06-activiti-个人任务&流程变量
date: 2019-06-29
categories: learning-in-activiti
tags: [Activiti]
---

## 个人任务

### 分配任务负责人

#### 固定分配方式

在 [入门案例](03-activiti-入门案例.md) 中，我们绘制流程定义图时，在每个`usertask`节点上写入固定的`Assignee`就属于固定分配任务负责人。

#### 表达式分配方式

##### UEL表达式

> UEL：Unified Expression Language，统一表达式语言。
>
> UEL 时 Java EE 6 规范的一部分。

Activiti 支持两个 UEL 表达式：`UEL-value`和`UEL-method`。

【UEL-value】：有两种写法：

![UEL-value-1](images/UEL-value-1.png)

> `assignee`这个变量是 activiti 的一个**流程变量**。

![UEL-value-2](images/UEL-value-2.png)

> `user`是 activiti 的一个**流程变量**，`user.assignee`表示通过调用`user`的`getter`方法获取值。

【UEL-method】：

![UEL-method](images/UEL-method.png)

> `holidayBean`是spring容器中的一个bean，表示调用该bean的`getHolidayId()`方法。

【UEL-method 与 UEL-value 结合】：

`${IdapService.findManagerForEmployee(emp)}`：

`IdapService`是Spring容器中的一个bean，`findManagerForEmployee()`是该bean的方法。`emp`是activiti的一个流程变量。`emp`作为参数传递到`findManagerForEmployee()`方法中。

【其他】：

表达式支持解析基础类型、`bean`、`list`、`array`和`map`，也可作为条件判断：

`${order.price > 100 && order.price < 250}`

##### 测试

> 工程：`activity-sample`
>
> 包：`com.enhao.learning.in.activiti.persional_task`

首先，绘制流程定义图`holiday_UEL.bpmn`，id为`holiday_UEL`，即`processDefinitionKey`。

依次指定三个`usertask`的`assignee`分别为`${assignee1}`、`${assignee2}`、`${assignee3}`。

然后编写代码，部署流程定义并启动一个流程实例，并查看效果：

```java
/**
 * 通过UEL表达式分配任务负责人
 *
 * @author enhao
 */
public class AssigneeByUEL {

    public static void main(String[] args) {
        // 部署流程定义，部署只需要执行一次
        // 流程定义是 holiday_UEL.bpmn
        deployProcessDefinition();

        // 启动流程实例
        startProcessInstance();
    }

    private static void deployProcessDefinition() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday_UEL.bpmn")
                .name("请假单审核流程(UEL)")
                .deploy();

        // 4. 获取部署的一些信息
        System.out.println("name:" + deployment.getName());
        System.out.println("id:" + deployment.getId());
        System.out.println("key:" + deployment.getKey());
    }

    private static void startProcessInstance() {
        // 1. 创建 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取 RuntimeService 对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3. 设置 assignee 的取值
        Map<String, Object> map = new HashMap<>();
        map.put("assignee1", "zhangsan1");
        map.put("assignee2", "lisi1");
        map.put("assignee3", "wangwu1");

        // 4. 启动流程实例
        // 需要流程定义的key : 流程定义的bpmn文件的id, 或者查看 act_re_procdef 表的KEY_ 字段。
        String processDefinitionKey = "holiday_UEL";
        // 业务主键
        String businessKey = "1001";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, map);

        // 4. 输出实例的相关信息
        System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id : " + processInstance.getId());
        System.out.println("当前活动id : " + processInstance.getActivityId());
        System.out.println("业务主键 : " + processInstance.getBusinessKey());
    }
}
```

> 查看`act_hi_identitylink`就可以看到`assignee`有一个`zhangsan1`。

#### 监听器分配方式

任务监听器是发生对应的任务相关事件时执行自定义 Java 逻辑 或表达式。

事件包括：

- `Create`：任务创建后触发。
- `Assignment`：任务分配后触发。
- `Delete`：任务完成后触发。
- `All`：所有事件发生都触发。

事件发生后执行Java逻辑或者UEL表达式。

Java逻辑通过任务监听器触发：

```java
public class MyTaskListener implments TaskListener {
  
  @Override
  public void notify(DelegateTask delegateTask) {
    // 指定任务负责人
    delegateTask.setAssignee("zhangsan");
  }
}
```



### 查询任务&任务办理

详见 [入门案例](03-activiti-入门案例.md) 中的任务查询和任务处理。



## 流程变量

### 流程变量类型

| 类型名称     | 描述                   |
| ------------ | ---------------------- |
| string       | `java.lang.String`     |
| integer      | `java.lang.Integer`    |
| short        | `java.lang.Short`      |
| long         | `java.lang.Long`       |
| double       | `java.lang.Double`     |
| boolean      | `java.lang.Boolean`    |
| date         | `java.util.Date`       |
| binary       | 二进制文件，字节数组。 |
| serializable | 序列化                 |

> 如果将pojo存储到流程变量中，必须实现序列化接口`Serializable`，并生成`serialVersionUID`。

### 流程变量作用域

作用域默认是**一个流程实例（Process Instance）**，也可以是一个任务（Task）或一个执行实例（execution）。

这三个作用域中，流程实例的范围最大，可以称为 **global 变量**，任务和执行实例仅仅针对一个任务和一个执行实例范围，范围没有流程实例大，称为 **local 变量**。

- global 变量中变量名不允许重复，设置相同名称的变量，后设置的值会覆盖前设置的变量值。

- local 变量由于在不同的任务或不同的执行实例中，作用域互不影响，变量名可以相同没有影响。
- local 变量名也可以和 global 变量名相同，没有影响。

### 流程变量使用方式

【第一步】：设置流程变量。global 变量或 local 变量。

【第二步】：通过 UEL 表达式使用流程变量。

1. 可以在任务分配`assignee`处设置 UEL 表达式，表达式的值为任务的负责人。

   `${assignee}`，其中`assignee`就是一个流程变量名称。

2. 可以在连线上设置 UEL 表达式，决定流程走向。

   `${price>=10000}`，`price`就是一个流程变量名称，UEL 表达式结果类型为布尔类型。

#### 流程变量操作的数据库表

- `act_ru_variable`：当前流程变量表。

  记录当前运行流程实例可使用的流程变量，包括 global 和 local 变量。

  | 列名          | 描述                                                      |
  | ------------- | --------------------------------------------------------- |
  | Id_           | 主键                                                      |
  | Type_         | 变量类型（上面表格列出的9种）                             |
  | Name_         | 变量名称（key）                                           |
  | Execution_id_ | 所属流程实例执行id，global 和 local 变量都存储            |
  | Proc_inst_id_ | 所属流程实例id，global 和 local 变量都存储                |
  | Task_id_      | 所属任务id，local 变量存储                                |
  | Bytearray_    | serializable 类型变量存储对应`act_ge_bytearray`表的主键id |
  | Double_       | double 类型变量的值                                       |
  | Long_         | long 类型变量的值                                         |
  | Text_         | text 类型变量的值                                         |

- `act_hi_varinst`：历史流程变量表。

- `act_ge_bytearray`：存储 serializable 类型变量存储的值。

#### 设置 global 变量控制流程

> 工程：`activity-sample`
>
> 包：`com.enhao.learning.in.activiti.process_variable`

##### 启动流程时设置

> 案例类：`GlobalVariableInStartProcessInstanceSample`

在启动流程时设置流程变量，变量的作用域是**整个流程实例**。

启动流程实例方法`startProcessInstanceByKey()`的重载方法，有一个参数是`Map`类型，就是用来设置流程变量。

【需求】：员工创建请假申请单，由部门经理审核，部门经理审核通过后请假3天及以下由人事经理直接审核，3天以上先由总经理审核，总经理审核通过再由人事经理存档。

![holiday_variable](images/holiday_variable.png)

部署流程定义：

```java
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
RepositoryService repositoryService = processEngine.getRepositoryService();

Deployment deployment = repositoryService.createDeployment()
  .addClasspathResource("diagram/holiday_variable.bpmn")
  .addClasspathResource("diagram/holiday_variable.png")
  .name("请假流程-流程变量")
  .deploy();

System.out.println("name:" + deployment.getName());
System.out.println("id:" + deployment.getId());
```

定义po：

```java
public class Holiday implements Serializable {
    private Integer id;
    private String assigneeName;//申请人的名字
    private Date beginDate;//开始时间
    private Date endDate;//结束日期
    private Float num;//请假天数
    private String reason;//事由
    private String type;//请假类型
  	// getter/setter
}
```

> 注意这里的`Holiday`类一定要实现`Serializable`接口。

启动一个流程实例：

```java
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
RuntimeService runtimeService = processEngine.getRuntimeService();

// 设置流程变量
Map<String, Object> variables = new HashMap<>();
Holiday holiday = new Holiday();
holiday.setNum(1F);
variables.put("holiday", holiday);
variables.put("assignee1", "zhangsan"); // 填写请假单assignee
variables.put("assignee2", "lisi"); // 部门经理审核assignee
variables.put("assignee3", "wangwu"); // 总经理审核assignee
variables.put("assignee4", "zhaoliu"); // 人事经理审核assignee

private static String processDefinitionKey = "holiday_variable";
ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
System.out.println("流程实例id : " + processInstance.getId());
```

> 流程变量操作的表：
>
> - `act_hi_varinst`：历史流程变量实例。
> - `act_ru_variable`：运行时流程变量实例。
> - `act_ge_bytearray`：字节数组，存流程变量字节数组的值。

执行任务：

```java
private static void completeTask(String assignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  private static String processDefinitionKey = "holiday_variable";
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

整体执行流程：

```java
public static void main(String[] args) {
  // 流程定义部署，只要执行一次
  // deployProcessDefinition();

  // 启动一个流程实例，并设置流程变量
  startProcessInstance();

  // 执行任务
  // 填写请假单
  completeTask("zhangsan");
  // 部门经理审批
  completeTask("lisi");
  // 总经理审批
  completeTask("wangwu");
  // 人事经理审批
  completeTask("zhaoliu");
}
```

查看结果：

当设置`holiday`的`num`的值小于等于3时，执行流程中没有总经理审批。`num`的值大于3时，有总经理审批。

##### 任务办理时设置

> 案例类：`GlobalVariableInCompleteTaskSample`

在完成任务时设置流程变量，该流程变量**只有在该任务完成后其他节点才可以使用**，它的**作用域是整个流程实例**，如果设置的流程变量的key在流程实例中已存在相同的名称，则后设置的变量替换前面设置的变量。

任务完成方法`complete()`的重载方法，有一个参数是`Map`类型，就是用来设置流程变量：

可以修改上面的案例，启动流程实例时不设置`holiday`流程变量，在完成任务时设置：

```java
private static void completeTask(String assignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskAssignee(assignee).list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);
    // taskService.complete(task.getId());
    // 可以在完成任务时设置流程实例
    Holiday holiday = new Holiday();
    holiday.setNum(4F);
    Map<String, Object> variables = new HashMap<>();
    variables.put("holiday", holiday);
    taskService.complete(task.getId(), variables);
    System.out.println(assignee + "一个任务执行完毕");
  }
}
```

##### 通过当前流程实例设置

> 案例类：`GlobalVariableByProcessInstanceIdSample`

通过流程实例id设置 global 变量，该流程实例必须未执行完成。

使用`runtimeService.setVariable()`方法设置当前流程实例的 global 流程变量。

```java
private static void startProcessInstance() {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  RuntimeService runtimeService = processEngine.getRuntimeService();

  // 启动流程实例时设置流程变量
  Map<String, Object> variables = new HashMap<>();

  variables.put("assignee1", "zhangsan"); // 填写请假单assignee
  variables.put("assignee2", "lisi"); // 部门经理审核assignee
  variables.put("assignee3", "wangwu"); // 总经理审核assignee
  variables.put("assignee4", "zhaoliu"); // 人事经理审核assignee

  ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

  System.out.println("流程定义id : " + processInstance.getProcessDefinitionId());
  System.out.println("流程实例id : " + processInstance.getId());

  // 通过流程实例id，设置流程变量
  Holiday holiday = new Holiday();
  holiday.setNum(1F);
  // 如果有多个变量，可以调用 org.activiti.engine.impl.RuntimeServiceImpl.setVariables(String, Map)
  runtimeService.setVariable(processInstance.getId(), "holiday", holiday);

}
```

##### 通过当前任务设置

> 案例类：`GlobalVariableByTaskIdSample`

```java
private static void completeTask(String assignee) {
  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  TaskService taskService = processEngine.getTaskService();

  List<Task> taskList = taskService.createTaskQuery()
    .processDefinitionKey(processDefinitionKey)
    .taskAssignee(assignee).list();

  if (taskList.size() > 0) {
    Task task = taskList.get(0);

    // 通过任务id，设置流程变量
    Holiday holiday = new Holiday();
    holiday.setNum(4F);
    taskService.setVariable(task.getId(), "holiday", holiday);

    taskService.complete(task.getId());
    System.out.println(assignee + "一个任务执行完毕");
  }
}
```

##### 注意事项

1. 如果 UEL 表达式中流程变量名不存在则报错。
2. 如果 UEL 表达式中流程变量值为空NULL，流程不按 UEL 表达式去执行，而流程结束。
3. 如果 UEL 表达式都不符合条件，流程结束。
4. 如果连线不设置条件，会走 flow 需要小的那条线。

#### 设置 local 变量控制流程

TODO

##### 任务办理时设置

TODO

##### 通过当前任务设置

TODO