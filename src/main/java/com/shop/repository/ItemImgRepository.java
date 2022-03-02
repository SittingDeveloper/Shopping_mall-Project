package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long > {

    // 이미지가 잘 저장됬는지 테스트 코드를 작성하기 위한 인터페이스
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    // 상품의 대표 이미지를 찾는 쿼리 메소드
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);

}
