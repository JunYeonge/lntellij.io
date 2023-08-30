package com.bed.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="address")
@Getter
@Setter
public class Address {

//    @Column(name="address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    @OneToOne(mappedBy = "address")
    private Member member;

    @Column
    private String merge_address;
}
