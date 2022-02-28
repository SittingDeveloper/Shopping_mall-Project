package com.shop.exception;

/*
고객이 주문을 했는데 실제 재고가 없다면 배송을 하지 못하고 결품처리가 되기 때문에 주문 수량 만큼 상품의 재고를 감소.
또한 주문 수량이 현재 재고 수보다 클 경우 주문이 되지 않도록 구현.
 */
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}
