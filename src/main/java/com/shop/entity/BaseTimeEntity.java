package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
    보통 테이블에 등록일, 수정일, 등록자, 수정자를 모두 다 넣어주지만
    어떤 테이블은 등록자, 수정자를 넣지 않는 테이블도 있을 수 있다.
    그런 엔티티는 BaseTimeEntity 만 상속받을 수 있도록 BaseTimeEntity 클래스를 생성
     */

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 을 적용하기 위해서 @EntityListeners 어노테이션을 추가
@MappedSuperclass // 공통 매핑 정보가 필요할 떄 사용하는 어노테이션으로 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공한다.
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장한다.
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // 엔티티의 값을 변경할 때 시간을 자동으로 저장한다.
    private LocalDateTime updateTime;

}
