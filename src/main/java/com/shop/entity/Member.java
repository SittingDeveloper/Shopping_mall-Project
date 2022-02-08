package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member { // 회원정보를 저장하는 Member Entity

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true) // 이메일을 통한 구분을 위해 동일한 값이 DB에 들어올 수 없도록 unique 속성 부여
    private String email;

    private String password;

    private String address;

    /*
    자바의 enum 타입을 엔티티의 속성으로 지정.
    Enum 을 사용하면 기본적으로 순서가 저장, enum 의 순서가 바뀔 경우 문제가 발생 할 수 있으므로 "EnumType.String"
    옵션을 사용해서 String 으로 저장하는 것을 권장.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        //Spring Security 설정 클래스에 등록한 BCryptoPasswordEncoder Bean을 파라미터로 넘겨서 비밀번호를 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
