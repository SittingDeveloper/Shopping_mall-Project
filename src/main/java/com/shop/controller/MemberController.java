package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new") // MemberFormDto 값을 memberForm.html 에서 입력
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    /*
    검증하려는 객체의 앞에 @Valid Annotation 을 선언하고, 파라미터로 bindingResult 객체를 추가한다.
    검사후 결과는 bindingResult 에 담아준다. bindingResult.hasErrors() 를 호출하여 에러가 있다면 회원 가입 페이지로 이동한다.
     */
    @PostMapping(value = "/new") // 입력된 값을 통해 member 생성 후 저장
    public String newMember(@Valid MemberFormDto memberFormDto,
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage()); // 회원가입시 중복회원가입 예외가 발생하면 메시지를 뷰로 전달.
            return "member/memberForm";
        }
        return "redirect:/";
    }
}
