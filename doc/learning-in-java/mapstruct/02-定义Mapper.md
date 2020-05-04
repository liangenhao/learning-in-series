---
title: 02-定义Mapper
date: 2019-05-31
categories: learning-in-java
tags: [MapStruct]
---

> 版本基于`MapStruct 1.3.0.Final`。
>
> 内容来自于官方文档。

## 定义一个Mapper

```java
public class Car {
 
    private String make;
    private int numberOfSeats;
    private CarType type;
 
    //constructor, getters, setters etc.
}

public class CarDto {
 
    private String make;
    private int seatCount;
    private String type;
 
    //constructor, getters, setters etc.
}
```



### 基本映射

定义一个最简单的`Mapper`：

```java
@Mapper
public interface CarMapper {

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);
}
```

`@Mapper`注解使`MapStruct`代码生成器在构建期间创建`CarMapper`接口的实现。

在生成的方法实现中，源类型（例如`Car`）的所有可读属性将被复制到目标类型（例如`CarDto`）中的响应属性中。

- 如果源类型中的属性和目标类型中的**属性名称相同，可以隐式映射。**
- 如果名称不同时，使用`@Mapping`映射。

### 自定义方法映射

在某些情况下，可能需要手动实现从一种类型到另一种类型的映射。

可以直接在`Mapper`接口中实现自定义方法作为默认方法。如果**参数和返回类型匹配，生成的代码将调用默认方法。**

```java
@Mapper
public interface CarMapper {

    @Mapping(...)
    ...
    CarDto carToCarDto(Car car);

    default PersonDto personToPersonDto(Person person) {
        //hand-written mapping logic
    }
}
```

> `carToCarDto()`生成的实现，如果参数和返回类型匹配，会自动调用`personToPersonDto`方法。

或者采用抽象类的方式：

```java
@Mapper
public abstract class CarMapper {

    @Mapping(...)
    ...
    public abstract CarDto carToCarDto(Car car);

    public PersonDto personToPersonDto(Person person) {
        //hand-written mapping logic
    }
}
```

> 抽象类的优势在于可以声明一些额外的变量。

### 多个源参数的映射

```java
@Mapper
public interface AddressMapper {

    @Mapping(source = "person.description", target = "description")
    @Mapping(source = "address.houseNo", target = "houseNumber")
    DeliveryAddressDto personAndAddressToDeliveryAddressDto(Person person, Address address);
}
```

> 如果多个源对象定义具有相同名称的属性，则必须使用`@Mapping`注解指定从中检索属性的`source`参数。

直接引用源参数

```java
@Mapper
public interface AddressMapper {

    @Mapping(source = "person.description", target = "description")
    @Mapping(source = "hn", target = "houseNumber")
    DeliveryAddressDto personAndAddressToDeliveryAddressDto(Person person, Integer hn);
}
```

### 更新现有的bean实例

通过为目标对象添加参数并使用`@MappingTarget`标记此参数来实现此类映射。

```java
@Mapper
public interface CarMapper {

    void updateCarFromDto(CarDto carDto, @MappingTarget Car car);
}
```

### 直接属性映射

如果字段没有合适的getter/setter方法：

- 如果一个字段是`public`或者`public final`，则该字段被视为读取访问器。
- 如果一个字段是`static`，则不将其视为读取访问器。
- 仅当一个字段是`public`，才视为写访问器。
- 如果一个字段是`final`/`static`/`final static`，不将其视为写访问器。