package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;
/*
    public static ItemImgDto of(ItemImg itemImg) {
        ItemImgDto itemImgDto = new ItemImgDto();

        itemImgDto.setId(itemImg.getId());
        itemImgDto.setImgName(itemImg.getImgName());
        itemImgDto.setOriImgName(itemImg.getOriImgName());
        itemImgDto.setImgUrl(itemImg.getImgUrl());
        itemImgDto.setRepImgYn(itemImg.getRepImgYn());

        return itemImgDto;
    }
*/
    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
    // itemImg 엔티티의 객체를 파라미터로 받아서 itemImg 객체의 자료형과 이름이 같으면
    // ItemImgDto 로 값을 복사해서 반환합니다.
}
