package com.bed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressFormDto {
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
}
