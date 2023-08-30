package com.bed.service;

import com.bed.dto.*;
import com.bed.entity.Item;
import com.bed.entity.ItemImg;
import com.bed.repository.ItemDetailBuyRepository;
import com.bed.repository.ItemImgRepository;
import com.bed.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class ItemService {
    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    private final ItemDetailBuyRepository itemDetailBuyRepository;

    private final EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(ItemService.class);;

    public Long saveItem(Item item, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
//        Item item = itemFormDto.createItem();
        itemRepository.save(item);
        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if (i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }


        //itemId에 해당하는 Item을 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        //ItemFormDto.of(item) 호출해서 item 을  itemFormDto로 변환 이때 itemFormDto 변수에 결과를 할당
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        //itemFormDto 의 setItemImgDtoList(itemImgDtoList) 메서드를 사용하여 변환된 ItemImgDto 목록 설정
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        logger.debug("itemImgIds size: {}", itemImgIds.size());
        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
//
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public ItemDetailBuyDto getItemById(Long itemId) {
        ItemDetailBuyDto itemDetailBuyDto = itemDetailBuyRepository.findItemDetailBuyDto(itemId);

        Item item = itemDetailBuyRepository.findById(itemId).orElse(null);
        if (item == null) {
            return null;
        }
        return itemDetailBuyDto;
    }




}





