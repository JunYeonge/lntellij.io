package com.bed.repository;

import com.bed.entity.AsFormImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsFormImgRepository extends JpaRepository<AsFormImg, Long> {
    AsFormImg findByAsFormId(Long asFormId);
}
