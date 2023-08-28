package com.bed.dto;

import com.bed.constant.Age;
import com.bed.constant.Gender;
import com.bed.constant.Height;
import com.bed.constant.Weight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatSearchDto {
    private Gender gender;
    private Age age;
    private Height height;
    private Weight weight;

}
