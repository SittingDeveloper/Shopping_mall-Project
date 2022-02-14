package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/*
지금까지 생성한 Entity 를 확인해보면 등록시간(regTime), 수정시간(updateTime) 멤버 변수가 공통으로 들어가있다.
실제 서비스를 운영할 때 어떤 사용자가 등록을 했는지 아이디를 남기는데,
데이터를 대용량으로 업데이트 했을 때 다시 업데이트를 해야할 경우 변경된 대상을 찾을 때 활용할 수도 있다.

Audit 의 사전적 정의는 '감시하다'
즉, 엔티티의 생성과 수정을 감시하고 있는 것.
이런 공통 멤버 변수들을 추상 클래스로 만들고, 해당 추상 클래스를 상속받는 형태로 엔티티를 리팩토링.

현재 로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해서 AuditorAware 인터페이스를 구현한 클래스를 생성
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null) {
            // 현재 로그인한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정
            userId = authentication.getName();
        }
        return Optional.of(userId);
    }
}
