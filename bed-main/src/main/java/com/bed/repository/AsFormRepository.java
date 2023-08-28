package com.bed.repository;

import com.bed.dto.AsFormSearchDto;
import com.bed.entity.AsForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface AsFormRepository extends JpaRepository<AsForm, Long>, QuerydslPredicateExecutor<AsForm>,AsFormRepositoryCustom {
    @Override
    ArrayList<AsForm> findAll();

//    @Query("select o from AsForm o where o.member.email=:email order by o.regTime desc")
    @Query("select f from AsForm f where f.createdBy = (select m.email from Member m where m.email = :email)")
    List<AsForm> findAsForms(@Param("email")String email, Pageable pageable);
//    @Query("select count(o) from AsForm o where o.member.email=:email")
    @Query("select count(f) from AsForm f where f.createdBy = (select m.email from Member m where m.email = :email)")
    Long countAsForm(@Param("email")String email);
}
