package com.bed.controller;

import com.bed.dto.ItemDetailBuyDto;
import com.bed.dto.ItemFormDto;
import com.bed.dto.ItemSearchDto;
import com.bed.dto.MainItemDto;
import com.bed.entity.Item;
import com.bed.service.ItemImgService;
import com.bed.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    @PersistenceContext
    private EntityManager entityManager;


    private final ItemService itemService;

    private final ItemImgService itemImgService;

    // --------------상품 등록--------------
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }


//------------아이템저장페이지
@PostMapping(value = "/admin/item/new")
public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                      Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
    if (bindingResult.hasErrors()) {
        return "item/itemForm";
    }
    if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
        model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
        return "item/itemForm";
    }

    // 아이템 번호 생성
    String itemNum = generateItemNumber();

    // 아이템 정보 설정
    Item item = new Item();
    item.setItemNum(itemNum);
    item.setItemNm(itemFormDto.getItemNm());
    item.setPrice(itemFormDto.getPrice());
    item.setTax(itemFormDto.getTax());
    item.setPre_tax(itemFormDto.getPre_tax());
    item.setStockNum(itemFormDto.getStockNum());
    item.setItemDetail(itemFormDto.getItemDetail());
    item.setBrand(itemFormDto.getBrand());
    item.setType(itemFormDto.getType());
    item.setColor(itemFormDto.getColor());
    item.setSize(itemFormDto.getSize());
    item.setOrderDelivery(itemFormDto.getOrderDelivery());
    item.setItemSellStatus(itemFormDto.getItemSellStatus());
    item.setUpdateTime(LocalDateTime.now());

    try {
        itemService.saveItem(item, itemImgFileList);
    } catch (Exception e) {
        model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
        return "item/itemForm";
    }

    return "redirect:/";
}

    private String generateItemNumber() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    // --------------상세 페이지--------------
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }
///   ------------- 아이템 수정페이지-------------------------
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        model.addAttribute("headerImg","/images/header.jpg");
        return "item/itemMng";
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemChange(@PathVariable(value = "itemId", required = false) Long itemId, Model model) {
        model.addAttribute("headerImg", "/images/header.jpg");

        if (itemId == null) {
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }


//    @PostMapping(value = "/admin/item/{itemId}")
//    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
//        model.addAttribute("headerImg","/images/header.jpg");
//
////        int uploadedImageCount = itemImgFileList.size();
//        List<Long> imageIds = itemImgService.getImageIdsForItem();
////        int imageIdCount = itemFormDto.getItemImgIds().size();
////
////        // 로그 기록
////        logger.info("업로드된 이미지 개수: {}", uploadedImageCount);
////        logger.info("이미지 ID 개수: {}", imageIdCount);
//
//        for (Long imageId : imageIds) {
//            itemFormDto.addItemImgId(imageId);
//        }
//        itemFormDto.setItemImgIds(imageIds);
//
//
//        if (bindingResult.hasErrors()) {
//            return "item/itemForm";
//        }
//
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }
//
////        if (itemFormDto.getId() != null && itemImgFileList.size() != itemFormDto.getItemImgIds().size()) {
////            // 이미 등록된 상품 수정 시 이미지 개수가 일치하지 않는 경우
////            model.addAttribute("errorMessage", "상품 이미지 개수가 일치하지 않습니다.");
////            return "item/itemForm";
////        }
//
//        try {
//            itemService.updateItem(itemFormDto, itemImgFileList);
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
//            return "item/itemForm";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
//            e.printStackTrace(); // 에러 로그 출력 (필요에 따라 제거 가능)
//            return "item/itemForm";
//        }
//
//        return "redirect:/";
//    }
@PostMapping(value = "/admin/item/{itemId}")
public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                         @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
    model.addAttribute("headerImg","/images/header.jpg");
    if(bindingResult.hasErrors()){


        return "item/itemForm";
    }

    if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
        model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
        return "item/itemForm";
    }

    try {
        itemService.updateItem(itemFormDto, itemImgFileList);
    } catch (Exception e){
        model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
        return "item/itemForm";
    }

    return "redirect:/";
}

    //-------------------아이템 삭제페이지-------------
    @Transactional
    @PostMapping(value = "/item/delete/{itemId}")
    public String itemDelete(@PathVariable("itemId") Long itemId){
        itemImgService.deleteItemImg(itemId);

        String queryString = "DELETE FROM Item i WHERE i.id = :itemId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("itemId", itemId);
        query.executeUpdate();

        return "redirect:/";
    }
    //------------------아이템 선택---------------
    @GetMapping("/item/buy/{itemId}")
    @ResponseBody
    public ResponseEntity<ItemDetailBuyDto> getItemById(@PathVariable Long itemId) {
        ItemDetailBuyDto itemDetailBuyDto = itemService.getItemById(itemId);
        if (itemDetailBuyDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(itemDetailBuyDto);
    }

    @GetMapping(value = {"/items/search","/items/search/{page}"})
    public String itemBuy(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("headerImg","/images/header2.jpg");
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemBuy";
    }

}

