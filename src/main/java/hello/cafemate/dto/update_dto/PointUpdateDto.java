package hello.cafemate.dto.update_dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PointUpdateDto {
    private Integer amount;
    private ZonedDateTime savedDate;

    public PointUpdateDto(){}
}
