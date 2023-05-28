package hello.cafemate.dto.update_dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderUpdateDto {
    private Integer quantity;
    private Integer payments;

    private Integer usePointAmount;
    private Boolean isComplete;
    private Timestamp orderDate;
    public OrderUpdateDto(){}
}
