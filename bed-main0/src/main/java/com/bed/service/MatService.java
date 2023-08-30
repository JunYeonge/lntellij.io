package com.bed.service;

import com.bed.dto.ArticleSearchDto;
import com.bed.dto.MatSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Mat;
import com.bed.repository.MatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatService {
    private final MatRepository matRepository;
    public List<Mat> index(){
        return matRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mat> getAdminMat(MatSearchDto matSearchDto){
        return matRepository.getAdminMat(matSearchDto);
    }
}
