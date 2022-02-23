package com.shop.repository;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    /*
    상품 정보를 담고있는 itemSearchDto 객체와 페이징 정보를 담고있는 Pageable 객체를 파라미터로 받는 메소드 정의.
    반환 데이터로 Page<item> 객체를 반환
     */
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
