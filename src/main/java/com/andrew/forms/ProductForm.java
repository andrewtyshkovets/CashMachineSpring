package com.andrew.forms;

import com.andrew.models.Measure;
import lombok.Data;


@Data
public class ProductForm {
    private Integer code;
    private String name;
    private Double amount;
    private Measure measure;
    private Double price;
}
