---
title: 01-MapStruct介绍和配置
date: 2019-05-30
categories: learning-in-java
tags: [MapStruct]
---

> 版本基于`MapStruct 1.3.0.Final`。
>
> 内容来自于官方文档。

## MapStruct介绍

`MapStruct`是一个Java注释处理器(`annotation processor`)，用于生成类型安全的bean映射类。

我们只需要定义一个`mapper`接口，该接口用来声明需要映射的方法即可。

### MapStruct原理

- 在**编译阶段**，`MapStruct`会**自动生成**我们定义`mapper`接口的实现类。

- 这个实现类使用普通的 Java 方法来调用来自源对象和目标对象的映射。

- 不采用反射。

### MapStruct优点

- 性能高。因为采用普通的方法调用代替反射。
- 编译器类型安全。
- 如果映射不完整（没有所有的目标属性都映射），或者映射不正确（找不到合适的映射方法或类型转换）时，在构建时清楚错误报告。

## MapStruct的配置

### 基本配置

- `org.mapstruct:mapstruct`：包含了必须的注解，例如`@Mapping`。
- `org.mapstruct:mapstruct-processor`：包含了注解处理器，用来生成`mapper`的实现类。

Maven配置如下：

```xml
...
<properties>
    <org.mapstruct.version>1.3.0.Final</org.mapstruct.version>
</properties>
...
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>
...
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.5.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${org.mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
...
```

### 选项配置

可以使用注释处理器选项配置`MapStruct`代码生成器。

直接调用`javac`时，这些选项将以`-Akey = value`的形式传递给编译器。通过`Maven`使用`MapStruct`时，可以使用`Maven`处理器插件配置中的`options`元素传递任何处理器选项，如下所示：

```xml
...
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.5.1</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${org.mapstruct.version}</version>
            </path>
        </annotationProcessorPaths>
        <compilerArgs>
            <compilerArg>
                -Amapstruct.suppressGeneratorTimestamp=true
            </compilerArg>
            <compilerArg>
                -Amapstruct.suppressGeneratorVersionInfoComment=true
            </compilerArg>
        </compilerArgs>
    </configuration>
</plugin>
...
```

存在一下选项：

- `mapstruct.suppressGeneratorTimestamp`
  - 【作用】：如果设置为true，则禁止在生成的映射器类中的`@Generated`注解中创建时间戳。
  - 【默认值】：false
- `mapstruct.suppressGeneratorVersionInfoComment`
  - 【作用】：如果设置为true，则禁止在生成的映射器类中的`@Generated`注解中创建`comment`属性。`comment`包含有关`MapStruct`版本以及用于`annotation processing`的编译器的信息。
  - 【默认值】：false
- `mapstruct.defaultComponentModel`
  - 【作用】：组建模型的名称基于生成的映射器。支持的值：
    1. `default`:映射器不使用组件模型，通常通过`Mappers#getMapper(Class)`检索实例；
    2. `cdi`：生成的映射器是一个应用程序范围的`CDI bean`，可以通过`@Inject`检索；
    3. **`spring`：生成的映射器是单例范围的`Spring bean`，可以通过`@Autowired`检索。**
    4. `jsr330`：生成的映射器使用`@Named`注释，可以通过`@Inject`检索。例如使用spring。
  - 【默认值】：default
  - 【注意】：**如果组建模型使用了`@Mapper#componentModel()`，则优先使用注解设置的值。**
- `mapstruct.unmappedTargetPolicy`
  - 【作用】：未映射的报告策略：
    1. `ERROR`：任何未映射的目标属性都将导致映射代码生成失败。
    2. `WARN`：任何未映射的目标属性都会在构建时发出警告。
    3. `IGNORE`：未映射的目标属性将被忽略。
  - 【默认值】：WARN
  - 【注意】：**如果使用了`@Mapper#unmappedTargetPolicy()`，则优先使用注解设置的值。**



## MapStruct和Lombok集成

【添加依赖】：

1. 添加lombok的依赖
2. 添加lombok-processor

```xml
<properties>
  <org.mapstruct.version>1.3.0.Final</org.mapstruct.version>
  <lombok.version>1.18.12</lombok.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
  </dependency>

  <!-- 引入 lombok 依赖 -->
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
    <scope>provided</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <!-- 提供给 mapstruct 使用 -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.5.1</version> <!-- or newer version -->
      <configuration>
        <source>1.8</source> <!-- depending on your project -->
        <target>1.8</target> <!-- depending on your project -->
        <annotationProcessorPaths>
          <!-- 引入 mapstruct-processor -->
          <path>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${org.mapstruct.version}</version>
          </path>

          <!-- 引入 lombok-processor -->
          <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>
  </plugins>
</build>
```

【使用lombok注解即可】