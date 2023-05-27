package hello.cafemate.web.dto.order;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private Long customerId;
    private String alias;
    private List<String> menuName;
    private Integer amount;
    private Timestamp ordersDate;

    public OrderResponseDto(Long orderId, Long customerId, String alias,
                            List<String> menuName, Integer amount, Timestamp ordersDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.alias = alias;
        this.menuName = menuName;
        this.amount = amount;
        this.ordersDate = ordersDate;
    }
}
