package com.bed.dto;

import com.bed.constant.Color;
import com.bed.constant.Size;
import com.bed.constant.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDetailBuyDto {

    private Long Id; // 아이템Id

    private Type type; //타입

    private Color color; //색상

    private Size size; // 사이즈

    public ItemDetailBuyDto(Long id, Type type, Color color, Size size){
        this.Id = id;
        this.type = type;
        this.color = color;
        this.size = size;
    }
}
