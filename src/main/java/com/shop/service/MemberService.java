package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/*
비즈니스 로직을 담당하는 서비스 계층에 @Transactional 선언,
로직을 처리하다가 에러가 발생하면 변경된 데이터를 로직을 수행하기 이전 상태로 콜백 시킴
 */
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { // UserDetailsService 구현
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 IllegalException 을 발생시킴
    private void validateDuplicateMember(Member member) { 
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 구현해야할 loadUserByUsername 오버라이딩. 로그인할 유저의 email 을 파라미터로 전달받음.
    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        /*
        UserDetail 을 구현하고 있는 User 객체를 반환해줌.
        User 개체를 생성하기 위해서 생성자로 회원의 이메일, 비밀번호, role 을 파라미터로 넘겨준다.
         */
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
