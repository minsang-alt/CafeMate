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

    public OrderMenu(){}

    public OrderMenu(Long id, Long orderId, Long menuId) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
    }

    public OrderMenu(Long orderId, Long menuId) {
        this.orderId = orderId;
        this.menuId = menuId;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
