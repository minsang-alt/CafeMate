package hello.cafemate.dto.simple_dto;

import hello.cafemate.domain.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class PointDto {
    private Long customerId;
    private int amount;
    private Timestamp savedDate;

    public PointDto(Long customerId, int amount, Timestamp savedDate) {
        this.customerId = customerId;
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public PointDto(int amount, Timestamp savedDate) {
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public PointDto(Point point){
        this.customerId=point.getCustomerId();
        this.amount=point.getAmount();
        this.savedDate=point.getSavedDate();
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
