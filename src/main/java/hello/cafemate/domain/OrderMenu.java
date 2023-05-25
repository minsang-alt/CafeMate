package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OrderMenu {
    private Long id;
    private Long orderId;
    private Long menuId;
    private Integer amount;

    public OrderMenu(){}

    public OrderMenu(Long id, Long orderId, Long menuId, Integer amount) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.amount = amount;
    }

    public OrderMenu(Long orderId, Long menuId, Integer amount) {
        this.orderId = orderId;
        this.menuId = menuId;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
