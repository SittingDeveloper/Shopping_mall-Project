package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// Member Entity 를 DB에 저장할 수 있도록 MemberRepository 생성
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원가입시 중복된 회원이 있는지 검사하기 위해서 이메일로 회원을 검사할 수 있도록 쿼리 메소드를 작정
    Member findByEmail(String email);

}