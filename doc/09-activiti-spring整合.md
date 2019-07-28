---
title: 09-activiti-spring整合
date: 2019-07-09
categories: learning-in-activiti
tags: [Activiti]
---

## 依赖

同 [环境搭建](doc/02-activiti-环境搭建.md) 中的依赖一致。

## 配置文件

```xml
<!-- 数据库连接池 -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
  <property name="url" value="jdbc:mysql://localhost:3306/activiti"/>
  <property name="username" value="root"/>
  <property name="password" value="root"/>
</bean>

<!-- 工作流引擎配置bean -->
<bean id="processEngineConfiguration" class="org.activiti.spring.SpringProcessEngineConfiguration">
  <!-- 数据源 -->
  <property name="dataSource" ref="dataSource"/>
  <!-- 使用spring事务管理器 -->
  <property name="transactionManager" ref="transactionManager"/>
  <!-- 数据库策略 -->
  <property name="databaseSchemaUpdate" value="true"/>
</bean>

<!-- 流程引擎 -->
<bean id="processEngine" class="org.activiti.spring.ProcessEngineFactoryBean">
  <property name="processEngineConfiguration" ref="processEngineConfiguration"/>
</bean>

<!-- 资源服务service -->
<bean id="repositoryService" factory-bean="processEngine"
      factory-method="getRepositoryService"/>
<!-- 流程运行service -->
<bean id="runtimeService" factory-bean="processEngine"
      factory-method="getRuntimeService"/>
<!-- 任务管理service -->
<bean id="taskService" factory-bean="processEngine"
      factory-method="getTaskService"/>
<!-- 历史管理service -->
<bean id="historyService" factory-bean="processEngine"
      factory-method="getHistoryService"/>

<!-- 事务管理器 -->
<bean id="transactionManager"
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource"/>
</bean>

<!-- 通知 -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
  <tx:attributes>
    <!-- 传播行为 -->
    <tx:method name="save*" propagation="REQUIRED"/>
    <tx:method name="insert*" propagation="REQUIRED"/>
    <tx:method name="delete*" propagation="REQUIRED"/>
    <tx:method name="update*" propagation="REQUIRED"/>
    <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
    <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
  </tx:attributes>
</tx:advice>

<!-- 切面，根据具体项目修改切点配置 -->
<aop:config proxy-target-class="true">
  <aop:advisor advice-ref="txAdvice"
               pointcut="execution(* com.enhao.learning.in.activiti.spring.service.impl.*.*(..))"/>
</aop:config>
```

在和Spring进行整合时，使用的`ProcessEngineConfiguration`是`SpringProcessEngineConfiguration`。