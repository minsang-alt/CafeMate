package hello.cafemate.dto.update_dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderUpdateDto {
    private Integer quantity;
    private Integer payments;

    private Integer usePointAmount;
    private Boolean isComplete;
    private LocalDateTime orderDate;
    public OrderUpdateDto(){}
}
