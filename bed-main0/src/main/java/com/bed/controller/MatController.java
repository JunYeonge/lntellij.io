package com.bed.controller;

import com.bed.constant.Age;
import com.bed.constant.Gender;
import com.bed.constant.Height;
import com.bed.constant.Weight;
import com.bed.dto.ArticleSearchDto;
import com.bed.dto.MatSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Mat;
import com.bed.repository.MatRepository;
import com.bed.service.MatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class MatController {
    private final MatRepository matRepository;
    private final MatService matService;



    @GetMapping("/mattress")
    public String index(MatSearchDto matSearchDto,Model model){

//        MatSearchDto matSearchDto = new MatSearchDto();
        matSearchDto.setGender(Gender.M); // 또는 다른 값으로 설정
        matSearchDto.setAge(Age.AGE_230); // 다른 속성들도 설정
        matSearchDto.setHeight(Height.H_170);
        matSearchDto.setWeight(Weight.W_780);


        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("matSearchDto",matSearchDto);
        return "mattress/matList";
    }
    @GetMapping("/mattress/result")
    public String showMatList(@ModelAttribute MatSearchDto matSearchDto, Model model) {
        List<Mat> matList = matService.getAdminMat(matSearchDto);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("matList", matList);
    return "mattress/matResult";
    }
}
