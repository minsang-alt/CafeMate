package hello.cafemate.dto.simple_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class PointDto {
    private Long customerId;
    private int amount;
    private LocalDateTime savedDate;

    public PointDto(Long customerId, int amount, LocalDateTime savedDate) {
        this.customerId = customerId;
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public PointDto(int amount, LocalDateTime savedDate) {
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
