package hello.cafemate.dto.simple_dto;

import hello.cafemate.domain.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDto {
    private Long customerId;
    private int quantity;
    private int payments;

    private int usePointAmount;
    private boolean isComplete;
    private LocalDateTime orderDate;
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    public OrderDto(int quantity, int payments,
                    int usePointAmount, boolean isComplete, LocalDateTime orderDate) {
        this.quantity = quantity;
        this.payments = payments;
        this.usePointAmount = usePointAmount;
        this.isComplete = isComplete;
        this.orderDate = orderDate;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderMenuList(List<OrderMenu> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }
}
