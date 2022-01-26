package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item") // Entity 테이블과 매핑될 테이블의 이름 'item'
@Getter
@Setter
@ToString
public class Item {

    @Column(name = "item_id") // Item 클래스의 id 컬럼 || Item 테이블의 item_id 컬럼 (Mapping)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Primary Key Setting, Type = Auto
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNum; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태, Enum Class

    private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정시간
}
