---
title: 03-获取Mapper对象
date: 2019-06-01
categories: learning-in-java
tags: [MapStruct]
---

> 版本基于`MapStruct 1.3.0.Final`。
>
> 内容来自于官方文档。

## 获取一个Mapper

#### Mappers工厂

`mapper`接口中应该定义一个名为`INSTANCE`的成员，该成员包含映射器类型的单个实例：

```java
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    CarDto carToCarDto(Car car);
}
```

获取`mapper`对象：

```java
Car car = ...;
CarDto dto = CarMapper.INSTANCE.carToCarDto( car );
```

### 使用依赖注入

通常我们都是用Spring框架，因此可以通过依赖注入的方式获取`mapper`对象。

通过指定`@Mapper#componentModel`。

> 详见 [MapStruct介绍和配置](01-MapStruct介绍和配置.md) 中的选项配置。
>
> `componentModel`属性的允许值与`mapstruct.defaultComponentModel`处理器选项相同。

```java
@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto carToCarDto(Car car);
}
```

获取`mapper`对象：

```java
@Autowired
private CarMapper mapper;
```

