package com.bed.controller;

import com.bed.dto.ItemSearchDto;
import com.bed.dto.MainItemDto;
import com.bed.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;
    @GetMapping(value = "/")
    public String main(Model model) {
        return "main";
    }
    @GetMapping(value = "/story/spring")
    public String spring(Model model) {
        return "story/spring";
    }
    @GetMapping(value = "/story/gallery")
    public String gallery(Model model) {
        model.addAttribute("headerImg","/images/gallery_header.jpg");
        return "story/gallery";
    }
    @GetMapping(value = "/story/sheet")
    public String sheet(Model model) {
        model.addAttribute("headerImg","/images/sheet_header.jpg");
        return "story/sheet";
    }

    @GetMapping(value = "/item/buy")
    public String itemBuy(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("headerImg","/images/header2.jpg");
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemBuy";
    }

}

