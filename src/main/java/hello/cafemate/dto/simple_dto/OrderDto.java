package hello.cafemate.dto.simple_dto;

import hello.cafemate.domain.Order;
import hello.cafemate.domain.OrderMenu;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDto {
    private Long customerId;

    private Integer payments;
    private Integer quantity;
    private Integer usePointAmount;
    private boolean isComplete;
    private Timestamp orderDate;
    private List<OrderMenu> orderMenuList = new ArrayList<>();


    public OrderDto( int quantity,int payments,
                    int usePointAmount, boolean isComplete,Timestamp orderDate) {

        this.quantity = quantity;
        this.payments = payments;
        this.usePointAmount = usePointAmount;
        this.isComplete = isComplete;
        if(orderDate==null)
            orderDate = new Timestamp(System.currentTimeMillis());
        this.orderDate = orderDate;
    }


    public OrderDto(Order order){
        this.customerId=order.getCustomerId();
        this.quantity=order.getQuantity();
        this.payments=order.getPayments();
        this.usePointAmount=order.getUsePointAmount();
        this.isComplete= order.isComplete();
        this.orderDate=order.getOrderDate();
        setOrderMenuList(order.getOrderMenuList());
    }


    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderMenuList(List<OrderMenu> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }
}
