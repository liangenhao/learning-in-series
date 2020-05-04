package com.enhao.learning.in.java.mapstruct.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author enhao
 */
@Data
@Accessors(chain = true)
public class PersonDto {

    private String fullName;

}
