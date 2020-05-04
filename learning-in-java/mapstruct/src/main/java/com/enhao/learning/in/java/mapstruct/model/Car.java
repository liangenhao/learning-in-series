package com.enhao.learning.in.java.mapstruct.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author enhao
 */
@Data
@Accessors(chain = true)
public class Car {

    /**
     * 生产者
     */
    private String make;

    /**
     * 座位数
     */
    private int numberOfSeats;


    /**
     * 价格
     */
    private int prize;

    /**
     * 功率
     */
    private BigDecimal power;

    /**
     * 生产时间
     */
    private Date manufacturingDate;


    private LocalDateTime makeLocalDateTime;

    private String makeTimeString;
}
