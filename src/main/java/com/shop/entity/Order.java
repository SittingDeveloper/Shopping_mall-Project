package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 정렬에 사용하는 order 라는 키워드가 이미 존재하기 때문에 orders 로 변경
@Getter
@Setter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @JoinColumn(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 한명의 회원은 여러번 주문을 할 수 있으므로 주문 엔티티에서 다대일 단방향 매핑

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    /*
    주문 상품 엔티티와 일대다 매핑
    외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem Entity 이다.
    Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정한다.
    속성의 값으로 "order" 를 적어준 이유는 OrderItem 에 있는 Order 에 의해 관리된다는 의미로 해석하면 된다.
    즉, 연관 관계의 주인의 필드인 order 를 mappedBy 의 값으로 세팅하면 된다.

    하나의 주문이 여러개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑을 한다.
     */
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정
    // 고아 객체를 제거하기 위해서 orphanRemoval = true 옵션 사용
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

}