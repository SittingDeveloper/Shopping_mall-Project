package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /*
    JPARepository 를 상속받는 ItemRepository.
    2개의 Generic 중 첫 번째에는 Entity Type Class, 두 번째에는 Primary Key Type 을 넣어준다.
    JpaRepository 에는 기본적인 CRUD 및 패키징 처리를 위한 Method 정의가 이미 되어있다.
     */
    List<Item> findByItemNm(String itemNm); // 이름으로 조회
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 이름 or 상세설명 조회
    List<Item> findByPriceLessThan(Integer price); // 파라미터로 넘어온 price 보다 값이 작은 상품 데이터 조회

}
