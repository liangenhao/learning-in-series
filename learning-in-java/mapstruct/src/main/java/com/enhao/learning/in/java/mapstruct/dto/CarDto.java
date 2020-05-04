package com.enhao.learning.in.java.mapstruct.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author enhao
 */
@Data
@Accessors(chain = true)
public class CarDto {

    /**
     * 生产者
     */
    private String manufacturer;

    /**
     * 座位数
     */
    private int seatCount;

    /**
     * 价格
     */
    private String prize;

    /**
     * 功率
     */
    private String power;

    /**
     * 生产时间
     */
    private String manufacturingDate;

    private String makeLocalDateTime;

    private LocalDateTime makeTime;
}
