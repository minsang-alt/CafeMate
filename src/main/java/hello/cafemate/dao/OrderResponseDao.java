package hello.cafemate.dao;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class OrderResponseDao {
    private Long orderId;
    private Integer quantity;
    private Timestamp orderDate;

    public OrderResponseDao(Long orderId, Integer quantity, Timestamp orderDate) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
}
