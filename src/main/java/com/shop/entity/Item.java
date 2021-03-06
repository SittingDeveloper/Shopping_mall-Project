package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
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
public class Item extends BaseEntity{

    @Column(name = "item_id") // Item 클래스의 id 컬럼 || Item 테이블의 item_id 컬럼 (Mapping)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Primary Key Setting, Type = Auto
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태, Enum Class

    /*private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정시간*/

    /*
    상품 데이터를 업데이트하는 로직.
    Entity Class 에 비즈니스 로직을 추가하면 좀더 객체지향적으로 코딩이 가능하고
    코드를 재활용 할 수 있음.
     */
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    // 상품을 주문할 경우 상폼의 재고를 감소시키는 로직
    public void removeStock(int stockNumber) {

        // 상품의 재고 수량에서 주문 후 남은 재고 수량을 구함
        int restStock = this.stockNumber - stockNumber;

        // 상품의 재고가 주문 수량보다 작을 경우 재고 부족 예외를 발생
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다." +
                    "(현재 재고 수량 : " + this.stockNumber + ")");
        }

        // 주문후 남은 재고 수량을 상품의 현재 재고 값으로 할당
        this.stockNumber = restStock;

    }

    // 상품의 재고를 증가시키는 메소드 ( ex 주문을 취소할 경우
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }

}
