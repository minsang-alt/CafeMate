package hello.cafemate.dto.simple_dto;

import hello.cafemate.domain.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuDto {
    private String name;
    private Category category;
    private int price;
    private boolean onSale;
    private LocalDateTime registrationDate;

    public MenuDto(String name, Category category,
                   int price, boolean onSale, LocalDateTime registrationDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.onSale = onSale;
        this.registrationDate = registrationDate;
    }
}
