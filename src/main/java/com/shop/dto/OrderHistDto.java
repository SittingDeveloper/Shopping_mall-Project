package com.shop.dto;

import com.shop.constant.OrderStatus;
import com.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class OrderHistDto { // 주문 정보를 담을 orderHistDto

    private Long orderId; // 주문 아이디
    private String orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태

    /*
     OrderHistDto 클래스의 생성자로 order 객체를 파라미터로 받아서 멤버 변수 값을 세팅.
     주문 날짜의 경우 화면에 "yyyy-MM-dd HH-mm" 형태로 전달하기 위해서 포맷을 수정
     */
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    // orderItemDto 객체를 주문 상품 리스트에 추가하는 메소드
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }

}
