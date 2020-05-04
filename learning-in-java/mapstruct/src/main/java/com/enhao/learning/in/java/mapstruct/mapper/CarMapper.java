package com.enhao.learning.in.java.mapstruct.mapper;

import com.enhao.learning.in.java.mapstruct.dto.CarDto;
import com.enhao.learning.in.java.mapstruct.dto.PersonDto;
import com.enhao.learning.in.java.mapstruct.model.Car;
import com.enhao.learning.in.java.mapstruct.model.Person;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author enhao
 */
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings(value = {
            @Mapping(source = "make", target = "manufacturer"),
            @Mapping(source = "numberOfSeats", target = "seatCount"),
            // int -> String 通过DecimalFormat#format进行转换
            // 注意不能缺少target，官方文档里没有写，不写会报错。
            @Mapping(source = "prize", numberFormat = "$#.00", target = "prize"),
            // 大数 -> String
            @Mapping(source = "power", numberFormat = "#.##E0", target = "power"),
            // Date -> String
            @Mapping(source = "manufacturingDate", dateFormat = "dd.MM.yyyy", target = "manufacturingDate"),
            // LocalDateTime -> String
            @Mapping(source = "makeLocalDateTime", dateFormat = "yyyy-MM-dd HH:mm:dd", target = "makeLocalDateTime"),
            // String -> LocalDateTime
            @Mapping(source = "makeTimeString", dateFormat = "yyyy-MM-dd HH:mm:dd", target = "makeTime")
    })
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);

    @IterableMapping(numberFormat = "$#.00")
    List<String> prices(List<Integer> prices);
}
