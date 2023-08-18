package com.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})  //생성일과 수정일 자동관리
@MappedSuperclass // 다른 엔티티 클래스에서 상속받아 사용할수 있습니다
@Getter
public abstract class BaseEntity extends  BaseTimeEntity{
    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 생성자 정보를 저장

    @LastModifiedBy
    private String modifiedBy; // 수정자 정보를 저장
}
