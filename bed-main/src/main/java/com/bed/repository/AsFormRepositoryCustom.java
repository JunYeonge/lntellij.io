package com.bed.repository;

import com.bed.dto.AsFormSearchDto;
import com.bed.entity.AsForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AsFormRepositoryCustom {
    Page<AsForm> getAdminAsPage(AsFormSearchDto asFormSearchDto, Pageable pageable);
}
