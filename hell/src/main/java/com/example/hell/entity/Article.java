package com.example.hell.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue
    private  Long id;

    @Column
    private  String title;

    @Column
    private  String content;

    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
