package com.bed.repository;

import com.bed.dto.MatSearchDto;
import com.bed.entity.Mat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatRepositoryCustom {
//    Page<Mat> getAdminMatPage(MatSearchDto matSearchDto, Pageable pageable);
List<Mat> getAdminMat(MatSearchDto matSearchDto);
}
