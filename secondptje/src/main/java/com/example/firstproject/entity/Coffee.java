package com.example.firstproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer price;

    public Coffee(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


}
