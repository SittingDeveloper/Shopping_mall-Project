package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    // 하나의 장바구니에는 여러 개의 상품을 담을 수 있으므로 @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 장바구니에 담을 상품의 정보를 알아야 하므로 상품 엔티티를 매핑.

    private int count; // 같은 상품을 장바구니에 몇개 담을지 저장
}
