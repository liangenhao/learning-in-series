package com.enhao.learning.in.java.mapstruct.mapper;

import com.enhao.learning.in.java.mapstruct.dto.CarDto;
import com.enhao.learning.in.java.mapstruct.dto.PersonDto;
import com.enhao.learning.in.java.mapstruct.model.Car;
import com.enhao.learning.in.java.mapstruct.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author enhao
 */
@Mapper
public interface CarMapper {

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);
}
