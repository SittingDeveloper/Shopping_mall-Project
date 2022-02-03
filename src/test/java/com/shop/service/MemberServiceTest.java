package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Test Class 에 @Transactional Annotation 을 선언할 경우 테스트 실행 후 자동 롤백처리. 같은 메소드를 반복적으로 테스트 할 수 있다.
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원 정보를 입력한 Member Entity 를 만드는 Method
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveMemberTest() {
        Member member = createMember(); // 멤버 생성
        Member saveMember = memberService.saveMember(member); // 저장된 멤버

        assertEquals(member.getEmail(), saveMember.getEmail());
        assertEquals(member.getName(), saveMember.getName());
        assertEquals(member.getAddress(), saveMember.getAddress());
        assertEquals(member.getPassword(), saveMember.getPassword());
        assertEquals(member.getRole(), saveMember.getRole());

        System.out.println("member : " + member.getRole());
        System.out.println("saveMember : " + saveMember.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        // Junit 의 Assertions 클래스의 assertThrows 메소드를 이용하면 예외 처리 테스트가 가능.
        // 첫 번째 파라미터에는 발생할 예외 타입을 넣는다.
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);});

        // 발생한 예외 메시지가 예상 결과와 맞는지 검증.
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}