package hello.cafemate.dto.update_dto;

import hello.cafemate.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MenuUpdateDto {
    private String name;
    private Category category;
    private Integer price;
    private Boolean onSale;
    private LocalDateTime registrationDate;

    public MenuUpdateDto(){}
}
