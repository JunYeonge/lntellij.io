package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CoffeeForm {
    private Long id;
    private String name;
    private Integer price;

    public Coffee toEntity() {
        return  new Coffee(id,name,price);
    }
}
