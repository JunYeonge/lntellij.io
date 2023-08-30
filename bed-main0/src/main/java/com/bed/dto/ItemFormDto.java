package com.bed.dto;

import com.bed.constant.*;
import com.bed.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class ItemFormDto {

    private Long id;

//    @Column(nullable = true)
    private String itemNum;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private Integer price;
    

    private double tax;


    private double pre_tax;

    @NotNull(message = "재고를 입력해주세요")
    private Integer stockNum;

    @NotBlank(message = "설명을 입력해 주세요")
    private String itemDetail;

    @NotBlank(message = "브랜드를 입력해 주세요")
    private String brand;

    @NotNull(message = "상품 상태를 입력해주세요")
    private ItemSellStatus itemSellStatus;

    @NotNull(message = "타입을 입력해주세요")
    private Type type;

    @NotNull(message = "색상을 입력해주세요")
    private Color color;
    @NotNull(message = "사이즈을 입력해주세요")
    private Size size;
    @NotNull(message = "배송방법을 입력해주세요")
    private OrderDelivery orderDelivery;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();


    private static ModelMapper modelMapper = new ModelMapper();

//    public Item createItem(){
//        return modelMapper.map(this, Item.class);
//    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

    public void addItemImgId(Long imageId) {
        itemImgIds.add(imageId);
    }

    public void setItemImgIds(List<Long> imageIds) {
        this.itemImgIds = imageIds;
    }
}
