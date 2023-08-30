package com.bed.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;import javax.persistence.*;


@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity{


    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", updatable = false)
    private String modifiedBy;

     public String getCreatedBy() {
        return this.createdBy;
    }

    public void setModifiedBy(String modifiedBy) {
        // 기존의 modifiedBy 값을 변경하지 않도록 설정
        if (this.modifiedBy == null) {
            this.modifiedBy = modifiedBy;
        }
    }
}
