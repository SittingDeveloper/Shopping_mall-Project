package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /*
    JPARepository 를 상속받는 ItemRepository.
    2개의 Generic 중 첫 번째에는 Entity Type Class, 두 번째에는 Primary Key Type 을 넣어준다.
    JpaRepository 에는 기본적인 CRUD 및 패키징 처리를 위한 Method 정의가 이미 되어있다.
     */
}
