package com.enhao.learning.in.java.mapstruct;

import com.enhao.learning.in.java.mapstruct.dto.CarDto;
import com.enhao.learning.in.java.mapstruct.mapper.CarMapper;
import com.enhao.learning.in.java.mapstruct.model.Car;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author enhao
 */
public class MapperTest {

    /**
     * Mapper工厂的方式
     */
    @Test
    public void testMapperFactory() {
        Car car = new Car();
        car.setMake("make").setNumberOfSeats(10).setPrize(1000).setPower(new BigDecimal(1234567890))
                .setManufacturingDate(new Date()).setMakeLocalDateTime(LocalDateTime.now()).setMakeTimeString("2020-05-04 22:58:04");

        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);
        System.out.println(carDto);

        List<Integer> prizeList = new ArrayList<>();
        prizeList.add(12);
        prizeList.add(120);
        prizeList.add(1200);
        List<String> priceStringList = CarMapper.INSTANCE.prices(prizeList);
        System.out.println(priceStringList);
    }
}
