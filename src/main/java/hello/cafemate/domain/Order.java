package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Order {
    private Long id;
    private Long customerId;
    private int quantity;
    private int payments;

    private int usePointAmount;
    private boolean isComplete;
    private Timestamp orderDate;

    private List<OrderMenu> orderMenuList=
            Collections.synchronizedList(new ArrayList<>());

    public Order() {
    }

    public Order(Long id, Long customerId, int quantity, int payments,
                 int usePointAmount, boolean isComplete, Timestamp orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.quantity = quantity;
        this.payments = payments;
        this.usePointAmount = usePointAmount;
        this.isComplete = isComplete;
        this.orderDate = orderDate;
    }

    public Order(Long customerId, int payments,int quantity,
                 int usePointAmount, boolean isComplete, Timestamp orderDate) {
        this.customerId = customerId;
        this.quantity = quantity;
        this.payments = payments;
        this.usePointAmount = usePointAmount;
        this.isComplete = isComplete;
        this.orderDate = orderDate;
    }

    public void addMenu(List<OrderMenu> menuList){
        this.orderMenuList=menuList;
    }

    /**
     * JDBC Template을 이용할때 auto_increment로 설정된 PK 값을 식별자로써
     * 코드 사이드로 받아와서 Entity와 스키마를 일치시키는 방향으로 설정하려면
     * setter를 이용할 수 밖에 없는 구조이다.
     * @param id
     */

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isComplete(){
        return isComplete;
    }
}
