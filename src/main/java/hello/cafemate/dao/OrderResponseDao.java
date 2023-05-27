package hello.cafemate.dao;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class OrderResponseDao {
    private Long orderId;
    private Integer amount;
    private Timestamp orderDate;

    public OrderResponseDao(Long orderId, Integer amount, Timestamp orderDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderDate = orderDate;
    }
}
