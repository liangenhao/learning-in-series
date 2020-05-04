package com.enhao.learning.in.java.mapstruct.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author enhao
 */
@Data
@Accessors(chain = true)
public class Person {

    private String name;
}
