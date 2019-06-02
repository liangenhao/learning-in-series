---
title: 02-activiti-环境搭建
date: 2019-05-27
categories: Activiti
tags: [Activiti]
---

## acticiti依赖

BOM：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-dependencies</artifactId>
      <version>${activiti.version}</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

依赖：

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
  <java.version>1.8</java.version>
  <maven.compiler.source>1.8</maven.compiler.source>
  <maven.compiler.target>1.8</maven.compiler.target>

  <slf4j.version>1.6.6</slf4j.version>
  <log4j.version>1.2.12</log4j.version>
  <junit.version>4.12</junit.version>
  <mysql.connector.version>5.1.40</mysql.connector.version>
  <mybatis.version>3.4.5</mybatis.version>
  <commons.dbcp.version>1.4</commons.dbcp.version>
</properties>
<dependencies>
  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-engine</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-spring</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-bpmn-model</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-bpmn-converter</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-json-converter</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-bpmn-layout</artifactId>
  </dependency>

  <dependency>
    <groupId>org.activiti.cloud</groupId>
    <artifactId>activiti-cloud-services-api</artifactId>
    <version>${activiti.version}</version>
  </dependency>

  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.connector.version}</version>
  </dependency>

  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>${junit.version}</version>
  </dependency>

  <!-- log start -->
  <dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>${log4j.version}</version>
  </dependency>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
  </dependency>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>${slf4j.version}</version>
  </dependency>
  <!-- log end -->

  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>${mybatis.version}</version>
  </dependency>

  <dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>${commons.dbcp.version}</version>
  </dependency>
</dependencies>
```

## 表结构生成

### 配置文件

在`classpath`下创建`activiti.cgf.xml`。

配置项包括：

- 数据库连接池。

- `processEngineConfiguration`

  > `processEngineConfiguration`用来创建`ProcessEngine`，在创建`ProcessEngine`时会执行数据库的操作。

```xml
<!-- 数据库连接池 -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
  <property name="url" value="jdbc:mysql://localhost:3306/activiti"/>
  <property name="username" value="root"/>
  <property name="password" value="root"/>
</bean>

<!-- 单独运行 processEngine 的配置 -->
<bean id="processEngineConfiguration" class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
  <!-- 数据源 -->
  <property name="dataSource" ref="dataSource"/>
  <!--代表是否生成表结构-->
  <property name="databaseSchemaUpdate" value="true"/>
</bean>
```

> 注意：关于`processEngine`有多种启动方式，这里我们使用的是单独运行的配置。即`org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration`。

> 关于`databaseSchemaUpdate`的值：
>
> - `false`（默认）：检查数据库表的版本和依赖库的版本，如果版本不匹配则抛出异常。
> - `true`：构建流程引擎时，执行检查，如果需要就执行更新。如果表不存在，就创建。
> - `create-drop`：构建流程引擎时，创建数据库表，关闭流程引擎时，删除这些表。
> - `drop-create`：先删除表，再创建表。
> - `create`：构建流程引擎时，创建数据库表，关闭流程引擎时，不删除这些表。

#### 生成表结构

```java
// 1. 创建 ProcessEngineConfiguration 对象
ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

// 2. 创建 ProcessEngine 对象
configuration.buildProcessEngine();
```

注意点：

1. 在配置文件中，我们配置了名称为`processEngineConfiguration`的bean。**注意这里的`processEngineConfiguration`是默认的bean名称。**

   如果不使用该bean名称，加载时可以使用`ProcessEngineConfiguration.createProcessEngineConfigurationFromResource()`的重载方法。指定具体的beanName即可。

## 数据表的命名规则

activiti 的表的命名规则：

- 以`ACT_`开头。
- 表的用途：
  - `ACT_RE_*`：RE 表示 `repository`。包含了**流程定义和流程静态资源**（图片，规则等）。
  - `ACT_RU_*`：RU 表示`runtime`。运行时的表。包含**流程实例，任务，变量，异步任务** 等运行中的数据。activiti 只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录。
  - `ACT_HI_*`：HI 表示 `history`。包括历史数据，包括**历史流程实例，变量，任务** 等。
  - `ACT_GE_*`：GE 表示`general`。**通用数据。**

