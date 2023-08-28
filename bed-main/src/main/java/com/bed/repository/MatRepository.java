package com.bed.repository;

import com.bed.entity.Mat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.ArrayList;

public interface MatRepository extends JpaRepository<Mat,Long>, QuerydslPredicateExecutor<Mat>,MatRepositoryCustom {
    @Override
    ArrayList<Mat> findAll();
}
