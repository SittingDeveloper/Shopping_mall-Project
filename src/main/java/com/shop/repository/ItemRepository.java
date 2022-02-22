package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    /*
    JPARepository 를 상속받는 ItemRepository.
    2개의 Generic 중 첫 번째에는 Entity Type Class, 두 번째에는 Primary Key Type 을 넣어준다.
    JpaRepository 에는 기본적인 CRUD 및 패키징 처리를 위한 Method 정의가 이미 되어있다.
     */
    List<Item> findByItemNm(String itemNm); // 이름으로 조회

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 이름 or 상세설명 조회

    List<Item> findByPriceLessThan(Integer price); // 파라미터로 넘어온 price 보다 값이 작은 상품 데이터 조회

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc ")
        // %:itemDetail 안에 @Param 뒤에 쓰인 변수 값을 집어넣음
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}